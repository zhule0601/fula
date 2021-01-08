package com.fula.demo.component;

import com.fula.demo.uitl.JsonUtils;
import com.google.common.collect.ImmutableList;
import kafka.zk.KafkaZkClient;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.security.JaasUtils;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.utils.Time;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import scala.collection.JavaConversions;
import scala.collection.Seq;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

// refer
// https://github.com/smartloli/kafka-eagle/blob/master/kafka-eagle-core/src/main/java/org/smartloli/kafka/eagle/core/factory/KafkaServiceImpl.java

public class KafkaComponent {
  // kafka server
  private static final String BOOTSTRAP_SERVERS = "xxx:9092";
  // zookeeper server
  private static final String ZOOKEEPER_SERVER = "xxx:2181";
  // kafka version >= 0.10 ?
  public static final boolean NEW_VERSION = true;
  private static final String BROKER_TOPICS_PATH = "/brokers/topics";
  private static KafkaZkClient zkc = null;
  private static AdminClient adminClient = null;

  public static final String topic = "testTopic";
  public static final String consumerGroup = "console-consumer-15314";
  public static final List<Integer> partitions = ImmutableList.of(0);

  @BeforeTest
  public void before() {
    zkc =
        KafkaZkClient.apply(
            ZOOKEEPER_SERVER,
            JaasUtils.isZkSecurityEnabled(),
            3000,
            3000,
            Integer.MAX_VALUE,
            Time.SYSTEM,
            "topic-management-service",
            "SessionExpireListener");
    Properties prop = new Properties();
    prop.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    adminClient = AdminClient.create(prop);
  }

  // 通过 zk 获取 topicList
  @Test
  public void listTopicsTest() {
    List<String> topics = new ArrayList<>();
    if (zkc.pathExists(BROKER_TOPICS_PATH)) {
      Seq<String> subBrokerTopicsPaths = zkc.getChildren(BROKER_TOPICS_PATH);
      topics = JavaConversions.seqAsJavaList(subBrokerTopicsPaths);
    }
    System.out.println(topics);
  }

  // list topic by adminClient
  @Test
  public void listTopic() throws Exception {
    ListTopicsResult listTopicsResult = adminClient.listTopics();
    Set<String> topics = listTopicsResult.names().get();
    System.out.println(JsonUtils.objectToJson(topics));
  }

  // create Topic
  @Test
  public void createTopic() throws Exception {
    NewTopic newTopic = new NewTopic(topic, 1, (short) 1);
    CreateTopicsResult result = adminClient.createTopics(ImmutableList.of(newTopic));
    result.all().get();
  }

  // delete topic
  @Test
  public void deleteTopic() throws Exception {
    DeleteTopicsResult result = adminClient.deleteTopics(ImmutableList.of(topic));
    result.all().get();
  }

  // 消费 topic 内消息
  @Test
  public void consumerMessage() {
    Properties props = new Properties();
    props.setProperty("bootstrap.servers", BOOTSTRAP_SERVERS);
    props.setProperty("group.id", "zhule_test");
    props.setProperty("enable.auto.commit", "true");
    props.setProperty("auto.commit.interval.ms", "1000");
    props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
    consumer.subscribe(ImmutableList.of(topic));
    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
      for (ConsumerRecord<String, String> record : records)
        System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
    }
  }

  @Test
  public void listTopicPartitions() {
    Seq<String> brokerTopicsPaths =
        zkc.getChildren(BROKER_TOPICS_PATH + "/" + topic + "/partitions");
    System.out.println(JavaConversions.seqAsJavaList(brokerTopicsPaths));
  }

  // 调用 kafka client 时, 需要注意 server.properties#listener 的配置, kafka 集群能否被外部访问
  // 本地配置 hosts 或者 listener 配置为 外网 ip
  @Test
  public void listGroups() throws Exception {
    List<String> allGroups =
        adminClient.listConsumerGroups().valid().get(10, TimeUnit.SECONDS).stream()
            .map(ConsumerGroupListing::groupId)
            .collect(Collectors.toList());
    System.out.println(allGroups);
    List<String> groups = new ArrayList<>();
    ListConsumerGroupsResult cgrs = adminClient.listConsumerGroups();
    java.util.Iterator<ConsumerGroupListing> iterator = cgrs.all().get().iterator();
    while (iterator.hasNext()) {
      ConsumerGroupListing gs = iterator.next();
      String groupId = gs.groupId();
      groups.add(groupId);
    }
    System.out.println(groups);
  }

  @Test
  public void listTopicsOnGroup() throws Exception {
    DescribeConsumerGroupsResult descConsumerGroup =
        adminClient.describeConsumerGroups(Collections.singletonList(consumerGroup));
    Collection<MemberDescription> consumerMetaInfos =
        descConsumerGroup.describedGroups().get(consumerGroup).get().members();
    Set<String> hasOwnerTopics = new HashSet<>();
    List<Map<String, Object>> consumerGroups = new ArrayList<>();
    if (consumerMetaInfos.size() < 1) {
      return;
    }
    for (MemberDescription consumerMetaInfo : consumerMetaInfos) {
      Map<String, Object> topicSub = new HashMap<>();
      List<Map<String, Object>> topicSubs = new ArrayList<>();
      for (TopicPartition topic : consumerMetaInfo.assignment().topicPartitions()) {
        Map<String, Object> object = new HashMap<>();
        object.put("topic", topic.topic());
        object.put("partition", topic.partition());
        topicSubs.add(object);
        hasOwnerTopics.add(topic.topic());
      }
      topicSub.put("owner", consumerMetaInfo.consumerId());
      topicSub.put("node", consumerMetaInfo.host().replaceAll("/", ""));
      topicSub.put("topicSub", topicSubs);
      consumerGroups.add(topicSub);
    }
    // 不同分区下不同的 topic name
    System.out.println(JsonUtils.objectToJson(consumerGroups));
    System.out.println(JsonUtils.objectToJson(hasOwnerTopics));
  }

  @Test
  public void getTopicOffset() throws Exception {
    Map<Integer, Long> partitionOffset = new HashMap<>();
    List<TopicPartition> topicPartitions = new ArrayList<>();
    for (Integer partition : partitions) {
      TopicPartition tp = new TopicPartition(topic, partition);
      topicPartitions.add(tp);
    }
    ListConsumerGroupOffsetsOptions consumerOffsetOptions = new ListConsumerGroupOffsetsOptions();
    consumerOffsetOptions.topicPartitions(topicPartitions);
    ListConsumerGroupOffsetsResult offsets =
        adminClient.listConsumerGroupOffsets(consumerGroup, consumerOffsetOptions);
    for (Map.Entry<TopicPartition, OffsetAndMetadata> entry :
        offsets.partitionsToOffsetAndMetadata().get().entrySet()) {
      if (topic.equals(entry.getKey().topic())) {
        partitionOffset.put(entry.getKey().partition(), entry.getValue().offset());
      }
    }
    System.out.println(JsonUtils.objectToJson(partitionOffset));
  }

  @Test
  public void getTopicLogSize() {
    Properties props = new Properties();
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "system-group");
    props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    props.put(
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
    props.put(
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class.getCanonicalName());
    // 新建一个消费者
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
    Set<TopicPartition> tps = new HashSet<>();
    for (int partition : partitions) {
      TopicPartition tp = new TopicPartition(topic, partition);
      tps.add(tp);
    }
    // 重点!!!!
    // 当调用 assign() 方法手动分配 topic-partition 列表时，是不会使用 consumer 的 Group 管理机制，
    // 即是当 consumer group member 变化或 topic 的 metadata 信息变化时是不会触发 reBalance 操作的。
    consumer.assign(tps);
    java.util.Map<TopicPartition, Long> endLogSize = consumer.endOffsets(tps);
    consumer.close();
    System.out.println(endLogSize);
  }

  @AfterTest
  public void after() {
    if (zkc != null) {
      zkc.close();
    }
    if (adminClient != null) {
      adminClient.close();
    }
  }
}

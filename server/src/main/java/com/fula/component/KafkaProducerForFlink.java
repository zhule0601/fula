package com.fula.component;

import com.fula.util.JsonUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class KafkaProducerForFlink {

  // kafka server
  private static final String BOOTSTRAP_SERVERS = "xxx:9092";
  public static final String topic = "testTopic";
  public static final Properties props = new Properties();

  @BeforeTest
  public void init() {
    props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
    props.put("acks", "all");
    props.put("retries", 0);
    props.put("batch.size", 16384);
    props.put("linger.ms", 1);
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
  }

  @Test
  public void produceMessage() throws InterruptedException {
    Producer<String, String> producer = new KafkaProducer<>(props);
    CountDownLatch countDownLatch = new CountDownLatch(2);
    // 模拟生成浏览数据
    new Thread(
            () -> {
              while (true) {
                // 10 位时间戳
                Long currentTime = System.currentTimeMillis() / 1000;
                Map<String, Object> messageMap = new LinkedHashMap<>();
                messageMap.put("time", currentTime);
                messageMap.put("type", "visit");
                messageMap.put("user_id", "user_" + new Random().nextInt(100));
                messageMap.put("custom", getRandomObject());
                String message = JsonUtils.objectToJson(messageMap);
                producer.send(new ProducerRecord<>(topic, message));
                try {
                  Thread.sleep(200);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
            })
        .start();
    countDownLatch.await();
    producer.close();
  }

  private String getRandomObject() {
    Random random = new Random();
    int objectNum = random.nextInt(6);
    switch (objectNum) {
      case 1:
        return CustomObject.customA.name();
      case 2:
        return CustomObject.customB.name();
      case 3:
        return CustomObject.customC.name();
      case 4:
        return CustomObject.customD.name();
      case 5:
        return CustomObject.customE.name();
      case 6:
        return CustomObject.customF.name();
      default:
        return CustomObject.UN_KNOWN.name();
    }
  }

  enum CustomObject {
    customA(7),
    customB(15),
    customC(123),
    customD(79),
    customE(1),
    customF(90),
    UN_KNOWN(99999);

    private double price;

    public double getPrice() {
      return price;
    }

    public void setPrice(double price) {
      this.price = price;
    }

    CustomObject(double price) {
      this.price = price;
    }
  }
}

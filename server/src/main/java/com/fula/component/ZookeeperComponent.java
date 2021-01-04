package com.fula.component;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.springframework.util.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class ZookeeperComponent {

  public static String zkConnectString = null;

  @BeforeTest
  public void initServer() throws Exception {
    // TestingServer server = new TestingServer(2181);
    // zkConnectString = server.getConnectString();
    zkConnectString = "xxx:2181";
  }

  public CuratorFramework initClient() {
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    return CuratorFrameworkFactory.builder()
        .connectString(zkConnectString)
        .retryPolicy(retryPolicy)
        .build();
  }

  @Test
  public void zookeeperTest() throws Exception {
    CuratorFramework client = initClient();
    client.start();
    // 1. 先删除, 再创建 namespace, 再使用 namespace
    String defaultNameSpace = "curator_test_namespace";
    String nameSpaceNode = ZKPaths.makePath(ZKPaths.PATH_SEPARATOR, defaultNameSpace);
    if (client.checkExists().forPath(nameSpaceNode) != null) {
      client.delete().deletingChildrenIfNeeded().forPath(nameSpaceNode);
    }
    client.create().forPath(nameSpaceNode);
    Assert.assertNotNull(client.checkExists().forPath(nameSpaceNode));
    Assert.assertTrue(CollectionUtils.isEmpty(client.getChildren().forPath(nameSpaceNode)));
    client = client.usingNamespace(defaultNameSpace);

    // 2. 创建临时节点
    String tempNode = "tempNode";
    client
        .create()
        .withMode(CreateMode.EPHEMERAL)
        .forPath(ZKPaths.makePath(ZKPaths.PATH_SEPARATOR, tempNode));
    Assert.assertTrue((client.getChildren().forPath(ZKPaths.PATH_SEPARATOR).contains(tempNode)));

    // 3. 创建持久化节点
    String persistNode = "persistNode";
    client.create().forPath(ZKPaths.makePath(ZKPaths.PATH_SEPARATOR, persistNode));
    Assert.assertTrue((client.getChildren().forPath(ZKPaths.PATH_SEPARATOR).contains(persistNode)));

    // 4. 递归创建节点, 注意: zk 中非叶子节点都必须是 持久化节点
    String longNode = "test/test1/test2/test3/test4/test5";
    client
        .create()
        .creatingParentsIfNeeded()
        .forPath(ZKPaths.makePath(ZKPaths.PATH_SEPARATOR, longNode));
    Assert.assertNotNull(
        client.checkExists().forPath(ZKPaths.makePath(ZKPaths.PATH_SEPARATOR, longNode)));

    // 4. 递归创建节点, 注意: zk 中非叶子节点都必须是 持久化节点
    String longTempNode = "testTemp/test1/test2/test3/test4/test5";
    client
        .create()
        .creatingParentsIfNeeded()
        .withMode(CreateMode.EPHEMERAL)
        .forPath(ZKPaths.makePath(ZKPaths.PATH_SEPARATOR, longTempNode));
    Assert.assertNotNull(
        client.checkExists().forPath(ZKPaths.makePath(ZKPaths.PATH_SEPARATOR, longTempNode)));

    // 5. 创建顺序节点
    String uniqueOrderNode = "order-";
    String uniqueOrderNodeId =
        client
            .create()
            .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
            .forPath(ZKPaths.makePath(ZKPaths.PATH_SEPARATOR, uniqueOrderNode));
    System.out.println(uniqueOrderNodeId);
  }

  // 抢锁成功, 则执行 xxx 并释放
  @Test(invocationCount = 50, threadPoolSize = 50)
  public void distributedLock() throws Exception {
    CuratorFramework client = initClient();
    client.start();
    InterProcessMutex lock = new InterProcessMutex(client, "/lockPath");
    if (lock.acquire(1, TimeUnit.SECONDS)) {
      try {
        // do some work inside of the critical section here
        System.out.println("i'm lock success. -- " + Thread.currentThread().getName());
        Thread.sleep(5 * 1000);
      } finally {
        lock.release();
      }
    }
  }

  @Test(invocationCount = 50, threadPoolSize = 50)
  public void distributedLock1() throws Exception {
    CuratorFramework client = initClient();
    client.start();
    String path = "tempPath";
    try {
      client.create().withMode(CreateMode.EPHEMERAL).forPath(ZKPaths.makePath("/", path));
      Thread.sleep(10 * 1000);
    } catch (Exception e) {
      throw e;
    }
  }

  // 50 并发创建 有序节点, 不会重复. 因此可以用于全局唯一 id
  @Test(invocationCount = 50, threadPoolSize = 50)
  public void uniqueSequenceNode() throws Exception {
    CuratorFramework client = initClient();
    client.start();
    String path = "/tmp/seq";
    String seq =
        client
            .create()
            .creatingParentsIfNeeded()
            .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
            .forPath(ZKPaths.makePath("/", path));
    System.out.println("===" + seq);
  }

  // master select
  // 一旦执行完 takeLeadership. curator 就会立即释放 master. 并进行新的一轮选举
  @Test
  public void masterSelect() throws InterruptedException {
    CuratorFramework client = initClient();
    client.start();
    LeaderSelectorListener listener =
        new LeaderSelectorListenerAdapter() {
          @Override
          public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
            System.out.println("i'm lock success. -- " + Thread.currentThread().getName());
          }
        };
    LeaderSelector leaderSelector = new LeaderSelector(client, "/master_select", listener);
    leaderSelector.autoRequeue();
    leaderSelector.start();
    Thread.sleep(10 * 1000);
  }
}

package com.fula.component;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Collections;
import java.util.UUID;

public class RedisComponent {

  public static JedisPool jedisPool;

  @BeforeTest
  public void init() {
    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxTotal(1024);
    config.setMaxIdle(100);
    config.setMaxWaitMillis(10000);
    config.setTestOnBorrow(true);
    config.setTestOnReturn(true);
    jedisPool = new JedisPool(new JedisPoolConfig(), "xxx", 6379);
    Jedis jedis = jedisPool.getResource();
    jedis.set("iphone", "100");
    jedis.close();
  }

  @AfterTest
  public void close() {
    jedisPool.close();
  }

  @Test(invocationCount = 500, threadPoolSize = 10)
  public void setNxEx() throws Exception {
    try (Jedis jedis = jedisPool.getResource()) {
      // 抢占分布式锁同时设置锁过期时间
      String lock = "lock";
      String lockRequestId = UUID.randomUUID().toString();
      String RLock = jedis.set(lock, lockRequestId, "NX", "PX", 10000);
      System.out.println(RLock + "===");
      if ("ok".equalsIgnoreCase(RLock)) {
        System.out.println("lock success");
      } else {
        throw new Exception("lock failure");
      }
      //      // 删除锁, 能否直接删除呢? 需要保证删除的是刚才自己创建的锁, 此处加入了一个 requestId
      //      String script =
      //          "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1])
      // else return 0 end";
      //      Object result =
      //          jedis.eval(
      //              script, Collections.singletonList(lock),
      // Collections.singletonList(lockRequestId));
      //      Assert.assertEquals(Integer.parseInt(result.toString()), 1);
      //      Set<String> keys_new = jedis.keys("*");
      //      Assert.assertFalse(keys_new.contains(lock));
    }
  }

  // 模拟秒杀
  @Test(invocationCount = 1000, threadPoolSize = 10)
  public void ms() throws Exception {
    try (Jedis jedis = jedisPool.getResource()) {
      String uid = Thread.currentThread().getName();
      if (exec(jedis, uid, "iphone")) {
        System.out.println("秒杀成功=" + uid);
      } else {
        throw new Exception("秒杀失败");
      }
    }
  }

  private static boolean exec(Jedis jedis, String uid, String prod) {
    StringBuilder script = new StringBuilder();
    script.append("local goodsKey = KEYS[1];");
    script.append("local goodsNum = redis.call('get', goodsKey);");
    script.append("if goodsNum < '1' then");
    script.append("   return -1;");
    script.append("end;");
    script.append("redis.call('decrBy', goodsKey, '1');");
    script.append("return 1;");
    Object result =
        jedis.eval(
            script.toString(), Collections.singletonList(prod), Collections.singletonList("1"));
    return result.toString().equalsIgnoreCase("1");
  }

  @Test
  public void stringTest() throws InterruptedException {
    try (Jedis jedis = jedisPool.getResource()) {
      String expireKey = jedis.setex("expire", 100, "100s过期时间");
      Assert.assertEquals(expireKey, "OK");
      Thread.sleep(1000 * 5);
      System.out.println("ttl=" + jedis.ttl("expire"));
      System.out.println(jedis.get("expire"));
    }
  }
}

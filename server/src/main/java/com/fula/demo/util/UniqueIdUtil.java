package com.fula.demo.util;

import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

/** 生成唯一Id */
public class UniqueIdUtil {

  // 单机情况, 多线程并发下生成唯一Id
  private static int seqInt = 0;

  private static final int ROTATION = 99999;

  private static final String TIME_PATTERN = "yyyyMMddHHMMss";

  private static ReentrantLock lock = new ReentrantLock();

  /**
   * 由于ROTATION的限制，每秒最多生成99999个不同的uid,否则会重复
   *
   * @return
   */
  public static String getUniqueId() {
    lock.lock();
    try {
      if (seqInt > ROTATION) {
        seqInt = 0;
      }
      return String.format("%s-%04d", System.currentTimeMillis(), seqInt++);
    } finally {
      lock.unlock();
    }
  }
}

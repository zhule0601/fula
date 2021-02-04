package com.fula.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UUIDUtils {

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");

  public static String generateDateUUID() {
    String datePrefix = SIMPLE_DATE_FORMAT.format(new Date());
    String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    return datePrefix + "-" + uuid;
  }
}

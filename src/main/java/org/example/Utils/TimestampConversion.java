package org.example.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author unibeam
 * @date 2022-08-11
 * @description:时间戳格式转换
 */
public class TimestampConversion {

  public static String TS2Date(Long time) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return format.format(time);
  }

  public static Long Date2TS(String timeStr) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date;
    try {
      date = format.parse(timeStr);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    return date.getTime();
  }
}

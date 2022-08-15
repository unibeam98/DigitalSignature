package org.example.Utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;

/**
 * @author unibeam
 * @date 2022-06-27 10:48
 * @description:NTP
 */
public class NTPClient {
  public static String IntTime() {
    String time;
    try {
      NTPUDPClient timeClient = new NTPUDPClient();
      String timeServerUrl = "time.pool.aliyun.com";
      InetAddress timeServerAddress = InetAddress.getByName(timeServerUrl);
      TimeInfo timeInfo = timeClient.getTime(timeServerAddress);
      TimeStamp timeStamp = timeInfo.getMessage().getTransmitTimeStamp();
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      time = dateFormat.format(timeStamp.getDate());
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return time;
  }

  public static Long TimeStampOfMillis() {
    long time = -1;
    try {
      NTPUDPClient timeClient = new NTPUDPClient();
      String timeServerUrl = "time.asia.apple.com";
      InetAddress timeServerAddress = InetAddress.getByName(timeServerUrl);
      TimeInfo timeInfo = timeClient.getTime(timeServerAddress);
      TimeStamp timeStamp = timeInfo.getMessage().getTransmitTimeStamp();
      time = timeStamp.getTime();
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return time;
  }
}

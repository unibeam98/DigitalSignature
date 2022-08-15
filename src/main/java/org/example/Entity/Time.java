package org.example.Entity;

import org.example.Utils.NTPClient;

/**
 * @author unibeam
 * @date 2022-07-25
 * @description:默认获取网络时间，如果没有网络时间则默认为系统时间
 */
public class Time {
  //时间精确到毫秒
  private Long time;

  public Time() {
    time = System.currentTimeMillis();
//    Long NetworkTime = NTPClient.TimeStampOfMillis();
//    if(NetworkTime != -1L){
//      time = NTPClient.TimeStampOfMillis();
//    }else {
//      time = System.currentTimeMillis();
//    }
  }


  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }
}

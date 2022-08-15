package org.example.Utils;

/**
 * @author unibeam
 * @date 2022-06-27 17:03
 * @description:字符串转换
 */
public final class Hex {

  public static final int BYTE_BIT_SIZE = 8;

  /**
   * 用于建立十六进制字符的输出的小写字符数组
   */
  private static final char[] DIGITS_LOWER = {
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
  };

  /**
   * 用于建立十六进制字符的输出的大写字符数组
   */
  private static final char[] DIGITS_UPPER = {
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
  };

  /**
   * @param data byte[]
   * @return 十六进制char[]
   * @author Herman.Xiong
   * @date 2014年5月5日 17:06:52
   */
  public static char[] encodeHex(byte[] data) {
    return encodeHex(data, true);
  }

  /**
   * @param data        byte[]
   * @param toLowerCase true传换成小写格式 ，false传换成大写格式
   * @return 十六进制char[]
   * @author Herman.Xiong
   * @date 2014年5月5日 17:07:14
   */
  public static char[] encodeHex(byte[] data, boolean toLowerCase) {
    return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
  }

  /**
   * @param data     byte[]
   * @param toDigits 用于控制输出的char[]
   * @return 十六进制char[]
   * @author Herman.Xiong
   * @date 2014年5月5日 17:07:31
   */
  protected static char[] encodeHex(byte[] data, char[] toDigits) {
    int l = data.length;
    char[] out = new char[l << 1];
    for (int i = 0, j = 0; i < l; i++) {
      out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
      out[j++] = toDigits[0x0F & data[i]];
    }
    return out;
  }

  /**
   * @param data byte[]
   * @return 十六进制String
   * @date 2014年5月5日 17:07:43
   * @author Herman.Xiong
   */
  public static String encodeHexStr(byte[] data) {
    return encodeHexStr(data, true);
  }

  /**
   * @param data        byte[]
   * @param toLowerCase true 传换成小写格式 ， false 传换成大写格式
   * @return 十六进制String
   * @author Herman.Xiong
   * @date 2014年5月5日 17:08:01
   */
  public static String encodeHexStr(byte[] data, boolean toLowerCase) {
    return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
  }

  /**
   * @param data     byte[]
   * @param toDigits 用于控制输出的char[]
   * @return 十六进制String
   * @author Herman.Xiong
   * @date 2014年5月5日 17:08:15
   */
  protected static String encodeHexStr(byte[] data, char[] toDigits) {
    return new String(encodeHex(data, toDigits));
  }

  /**
   * @param data 十六进制char[]
   * @return byte[]
   * @throws RuntimeException 如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
   * @author Herman.Xiong
   * @date 2014年5月5日 17:08:28
   */
  public static byte[] decodeHex(char[] data) {
    int len = data.length;
    if ((len & 0x01) != 0) {
      throw new RuntimeException("未知的字符");
    }
    byte[] out = new byte[len >> 1];
    for (int i = 0, j = 0; j < len; i++) {
      int f = toDigit(data[j], j) << 4;
      j++;
      f = f | toDigit(data[j], j);
      j++;
      out[i] = (byte) (f & 0xFF);
    }
    return out;
  }

  /**
   * @param ch    十六进制char
   * @param index 十六进制字符在字符数组中的位置
   * @return 一个整数
   * @throws RuntimeException 当ch不是一个合法的十六进制字符时，抛出运行时异常
   * @author Herman.Xiong
   * @date 2014年5月5日 17:08:46
   */
  protected static int toDigit(char ch, int index) {
    int digit = Character.digit(ch, 16);
    if (digit == -1) {
      throw new RuntimeException("非法16进制字符 " + ch + " 在索引 " + index);
    }
    return digit;
  }

  /**
   * @param data byte数组
   * @return String 转换后的字符串
   * @author Herman.Xiong
   * @date 2014年5月5日 17:15:42
   */
  public static String byteToArray(byte[] data) {
    String result = "";
    for (int i = 0; i < data.length; i++) {
      result += Integer.toHexString((data[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3);
    }
    return result;
  }

  public static String toBitStr(byte[] bytes) {
    StringBuilder strBuilder = new StringBuilder();
    for (byte b : bytes) {
      for (int i = BYTE_BIT_SIZE - 1; i >= 0; i--) {
        strBuilder.append(b >> i & 1);
      }
    }
    return strBuilder.toString();
  }

  public static String fromByte(byte b) {
    return ""
        + (byte) ((b >> 7) & 0x1)
        + (byte) ((b >> 6) & 0x1)
        + (byte) ((b >> 5) & 0x1)
        + (byte) ((b >> 4) & 0x1)
        + (byte) ((b >> 3) & 0x1)
        + (byte) ((b >> 2) & 0x1)
        + (byte) ((b >> 1) & 0x1)
        + (byte) ((b) & 0x1);
  }

  public static String fromBytes(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(fromByte(b));
    }
    return sb.toString();
  }
}

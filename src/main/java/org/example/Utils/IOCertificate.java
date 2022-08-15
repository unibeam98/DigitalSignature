package org.example.Utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.example.Entity.PrivateCertificate;
import org.example.Entity.Time;

/**
 * @author unibeam
 * @date 2022-07-25
 * @description:读取文件最后几行，包含文件创建者、签名者、签名时间、签名到期时间等
 */
public class IOCertificate {

  /**
   * @author: unibeam
   * @time: 2022-07-26
   * @description:读取需要验证文件中的证书
   * @return: 证书类
   */
  public static PrivateCertificate readCertificate(File file) {

    String lastLine;
    PrivateCertificate privateCertificate;
    try (ReversedLinesFileReader reader = new ReversedLinesFileReader(file,
        Charset.defaultCharset())) {
      //AES解密
//      lastLine = new String(AESUtils.decrypt(Hex.decodeHex(reader.readLine().toCharArray()),
//          file.getName().getBytes()));
      lastLine = reader.readLine();
      privateCertificate = toCertificate(lastLine);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return privateCertificate;
  }

  /**
   * @author: unibeam
   * @time: 2022-07-26
   * @description:判断证书是否过期
   * @return:
   */
  public static boolean isTimeOut(PrivateCertificate privateCertificate) {
    Long DateLine = privateCertificate.getDatedTime();
    Time NowTime = new Time();
    return DateLine > NowTime.getTime();
  }

  /**
   * @author: unibeam
   * @time: 2022-07-26
   * @description:String转证书类
   * @return:
   */
  public static PrivateCertificate toCertificate(String str) {
    PrivateCertificate privateCertificate = new PrivateCertificate();
    if ("Certificate".equals(str.substring(0, 11))) {
      //获取CreateTime
      String CreateTimeStr = str.substring(str.indexOf("Certificate{CreateTime=") + 23,
          str.indexOf(", SignatureTime="));
      long CreateTime = Long.parseLong(CreateTimeStr);
      privateCertificate.setCreateTime(CreateTime);
      //获取SignatureTime
      String SignatureTimeStr = str.substring(str.indexOf(", SignatureTime=") + 16,
          str.indexOf(", DatedTime="));
      long SignatureTime = Long.parseLong(SignatureTimeStr);
      privateCertificate.setSignatureTime(SignatureTime);
      //获取DatedTime
      String DatedTimeStr = str.substring(str.indexOf(", DatedTime=") + 12,
          str.indexOf(", Creator='"));
      long DatedTime = Long.parseLong(DatedTimeStr);
      privateCertificate.setDatedTime(DatedTime);
      //获取Creator
      String CreatorStr = str.substring(str.indexOf(", Creator='") + 11,
          str.indexOf("', Signer='"));
      privateCertificate.setCreator(CreatorStr);
      //获取Signer
      String SignerStr = str.substring(str.indexOf("', Signer='") + 11, str.indexOf("'}"));
      privateCertificate.setSigner(SignerStr);
    }
    return privateCertificate;
  }

  /**
   * @author: unibeam
   * @time: 2022-07-26
   * @description:对文件写入证书（AES加密）
   * @return:
   */
  public static void WriteCertificate(File file, PrivateCertificate privateCertificate) {
    try {
      BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
      //对证书进行AES加密
      String cer = privateCertificate.toString();
      //对时间戳进行加密
//      String ciperStr = Hex.encodeHexStr(
//          AESUtils.encrypt(privateCertificate.toString().getBytes(), file.getName().getBytes()));
      out.write("\n");
      out.write(cer);
      out.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

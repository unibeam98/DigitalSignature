package org.example.Signature;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.util.Base64;

/**
 * @author unibeam
 * @date 2022-07-29
 * @description:RSA实现加密解密
 */
public class RSAUtil {

  public static final String CHARSET = "UTF-8";
  public static final String RSA_ALGORITHM = "RSA";


  /**
   * @author: unibeam
   * @time: 2022-07-29
   * @param: filename
   * @description: 从Key文件中获得公钥
   * @return: RSAPrivateKey
   */
  public static RSAPublicKey getPublicByFile(String KeyName) throws Exception {
    byte[] keyBytes = Files.readAllBytes(Paths.get(KeyName));
    X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return (RSAPublicKey) kf.generatePublic(spec);
  }

  /**
   * @author: unibeam
   * @time: 2022-07-29
   * @param: filename
   * @description: 从key文件中获取私钥
   * @return: RSAPublicKey
   */
  public static RSAPrivateKey getPrivateKeyByFile(String KeyName)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] keyBytes = Files.readAllBytes(Paths.get(KeyName));
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return (RSAPrivateKey) kf.generatePrivate(spec);
  }

  /**
   * @author: unibeam
   * @time: 2022-07-29
   * @param: String
   * @description: 从证书中获取RSAPublicKey
   * @return: RSAPublicKey
   */
  public static RSAPublicKey getPublicKeyByCertificate(String certificateName)
      throws CertificateException, FileNotFoundException {
    //获取X.509对象工厂
    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    //通过文件流读取证书文件
    X509Certificate cert = (X509Certificate) cf.generateCertificate(
        new FileInputStream(certificateName));
    //获取公钥对象
    return (RSAPublicKey) cert.getPublicKey();
  }

  /**
   * @author: unibeam
   * @time: 2022-07-29
   * @param: String, RSAPublicKey
   * @description: 使用公钥进行加密
   * @return: String
   */
  public static String publicEncrypt(String data, RSAPublicKey publicKey) {
    try {
      Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      return Base64.encodeBase64URLSafeString(
          rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),
              publicKey.getModulus().bitLength()));
    } catch (Exception e) {
      throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
    }
  }

  /**
   * @author: unibeam
   * @time: 2022-07-29
   * @param: String, RSAPrivateKey
   * @description: 使用私钥解密
   * @return: String
   */
  public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
    try {
      Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
          privateKey.getModulus().bitLength()), CHARSET);
    } catch (Exception e) {
      throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
    }
  }

  /**
   * @author: unibeam
   * @time: 2022-07-29
   * @param: String, RSAPrivateKey
   * @description: 使用私钥加密
   * @return: String
   */
  public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
    try {
      Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, privateKey);
      //return Hex.encodeHexStr(cipher.doFinal(data.getBytes()));
      return Base64.encodeBase64URLSafeString(
          rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),
              privateKey.getModulus().bitLength()));
    } catch (Exception e) {
      throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
    }
  }

  /**
   * @author: unibeam
   * @time: 2022-07-29
   * @param: String, RSAPublicKey
   * @description: 使用共钥解密
   * @return: String
   */
  public static String publicDecrypt(String data, RSAPublicKey publicKey) {
    try {
      Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, publicKey);
      //return new String(cipher.doFinal(Hex.decodeHex(data.toCharArray())));
      return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
          publicKey.getModulus().bitLength()), CHARSET);
    } catch (Exception e) {
      throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
    }
  }

  /**
   * @author: unibeam
   * @time: 2022-07-29
   * @param:
   * @description: doFinal
   * @return: byte[]
   */
  private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
    int maxBlock = 0;
    if (opmode == Cipher.DECRYPT_MODE) {
      maxBlock = keySize / 8;
    } else {
      maxBlock = keySize / 8 - 11;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;
    byte[] buff;
    int i = 0;
    try {
      while (datas.length > offSet) {
        if (datas.length - offSet > maxBlock) {
          buff = cipher.doFinal(datas, offSet, maxBlock);
        } else {
          buff = cipher.doFinal(datas, offSet, datas.length - offSet);
        }
        out.write(buff, 0, buff.length);
        i++;
        offSet = i * maxBlock;
      }
    } catch (Exception e) {
      throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
    }
    byte[] resultDatas = out.toByteArray();
    IOUtils.closeQuietly(out);
    return resultDatas;
  }

  /**
   * @author: unibeam
   * @time: 2022-07-29
   * @param: 验证码，数据，证书
   * @description: 通过证书进行验证
   * @return: boolean
   */
  public static boolean verifyByCertificate(String verifyCode, File data, File Certificate)
      throws FileNotFoundException, CertificateException {
    String decodedData = publicDecrypt(verifyCode,
        getPublicKeyByCertificate(Certificate.getPath()));
    String sha256Str = SHA256.getSha256Str(data);
    if (decodedData.equals(sha256Str)) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean verifyByCertificate(File verifyFile, File data, File Certificate)
      throws IOException, CertificateException {
    String verifyCode = new String(Files.readAllBytes(Paths.get(verifyFile.getPath())));
    String decodedData = publicDecrypt(verifyCode,
        getPublicKeyByCertificate(Certificate.getPath()));
    String sha256Str = SHA256.getSha256Str(data);
    if (decodedData.equals(sha256Str)) {
      return true;
    } else {
      return false;
    }
  }
}

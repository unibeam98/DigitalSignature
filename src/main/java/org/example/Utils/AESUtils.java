package org.example.Utils;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * @author unibeam
 * @date 2022-06-27 16:21
 * @description:AES加密和解密类
 */
public class AESUtils {
  // 密钥长度128，192，256
  private static final int KEY_SIZE = 128;
  // 算法名称
  private static final String ALGORITHM = "AES";
  // 随机数生成器
  private static final String RNG_ALGORITHM = "SHA1PRNG";

  /**
   * @author: unibeam
   * @time: 2022-06-27 16:44
   * @description: 生成密钥
   * @return:
   */
  private static SecretKey generaterKey(byte[] key) throws Exception {
    SecureRandom random = SecureRandom.getInstance(RNG_ALGORITHM);
    random.setSeed(key);

    KeyGenerator gen = KeyGenerator.getInstance(ALGORITHM);
    gen.init(KEY_SIZE, random);

    return gen.generateKey();
  }

  /**
   * @author: unibeam
   * @time: 2022-06-27 16:44
   * @description: 加密
   * @return:
   */
  public static byte[] encrypt(byte[] plainBytes, byte[] key) throws Exception {
    // 生成密钥对象
    SecretKey secretKey = generaterKey(key);
    // AES密码器
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    // 初始化
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    // 加密数据，返回密文
    return cipher.doFinal(plainBytes);
  }

  /**
   * @author: unibeam
   * @time: 2022-06-27 16:48
   * @description: 解密
   * @return:
   */
  public static byte[] decrypt(byte[] cipherBytes, byte[] key) throws Exception {
    // 生成密钥对象
    SecretKey secretKey = generaterKey(key);
    // AES密码器
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    // 初始化
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    // 解密数据，返回明文
    return cipher.doFinal(cipherBytes);
  }
}

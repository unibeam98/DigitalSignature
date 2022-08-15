package org.example.Signature;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.example.Utils.Hex;

public class ECDSA {
    private static final String SIGNALGORITHMS = "SHA256withECDSA";
    private static final String ALGROITHM = "EC";
    private static final String SECP256R1 = "secp256r1";

    public static void main(String[] args) throws Exception{
        //获取X.509对象工厂
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        //通过文件流读取证书文件
        X509Certificate cert = (X509Certificate)cf.generateCertificate(new FileInputStream("unibeam.xyz.cer"));
        //获取公钥对象
        PublicKey publicKey = cert.getPublicKey();
        KeyPair keyPair1 = getKeyPair();
        PublicKey publicKey1 = keyPair1.getPublic();
        PrivateKey privateKey1 = keyPair1.getPrivate();

        String publicKeyString = Hex.encodeHexStr(publicKey.getEncoded());
        System.out.println("-----------------公钥--------------------");
        System.out.println(publicKeyString);
        System.out.println("-----------------公钥--------------------");
        //16进制字符串转换为密钥对象
        //PrivateKey privateKey2 = getPrivateKey(privateKey1);
        // 加签名验证
        String data = "hhhh";
        String signECDSA = signECDSA(privateKey1, data);
        System.out.println(signECDSA);
        boolean verifyECDSA = verifyECDSA(publicKey, signECDSA, data);
        System.out.println("验证结果: " +  verifyECDSA);
    }

    /**
     * @author: unibeam
     * @time: 2022-07-26
     * @description:生成密钥对并写入文件
     * @return: a     */
    public static void GenKey() throws Exception {
        KeyPair keyPair1 = getKeyPair();
        PublicKey publicKey1 = keyPair1.getPublic();
        PrivateKey privateKey1 = keyPair1.getPrivate();
        //密钥转16进制字符串
        String publicKey = Hex.encodeHexStr(publicKey1.getEncoded());
        String privateKey = Hex.encodeHexStr(privateKey1.getEncoded());

    }

    /**
     * 加签
     * @param privateKey 私钥
     * @param data 数据
     * @return
     */
    public static String signECDSA(PrivateKey privateKey, String data){
        String result = "";
        try {
            //执行签名
            Signature signature = Signature.getInstance(SIGNALGORITHMS);
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            byte[] sign = signature.sign();
            return Hex.encodeHexStr(sign);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }


    /**
     * 验签
     * @param publicKey 公钥
     * @param signed 签名
     * @param data 数据
     * @return
     */
    public static boolean verifyECDSA(PublicKey publicKey, String signed, String data){
        try {
            //验证签名
            Signature signature = Signature.getInstance(SIGNALGORITHMS);
            signature.initVerify(publicKey);
            signature.update(data.getBytes());
            byte[] hex = Hex.decodeHex(signed.toCharArray());
            return signature.verify(hex);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 从string转private key
     * @param key 私钥的字符串
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception{
        byte[] bytes = DatatypeConverter.parseHexBinary(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGROITHM);
        return keyFactory.generatePrivate(keySpec);
    }


    /**
     * 从string转publicKey
     * @param key 公钥的字符串
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception{
        byte[] bytes = DatatypeConverter.parseHexBinary(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGROITHM);
        return keyFactory.generatePublic(keySpec);
    }


    /**
     * 生成密钥对
     * @return
     * @throws Exception
     */
    public static KeyPair getKeyPair() throws Exception{
        ECGenParameterSpec ecSpec = new ECGenParameterSpec(SECP256R1);
        KeyPairGenerator kf = KeyPairGenerator.getInstance(ALGROITHM);
        kf.initialize(ecSpec, new SecureRandom());
        KeyPair keyPair = kf.generateKeyPair();
        return keyPair;
    }
}

package org.example.Signature;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * sha256加密工具类,已经修改为用流读取，可以对大文件加密
 *
 */
public class SHA256 {
    /**
     * sha256加密,输入目标字符串，输出加密字符串
     *
     * @param file
     * @return
     */
    public static String getSha256Str(File file){
        MessageDigest messageDigest;
        FileInputStream fileInputStream = null;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1048576];
            int length = 0;
            while ((length = fileInputStream.read(buffer)) != -1){
                messageDigest.update(buffer, 0 , length);
            }
            encodeStr = byte2Hex(messageDigest.digest());
        }catch (Exception e){
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * sha256加密 将byte转为16进制
     *
     * @param bytes 字节码
     * @return 加密后的字符串
     */
    public static String byte2Hex(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (byte aByte : bytes){
            temp = Integer.toHexString(aByte & 0xFF);
            if(temp.length() == 1){
                //进行补0操作
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }

}

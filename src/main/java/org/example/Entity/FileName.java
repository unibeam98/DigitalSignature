package org.example.Entity;

import java.io.File;

/**
 * @author unibeam
 * @date 2022-08-10
 * @description:处理数据实体
 */
public class FileName {

  private File Data;

  private File PrivateKey;

  private File Certificate;

  private File VerifyFile;

  private String VerifyData;

  public String getVerifyData() {
    return VerifyData;
  }

  public void setVerifyData(String verifyData) {
    VerifyData = verifyData;
  }

  public File getData() {
    return Data;
  }

  public void setData(File data) {
    Data = data;
  }

  public File getPrivateKey() {
    return PrivateKey;
  }

  public void setPrivateKey(File privateKey) {
    PrivateKey = privateKey;
  }

  public File getCertificate() {
    return Certificate;
  }

  public void setCertificate(File certificate) {
    Certificate = certificate;
  }

  public File getVerifyFile() {
    return VerifyFile;
  }

  public void setVerifyFile(File verifyFile) {
    VerifyFile = verifyFile;
  }
}

package org.example.Entity;

/**
 * @author unibeam
 * @date 2022-07-25
 * @description:签名证书信息
 */
public class PrivateCertificate {

  Time time = new Time();
  //创建时间
  private Long CreateTime;
  //签名时间
  private Long SignatureTime = time.getTime();
  //到期时间
  private Long DatedTime = 1661788812345L;
  //创建者
  private String Creator = "中国科学院";
  //签名者
  private String Signer = "中国科学院软件研究所";


  public Long getCreateTime() {
    return CreateTime;
  }

  public void setCreateTime(Long createTime) {
    CreateTime = createTime;
  }

  public Long getSignatureTime() {
    return SignatureTime;
  }

  public void setSignatureTime(Long signatureTime) {
    SignatureTime = signatureTime;
  }

  public Long getDatedTime() {
    return DatedTime;
  }

  public void setDatedTime(Long datedTime) {
    DatedTime = datedTime;
  }

  public String getCreator() {
    return Creator;
  }

  public void setCreator(String creator) {
    Creator = creator;
  }

  public String getSigner() {
    return Signer;
  }

  public void setSigner(String signer) {
    Signer = signer;
  }

  @Override
  public String toString() {
    return "Certificate{" +
        "CreateTime=" + CreateTime +
        ", SignatureTime=" + SignatureTime +
        ", DatedTime=" + DatedTime +
        ", Creator='" + Creator + '\'' +
        ", Signer='" + Signer + '\'' +
        '}';
  }

}

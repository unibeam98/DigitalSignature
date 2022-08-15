package org.example;

import static org.example.Signature.RSAUtil.getPrivateKeyByFile;
import static org.example.Signature.RSAUtil.privateEncrypt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import org.example.Entity.FileName;
import org.example.Entity.PrivateCertificate;
import org.example.GUI.ChooseFile;
import org.example.GUI.Label;
import org.example.GUI.NewFrame;
import org.example.Signature.RSAUtil;
import org.example.Signature.SHA256;
import org.example.Utils.IOCertificate;
import org.example.Utils.TimestampConversion;

/**
 * @author unibeam
 * @date 2022-07-25
 * @description:主函数测试接口
 */
public class main {

  public static void main(String[] args) {
    FileName fileName = new FileName();

    JFrame frame = new JFrame("数字签名");
    frame.setSize(600, 400);
    frame.setLocation(200, 200);
    frame.setLayout(new FlowLayout());

    //验证部分GUI
    JPanel p1 = new JPanel();
    p1.setBounds(50, 50, 300, 60);
    p1.setLayout(new FlowLayout());

    JFileChooser fileChooserData = ChooseFile.SelectFile(frame, ".txt");

    JButton btnOpenData = new JButton("打开数据文件");
    btnOpenData.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int returnVal = fileChooserData.showOpenDialog(frame);
        File Datafile = fileChooserData.getSelectedFile();
        fileName.setData(Datafile);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          JOptionPane.showMessageDialog(frame, "计划打开数据文件:" + Datafile.getAbsolutePath());
        }
      }
    });

    JFileChooser fileChooserCer = ChooseFile.SelectFile(frame, ".cer");

    JButton btnOpenCer = new JButton("打开证书文件");
    btnOpenCer.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int returnVal = fileChooserCer.showOpenDialog(frame);
        File CerFile = fileChooserCer.getSelectedFile();
        fileName.setCertificate(CerFile);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          JOptionPane.showMessageDialog(frame, "计划打开签名文件：" + CerFile.getAbsolutePath());
        }
      }
    });

    JFileChooser fileChooserVerify = ChooseFile.SelectFile(frame, "");
    JButton btnOpenVerify = new JButton("打开验证文件");
    btnOpenVerify.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int returnVal = fileChooserVerify.showOpenDialog(frame);
        File VerifyFile = fileChooserVerify.getSelectedFile();
        fileName.setVerifyFile(VerifyFile);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          JOptionPane.showMessageDialog(frame, "计划打开验证文件：" + VerifyFile.getAbsolutePath());
        }
      }
    });

//    JTextArea txtName = new JTextArea("请输入验证字符串");
//    txtName.setPreferredSize(new Dimension(500, 120));
//    txtName.setLineWrap(true);
//    txtName.setWrapStyleWord(true);

    JLabel label = new JLabel();
    label.setForeground(Color.red);
    label.setFont(new Font("微软雅黑", Font.BOLD, 16));
    label.setSize(600, 350);
    label.setPreferredSize(new Dimension(500, 150));

    //需要验证的签名文件
    JButton btnVerify = new JButton("验证");
    btnVerify.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
//        System.out.println(fileName.getData().getAbsolutePath());
//        System.out.println(fileName.getCertificate().getAbsolutePath());
//        System.out.println(fileName.getVerifyFile().getAbsolutePath());
        if (fileName.getVerifyFile() == null) {
          try {
            Label.JLabelSetText(label,
                "请选择验证文件");
          } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
          }
        } else if (fileName.getCertificate() == null) {
          try {
            Label.JLabelSetText(label, "请选择证书文件");
          } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
          }
        } else if (fileName.getData() == null) {
          try {
            Label.JLabelSetText(label, "请选择数据文件");
          } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
          }
        } else {
          //进行验证
          try {
            String result = verify(fileName.getVerifyFile(), fileName.getData(),
                fileName.getCertificate());
            label.setText(result);
          } catch (IOException | CertificateException ex) {
            throw new RuntimeException(ex);
          }
        }
      }
    });

    p1.add(btnOpenData);
    p1.add(btnOpenCer);
    p1.add(btnOpenVerify);
    p1.add(btnVerify);
    p1.add(label);

    //签名部分GUI
    JPanel p2 = new JPanel();
    //p2.setBackground(Color.BLUE);
    p2.setBounds(10, 150, 300, 60);

    JLabel labelBuild = new JLabel();
    labelBuild.setForeground(Color.red);
    labelBuild.setFont(new Font("微软雅黑", Font.BOLD, 16));
    labelBuild.setSize(600, 350);
    labelBuild.setPreferredSize(new Dimension(500, 150));

    //选择签名数据
    JFileChooser fileChooseBuildData = ChooseFile.SelectFile(frame, ".txt");

    JButton btnOpenBuildData = new JButton("打开数据文件");
    btnOpenBuildData.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int returnVal = fileChooseBuildData.showOpenDialog(frame);
        File Datafile = fileChooseBuildData.getSelectedFile();
        fileName.setData(Datafile);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          JOptionPane.showMessageDialog(frame, "计划打开数据文件:" + Datafile.getAbsolutePath());
        }
      }
    });

    //选择私钥文件
    JFileChooser fileChoosePrivateKey = ChooseFile.SelectFile(frame, ".der");

    JButton btnOpenPrivatekey = new JButton("打开私钥文件");
    btnOpenPrivatekey.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int returnVal = fileChoosePrivateKey.showOpenDialog(frame);
        File Datafile = fileChoosePrivateKey.getSelectedFile();
        fileName.setPrivateKey(Datafile);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          JOptionPane.showMessageDialog(frame, "计划打开私钥文件:" + Datafile.getAbsolutePath());
        }
      }
    });

    JTextArea txtName = new JTextArea("");
    txtName.setPreferredSize(new Dimension(500, 120));
    txtName.setLineWrap(true);
    txtName.setWrapStyleWord(true);

    JButton btnBuild = new JButton("签名");
    btnBuild.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (fileName.getData() == null) {
          try {
            Label.JLabelSetText(labelBuild, "请选择数据文件");
          } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
          }
        } else if (fileName.getPrivateKey() == null) {
          try {
            Label.JLabelSetText(labelBuild, "请选择私钥文件");
          } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
          }
        } else {
          //创建新窗口
          PrivateCertificate privateCertificate = NewFrame.getPrivateCerificate(frame);
          if (privateCertificate.getCreator() == null) {
            try {
              Label.JLabelSetText(labelBuild, "请输入创建时间并按下确定");
            } catch (InterruptedException ex) {
              throw new RuntimeException(ex);
            }
          } else if (privateCertificate.getDatedTime() == null) {
            try {
              Label.JLabelSetText(labelBuild, "请输入结束时间并按下确定");
            } catch (InterruptedException ex) {
              throw new RuntimeException(ex);
            }
          } else if (privateCertificate.getCreator() == null) {
            try {
              Label.JLabelSetText(labelBuild, "请输入创建者并按下确定");
            } catch (InterruptedException ex) {
              throw new RuntimeException(ex);
            }
          } else if (privateCertificate.getSigner() == null) {
            try {
              Label.JLabelSetText(labelBuild, "请输入签名者等信息并按下确定");
            } catch (InterruptedException ex) {
              throw new RuntimeException(ex);
            }
          } else {
            try {
              String x = build(fileName.getData(), fileName.getPrivateKey(), privateCertificate);
              txtName.setText(x);
              Label.JLabelSetText(labelBuild, "请复制并保存好验证数据！！！");
            } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException |
                     InterruptedException ex) {
              throw new RuntimeException(ex);
            }
          }
        }
      }
    });

    p2.add(btnOpenBuildData);
    p2.add(btnOpenPrivatekey);
    p2.add(btnBuild);
    p2.add(labelBuild);
    p2.add(txtName);

    JTabbedPane tp = new JTabbedPane();
    tp.add(p1);
    tp.add(p2);

    // 设置tab的标题
    tp.setTitleAt(0, "验证");
    tp.setTitleAt(1, "签名");

    //frame.pack();
    frame.setContentPane(tp);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  public static String verify(File verifyFile, File data, File Certificate)
      throws IOException, CertificateException {
    String result = null;
    //验证
    PrivateCertificate privateCertificate = IOCertificate.readCertificate(data);
    String PrivateCertificateStr =
        "<html>创建时间：" + TimestampConversion.TS2Date(privateCertificate.getCreateTime())
            + "<br/>"
            + "签名时间：" + TimestampConversion.TS2Date(privateCertificate.getSignatureTime())
            + "<br/>"
            + "过期时间：" + TimestampConversion.TS2Date(privateCertificate.getDatedTime()) + "<br/>"
            + "创建者：" + privateCertificate.getCreator() + "<br/>"
            + "签名者：" + privateCertificate.getSigner() + "<br/>";
    if (IOCertificate.isTimeOut(privateCertificate)) {
      if (RSAUtil.verifyByCertificate(verifyFile, data, Certificate)) {
        result = "验证通过</html>";
      } else {
        result = "验证不通过，有可能篡改，请勿使用</html>";
      }
    } else {
      result = "时间戳过期，请重新下载</html>";
    }
    return PrivateCertificateStr + result;
  }

  public static String build(File data, File Privatekey, PrivateCertificate privateCertificate)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    //写入证书，具体写入内容看Certificate类
    IOCertificate.WriteCertificate(data, privateCertificate);

    //SHA256
    long stime = System.currentTimeMillis();
    String sha256Str = SHA256.getSha256Str(data);
    System.out.println("Sha256结果: " + sha256Str);
    long etime = System.currentTimeMillis();
    System.out.printf("SHA256执行时间：%d秒\n", (etime - stime) / 1000);
    //输出
    String encodedData = privateEncrypt(sha256Str,
        getPrivateKeyByFile(Privatekey.getPath()));
    System.out.println("请注意即时保存密文");
    System.out.println("密文：" + encodedData);
    return encodedData;
  }
}

package org.example.GUI;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.example.Entity.PrivateCertificate;
import org.example.Utils.TimestampConversion;

/**
 * @author unibeam
 * @date 2022-08-11
 * @description:创建新窗口并输入信息
 */
public class NewFrame {

  public static PrivateCertificate getPrivateCerificate(JFrame frame) {

    PrivateCertificate privateCertificate = new PrivateCertificate();
    privateCertificate.setCreateTime(System.currentTimeMillis());
    privateCertificate.setDatedTime(System.currentTimeMillis());

    JDialog frameNew = new JDialog();
    frameNew.setBounds(
        new Rectangle(
            (int) frame.getBounds().getX() + 50,
            (int) frame.getBounds().getY() + 50,
            (int) frame.getBounds().getWidth(),
            (int) frame.getBounds().getHeight() + 100
        )
    );

    GridLayout gl = new GridLayout(5, 1, 10, 60);

    frameNew.setLayout(new FlowLayout());

    JPanel jp = new JPanel();
    jp.setLayout(gl);

    JLabel lblCreateTime = new JLabel("创建时间：");
    JTextField txtCreateTime = new JTextField(
        TimestampConversion.TS2Date(privateCertificate.getCreateTime()));
    txtCreateTime.setPreferredSize(new Dimension(100, 30));
    JLabel lblDatedTime = new JLabel("过期时间：");
    JTextField txtDatedTime = new JTextField(
        TimestampConversion.TS2Date(privateCertificate.getDatedTime()));
    txtCreateTime.setPreferredSize(new Dimension(150, 30));
    JLabel lblCreator = new JLabel("创建者：");
    JTextField txtCreator = new JTextField(privateCertificate.getCreator());
    txtCreateTime.setPreferredSize(new Dimension(150, 30));
    JLabel lblSigner = new JLabel("签名者：");
    JTextField txtSigner = new JTextField(privateCertificate.getSigner());
    txtCreateTime.setPreferredSize(new Dimension(150, 30));

    JPanel jp2 = new JPanel();
    GridLayout gl2 = new GridLayout(1, 2, 10, 0);
    jp2.setLayout(gl2);
    JButton Enter = new JButton("确定");
    JButton Cancel = new JButton("取消");
    Enter.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        privateCertificate.setCreateTime(TimestampConversion.Date2TS(txtCreateTime.getText()));
        privateCertificate.setDatedTime(TimestampConversion.Date2TS(txtDatedTime.getText()));
        privateCertificate.setCreator(txtCreator.getText());
        privateCertificate.setSigner(txtSigner.getText());
        frameNew.dispose();
      }
    });

    Cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        privateCertificate.setCreateTime(null);
        privateCertificate.setDatedTime(null);
        privateCertificate.setCreator(null);
        privateCertificate.setSigner(null);
        frameNew.dispose();
      }
    });
    jp2.add(Enter);
    jp2.add(Cancel);

    jp.add(lblCreateTime);
    jp.add(txtCreateTime);
    jp.add(lblDatedTime);
    jp.add(txtDatedTime);
    jp.add(lblCreator);
    jp.add(txtCreator);
    jp.add(lblSigner);
    jp.add(txtSigner);
    jp.add(jp2);

    frameNew.add(jp);

    frameNew.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);    // 设置模式类型。
    frameNew.setVisible(true);

    return privateCertificate;
  }

}

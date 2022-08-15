package org.example.GUI;

import java.awt.FontMetrics;
import javax.swing.JLabel;

/**
 * @author unibeam
 * @date 2022-08-11
 * @description:Label工具
 */
public class Label {

  public static void JLabelSetText(JLabel jLabel, String longString)
      throws InterruptedException {
    StringBuilder builder = new StringBuilder("<html>");
    char[] chars = longString.toCharArray();
    FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
    int start = 0;
    int len = 0;
    while (start + len < longString.length()) {
      while (true) {
        len++;
        if (start + len > longString.length()) {
          break;
        }
        if (fontMetrics.charsWidth(chars, start, len)
            > jLabel.getWidth()) {
          break;
        }
      }
      builder.append(chars, start, len - 1).append("<br/>");
      start = start + len - 1;
      len = 0;
    }
    builder.append(chars, start, longString.length() - start);
    builder.append("</html>");
    jLabel.setText(builder.toString());
  }
}

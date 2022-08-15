package org.example.GUI;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

/**
 * @author unibeam
 * @date 2022-08-10
 * @description:文件选择器
 */
public class ChooseFile {

  public static JFileChooser SelectFile(JFrame frame, String type) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileFilter() {
      @Override
      public boolean accept(File f) {
        return f.getName().toLowerCase().endsWith(type);
      }

      @Override
      public String getDescription() {
        return type;
      }
    });
    return fileChooser;
  }
}

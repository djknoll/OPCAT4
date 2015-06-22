package org.objectprocess.team;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class FileConvertor {
  public FileConvertor() {}

  public String convertFileToString(String fileName) throws Exception{

    StringBuffer sb = new StringBuffer(4096);
    byte[] b = new byte[4096];
    String s = null;
    int count = 0;

    try {
      FileInputStream fis = new FileInputStream(fileName);
      BufferedInputStream bis = new BufferedInputStream(fis, 4096);
      count = bis.read(b, 0, 4096);

      while (count != -1) {
        s = new String(b, 0, count);
        sb.append(s);
        count = bis.read(b, 0, 4096);
      }
      s = sb.toString();
      return s;

    }catch (Exception e) {throw e;}
  }

};//end of class

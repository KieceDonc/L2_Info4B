import java.io.BufferedReader;
import java.io.InputStream;

import java.io.*;

class exo1_1{

  public static void main(String[] args){
    try{
      InputStream fileln= new FileInputStream("file_test.txt");
      InputStreamReader lecture = new InputStreamReader(fileln);
      BufferedReader textebuffer = new BufferedReader(lecture);

      String ligne;

      while((ligne=textebuffer.readLine())!=null){
        System.out.println(ligne);
      }
      textebuffer.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
import java.io.BufferedReader;
import java.io.InputStream;

import java.io.*;

class exo1_2{

  public static void main(String[] args) throws IOException{
    int somme = 0;
    BufferedReader fichier = new BufferedReader(new FileReader("file_test2.txt"));
    StreamTokenizer lexeme = new StreamTokenizer(fichier);
    while(lexeme.nextToken()!=StreamTokenizer.TT_EOF){
      if(lexeme.ttype==StreamTokenizer.TT_NUMBER){
        somme+=(int)lexeme.nval;
        System.out.println("La somme vaut : "+somme);
      }
    }
    fichier.close();
  }
}
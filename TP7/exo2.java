import java.io.*;
import java.util.*;

public class exo2{
  public static void main(String[] args){
    Hashtable<String,Integer> tableH = new Hashtable<String,Integer>();
    String ligne, mot="";
    StringTokenizer st;

    BufferedReader fichier = new BufferedReader(new FileReader("texte.txt"));
    while((ligne=fichier.readLine())!=null){
      st = new StringTokenizer(ligne," ,;:/({}[]\n\t?!");
      while(st.hasMoreTokens()){
        // on récupère le mot
        mot=st.nextToken();
      }

      // recherche du mot dans la table
      Object o = tableH.get(mot);
      if(o==null){ // si le mot n'existe pas
        tableH.put(mot,1); // on l'ajoute
      }else{
        Integer i = (Integer) o;
        tableH.put(mot, new Integer(i.intValue()+1));
      }
    }
    fichier.close();
    Enumeration listeMots = tableH.keys();
    // Parcours de toutes les entrées de la table de hashage
    while(listeMots.hasMoreElements()){
      // Récupération du mot
      String m = (String)listeMots.nextElement();
      // Recherche de l'integer corresponddans la table de hashage
      Object o = tableH.get(m);
      Integer occurence = (Integer) o;
      System.out.println(m+"=>"+occurence);
    }
  }
}
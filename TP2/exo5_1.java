import java.util.ArrayList;

/*
Vous  avez  à  votre  disposition  dans  les  salles  de  TP,  des  machines  qui  
possèdent  unprocesseur  multi-cœurs  (4  par  exemple).  On  veut  créer  un  
programme  qui  permet  de calculer  les  nombres  premiers  compris  entre  1  et  
5  000  000  et  accélérer  le  traitementen utilisant tous les cœurs. Pour 
exploiter plusieurs cœurs du processeur vous utiliserezplusieurs threads. Pour 
toutes les questions, observer l’exécution de votre programme avec l’utilitaireh
top.

  1- Réaliser  une  version  séquentielle  du  programme  (mono-thread)  en  utilisant  une
  méthode boolean isPrime(int nb). Mesurer le temps d'exécution en utilisant lacommandetime
  d’UNIX

  2- Réaliser une première version parallèle du programme en attribuant l’exploration des 
  intervalles 1 - 2 500 000 à un thread et 2 500 001 - 5 000 000 à un autre thread.
  Mesurez le temps d’exécution. Quel est l’inconvénient de ce programme ? 

  3- Réaliser une deuxième version parallèle comportant un index qui indique la progression 
  du calcul. Chaque thread vient consulter l’index pour connaître le nombre qu’il doit 
  tester. L’algorithme en pseudo-code est le suivant :
  tant que nb < borne supnb=lire la valeur du nombre à tester dans le distributeur (index)
  incrémenter la valeur de l’index du distributeurtester si nb est premier fin pour utilisez 
  tous les cœurs, mesurez le temps d’exécution de cette version, calculer lefacteur d’accélération (speed-up). 
  Quel est lespeed-upthéorique maximum ?
  */

class exo5_1{

  public static ArrayList<Integer> premiers = new ArrayList<Integer>();
  public static long startTime = 0;
  public static long endTime = 0;

  public static void main(String[] args){
    startTime = System.currentTimeMillis();
    for(int x=0;x<=50000;x++){
      if(isPrime(x)){
        premiers.add(x);
      }
    }
    endTime = System.currentTimeMillis();
    for(int x=0;x<premiers.size();x++){
      System.out.println("n°"+x+" = "+premiers.get(x));
    }
    System.out.println("Temps de calcul : "+(endTime-startTime)+"ms");
  }

  public static boolean isPrime(int nb){
    if(nb==1 || nb==0){
      return true;
    }
    int divider = 2;
    boolean isPrime = true;
    do{
      isPrime = nb % divider!=0;
      divider++;
    }while(isPrime && divider<nb);
    return isPrime;
  }
}
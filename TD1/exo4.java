/*
1.écrire  un  programme  Java  qui  compte  indéfiniment  mais  qui  se  termine  lorsque l’utilisateur appuie sur une touche. 
Dans les langages de programmation, les entrées-sorties sont, dans la majorité des cas, gérées de manière bloquantes. 
Dans cet exer-cice vous devez donc utiliser un thread séparé pour ne pas bloquer le thread principal.
2.  Reprendre le programme précédent et le faire s’arrêter au bout d’un temps donné
(passé en param`etre au lancement du programme par l’utilisateur).
*/


public class exo4 {
  // java exo4 1000
  public static void main(String[] args){
    Cmpt cmpt = new Cmpt();
    cmpt.start();

    // lecture
    try{
      Thread.sleep(Integer.parseInt(args[0]));
    }catch(Exception e){
      e.printStackTrace();
    }
    cmpt.stopper();
    System.out.println("Fin");
  }

  public static class Cmpt extends Thread{

    private volatile boolean stop = false;

    public void run(){
      int i = 0;
      while(!stop){
        i++;
        System.out.println("i = "+i);
      }
    }

    public void stopper(){
      stop = true;
    }
  }
}

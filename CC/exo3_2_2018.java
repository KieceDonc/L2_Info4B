/*
Exercice 3. Thread compteurs revisités — (6pts)

On considère deux threads A et B qui comptent jusqu’à 10 000, l’un les nombre pairs, l’autre les
nombres impairs.

  1-  Proposez un programme dans la syntaxe Java utilisant un sémaphore ou un verrou
  2-  Modifier votre programme pour utiliser wait et notify.
  3-  On ajoute un troisième thread puis n quelle solution utiliser (celle de la question 1 ou celle de
      a question 2), justifiez. Modifier le programme en conséquence.
  4-  Ces programmes peuvent-il provoquer un verrou mortel ?
*/

public class exo3_2_2018{

  public static void main(String[] args){
      ObjCmpt objCmpt = new ObjCmpt();
      ThreadEven threadEven = new ThreadEven(objCmpt);
      ThreadOdd threadOdd = new ThreadOdd(objCmpt);
      threadEven.start();
      threadOdd.start();
    }

    public static class ObjCmpt{
      public int cmpt = 0;
    }

    public static class ThreadEven extends Thread{

      ObjCmpt objCmpt;

      ThreadEven(ObjCmpt objCmpt){
        this.objCmpt = objCmpt;
      }

      public void run(){
        try{
            do{
              synchronized(objCmpt){
                objCmpt.wait();
                objCmpt.cmpt+=1;
                System.out.println(objCmpt.cmpt+" even");
                objCmpt.notify();
              }
            }while(objCmpt.cmpt<1000);
        }catch(Exception e){
          e.printStackTrace();
        }
      } 
    }

    public static class ThreadOdd extends Thread{

      ObjCmpt objCmpt;
      
      ThreadOdd(ObjCmpt objCmpt){
        this.objCmpt = objCmpt;
      }

      public void run(){
        try{            
            do{
              synchronized(objCmpt){
                objCmpt.cmpt+=1;
                System.out.println(objCmpt.cmpt+" odd");
                objCmpt.notify();
                objCmpt.wait();
              }
            }while(objCmpt.cmpt<1000);
        }catch(Exception e){
          e.printStackTrace();
        }
      }
    }
}
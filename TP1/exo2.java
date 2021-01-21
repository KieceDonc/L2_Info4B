/*
  Il est possible d’associer une priorité à chaque thread créé, au moyen de méthodes
définies dans la classe java.lang.Thread.

  Au lancement d’un programme un seul thread s’exécute, c’est le thread initial. On peut
le contrôler via la méthode de classe public static Thread currentThread(); (utile
par exemple pour utiliser la méthode getName() avec des threads implémentant l’interface
Runnable. Il est ensuite possible de modifier les attributs du thread en le manipulant via
les méthodes d’instance de l’objet retourné.

  Plus généralement, un objet de la classe Thread (ou plutôt une référence sur un objet)
permet de contrôler le comportement du thread qui peut être actif, suspendu ou arrêté.

class exo2 {
  public static void main( String [] args) throws Exception {
    Thread threadInitial = Thread.currentThread();
    // Donner un nom au thread
    threadInitial.setName ("Mon thread");
    // Afficher le nom du thread
    System.out.println(threadInitia);
    // Faire dormir le thread 2 sec
    Thread.sleep(2000);
    System.out.println("fin");
  }
}

  Il est possible de changer la priorité d’un thread afin qu’il ait une priorité particulière
pour accéder au processeur. Par convention, le thread de plus forte priorité accède plus
souvent au processeur. Par défaut, un thread a la priorité de son parent.

  Pour changer la priorité d’un thread on utilise la méthode public void setPriority(int)
et pour visualiser la priorité on utilise public int getPriority(). Il existe des constantes
prédéfinies pour les valeurs des priorités (constantes membres de la classe Thread) :
  — public final static int MAX PRIORITY;
  — public final static int MIN PRIORITY;
  — public final static int NORM PRIORITY;

  Il n’est pas possible de sortir de ces bornes sous peine de recevoir une exception et
il est recommandé d’utiliser les constantes (comme par exemple en affectant la valeur
Thread.MIN PRIORITY avec la méthode setPriority()) au lieu d’un entier.

  Modifier le programme de l’exercice 1 afin de donner différentes priorités aux deux
threads. Notez vos observations pour chaque type de priorité testé. Pourquoi le compor-
tement souhaité n’est pas celui observé ?
*/

public class exo2{
  public static void main(String[] args){

    Thread[] threadList = new Thread[10];
    for(int x = 0; x<10;x++){
      threadList[x] = new monThread();
      threadList[x].setName("Thread n°"+x);
      System.out.println(threadList[x].getPriority());
      if(x<5){
        threadList[x].setPriority(Thread.MAX_PRIORITY);
      }else{
        threadList[x].setPriority(Thread.MIN_PRIORITY);
      }
      threadList[x].start();
    }
    System.out.println("Je suis le thread principal");
  }
}

class monThread extends Thread{
  public void run(){
    for(int i=1;i<5;i++){
      System.out.println("Je suis le thread "+getName()+" i="+i+" avec une id="+getId());
    }
  }
}
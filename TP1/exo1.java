/*
  Au démarrage d’un programme Java, un seul thread est crée. Pour en créer un nouveau,
il faut créer un nouvel objet de la classe Thread puis le démarrer c’est-à-dire lui demander
d’exécuter une portion de code (méthode run()). Il existe deux stratégies. Soit le code à
exécuter est spécifié dans une classe qui hérite de la classe Thread, soit il est spécifié dans
une classe qui implémente l’interface Runnable. Dans la seconde stratégie, un objet sera
passé en paramètre lors de la création du nouvel objet de la classe Thread. La première
solution a l’avantage de ne manipuler qu’un seul objet mais elle a l’inconvénient d’interdire
tout nouvel héritage.
  Pour créer le nouveau thread il faut utiliser l’un des constructeurs de la classe Thread.
Les plus couramment utilisés sont :
  — public Thread();
  — public Thread(String);
  — public Thread(Runnable, String);
  — public Thread(Runnable);
  — public Thread(ThreadGroup, String);
  — public Thread(ThreadGroup, Runnable);
  — public Thread(ThreadGroup, Runnable, String);
  Le paramètre ThreadGroup permet de regrouper un ensemble de threads et de leur
donner des propriétés communes ou de leur appliquer, à tous, une même méthode. Une
fois l’objet thread créé au moyen d’un des constructeurs, il faut l’activer (le démarrer) par
un appel à la méthode start() qui appellera la méthode public void run() de l’objet
contenant le code à exécuter. Si la méthode run() est appelée directement, le code de
cette méthode est exécuté dans le thread courant.

class monThread extends Thread {
  public void run() {
    for(int i =1 ; i <5 ; i++){
      System.out.println(" je suis le thread " + getName() + " i = " + i);
    }
  }
}

public class exo1 {
  public static void main ( String args []) {
    Thread th1 = new monThread();
    Thread th2 = new monThread();
    th1.start();
    th2.start();
    System.out.println(" Je suis le thread principal ");
  }
}


1. En vous inspirant du code donné en cours, et du code du listing 1, écrire deux
programmes pour créer des threads au moyen des deux méthodes évoquées c’est-à-
dire avec extends Thread ou bien implements Runnable.

2. Taper le programme du listing 1, l’exécuter (enventuellement plusieurs fois), que
constatez-vous dans les affichages ? interpréter le résultat.

3. Pourquoi ce programme ne met réellement pas en évidence le fonctionnement multi-
tâches du système d’exploitation ? Modifier le programme afin de faire apparaı̂tre
”l’effet multi-tâches” et la préemption.

4. Modifier les programmes déjà réalisés pour créer 10 threads dans une boucle (sauver
les références aux threads dans un tableau).

5. Quel peut être l’inconvénient de la méthode de création et de démarrage des threads
(surtout dans le cas d’une utilisation de plusieurs threads en boucle) ?

6. Affecter un nom à chacun des threads crées via la méthode setName(), afficher la
référence d’un thread, afficher son nom avec la méthode getName() ainsi que le
nombre de threads avec la méthode de classe activeCount(). Lorsqu’un thread
est déclaré via l’interface Runnable, certaines méthodes de la classe Thread ne sont
plus accessibles directement (getName() par exemple). La gestion des noms associés
aux threads est illustrée dans le listing 3 page 8.
*/


public class exo1{
  public static void main(String[] args){
    /*Thread th1 = new monThread1();
    th1.start();
    try{
      th1.join();
    }catch(Exception e){
      e.printStackTrace();
    }
    Runnable runner = new monThread2();
    Thread th2 = new Thread(runner);
    th2.start();
    try{
      th2.join();
    }catch(Exception e){
      e.printStackTrace();
    }*/

    Thread[] threadList = new Thread[10];
    for(int x = 0; x<10;x++){
      threadList[x] = new monThread1();
      threadList[x].setName("Thread n°"+x);
      System.out.println(threadList[x].getPriority());
      threadList[x].start();
    }
    System.out.println(monThread1.activeCount());
    
    System.out.println("Je suis le thread principal");
  }
}

class monThread1 extends Thread{
  public void run(){
    for(int i=1;i<5;i++){
      System.out.println("Je suis le thread "+getName()+" i="+i+" avec une id="+getId());
    }
  }
}

class monThread2 implements Runnable{
  public void run(){
    for(int i=1;i<5;i++){
      System.out.println("je suis le thread i="+i);
    }
  }
}
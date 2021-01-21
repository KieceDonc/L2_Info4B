/*
  Réaliser un programme qui simule une course de threads. Chaque thread démarre et
compte jusqu’à 1000. Dans la boucle simulant la course du thread, implanter une attente
aléatoire avec la méthode sleep() et la méthode Math.random(). Le premier thread arrivé
à 1000 est déclaré gagnant, néanmoins les autres threads continuent la course.

  1. Programmer le course en veillant à ce que votre programme soit en mesure d’établi
et d’afficher un classement lorsque tous les threads sont arrivés.

  2. Comment implanter un arbitre qui observe la course et établi le classement ?
*/

public class exo3{


  private static int nbThread = 10;
  public static void main(String[] args){

    Ranking ranking = new Ranking(nbThread);

    for(int x= 0;x<nbThread;x++){
      new CustomThread(new Counting(){
        public void onEnd(String threadName){
          ranking.onThreadCountingEnd(threadName);
          if(ranking.isRaceEnded()){
            ranking.show();
          }
        }
      }).start();;
    }
  }
}

interface Counting{
  void onEnd(String threadName);
}

class CustomThread extends Thread{

  private Counting threadCounting;

  CustomThread(Counting interfaceReference){
    this.threadCounting = interfaceReference;
  }
  
  public void run(){
    try{
      for(int x = 0;x<1000;x++){
        sleep((long) Math.random());
      }
      threadCounting.onEnd(getName());
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}

class Ranking{

  private String[] ranking;
  private int index=0; // array index which describe the position of the next thread ranking
  
  Ranking(int nbThread){
    ranking = new String[nbThread];
  }

  public void onThreadCountingEnd(String name){
    ranking[index] = name;
    index++;
  }

  public boolean isRaceEnded(){
    return ranking.length-1==index;
  }

  public void show(){
    for(int x = 0;x<ranking.length;x++){
      System.out.println("n°"+x+", thread name = "+ranking[x]);
    }
  }
}
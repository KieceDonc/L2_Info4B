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
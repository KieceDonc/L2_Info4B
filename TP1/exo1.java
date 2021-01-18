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
      if(x<5){
        threadList[x].setPriority(Thread.MAX_PRIORITY);
      }else{
        threadList[x].setPriority(Thread.MIN_PRIORITY);
      }
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
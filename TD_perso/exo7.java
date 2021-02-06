public class exo7{

  private static int cmpt = 0;
  
  public static void main(String[] args){

    IncrementeThread incrementeThread = new IncrementeThread();
    DecrementeThread decrementeThread = new DecrementeThread();

    decrementeThread.start();
    incrementeThread.start();

    System.out.println(cmpt);
  }

  public static class IncrementeThread extends Thread{
    
    public void run(){
      cmpt++;
    }
  }

  public static class DecrementeThread extends Thread{

    public void run(){
      cmpt--;
    }
  }
}
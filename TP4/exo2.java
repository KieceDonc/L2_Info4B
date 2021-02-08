public class ProdConsom{

  public static void main(String[] args){

  }

  public static class Buffer{
    private Object o;

    public Buffer(){
      o=null;
    }

    public void ecrire(Object o){
      while(this.o!=null){
        try{
          this.wait();
        }catch(InterruptedException e){
          e.printStackTrace();
        }
      }
      this.o=o;
      this.notify();
    }
  }

  public synchronized Object lire(){

    while(o==null){
      try{
        this.wait();
      }catch(InterruptedException e){
        e.printStackTrace();
      }
      Object tmp=0;
      o=null;
      this.notify();
      return tmp;
    }
  }
}
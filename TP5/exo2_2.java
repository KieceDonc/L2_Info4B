public class exo2_2{

  public static void main(String[] args){
    Buffer buffer = new Buffer();
    Consommateur consommateur = new Consommateur(buffer);
    Producteur producteur = new Producteur(buffer);
    consommateur.start();
    producteur.start();
  }
}

class Buffer{
  private int n = 8;
  private int index = 0;
  private Object[] listObj;

  public Buffer(){
    listObj=new Object[n];
  }

  // Producteur
  public synchronized void ecrire(Object o){
    while(this.index==n-1){
      try{
        this.wait();
      }catch(InterruptedException e){
        e.printStackTrace();
      }
    }
    this.listObj[index]=o;
    index+=1;
    this.notify(); // le producteur va notifier le consommateur de l'écriture dans le buffer
  }

  // Consommateur
  public synchronized Object lire(){
    while(this.index==0){
      try{
        this.wait();
      }catch(InterruptedException e){
        e.printStackTrace();
      }
    }
    index-=1;
    Object tmp=this.listObj[index];
    this.listObj[index]=null;
    this.notify(); // Le consommateur va notifier le producteur de la lecture
    return tmp;
  }
}

class Producteur extends Thread{

  private Buffer buffer;
  private int index = 0;

  public Producteur(Buffer buffer){
    this.buffer = buffer;
  }

  public void run(){
    while(true){
      buffer.ecrire("Bonjour tout le monde n°"+index);
      index+=1;
      System.out.println("Objet écrit.");
      try{
        sleep(500);
      }catch(Exception e){
        e.printStackTrace();
      }
    }
  }
}

class Consommateur extends Thread{

  private Buffer buffer;

  public Consommateur(Buffer buffer){
    this.buffer = buffer;
  }

  public void run(){
    while(true){
      Object o = buffer.lire();
      System.out.println("Object lu\n");
      System.out.println(o.toString());
      try{
        sleep(1000);
      }catch(Exception e){
        e.printStackTrace();
      }
    }
  }
}
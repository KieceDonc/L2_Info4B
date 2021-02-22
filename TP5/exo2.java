public class exo2{

  public static void main(String[] args){
    Buffer buffer = new Buffer();
    Consommateur consommateur = new Consommateur(buffer);
    Producteur producteur = new Producteur(buffer);
    consommateur.start();
    producteur.start();
  }
}

class Buffer{
  private Object o;

  public Buffer(){
    o=null;
  }

  // Producteur
  public synchronized void ecrire(Object o){
    while(this.o!=null){
      try{
        this.wait();
      }catch(InterruptedException e){
        e.printStackTrace();
      }
    }
    this.o=o;
    this.notify(); // le producteur va notifier le consommateur de l'écriture dans le buffer
  }

  // Consommateur
  public synchronized Object lire(){
    while(o==null){
      try{
        this.wait();
      }catch(InterruptedException e){
        e.printStackTrace();
      }
    }
    Object tmp=o;
    o=null;
    this.notify(); // Le consommateur va notifier le producteur de la lecture
    return tmp;
  }
}

class Producteur extends Thread{

  private Buffer buffer;

  public Producteur(Buffer buffer){
    this.buffer = buffer;
  }

  public void run(){
    buffer.ecrire("Bonjour tout le monde");
    System.out.println("Objet écrit.");
  }
}

class Consommateur extends Thread{

  private Buffer buffer;

  public Consommateur(Buffer buffer){
    this.buffer = buffer;
  }

  public void run(){
    Object o = buffer.lire();
    System.out.println("Object lu\n");
    System.out.println(o.toString());
  }
}
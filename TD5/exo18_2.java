import java.util.concurrent.locks.*;

public class exo18_2{
  static ReentrantLock lock = new ReentrantLock();

  public static void main(String[] args){

    Ligne l = new Ligne();
    l.t[0] = new Troncon("t1");
    l.t[1] = new Troncon("tc");
    l.t[2] = new Troncon("t2");
    l.t[3] = new Troncon("t3");
    l.t[4] = l.t[1];
    l.t[5] = new Troncon("t4");

    Rame r1 = new Rame(0,l);
    Rame r2 = new Rame(0,l);
    Rame r3 = new Rame(0,l);
    r1.start();
    r2.start();
    r3.start();
  }
}

class Troncon{
  public String nom;
  
  public Troncon(String nom){
    this.nom = nom;
  }
}

class Ligne{
  public Troncon[] t = new Troncon[6];
  
  public Troncon getTroncon(int i){
    return t[i];
  } 
}

class Rame extends Thread{
  private Ligne l;
  private int indiceTC; // indice troncon courant 
  private Troncon tCourant; // troncon courant

  public Rame(int i, Ligne l){
    this.indiceTC = i;
    this.l = l;
    this.tCourant = l.getTroncon(indiceTC);
  }

  public void run(){
    int j = 0;
    while(j<600){
      if(tCourant.nom.equals("t1") ||tCourant.nom.equals("t3")){
        // si tcourant est t1 (ou t3) il faut prendre le verrou pour passer sur tc ( troncon commun ) 
        exo18_2.lock.lock();
      }else if(tCourant.nom.equals("tc")){
        // si tcourant est tc, on relache le verrou
        exo18_2.lock.unlock();
      }
      indiceTC+=1;
      indiceTC = indiceTC%6;
      tCourant = l.getTroncon(indiceTC);
      System.out.println(Thread.currentThread()+"\t"+indiceTC + "\t" +tCourant.nom);
      try{
        Thread.sleep((int)(Math.random()*10));
      }catch(Exception e){
        e.printStackTrace();
      }
      j++;
    }
  }
}
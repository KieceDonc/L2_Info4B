import java.util.concurrent.Semaphore;

public class exo18_3{
  static Semaphore semaTC = new Semaphore(1);

  public static void main(String[] args){

    Ligne l = new Ligne();
    l.t[0] = new Troncon("t1");
    l.t[1] = new TronconCommun("tc",0);
    l.t[2] = new Troncon("t2");
    l.t[3] = new Troncon("t3");
    l.t[4] = l.t[1];
    l.t[5] = new Troncon("t4");

    Rame r1 = new Rame(0,l,1);
    Rame r2 = new Rame(0,l,1);
    Rame r3 = new Rame(0,l,1);
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

class TronconCommun extends Troncon{
  public int sensParcours = 0;
  int nbRames = 0;
  
  public TronconCommun(String nom,int sensParcours){
    super(nom);
    this.sensParcours = sensParcours;
  }

  public synchronized void setSensParcours(int sens){
    this.sensParcours = sens;
  }

  public synchronized int getSensParcours(){
    return this.sensParcours;
  }

  public synchronized int getNbRames(){
    return this.nbRames;
  }

  public synchronized void incrementeNbRames(){
    this.nbRames++;
  }

  public synchronized void decrementeNbRames(){
    this.nbRames--;
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
  private int sensDeplacement;
  private boolean bloque;

  public Rame(int i, Ligne l, int sensDeplacement){
    this.indiceTC = i;
    this.l = l;
    this.sensDeplacement = sensDeplacement;
    this.tCourant = l.getTroncon(indiceTC);
  }

  public void run(){
    int j = 0;
    while(j<600){
      if(tCourant.nom.equals("t1") ||tCourant.nom.equals("t3")){
        // si tc est dans l'état 0 ou dans le même état de que la rame, alors on peut accéder
        // sinon on doit attendre
        bloque = true;
        do{
          // si même état -> on incrémente le nb de rame
          if(sensDeplacement==((TronconCommun)l.t[1]).getSensParcours()){
            ((TronconCommun)l.t[1]).incrementeNbRames();
            bloque=false;
          }else if(((TronconCommun)l.t[1]).getSensParcours()==0){
            // si tc est dans l'état 0
            try{
              exo18_3.semaTC.acquire(); // on prend 1 jeton et on fixe le sens du parcours
            }catch(Exception e){
              e.printStackTrace();
            }
            ((TronconCommun)l.t[1]).incrementeNbRames();
            ((TronconCommun)l.t[1]).setSensParcours(sensDeplacement);
            bloque = false;
          }
        }while(bloque);
      }else if(tCourant.nom.equals("tc")){ 
        // on decremente le nb de rames
        ((TronconCommun)l.t[1]).decrementeNbRames();
        // si le nb de rames est à 0, on met le sens de parcours
        if(((TronconCommun)l.t[1]).getNbRames()==0){
          ((TronconCommun)l.t[1]).setSensParcours(0);
          try{
            exo18_3.semaTC.release();
          }catch(Exception e){
            e.printStackTrace();
          }
        }
      }
      // deplacement
      indiceTC+=1;
      indiceTC = indiceTC%6;
      tCourant = l.getTroncon(indiceTC);
      System.out.println(Thread.currentThread()+"\t"+indiceTC + "\t" +tCourant.nom);
      
      // on change de sens quand on arrive à t3 ou t1
      if(indiceTC==0){
        sensDeplacement=1;
      }else if(indiceTC==3){
        sensDeplacement=2;
      }

      try{
        Thread.sleep((int)(Math.random()*10));
      }catch(Exception e){
        e.printStackTrace();
      }
      j++;
    }
  }
}
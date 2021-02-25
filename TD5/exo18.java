  public class exo18{
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
      r1.start();
      r2.start();
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
    private Troncon tC; // troncon courant

    public Rame(int i, Ligne l){
      this.indiceTC = i;
      this.l = l;
      this.tC = l.getTroncon(indiceTC);
    }

    public void run(){
      int j = 0;
      while(j<=60){
        indiceTC+=1;
        indiceTC = indiceTC%6;
        tC = l.getTroncon(indiceTC);
        System.out.println(Thread.currentThread()+"\t"+indiceTC + "\t" +tC.nom);
        try{
          Thread.sleep((int)(Math.random()*10));
        }catch(Exception e){
          e.printStackTrace();
        }
        j++;
      }
    }
  }
public class Exo12{

  public static void main(String[] args){
    int nbPE=3;
    int nbPS=4;
    int nbPlaces=100;
    int nbVehiculesSimul=2000;

    Parking p = new Parking(nbPE, nbPS, nbPlaces);

    Afficheur a = new Afficheur(p);
    a.start();

    SimulationEntree se1 = new SimulationEntree(nbVehiculesSimul/2, p, nbPE);
    SimulationEntree se2 = new SimulationEntree(nbVehiculesSimul/2, p, nbPE);

    se1.start();
    se2.start();

    SimulationSortie s = new SimulationSortie(nbVehiculesSimul,p,nbPS);
    s.start();

    try{
      se1.join();
      se2.join();
      s.join();
    }catch(InterruptedException e){
      e.printStackTrace();
    }

    a.arret();
    p.terminer();
  }

  public static class Parking{
    private int nbPlaces;
    private int nbPlacesLibres;
    private int nbPlacesOccupees;
    private PorteEntree PE[];
    private PorteSortie PS[];
    private int nbPE, nbPS;

    public Parking(int nbPe, int nbPS, int places){
      this.nbPE = nbPE;
      this.nbPS = nbPS;
      nbPlaces = places;
      nbPlacesOccupees = 0;

      PE = new PorteEntree[nbPE];
      PS = new PorteSortie[nbPS];

      for(int x=0;x<nbPE;x++){
        PE[x] = new PorteEntree();
      }

      for(int x=0;x<nbPS;x++){
        PS[x] = new PorteSortie();
      }
    }

    public synchronized int nbPlacesLibres(){
      return nbPlacesLibres;
    }

    public synchronized int validerEntree(){
      if(nbPlacesLibres==0){
        return -1;
      }
      nbPlacesLibres--;
      nbPlacesOccupees++;
      notifyAll();
      return 0;
    }

    public synchronized int validerSortie(){
      if(nbPlacesOccupees==0){
        return -1;
      }
      nbPlacesLibres++;
      nbPlacesOccupees--;
      notifyAll();
      return 0;
    }

    // int IDPorte -> Numéro de la porte d'entrée 
    public synchronized void demandeEntree(int IDPorte){
      while(validerEntree()==-1){
        System.out.println("Attente entrée");
        try{
          wait();
        }catch(Exception e){
          e.printStackTrace();
        }
      }
      PE[IDPorte].entreeVoiture();
    }

    // int IDPorte -> Numéro de la porte d'entrée 
    public synchronized void demandeSortie(int IDPorte){
      while(validerSortie()==-1){
        System.out.println("Attente sortie");
        try{
          wait();
        }catch(Exception e){
          e.printStackTrace();
        }
      }
      PS[IDPorte].sortieVoiture();
    }

    public synchronized void AttenteEvenement(){
      try{
        wait();
      }catch(InterruptedException e){
        e.printStackTrace();
      }
    }

    public synchronized void terminer(){
      notifyAll();
    }
  }

  public static class PorteEntree{

    private boolean ouvert = false;

    public PorteEntree(){
      ouvert=false;
    }

    private void ouvrir(){
      ouvert = true;
    }

    private void fermer(){
      ouvert = false;
    }
  
    public synchronized void entreeVoiture(){
      this.ouvrir();
      try{
        Thread.sleep((int)(Math.random()*5));
      }catch(InterruptedException e){
        e.printStackTrace();
      }
      this.fermer();
    }
  }

  public static class PorteSortie{

    private boolean ouvert = false;

    public PorteSortie(){
      ouvert=false;
    }

    private void ouvrir(){
      ouvert = true;
    }

    private void fermer(){
      ouvert = false;
    }

    public synchronized void sortieVoiture(){
      this.ouvrir();
      try{
        Thread.sleep((int)(Math.random()*5));
      }catch(InterruptedException e){
        e.printStackTrace();
      }
      this.fermer();
    }
  }

  public static class Afficheur extends Thread{

    private Parking p;
    private boolean continuer = true;

    public Afficheur(Parking p){
      this.p=p;
    }

    public void run(){
      while(continuer){
        System.out.println("Nb places libres "+p.nbPlacesLibres());
        p.AttenteEvenement();
      }
    }

    public void arret(){
      continuer = false;
    }
  } 

  public static class SimulationEntree extends Thread{

    private int nbVoitures;
    private Parking p;
    private int nbPE;

    public SimulationEntree(int nb, Parking p, int nbPe){
      this.nbVoitures=nb;
      this.nbPE=nbPE;
      this.p=p;
    }
    
    public void run(){
      for(int x=1;x<=nbVoitures;x++){
        try{
          Thread.sleep((int)(Math.random()*40));
        }catch(InterruptedException e){
          e.printStackTrace();
        }
        p.demandeEntree((int)(Math.random()*nbPE));
      }
    }
  }

  public static class SimulationSortie extends Thread{
    
    private int nbVoitures;
    private Parking p;
    private int nbPS;

    public SimulationSortie(int nb, Parking p, int nbPe){
      this.nbVoitures=nb;
      this.nbPS=nbPS;
      this.p=p;
    }
    
    public void run(){
      for(int x=1;x<=nbVoitures;x++){
        try{
          Thread.sleep((int)(Math.random()*30));
        }catch(InterruptedException e){
          e.printStackTrace();
        }
        p.demandeSortie((int)(Math.random()*nbPS));
      }
    }
  }
}
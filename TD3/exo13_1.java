public class exo13_1{

  public static void main(String[] args){
    System.out.println("Initialisation");

    Philosophe[] TablePhilo = new Philosophe[5];
    Fourchette[] TableFourchette = new Fourchette[5];
    for(int i=0;i<=4;i++){
      TableFourchette[i] = new Fourchette(i);
    }
    for(int i=0;i<=4;i++){
      TablePhilo[i] = new Philosophe(i,TableFourchette[i],TableFourchette[(i+1)%5]);
    }

    for(int i=0;i<=4;i++){
      TablePhilo[i].start();
    }
  }

  public static class Fourchette{
    private int id;
    private volatile boolean utilise = false;

    Fourchette(int id){
      this.id=id;
    }

    synchronized void deposer(){
      utilise = false;
      notify();
    }

    synchronized void prendre(){
      while(utilise){
        try{
          wait();
        }catch(Exception e){
          e.printStackTrace();
        }
      }
      utilise = true;
    }
  }

  public static class Philosophe extends Thread{
    private int id;
    private Fourchette droite;
    private Fourchette gauche;
    private boolean arret;

    Philosophe(int id, Fourchette droite, Fourchette gauche){
      this.id=id;
      this.droite=droite;
      this.gauche=gauche;
    }

    public void run(){
      while(!arret){
        try{
          // pense
          System.out.println("philosophe "+id+" pense ");
          sleep((int)(Math.random()*20));
          
          // veut manger
          System.out.println("philosophe "+id+" prend fourchette droite");
          droite.prendre();
          System.out.println("philosophe "+id+" a pris fourchette droite");

          System.out.println("philosophe "+id+" prend fourchette gauche");
          gauche.prendre();
          System.out.println("philosophe "+id+" a pris fourchette gauche");

          // mange
          System.out.println("philosophe "+id+" mange");
          sleep((int)(Math.random()*10));

          // repose les fourchettes
          droite.deposer();
          gauche.deposer();
        }catch(Exception e){
          
        }
      }
    }

    public void arreter(){
      arret=true;
    }
  }
}
import java.util.*;

/**
 * Le probleme dit Sleeping-Barber Problem est un exemple classique, il illustre les probleemes
d'acces concurrents aux ressources et la synchronisation des processus. On considere un
salon de coiffure qui comporte une salle d'attente avec n chaises et une autre salle avec
un fauteuil et un coiffeur. Si il n'y a pas de client, le coiffeur dort. Si un client entre et
que toutes les chaises sont occuppees, le client s'en va. Si le coiffeur est occuppe et qu'au
moins une chaise est libre le client s'assied et attend. Si le coiffeur est endormi l'arrivee
d'un client le reveille.
1. On souhaite programmer l'activite du coiffeur et le fonctionnement du systeme au
moyen de threads. Identifer les classes qui constituent des ressources et les classes
qui donneront naissance a des threads.
2. Y-a-t'il des probleemes de concurrence ? Si oui expliquez de facon precise la methode
que vous choisissez pour les eviter ?
3. Ecrire les differentes classes du programme et la methode main().
4. On considere non plus 1 coiffeur mais 4 coiffeurs, quels sont les elements a changer
dans votre programme ?*/

public class exo10 {
  public static void main(String[] args){
    WaitingRoom waitingRoom = new WaitingRoom(5);

    Barber barber = new Barber(waitingRoom);
    barber.start();

    SimulIncomingClient simulIncomingClient = new SimulIncomingClient(waitingRoom, 20);
    simulIncomingClient.start();
    try{
      simulIncomingClient.join();
    }catch(Exception e){
      e.printStackTrace();
    }

    while(waitingRoom.getCustomersCount()>0){
      try{
        Thread.sleep((int)Math.random()*2000);
      }catch(Exception e){
        e.printStackTrace();
      }
    }
    barber.endWork();
    waitingRoom.close();
  }

  public static class Barber extends Thread{
    private WaitingRoom waitingRoom;
    private volatile boolean stop;

    public Barber(WaitingRoom waitingRoom){
      this.waitingRoom=waitingRoom;
      stop = false;
    }

    public void run(){
      while(!stop){
        waitingRoom.retirer();
        try{
          System.out.println("Coiffeur travaille...");
          Thread.sleep(1000);
        }catch(Exception e){
          e.printStackTrace();
        }
      }
    }

    public void endWork(){
      stop=true;
    }
  }

  public static class Customer{

    public Customer(){
    }
  }

  public static class WaitingRoom{
    private int maxElements = 0;
    private LinkedList<Customer> list;
    private volatile boolean open;

    public WaitingRoom(int max){
      maxElements=max;
      list = new LinkedList<Customer>();
      open = true;
    }

    synchronized public int ajouter(Customer customer){
      if(getCustomersCount()<maxElements && open){
        list.addLast(customer);
        notifyAll();
        return 0;
      }else{
        return -1;
      }
    }

    synchronized public Customer retirer(){
      while(getCustomersCount()==0 && open){
        System.out.println("Attente ... coiffeur dort ");
        try{
          wait();
        }catch(InterruptedException e){
          e.printStackTrace();
        }
      }
      return list.removeFirst();
    }

    public int getCustomersCount(){
      return list.size();
    }

    public void close(){
      open=false;
    }
  }

  public static class SimulIncomingClient extends Thread{
    private int nbCustomers;
    private WaitingRoom waitingRoom;
    private int CustomersWhoLeftCount;

    public SimulIncomingClient(WaitingRoom waitingRoom,int nbCustomers){
      this.waitingRoom = waitingRoom;
      this.nbCustomers = nbCustomers;
      CustomersWhoLeftCount = 0;
    }

    public void run(){
      for(int i=0;i<nbCustomers;i++){
        System.out.println("Nb clients dans la salle = "+waitingRoom.getCustomersCount());
        if(waitingRoom.ajouter(new Customer())==0){
          System.out.println("1 nouveau client est arrivé");
        }else{
          System.out.println("Salle pleine : client reparti");
          CustomersWhoLeftCount++;
        }
        try{
          System.out.println("Start waiting");
          Thread.sleep((int)(Math.random()*1000));
          System.out.println("End waiting");
        }catch(Exception e){
          e.printStackTrace();
        }
      }
      System.out.println("nb de clients arrivés = "+nbCustomers);
      System.out.println("nb de clients partis = "+CustomersWhoLeftCount);
    }
  }
}
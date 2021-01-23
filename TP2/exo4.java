/*
Créer un programme qui lance deux threads qui vont retirer 1000 fois 10 pour l’un 
et 1000 fois 50 pour l’autre, sur un compte qui comporte 800 000. Chaque thread
consulte le compte, met le solde dans une variable temporaire, effectue le retrait et
communique le solde à la banque. Les deux threads doivent être issus de la même classe, 
les objets de la classe compte sont instanciés tout comme les threads parle programme 
principal. 
1 - Quel doit être le solde du compte ? 
  - Qu’observez vous ? 
  - Ce résultat est il toujours le même ?

2 - Modifier  le  programme  afin  de  mettre  en   ́evidence,  de  manière  plus   ́evidente  le problème de concurrence 
  - (par exemple en introduisant une durée d’attente aléatoire au moment du retrait).

3 - Identifier la section critique, puis en utilisant le mécanisme de moniteur (constructions synchronized),
  - modifier votre programme, afin qu’à un instant donné, un seul thread puisse accéder au contenu du compte. 
  - Vérifier que son exécution est correcte.

4 - Faire de même en utilisant la notion de verrou (objetLock), voir encadré 3 : 
  - qui dispose du verrou ? qui l’utilise ? Critiquer l’utilisation du verrou.

5 - Modifier le code pour avoir une méthode retrait sur la classe compte, contrôlé par un moniteur.
*/

public class exo4{
  public static void main(String[] args){
    BankAccount bankAccount = new BankAccount(800000);
    ATMThread ATMThread10 = new ATMThread(bankAccount,10);
    ATMThread ATMThread50 = new ATMThread(bankAccount,50);
    try{
      ATMThread10.start();
      ATMThread50.start();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public static class ATMThread extends Thread{

    private BankAccount bankAccount;
    private int amountToWidthdraw;

    ATMThread(BankAccount bankAccount,int amountToWidthdraw){
      this.bankAccount = bankAccount;
      this.amountToWidthdraw = amountToWidthdraw;
    }

    public synchronized void run(){
      try{
        for(int x=0;x<1000;x++){
          widthdraw();
        }
      }catch(Exception e){
        e.printStackTrace();
      }
    }

    public synchronized void widthdraw(){
      int sold = bankAccount.getSold();
      sold-=amountToWidthdraw;
      bankAccount.setSold(sold);
    }
  }

  public static class BankAccount{

    private int sold;

    BankAccount(int sold){
      this.sold = sold;
    }

    public int getSold(){
      return this.sold;
    }

    public void setSold(int newSold){
      this.sold = newSold;
      System.out.println("New sold : "+this.sold);
    }
  }
}
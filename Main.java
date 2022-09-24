import javax.swing.plaf.TableHeaderUI;

public class Main{
    public static void main(String[] args){
        Account account = new Account();
        Deposit deposit = new Deposit(account);
        Withdrawl withdrawl = new Withdrawl(account);
        new Thread(deposit).start();
        new Thread(withdrawl).start();
    }
}

class Account extends Thread{
    private int balance = 100;
    private int withdrawl = 1500;

    public synchronized void toDeposit(){
        while(balance >= withdrawl){
            try{
                wait();
            }
            catch (Exception ex){
            }
        }
        balance += 100;
        System.out.println("Баланс пополнен на 100.");
        System.out.println("Баланс сейчас " + balance);
        notify();
    }

    public synchronized void toWithdrawl(){
        while(balance < withdrawl){
            try {
                wait();
            }
            catch (Exception ex){

            }
        }
        balance -= 1500;
        System.out.println("С карты списано 1500");
        System.out.println("Баланс сейчас " + balance);
        notify();
    }
}

class Deposit extends Thread{

    Account account;
    Deposit(Account account){
        this.account=account;
    }
    public void run(){
        for (int i = 1; i < 30; i++) {
            account.toWithdrawl();
        }
    }
}
class Withdrawl extends Thread{

    Account account;
    Withdrawl(Account account){
        this.account=account;
    }
    public void run(){
        for (int i = 1; i < 30; i++) {
            account.toDeposit();
        }
    }
}
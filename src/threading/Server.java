package threading;

public class Server implements Runnable{

    public void run() {
        while(true){
            System.out.println("Hallo vanuit de Server");
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

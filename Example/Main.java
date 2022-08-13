//SENG2250 Assignment 3
//Sebastian Hadley c3349742
//Main
import java.math.*;
class Main{

  //Port used for communcation
  private static int Port = 2000;
  //Create Server
  private static Server server = new Server(2000,182927);
  private static Thread thread_server = new Thread(server);

  //Create Client
  private static Client client = new Client(2000,"Aiklx0ELIWVGiqhmEqf0");
  private static Thread thread_client = new Thread(client);

  public static void main(String[] args)
  {
    System.out.println();

    thread_client.start();
    thread_server.start();


  }

}

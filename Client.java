//Client Class for SENG2250 A3
//Sebastian Hadley c3349742 
import java.math.*;
import java.security.*;
import java.util.Random;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
public class Client implements Runnable
{
  //CTR
  private BigInteger CTRiv,CTRcv;
  private boolean finished;
  //Create Connection
  private boolean connected;
  //Communication Port
  private int port;
  //ClientID
  private String client_ID;
  private Socket client_socket;

  public static void main(String[] args)
  {
    
    int port = 4202;
    if(args != null){
      port = Integer.parseInt(args[0]);
    }
    final Client client = new Client(port);
  }
  public Client(int port)
  {
    this.port = port;
    finished = false;
    connected = false;

  }
  public void run()
  {
    try(Socket client_socket = new Socket("localhost",port))
    {

      //Variables for use with recieving messages
      int session_ID,serverID;
      boolean messages = false;
      //Generate Input and Output
      InputStreamReader input = new InputStreamReader(client_socket.getInputStream());
      BufferedReader buffered = new BufferedReader(input);
      PrintWriter output = new PrintWriter(client_socket.getOutputStream(),true);

    }
    catch(UnknownHostException Null_Server)
    {
      print("No Such Server");
    }
    catch(IOException exception)
    {
      print("Error");
    }
  }

  public static void Send(String msg)
  {
    
  }
  private void pause()
  {
    try
    {
      Thread.sleep(10);
    }
    catch
    (InterruptedException e){}
  }
  private void print(String output)
  {
    System.out.println();
    System.out.println(output);
    try{
      Thread.sleep(5);
    }
    catch(InterruptedException fail){}
  }


}

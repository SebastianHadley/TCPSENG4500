//Server Class for SENG2250 A3
//Sebastian Hadley c3349742
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.io.OutputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class Server implements Runnable
{
  public static void main(String[] args)
  {
    final Server server = new Server(Integer.parseInt(args[0]));
    server.run();
  }
  //port
  private int port;

  //Server Information
  private int server_ID;
  private int session_ID;

  //Checks Completion
  private boolean finished;
  private boolean connection;
  //Create Library
  //CreateConnection


  public Server(int port)
  {
    Random rand = new SecureRandom();
    this.session_ID = session_ID;
    this.port = port;
    finished = false;
    server_ID = 1;
  }
  public void run()
  {
    try(ServerSocket server_socket = new ServerSocket(port))
    {

      //Variables for use with recieving messages
      int time_waiting;
      String recieved;
      print("Server: Connected");

      Socket socket = server_socket.accept();

      //Generate in Input and Output
      //Maybe wrap in datainputstream
      InputStreamReader input = new InputStreamReader(socket.getInputStream());
      BufferedReader buffered = new BufferedReader(input);
      PrintWriter output = new PrintWriter(socket.getOutputStream(),true);

      //Step 1 - Recieve Hello Setup Message From Client.
      recieved = buffered.readLine();


      try{
        Thread.sleep(5);
      }catch(InterruptedException e)
      {}
      //Step 3- Receive ClientID

      print("Step 3 - Server: Client_ID & Nonce <- Client");

    }
    catch(IOException exception)
    {
      print("Error");
    }
    catch(NoSuchAlgorithmException error)
    {
      print("no Algorithm");
    }
  }

  private void pause()
  {
    try
    {
      Thread.sleep(2);
    }
    catch
    (InterruptedException e){}
  }

  public void print(String output)
  {
    System.out.println();
    System.out.println(output);
    try{
      Thread.sleep(5);
    }
    catch(InterruptedException fail){}
  }
}

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
    int port = 4202;
    if(args != null){
      port = Integer.parseInt(args[0]);
    }
    final Server server = new Server(port);
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
    this.port = port;
    finished = false;
    server_ID = 1;
  }
  public void run()
  {
    try(ServerSocket server_socket = new ServerSocket(port))
    {
      server_socket.accept();
    }

    catch(IOException exception)
    {
      print("Error");
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;


public class ServerSample {
    public static void main(String[] args) {
        try (
	    ServerSocket ss = new ServerSocket(4500);
            Socket s = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        ) {
	    System.out.println("CONNECTION OPEN");
        String msg;
	    while (!(msg = in.readLine()).equals("TAX"));
	    outputMessage(msg);
        out.println("TAX: OK");
        while(!(msg = in.readLine()).equals("END") || (msg = in.readLine()).equals("BYE")) {
            switch (msg) {
                case "BYE":
                    int x = 0;
                    break;
                case "STORE":
                    while(in.readLine() == null)
                    {

                    }
                    int a = 1;
                    break;
                case "QUERY":
                    int b = 2;
            }
        }
        while(!(msg = in.readLine()).equals("STORE"));

        } catch (Exception e) {  // You should have some better exception handling
            e.printStackTrace();
        }
    }


    public static void outputMessage(String msg)
    {
        System.out.format("CLIENT: %s\n", msg);
    }
    
}

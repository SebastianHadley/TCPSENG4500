import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class ClientSample {
    public static void main(String[] args) {
        String inMsg = "";
        try (
                Socket s = new Socket(InetAddress.getLocalHost().getHostAddress(), 4500);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        ) {
            while(s.isConnected())
            {
                inMsg = "";
                String outMsg;
                while((outMsg = input.readLine()).isBlank());
                switch(outMsg){
                    case "TAX":
                        out.println(outMsg);
                        while(!(inMsg = in.readLine()).equals("TAX: OK"));
//                      System.out.println("done");
                        break;
                    case "STORE":
                        out.println(outMsg);
                        System.out.println("Input the tax bracket details:");
                        out.println(input.readLine());
                        out.println(input.readLine());
                        out.println(input.readLine());
                        out.println(input.readLine());
                        while((inMsg = in.readLine()) == null);
                        break;
                    case "QUERY":
                        out.println(outMsg);
                        System.out.println(inMsg+"boom");
                       while(!(inMsg = in.readLine()).equals("QUERY: OK")) {
                           if(!inMsg.isBlank()){
                               outputMessage(inMsg);
                           }
                       }
                       break;
                    case "BYE":
                        out.println(outMsg);
                        System.exit(0);
                    default:
                        out.println(outMsg);
                        while((inMsg = in.readLine()).isBlank());
                }
                outputMessage(inMsg);
            }

            out.println(input.readLine());
        } catch (Exception e) {  // You should have some better exception handling
            e.printStackTrace();
        }
    }

    public static void outputMessage(String msg){

        System.out.format("SERVER: %s\n", msg);
    }
}

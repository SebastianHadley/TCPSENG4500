import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException; 
public class TaxClient {
    public static void main(String[] args) throws UnknownHostException {
        String inMsg = "";
        String address = "";
        int port = 4500;
        if (args.length > 0) {
            for (String string : args) {
                if (string.contains(".")) {
                    address = string;
                } else {
                    port = Integer.parseInt(string);
                }
            }
        }
        if (address.isBlank()) {
            address = InetAddress.getLocalHost().getHostAddress();
        }
        try (

                Socket s = new Socket(address, port);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        ) {
            String outMsg;

            while(!(outMsg = input.readLine()).equals("TAX"));
            out.println(outMsg);
            while(!(inMsg = in.readLine()).equals("TAX: OK"));
            outputMessage(inMsg);
            while (s.isConnected()) {
                inMsg = "";
                outMsg = input.readLine();
                if(outMsg.isBlank()){continue;}
                switch (outMsg) {
                    case "STORE":
                        out.println(outMsg);
                        System.out.println("Input the tax bracket details:");
                        out.println(input.readLine());
                        out.println(input.readLine());
                        out.println(input.readLine());
                        out.println(input.readLine());
                        while ((inMsg = in.readLine()) == null && s.isConnected())
                            ;
                        break;
                    case "QUERY":
                        out.println(outMsg);
                        while (!(inMsg = in.readLine()).equals("QUERY: OK") && s.isConnected()) {
                            if (!inMsg.isBlank()) {
                                outputMessage(inMsg);
                            }
                        }
                        break;
                    case "END":
                    case "BYE":
                        out.println(outMsg);
                        while ((inMsg = in.readLine()).isBlank() && s.isConnected())
                            ;
                        outputMessage(inMsg);
                        s.close();
                        System.exit(0);
                    default:
                        out.println(outMsg);
                        while ((inMsg = in.readLine()).isBlank() && s.isConnected())
                            ;
                }
                outputMessage(inMsg);
            }
        } catch (Exception e) { // You should have some better exception handling
            e.printStackTrace();
        }
    }

    public static void outputMessage(String msg) {

        System.out.format("SERVER: %s\n", msg);
    }
}
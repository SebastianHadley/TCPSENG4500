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
            while (s.isConnected()) {
                inMsg = "";
                String outMsg;
                outMsg = input.readLine();
                if(outMsg.isBlank()){continue;}
                switch (outMsg) {
                    case "TAX":
                        out.println(outMsg);
                        while (!(inMsg = in.readLine()).equals("TAX: OK"))
                            ;
                        // System.out.println("done");
                        break;
                    case "STORE":
                        out.println(outMsg);
                        System.out.println("Input the tax bracket details:");
                        out.println(input.readLine());
                        out.println(input.readLine());
                        out.println(input.readLine());
                        out.println(input.readLine());
                        while ((inMsg = in.readLine()) == null)
                            ;
                        break;
                    case "QUERY":
                        out.println(outMsg);
                        System.out.println(inMsg + "boom");
                        while (!(inMsg = in.readLine()).equals("QUERY: OK")) {
                            if (!inMsg.isBlank()) {
                                outputMessage(inMsg);
                            }
                        }
                        break;
                    case "END":
                    case "BYE":
                        out.println(outMsg);
                        while ((inMsg = in.readLine()).isBlank())
                            ;
                        outputMessage(inMsg);
                        s.close();
                        System.exit(0);
                    default:
                        out.println(outMsg);
                        while ((inMsg = in.readLine()).isBlank())
                            ;
                }
                outputMessage(inMsg);
            }

            out.println(input.readLine());
        } catch (Exception e) { // You should have some better exception handling
            e.printStackTrace();
        }
    }

    public static void outputMessage(String msg) {

        System.out.format("SERVER: %s\n", msg);
    }
}

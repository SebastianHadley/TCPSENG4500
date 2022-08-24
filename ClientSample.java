import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientSample {
    public static void main(String[] args) {
        String msg = "";
        String outMsg = "";
        try (
            Socket s = new Socket("127.0.0.1", 4500);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        ) {
            while(s.isConnected())
            {
                msg = "";
                switch((outMsg = input.readLine())){
                    case "TAX":
                        out.println(outMsg);
                        while(!(msg = in.readLine()).equals("TAX: OK"));
                        break;
                    case "STORE":
                        out.println(outMsg);
                        for(int i = 0; i < 4; i++)
                        {

                        }
                        break;

                }
                outputMessage(msg);
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

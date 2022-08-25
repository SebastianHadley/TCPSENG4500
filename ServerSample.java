import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;


public class ServerSample {
    int socketnumber;
    ArrayList<TaxBracket> brackets = new ArrayList<TaxBracket>();


    public static void main(String[] args) {
       ServerSample server = new ServerSample(4500);
       server.go();
    }

    //Constructor so that not static methods can be used.
    public ServerSample(int socket)
    {
        socketnumber = socket;
    }

    //used to allow use of non static methods.
    public void go()
    {
        String msg;
        try (
                ServerSocket ss = new ServerSocket(4500);
                Socket s = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true)
        )
        {
            while (!(msg = in.readLine()).equals("TAX"));
            outputMessage(msg);
            out.println("TAX: OK");
            while(s.isConnected()) {
                {
                    switch (msg = in.readLine()) {
                        case "BYE":
                            int x = 0;
                            break;
                        case "STORE":
                            System.out.println("hello");
                            String[] taxInfo = new String[4];
                            for (int i = 0; i < 4; i++) {
                                while ((msg = in.readLine()).isBlank());
                                taxInfo[i] = msg;
                            }
                            setupBracket(taxInfo);
                            out.println("STORE: OK");
                            msg = "";
                            break;
                        case "QUERY":
                            outputMessage((msg));
                            System.out.println(brackets.size());
                            for(int i = 0; i < brackets.size(); i++){
                                out.println(brackets.get(i).outputFormat());
                            }
                            out.println("QUERY: OK");
                            break;
                    }
                }
            }
        } catch (Exception e) {  // You should have some better exception handling
            e.printStackTrace();
        }
    }


    public boolean setupBracket(String[] taxInfo) {
        System.out.println("start checking");
        TaxBracket temporary = new TaxBracket(taxInfo[0], taxInfo[1],taxInfo[2], taxInfo[3]);
        if(brackets.size() == 0) {
            brackets.add(temporary);
            return true;
        }
        for(int i = 0; i < brackets.size(); i++) {
            TaxBracket check = brackets.get(i);
            int checkNumber = brackets.get(i).inRange(temporary.getBottom(), temporary.getTop());
            System.out.println(checkNumber+"is number");

            if (brackets.size() < 10) {
                if (checkNumber == 0) {
                    if (i == brackets.size() - 1) {
                        if(brackets.get(brackets.size()-1).getTop() < temporary.getBottom()) {
                            System.out.println("check");
                            brackets.add(temporary);
                        }
                    }
                }
                if (checkNumber == 1) {
                    if (brackets.size() == i + 1) {
                        brackets.get(i).setTop(temporary.getBottom() - 1);
                        brackets.add(temporary);
                    }
                    else
                    {
                        brackets.get(i).setTop(temporary.getBottom() - 1);
                        brackets.add(i+1,temporary);
                    }

                }
                else if (checkNumber == 2) {
     /*               if (brackets.get(i).getTop() <= temporary.getTop()) {
                        brackets.remove(i);
                        brackets.add(i,temporary);
                    } else {*/

                        brackets.get(i).setBottom(temporary.getTop() + 1);
                        break;
//                        brackets.add(i, temporary);
                    }

                else if (checkNumber == 3) {
                    System.out.println("3 in");
                    if(brackets.get(i).getBottom() == temporary.getBottom()){
                        brackets.add(i,temporary);
                        brackets.get(i+1).setBottom(temporary.getTop()+1);
                        break;
                    }
                    else if(brackets.get(i).getTop() == temporary.getTop())
                    {
                        brackets.get(i).setTop(temporary.getBottom()-1);
                        brackets.add(i+1,temporary);
                        break;
                    }
                   else if(brackets.size() < 9) {
                        TaxBracket extra1 = new TaxBracket(
                                String.valueOf(brackets.get(i).getBottom()),
                                String.valueOf(brackets.get(i).getTop()),
                                String.valueOf(brackets.get(i).getBasetax()),
                                String.valueOf(brackets.get(i).getTaxDollarInt())
                        );
                        brackets.add(i + 1, extra1);
                        brackets.get(i + 1).setBottom(temporary.getTop() + 1);
                        brackets.get(i).setTop(temporary.getBottom() - 1);
                        brackets.add(i + 1, temporary);
                        break;
                    }
                }

                else if (checkNumber == 4) {
                    brackets.set(i, temporary);
                }

                else if(checkNumber == 5)
                {
                    System.out.println("in 5");
                    brackets.remove(i);
                    i--;
                }

            }else {
                if (brackets.get(i).inRange(temporary.getBottom(), temporary.getTop()) == 4) {
                    brackets.set(i, temporary);
                    break;
                }
            }
        }
        return true;
    }
    public static void outputMessage(String msg) {
        System.out.format("CLIENT: %s\n", msg);
    }
}

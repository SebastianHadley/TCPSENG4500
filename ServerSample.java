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
    public ServerSample(int socket) {
        socketnumber = socket;
    }

    //used to allow use of non static methods.
    public void go() {
        String msg;
        try (
                ServerSocket ss = new ServerSocket(4500);
                Socket s = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true)
        ) {
            while(ss.isBound()) {
                while (!(msg = in.readLine()).equals("TAX")) ;
                outputMessage(msg);
                out.println("TAX: OK");
                while (s.isConnected()) {
                    {
                        while((msg = in.readLine()).isBlank());
                        outputMessage(msg);
                        switch (msg) {
                            case "STORE":
                                String[] taxInfo = new String[4];
                                for (int i = 0; i < 4; i++) {
                                    while ((msg = in.readLine()).isBlank()) ;
                                    taxInfo[i] = msg;
                                }
                                boolean check = setupBracket(taxInfo);
                                if (check) {
                                    out.println("STORE: OK");
                                } else
                                    out.println("STORE: FULL");
                                msg = "";
                                break;
                            case "QUERY":
                                for (int i = 0; i < brackets.size(); i++) {
                                    out.println(brackets.get(i).outputFormat());
                                }
                                out.println("QUERY: OK");
                                break;
                            case "BYE":
                                s.close();
                                break;
                            default:
                                int income = Integer.parseInt(msg);
                                out.println(getIncomeTax(income));
                        }
                    }
                }
            }
        } catch (Exception e) {  // You should have some better exception handling
            e.printStackTrace();
        }
    }


    public boolean setupBracket(String[] taxInfo) {
        TaxBracket temporary = new TaxBracket(taxInfo[0], taxInfo[1], taxInfo[2], taxInfo[3]);
        if (brackets.size() == 0) {
            brackets.add(temporary);
            return true;
        }
        for (int i = 0; i < brackets.size(); i++) {
            TaxBracket check = brackets.get(i);
            int checkNumber = brackets.get(i).inRange(temporary.getBottom(), temporary.getTop());
            System.out.println(checkNumber);
            if (brackets.size() < 10) {
                if (checkNumber == 0) {
                    if (i == brackets.size() - 1) {
                        if (brackets.get(brackets.size() - 1).getTop() < temporary.getBottom()) {
                            brackets.add(temporary);
                        }
                    }
                }
                //If the new bracket bottom is between bracket at i's values.
                if (checkNumber == 1) {
                    if (brackets.size() == i + 1) {
                        if (brackets.get(i).getBottom() == 0 && temporary.getBottom() == 0) {
                            brackets.set(i, temporary);
                        } else {
                            brackets.get(i).setTop(temporary.getBottom() - 1);
                            brackets.add(i+1,temporary);
                            i++;
                        }
                    } else {
                        brackets.get(i).setTop(temporary.getBottom() - 1);
                        brackets.add(i + 1, temporary);
                        i++;
                    }
                //If the new bracket top is between bracket at i's values
                } else if (checkNumber == 2) {
                    brackets.get(i).setBottom(temporary.getTop() + 1);
                    break;
                //If the new brackets bottom and top values are between the bracket at i's
                } else if (checkNumber == 3) {
                    if (brackets.get(i).getBottom() == temporary.getBottom()) {
                        brackets.add(i, temporary);
                        brackets.get(i + 1).setBottom(temporary.getTop() + 1);
                        break;
                    } else if (brackets.get(i).getTop() == temporary.getTop()) {
                        brackets.get(i).setTop(temporary.getBottom() - 1);
                        brackets.add(i + 1, temporary);
                        break;
                    } else if (brackets.size() < 9) {
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
                } else if (checkNumber == 4) {
                    brackets.set(i, temporary);
                    break;
                } else if (checkNumber == 5) {
                    if(i == brackets.size()-1){
                        brackets.set(i,temporary);
                        break;
                    }
                    brackets.remove(i);
                    i--;

                }

            } else {
                if (brackets.get(i).inRange(temporary.getBottom(), temporary.getTop()) == 4) {
                    brackets.set(i, temporary);
                    break;
                }
                return false;
            }
        }
        return true;
    }

    public String getIncomeTax(int income)
    {
        for(int i=0; i <brackets.size(); i ++)
        {
            if(brackets.get(i).inRange(income)){
               return "TAX IS "+String.valueOf(brackets.get(i).calculateTax(income));
            }
        }
        return "I DON'T KNOW "+income;
    }
    public static void outputMessage(String msg) {
        System.out.format("CLIENT: %s\n", msg);
    }
}

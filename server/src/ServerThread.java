import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ServerThread implements Runnable {

    Socket socket;
    private BufferedReader klijentInput = null;
    private PrintWriter klijentOutput = null;
    volatile boolean  posotjiIme=true;
    String ime = null;
    String poruka = null;


    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {


            try {
                System.out.println("uso");
                klijentInput=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                klijentOutput=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
                while(posotjiIme){
                    klijentOutput.println("Unesite Vase ime: ");
                    ime=klijentInput.readLine();
                    if(Main.imena.contains(ime)){
                        klijentOutput.println("Korisnik vec posotji!");
                    }
                    else {
                        posotjiIme=false;
                    }
                }
                Main.imena.add(ime);
                for(ServerThread serverThread:Main.klijneti){
                    serverThread.klijentOutput.println("Korisnik "+ime+" se pridruzio cetu!");
                }
                Main.klijneti.add(this);
                for(String poruka:Main.poruke){
                    klijentOutput.println(poruka);
                }

                klijentOutput.println("Dobrodosao "+ime);
                while(true){
                    poruka=klijentInput.readLine();
                    if(poruka.equalsIgnoreCase("izlaz")){
                        Main.imena.remove(ime);
                        Main.klijneti.remove(this);
                        for(ServerThread serverThread:Main.klijneti){
                            serverThread.klijentOutput.println("Korisnik "+ime+" je napustio grupu.");
                        }
                        this.klijentOutput.println("izlaz");
                        socket.close();
                        klijentInput.close();
                        klijentOutput.close();
                        break;
                    }
                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("<yyyy-mm-dd hh:mm:ss>");
                    String strDate = dateFormat.format(date);
                    String[] proveraCenzura=poruka.split(" ");
                    String zaNovuPoruku="";
                    for(String s:proveraCenzura){
                        if(Main.cenzura.contains(s)){
                            String zvezdice="*";
                            zvezdice=zvezdice.repeat(s.length()-2);
                            zaNovuPoruku=zaNovuPoruku+s.charAt(0)+zvezdice+s.charAt(s.length()-1)+" ";
                        }
                        else{
                            zaNovuPoruku=zaNovuPoruku+s+" ";
                        }
                    }
                    String novaPoruka=strDate+":"+ime+": "+zaNovuPoruku;
                    if(Main.poruke.size()>100){
                        Main.poruke.remove(0);
                    }
                    Main.poruke.add(novaPoruka);
                    for(ServerThread serverThread:Main.klijneti){
                        serverThread.klijentOutput.println(novaPoruka);
                    }


                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }



    }
}

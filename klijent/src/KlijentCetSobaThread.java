import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class KlijentCetSobaThread implements Runnable{

    Socket socket = null;
    BufferedReader in = null;


    public KlijentCetSobaThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true){
                String poruka=in.readLine();
                if(poruka.equalsIgnoreCase("izlaz")){
                    break;
                }
                System.out.println(poruka);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

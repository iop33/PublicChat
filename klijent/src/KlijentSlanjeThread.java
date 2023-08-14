import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class KlijentSlanjeThread implements Runnable{

    Socket socket = null;
    PrintWriter out = null;
    String poruka = null;

    public KlijentSlanjeThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String unos=scanner.nextLine();
                out.println(unos);
                if(unos.equalsIgnoreCase("izlaz")){
                    break;
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

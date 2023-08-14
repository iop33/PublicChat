import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {

    public static final int PORT = 8999;
    public static List<String>poruke=new CopyOnWriteArrayList<>();
    public static List<ServerThread>klijneti=new CopyOnWriteArrayList<>();
    public static List<String>cenzura=new CopyOnWriteArrayList<>();
    public static List<String>imena=new CopyOnWriteArrayList<>();

    public static void main(String[] args) {

        try {
            cenzura.add("rat");
            cenzura.add("Hitler");
            cenzura.add("pobuna");
            cenzura.add("tuca");
            cenzura.add("pucao");
            ServerSocket serverSocket = new ServerSocket(Main.PORT);
            System.out.println("Server ocekuje konekcije");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Server primio konekciju");
                Thread serverThread = new Thread(new ServerThread(socket));
                serverThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

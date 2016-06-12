import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Gary on 16/5/28.
 */
public class Server implements Runnable{
    private Thread thread;
    private ServerSocket servSock;
    private String result;
    private Frame frame;
    private TextArea text;

    public Server(){
        frame = new Frame("ServerWindow");
        frame.setLayout(new BorderLayout());
        frame.setSize(500, 200);

        text = new TextArea();
        text.setColumns(500);
        text.setRows(200);
        text.setText("Waiting for client......");
        text.setVisible(true);
        frame.setVisible(true);
        frame.add(text);

        try {
            // Detect server ip
            InetAddress IP = InetAddress.getLocalHost();
            System.out.println("IP of my system is := "+IP.getHostAddress());
            System.out.println("Waitting to connect......");

            // Create server socket
            servSock = new ServerSocket(2000);

            // Create socket thread
            thread = new Thread(this);
            thread.start();
        } catch (java.io.IOException e) {
            System.out.println("Socket start error !");
            System.out.println("IOException :" + e.toString());
        } finally{

        }
    }

    @Override
    public void run(){
        // Running for waitting multiple client
        while(true){
            try{
                // After client connected, create client socket connect with client
                Socket clntSock = servSock.accept();
                InputStream in = clntSock.getInputStream();

                System.out.println("Connected!!");

                // Transfer data
                byte[] b = new byte[1024];
                int length;

                length = in.read(b);
                result = new String(b);
                System.out.println("[Server Said]" + result);
                text.setText(result);

            }
            catch(Exception e){
                //System.out.println("Error: "+e.getMessage());
            }
        }
    }
}

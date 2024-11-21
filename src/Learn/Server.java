package Learn;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JFrame;



public class Server extends JFrame implements Runnable {
    private Socket s=null;
    private ServerSocket ss=null;
    private ArrayList clients=new ArrayList();


    public Server ()throws Exception{
        this.setTitle("Server");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        ss=new ServerSocket(9999);
        new Thread(this).start();
        }

        public void run(){
            try {
                while(true){
                    s=ss.accept();
                    ChatThread ct=new ChatThread(s);
                    clients.add(ct);
                    ct.start();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        class ChatThread extends Thread{
            private Socket s=null;
            private BufferedReader br=null;
            public PrintStream ps=null;
            public ChatThread(Socket s)throws Exception{
                this.s=s;
                br=new BufferedReader(new InputStreamReader(s.getInputStream()));
                ps=new PrintStream(s.getOutputStream());
            }
            public void run(){
                try {
                    while(true){
                        String str=br.readLine();
                        sendMessage(str);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }

        public void sendMessage(String msg){
        	for(int i=0;i<clients.size();i++){
        		ChatThread ct=(ChatThread)clients.get(i);
        		ct.ps.println(msg);
        	}
        }

        public static void main(String[] args) throws Exception{
            Server server=new Server();
        }


}
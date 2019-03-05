import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;



public class Server extends Thread
{
    Socket clientSocket;

    Server(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public static void main(String[] args)
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(1717);
            while (true)
            {
                System.out.println("Waiting for connections on localhost:1717");
                Socket cSock = serverSocket.accept();
                new Thread(new Server(cSock)).start();
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public void run()
    {
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

            for (int i = 10; i >= 0; i--)
            {
                oos.writeObject(i + " bottles of beer on the wall");
                oos.flush();
                Thread.sleep(1000);
            }

            clientSocket.close();
        } 
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }
    }
}
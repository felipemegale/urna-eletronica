import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;



public class Server extends Thread
{
    Socket clientSocket;
    static volatile int[] votes;

    Server(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public static void main(String[] args)
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(1717);
            votes = new int[5];
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
            try
            {
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                String data;
    
                while (true)
                {
                    data = ois.readObject().toString();
                    System.out.println(data + " received from Thread " + this.getId());
                    computeVote(Integer.parseInt(data));
                }
            }
            catch (Exception e)
            {
                clientSocket.close();
                System.out.println(" -S- Conexao finalizada - Thread " + this.getId());

                File results = new File("results.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(results));

                for (int i = 0; i < votes.length; i++)
                {
                    bufferedWriter.write("Candidate " + i + " - " + votes[i] + "\n");
                }

                bufferedWriter.close();
                // e.printStackTrace();
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public static synchronized void computeVote(int candidate) {
        votes[candidate]++;
        System.out.println("Candidate " + candidate + " - " + votes[candidate]);
    }
}
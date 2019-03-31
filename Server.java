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
            //Abre uma conexao socket
            ServerSocket serverSocket = new ServerSocket(1717);

            //Vetor para armazenar quantidade de votos para cada candidato
            votes = new int[5];

            //Enquanto existem conexões a serem realizadas, espera e cria-se novas threads
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
                //Fluxo de entrada para receber os dados
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

                //Computa os votos para cada candidato e escreve no arquivo results.txt
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

    // Recebe o numero do candidato como parametro e acrescenta um voto para o mesmo
    public static synchronized void computeVote(int candidate) {
        votes[candidate]++;
        System.out.println("Candidate " + candidate + " - " + votes[candidate]);
    }
}
import java.net.Socket;
import java.io.*;

public class Client {
    public static void main(String[] args) {
        try {
            //Endereco do servidor
            String IPServidor = "127.0.0.1";
            int PortaServidor = 1717;
            String vote;
            
            //Estabelece uma conexao com o servidor
            System.out.println(" -C- Conectando ao servidor ->" + IPServidor + ":" +PortaServidor);
            Socket sockCli = new Socket (IPServidor,PortaServidor);
            System.out.println(" -C- Detalhes conexao :" + sockCli.toString()); //Detalha melhor sobre a conexao
            
            //Cria um pacote de saida para enviar mensagens, associando-a conexao(c)
            ObjectOutputStream sCliOut = new ObjectOutputStream(sockCli.getOutputStream());

            //Le no arquivo os votos que um Cliente deseja computar
            File votes = new File(args[0]);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(votes));
            
            while ((vote = bufferedReader.readLine()) != null) {
                sCliOut.writeObject(vote);//Escreve o voto no pacote
                System.out.println(" -C- Enviando mensagem...");
                sCliOut.flush(); //Envia o pacote para o servidor
                Thread.sleep(150);
            }

            bufferedReader.close();
            sockCli.close();
            
        } catch(Exception e) {
            System.out.println(" -C- O seguinte problema ocorreu : \n" + e.toString());
        }
    }
}
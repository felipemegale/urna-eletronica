import java.net.Socket;
import java.io.*;

public class Client {
    public static void main(String[] args) {
        try {
            //ENDERECO DO SERVIDOR
            String IPServidor = "127.0.0.1";
            int PortaServidor = 1717;
            String vote;
            
            //ESTABELECE CONEXAO COM SERVIDOR
            System.out.println(" -C- Conectando ao servidor ->" + IPServidor + ":" +PortaServidor);
            Socket sockCli = new Socket (IPServidor,PortaServidor);
            System.out.println(" -C- Detalhes conexao :" + sockCli.toString()); //DETALHAMENTO (EXTRA)
            
            //CRIA UM PACOTE DE SAIDA PARA ENVIAR MENSAGENS, ASSOCIANDO-O A CONEXAO (c)
            ObjectOutputStream sCliOut = new ObjectOutputStream(sockCli.getOutputStream());

            File votes = new File(args[0]);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(votes));
            
            while ((vote = bufferedReader.readLine()) != null) {
                sCliOut.writeObject(vote);//ESCREVE NO PACOTE
                System.out.println(" -C- Enviando mensagem...");
                sCliOut.flush(); //ENVIA O PACOTE
                Thread.sleep(150);
            }

            bufferedReader.close();
            sockCli.close();
            
        } catch(Exception e) {
            System.out.println(" -C- O seguinte problema ocorreu : \n" + e.toString());
        }
    }
}
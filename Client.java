import java.net.Socket;
import java.io.*;

public class Client {
    public static void main(String[] args) {
        try {
            //ENDERECO DO SERVIDOR
            String IPServidor = "127.0.0.1";
            int PortaServidor = 1717;
            
            //ESTABELECE CONEXAO COM SERVIDOR
            System.out.println(" -C- Conectando ao servidor ->" + IPServidor + ":" +PortaServidor);
            Socket sockCli = new Socket (IPServidor,PortaServidor);
            System.out.println(" -C- Detalhes conexao :" + sockCli.toString()); //DETALHAMENTO (EXTRA)
            
            //CRIA UM PACOTE DE SAIDA PARA ENVIAR MENSAGENS, ASSOCIANDO-O A CONEXAO (c)
            // ObjectOutputStream sCliOut = new ObjectOutputStream(socktCli.getOutputStream());
            // sCliOut.writeObject("MENSAGEM TESTE");//ESCREVE NO PACOTE
            // System.out.println(" -C- Enviando mensagem...");
            // sCliOut.flush(); //ENVIA O PACOTE
            
            //CRIA UM PACOTE DE ENTRADA PARA RECEBER MENSAGENS, ASSOCIADO A CONEXAO (c)
            ObjectInputStream sCliIn = new ObjectInputStream (sockCli.getInputStream());
            System.out.println(" -C- Recebendo mensagem...");

            try
            {
                while (!sockCli.isClosed())
                {
                    String strMsg = sCliIn.readObject().toString(); //ESPERA (BLOQUEADO) POR UM PACOTE
                    //PROCESSA O PACOTE RECEBIDO
                    System.out.println(" -C- Mensagem recebida: " + strMsg);
                }
            }
            catch (EOFException eofe)
            {
                //FINALIZA A CONEXAO
                sockCli.close();
                System.out.println(" -C- Conexao finalizada...");
                //eofe.printStackTrace();
            }
            
            
        } catch(Exception e) {
            System.out.println(" -C- O seguinte problema ocorreu : \n" + e.toString());
        }
    }
}
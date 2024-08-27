import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
public class Main {

     public static Filme ler(Filme f){

          FileInputStream arq;
          DataInputStream dis; 

          byte[] ba;
          int len;
          Filme f_temp = new Filme();

           try{
               arq =  new FileInputStream("filmes.db");
               dis = new DataInputStream(arq);
               
               while (dis.available() > 0) { //ler ate eof

                    byte lapide = dis.readByte();
                    len = dis.readInt(); //Tamanho do registro

                    //ler se nao for um registro excluido
                    if(lapide != 1){
                         ba = new byte[len];
                         dis.read(ba);                        
                         f_temp.fromByteArray(ba); 
                         if (f_temp.id == f.id) {
                              return f_temp;
                         }
                    }

                    //pular se registro tiver sido excluido
                    else{
                         dis.skipBytes(len);
                    } 
               } 
               dis.close();
          } catch(Exception e)
          {
               System.out.println(e.getMessage());
          } 
          return null; //retornar nulo se não encotrar id
     }

     public static boolean remover(int id){    

          
          return false;
     }

     public static void main(String[] args) {
          
          String csv = "imdb_movies.csv";
          String linha;
          Filme filme = new Filme();

          FileOutputStream arq;
          DataOutputStream dos; 

          byte[] ba;
          
     
          SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");

          //lendo arquivo csv
           try (BufferedReader br = new BufferedReader(new FileReader(csv))) {

               arq = new FileOutputStream("filmes.db");//criar arq
               dos = new DataOutputStream(arq);//escrever no arq

               linha = br.readLine();//pular primeira linha do csv

            while ((linha = br.readLine()) != null)
            {
               String[] dados = linha.split(";");
               filme.id = Integer.parseInt(dados[0]);
               filme.name = dados[1];
               //filme.date = dados[2];
               try {
                    filme.date = inputFormat.parse(dados[2]);
               } catch (ParseException e) {
                    System.out.println("Erro ao converter a data: " + e.getMessage());
                    e.printStackTrace();
               }
               filme.score = Float.parseFloat(dados[3]);
               //filme.genre = dados[4];
               filme.genre = new ArrayList<>();
               String[] g_tmp = dados[4].split(",");
                for (String g : g_tmp) {
                    filme.genre.add(g.trim()); 
                }
               filme.status = dados[5];
               filme.orig_lang = dados[6];
               filme.country = dados[7];    
               filme.toString();

               // Convertendo para array de bytes
               ba = filme.toByteArray();
               //  lápide 0 para registro válido
               dos.writeByte(0); 
               // Escrevendo o tamanho do registro e o registro
               dos.writeInt(ba.length); 
               dos.write(ba);
            }
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  
     }
}


import java.io.FileOutputStream; 
import java.io.DataOutputStream;
import java.io.FileInputStream; 
import java.io.DataInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
public class Main {

     public static Filme ler(Filme f){

          FileInputStream arq2;
          DataInputStream dis; 

          byte[] ba;
          int len;
          Filme f_temp = new Filme();

           //ler arq
           try{
               arq2 =  new FileInputStream("filmes.db");
               dis = new DataInputStream(arq2);//ler arquivo
               
               while (!f_temp.name.equals(f.name)) {
                    byte lapide = dis.readByte();
                    len = dis.readInt(); //Tamano do registro em bytes

                    if(lapide == 0){
                         ba = new byte[len];
                         dis.read(ba);                        
                         f_temp.fromByteArray(ba);
                    }
                    else{

                    }
                    
               }     

          } catch(Exception e)
          {
               System.out.println(e.getMessage());
          } 

          return f_temp;
     }
     public static void main(String[] args) {
          
          String csv = "imdb_movies.csv";
          String linha;
          Filme filme = new Filme();
          Filme f = new Filme();
          f.name = "Avatar: The Way of Water";


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
               // Escrevendo lápide (0 para registro válido)
               dos.writeByte(0); 
               // Escrevendo o tamanho do registro e o registro
               dos.writeInt(ba.length); 
               dos.write(ba);
            }
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(ler(f));
       
     }
}

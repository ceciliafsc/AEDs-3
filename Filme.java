import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Filme {
    protected int id;
    protected String name;
    protected Date date; 
    protected float score;
    protected ArrayList<String> genre;
    protected String status;
    protected String orig_lang;
    protected String country;
    
    // Construtor com parâmetros
    public Filme(int i, String n, Date d, float sc, ArrayList<String> g, String st, String ol, String c) {
        id = i;
        name = n;
        date = d;
        score = sc;
        genre = g;
        status = st;
        orig_lang = ol;
        country = c;
    }

    // Construtor padrão
    public Filme() {
        id = -1;
        name = "";
        date = new Date(); // Inicializa com a data atual
        score = 0;
        genre = new ArrayList<>();
        status = "";
        orig_lang = "";
        country = "";
    }

    // Convertendo para byte array
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(id);
        dos.writeUTF(name);
        dos.writeLong(date.getTime()); // Salva a data como long
        dos.writeFloat(score);
        dos.writeInt(genre.size());
        for (String g : genre) {
            dos.writeUTF(g);
        }
        dos.writeUTF(status);
        dos.writeUTF(orig_lang);
        dos.writeUTF(country);
        
        return baos.toByteArray();
    }

    // Lendo de byte array
    public void fromByteArray(byte ba[]) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        id = dis.readInt();
        name = dis.readUTF();
        date = new Date(dis.readLong()); // Lê a data como long
        score = dis.readFloat();
        int genreSize = dis.readInt();
        genre = new ArrayList<>(genreSize);
        for (int i = 0; i < genreSize; i++) {
            genre.add(dis.readUTF());
        }
        status = dis.readUTF();
        orig_lang = dis.readUTF();
        country = dis.readUTF();
    }

  
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Formato data
        String formattedDate = sdf.format(date);
        
        return "\nID: " + id
             + "\nTítulo: " + name
             + "\nData: " + formattedDate 
             + "\nNota: " + score
             + "\nGêneros: " + genre
             + "\nStatus: " + status
             + "\nLíngua Original: " + orig_lang
             + "\nPaís: " + country;
    }
}

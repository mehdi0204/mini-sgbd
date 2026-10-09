import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class MiniSGBD {

    public static final int PAGE_SIZE = 4096;
    public static final int RECORD_SIZE = 100;
    public static final int RECORDS_PER_PAGE = PAGE_SIZE / RECORD_SIZE; // 40

    private RandomAccessFile file;

    public MiniSGBD(String fileName) throws IOException {
        this.file = new RandomAccessFile(fileName, "rw");
    }

    private byte[] normaliser(String data) {
        byte[] resultat = new byte[RECORD_SIZE];
        byte[] texte = data.getBytes();

        for (int i = 0; i < texte.length && i < RECORD_SIZE; i++) {
            resultat[i] = texte[i];
        }
        return resultat;
    }

    public void insertRecord(String data) throws IOException {
       
        long taille = file.length();
        int nbPages = (int) (taille / PAGE_SIZE);
        int resteDansPage = (int) (taille % PAGE_SIZE) / RECORD_SIZE;
        int recordId = nbPages * RECORDS_PER_PAGE + resteDansPage;

        int pageId = recordId / RECORDS_PER_PAGE;
        int recordOffset = recordId % RECORDS_PER_PAGE;
        int position = (pageId * PAGE_SIZE) + (recordOffset * RECORD_SIZE);

        file.seek(position);
        file.write(normaliser(data));
    }

    public String readRecord(int recordId) throws IOException {
        int pageId = recordId / RECORDS_PER_PAGE;
        int recordOffset = recordId % RECORDS_PER_PAGE;
        int position = (pageId * PAGE_SIZE) + (recordOffset * RECORD_SIZE);

        byte[] octets = new byte[RECORD_SIZE];
        file.seek(position);
        file.read(octets);

        
        return new String(octets).trim();
    }

    public List<String> getPage(int pageId) throws IOException {
        List<String> liste = new ArrayList<>();

        byte[] page = new byte[PAGE_SIZE];
        file.seek(pageId * PAGE_SIZE);
        file.read(page);
        for (int i = 0; i < RECORDS_PER_PAGE; i++) {
            byte[] enregistrement = new byte[RECORD_SIZE];
            for (int j = 0; j < RECORD_SIZE; j++) {
                enregistrement[j] = page[i * RECORD_SIZE + j];
            }

            String texte = new String(enregistrement).trim();
            if (!texte.isEmpty()) {
                liste.add(texte);
            }
        }
        return liste;
    }
}

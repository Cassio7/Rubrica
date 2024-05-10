import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

class Utente {

    private String username;

    Utente(String username) {
        this.username = username;
    }

    // prende tutti i file dentro la cartella informazioni e crea una lista
    void getData(Vector<Persona> temp) {
        List<File> fileList = getFiles();
        try {
            // scorro tutti i file
            for (int i = 0; i < fileList.size(); i++) {
                // classe per leggere da file
                Scanner reader = new Scanner(fileList.get(i));
                // leggo finche sono presenti righe
                while (reader.hasNextLine()) {
                    // inserisco la riga, separandola tramite la virgola, dentro persona, eta ha un controllo sullo spazio per eliminare errore casting
                    String[] data = reader.nextLine().split(";");
                    if (data[0].length() > 0){
                        temp.add(new Persona(data[0], data[1], data[2], data[3], Integer.parseInt(data[4].replaceAll("\\s+", ""))));
                    }
                }
                // chiudo lettura
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
    // ritorna la lista dei file presenti
    static List getFiles(){
        File cartella = new File("src/informazioni/");
        List<File> fileList = Arrays.asList(cartella.listFiles());
        return fileList;
    }

    public String getUsername() {
        return username;
    }

}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Utente {

    String username;

    Utente(String username) {
        this.username = username;
    }

    // prende tutti i file dentro la cartella informazioni e crea una lista
    void getFiles(Vector<Persona> temp) {
        File cartella = new File("src/informazioni/");
        List<File> fileList = Arrays.asList(cartella.listFiles());
        try {
            // scorro tutti i file
            for (int i = 0; i < fileList.size(); i++) {
                // classe per leggere da file
                Scanner reader = new Scanner(fileList.get(i));
                // leggo finche sono presenti righe
                while (reader.hasNextLine()) {
                    // inserisco la riga, separandola tramite la virgola, dentro persona, eta ha un controllo sullo spazio per eliminare errore casting
                    String[] data = reader.nextLine().split(",");
                    temp.add(new Persona(data[0], data[1], data[2], data[3], Integer.parseInt(data[4].replaceAll("\\s+", ""))));
                }
                // chiudo lettura
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

}



public class Main {
    public static void main(String[] args) {

        Login login = new Login();
        while (true) {
            if (login.isAccesso())
                break;

        }
        Vector<Persona> persone = new Vector<>();
        Utente utente = new Utente(login.getUser());
        //persone.add(new Persona("Giorgio", "Verdi", "3334414987", "Via Aranciotto 77", 23));
        utente.getFiles(persone);
        new FinestraPrinc(persone);

    }


}
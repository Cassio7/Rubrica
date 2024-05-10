import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;





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
        utente.getData(persone);
        FinestraPrinc finestraPrinc = new FinestraPrinc(persone,utente);
        while (true) {
            if (finestraPrinc.getRefresh() || FinestraPrinc.refreshforced){
                persone = new Vector<>();
                utente.getData(persone);
                finestraPrinc = new FinestraPrinc(persone,utente);
            }

        }
    }


}
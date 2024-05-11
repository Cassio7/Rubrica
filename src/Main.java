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
        // parte di login utente
        Login login = new Login();
        // il login finisce quando la variabile accesso diventa positiva
        while (true) {
            if (login.isAccesso())
                break;

        }
        // vettore di salvataggio di tutte le persone
        Vector<Persona> persone = new Vector<>();
        //creo utente con il nome inserito nell'user
        Utente utente = new Utente(login.getUser());
        // populo il vettore
        utente.getData(persone);
        // avvio la finestra principale ed inserisco le persone all interno
        FinestraPrinc finestraPrinc = new FinestraPrinc(persone, utente);
        // ciclo di funzionamento
        while (true) {
            // se no dei 2 flag viene modificato è necessario ricaricare la pagina per nuovi contenuti
            if (finestraPrinc.getRefresh() || FinestraPrinc.refreshforced) {
                persone = new Vector<>();
                utente.getData(persone);
                finestraPrinc = new FinestraPrinc(persone, utente);
            }
        }
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

class FinestraNuovo implements ActionListener {
    static int W = 400;
    JFrame f;
    JButton salva, annulla;
    JTextField nome, cognome, telefono, indirizzo, eta;

    FinestraNuovo() {
        f = new JFrame("Editor");

        JToolBar toolBar = new JToolBar();
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        textArea();
        salva = new JButton("Salva");
        annulla = new JButton("Annulla");
        // imposto grandezza
        salva.setPreferredSize(new Dimension(80, 30));
        annulla.setPreferredSize(new Dimension(80, 30));
        // aggiungo listener per eventi che punta a se stesso
        salva.addActionListener(this);
        annulla.addActionListener(this);
        toolBar.add(salva);
        toolBar.add(annulla);
        f.add(toolBar, BorderLayout.SOUTH);

        f.setSize(W, 400);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    // tutta la parte della text area
    private void textArea() {
        JPanel textarea = new JPanel(new FlowLayout());
        textarea.setPreferredSize(new Dimension(W, 320));
        // etichette laterali
        JLabel labnome = new JLabel("Nome:");
        JLabel labcognome = new JLabel("Cognome:");
        JLabel labtel = new JLabel("Telefono:");
        JLabel labind = new JLabel("Indirizzo:");
        JLabel labeta = new JLabel("Età:");
        // 5 campi inserimento
        nome = new JTextField(32);
        cognome = new JTextField(32);
        telefono = new JTextField(32);
        indirizzo = new JTextField(32);
        eta = new JTextField(32);

        // aggiungo tutto
        textarea.add(labnome);
        textarea.add(nome);
        textarea.add(labcognome);
        textarea.add(cognome);
        textarea.add(labtel);
        textarea.add(telefono);
        textarea.add(labind);
        textarea.add(indirizzo);
        textarea.add(labeta);
        textarea.add(eta);

        f.getContentPane().add(textarea, BorderLayout.NORTH);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // non succede nulla ma ricarico pagina per far riapparire finestra principale
        if (e.getSource() == annulla) {
            FinestraPrinc.refreshforced = true;
            f.dispose();
        } //se viene premuto salva
        else if (e.getSource() == salva) {
            // controllo se tutti i campi  non sono vuoti
            if (!nome.getText().isEmpty() && !cognome.getText().isEmpty() && !telefono.getText().isEmpty() && !indirizzo.getText().isEmpty() && !eta.getText().isEmpty()) {
                // controllo se eta e telefono sono numeri con espressioni regolari
                if (eta.getText().matches("-?\\d+(\\.\\d+)?") && telefono.getText().matches("-?\\d+(\\.\\d+)?")) {
                    // aggiungo persona
                    aggiungiPersona();
                    // cambio flag in modo da ricaricare pagina principale
                    FinestraPrinc.refreshforced = true;
                    f.dispose();
                } else JOptionPane.showMessageDialog(f, "Inserisci numeri nei campi età e telefono");
            } else JOptionPane.showMessageDialog(f, "Assicurati di inserire tutti i campi");
        }
    }

    void aggiungiPersona() {
        String[][] fileName = getFileTitles();
        for (int i = 0; i < fileName.length; i++) {
            // controllo se la persona è già stata inserita
            if (nome.getText().toLowerCase().equals(fileName[i][0]) && cognome.getText().toLowerCase().equals(fileName[i][1])) {
                try {
                    // accodo contenuto nuovo a file esistente con il true
                    FileWriter myWriter = new FileWriter("src/informazioni/" + nome.getText().toUpperCase() + "-" + cognome.getText().toUpperCase() + ".txt", true);
                    myWriter.write("\n" + nome.getText() + ";");
                    myWriter.write(cognome.getText() + ";");
                    myWriter.write(indirizzo.getText() + ";");
                    myWriter.write(telefono.getText() + ";");
                    myWriter.write(eta.getText());

                    myWriter.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
                JOptionPane.showMessageDialog(f, "Numero aggiunto per un contatto esistente!");
                break;
            } else {
                try {
                    // crea file
                    FileWriter myWriter = new FileWriter("src/informazioni/" + nome.getText().toUpperCase() + "-" + cognome.getText().toUpperCase() + ".txt");
                    // scrivo dati
                    myWriter.write(nome.getText() + ";");
                    myWriter.write(cognome.getText() + ";");
                    myWriter.write(indirizzo.getText() + ";");
                    myWriter.write(telefono.getText() + ";");
                    myWriter.write(eta.getText());

                    myWriter.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
                JOptionPane.showMessageDialog(f, "Numero aggiunto per nuovo contatto!");
                break;
            }
        }


    }

    // funzione che ritorna una matrice di stringhe per contenenti i titoli dei file trovati
    static String[][] getFileTitles() {
        List<File> fileList = Utente.getFiles();
        String[][] fileName = new String[fileList.size()][2];
        int j = 0;
        for (File i : fileList) {
            // prendo path
            String[] parts = i.toString().split("\\\\");
            // divido in base al trattino prendendo solo titolo file
            fileName[j] = parts[2].split("-");
            // elimino estensione
            fileName[j][1] = fileName[j][1].substring(0, fileName[j][1].lastIndexOf('.'));
            // tutto minuscolo
            fileName[j][0] = fileName[j][0].toLowerCase();
            fileName[j][1] = fileName[j][1].toLowerCase();
            j++;
        }
        return fileName;
    }
}

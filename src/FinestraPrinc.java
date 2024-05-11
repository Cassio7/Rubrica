import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

class FinestraPrinc implements ActionListener {
    static int W = 700;
    // per refresh finestra
    private volatile boolean refresh;
    static volatile boolean refreshforced;
    JFrame f;
    JTable t;
    JButton nuovo, modifica, elimina;
    Vector<Persona> persone;

    FinestraPrinc(Vector<Persona> temp, Utente user) {
        refresh = false;
        refreshforced = false;
        this.persone = temp;
        f = new JFrame("Rubrica di " + user.getUsername());
        // titoli colonne
        String[] colonna = {"Nome", "Cognome", "Numero"};
        // crea la tabella ed inserisce come righe le informazioni prese dalla funzione
        t = new JTable(getData(persone), colonna);
        t.setBounds(30, 40, 200, 300);
        t.setRowSelectionAllowed(true);
        // per scorrere
        JScrollPane sp = new JScrollPane(t);
        f.add(sp);

        JToolBar toolBar = new JToolBar();
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        // creazione dei 3 bottoni
        createButtons();

        // aggiungo i bottoni alla toolbar
        toolBar.add(nuovo);
        toolBar.add(modifica);
        toolBar.add(elimina);

        // aggiungo la toolbar al JFrame
        f.add(toolBar, BorderLayout.SOUTH);

        // imposto finestra
        f.setSize(W, 500);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        // chiude finestra ed interrompe applicazione
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // prende tutte le info dal vettore e le inserisce in una matrice di stringhe che poi restituisce
    String[][] getData(Vector<Persona> temp) {
        String[][] data = new String[temp.size()][3];
        for (int i = 0; i < temp.size(); i++) {
            data[i][0] = temp.get(i).nome;
            data[i][1] = temp.get(i).cognome;
            data[i][2] = temp.get(i).telefono;
        }
        return data;
    }

    private void createButtons() {
        // definisco bottoni
        Icon add = new ImageIcon("src/img/add.png");
        Icon edit = new ImageIcon("src/img/edit.png");
        Icon remove = new ImageIcon("src/img/remove.png");
        nuovo = new JButton(add);
        modifica = new JButton(edit);
        elimina = new JButton(remove);
        // imposto grandezza
        nuovo.setPreferredSize(new Dimension(60, 50));
        modifica.setPreferredSize(new Dimension(60, 50));
        elimina.setPreferredSize(new Dimension(60, 50));
        // aggiungo listener per eventi che punta a se stesso
        nuovo.addActionListener(this);
        modifica.addActionListener(this);
        elimina.addActionListener(this);
    }

    // controlli onclick eventi bottoni
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nuovo) {
            new FinestraNuovo();
            f.dispose();
        } else if (e.getSource() == modifica) {
            // controllo riga selezionata
            if (t.getSelectedRow() != -1) {
                // avvia finestra di mofica
                new FinestraModifica(persone.get(t.getSelectedRow()));
                f.dispose();
            } else JOptionPane.showMessageDialog(f, "Seleziona una riga!!");

        } else if (e.getSource() == elimina) {
            // controllo riga selezionata
            if (t.getSelectedRow() != -1) {
                // finestra di scelta eliminazione
                int risposta = JOptionPane.showConfirmDialog(f,
                        "Eliminare la persona " +
                                persone.get(t.getSelectedRow()).nome + " " +
                                persone.get(t.getSelectedRow()).cognome + "?",
                        "Sei sicuro?",
                        JOptionPane.YES_NO_OPTION);
                if (risposta == JOptionPane.YES_OPTION) {
                    eliminaPersona();
                    setRefresh(true);
                    f.dispose();
                }
            } else JOptionPane.showMessageDialog(f, "Seleziona una riga!!");
        }
    }

    void eliminaPersona() {
        String[][] fileName = FinestraNuovo.getFileTitles();
        for (int i = 0; i < fileName.length; i++) {
            String nome, cognome, numero;
            nome = persone.get(t.getSelectedRow()).nome.toLowerCase();
            cognome = persone.get(t.getSelectedRow()).cognome.toLowerCase();
            numero = persone.get(t.getSelectedRow()).telefono.toLowerCase();
            // trovato file corrispondente alla persona
            if (nome.equals(fileName[i][0]) && cognome.equals(fileName[i][1])) {
                // prendo lista file
                List<File> fileList = Utente.getFiles();
                try {
                    // leggo dal file corrispondete
                    Scanner reader = new Scanner(fileList.get(i));
                    // uso file temporaneo di appoggio
                    File tempFile = new File("src/informazioni/temp.txt");
                    FileWriter myWriter = new FileWriter(tempFile);
                    // leggo tutto
                    while (reader.hasNextLine()) {
                        String line = reader.nextLine();
                        String[] data = line.split(";");
                        // se il numero corrisponde non trascrivo la riga perchè mando continue
                        if (data[3].equals(numero)) {
                            continue;
                        }
                        if (reader.hasNextLine()) {
                            myWriter.write(line + "\n");
                        } else myWriter.write(line);

                    }
                    reader.close();
                    myWriter.close();
                    // elimino file precedente e rinomino il file nuovo
                    File swap = new File(fileList.get(i).toString());
                    fileList.get(i).delete();
                    tempFile.renameTo(swap);
                } catch (FileNotFoundException e) {
                    System.out.println(e);
                } catch (IOException e) {
                    System.out.println(e);
                }
                break;
            }
        }
    }
    // cambio lo stato del flag per ricaricare la pagina quando necessario
    void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }
    // ritorno la variabile
    boolean getRefresh() {
        return this.refresh;
    }
}
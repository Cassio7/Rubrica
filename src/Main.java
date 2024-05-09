import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class FinestraPrinc implements ActionListener {
    static int W = 700;
    JFrame f;
    JTable t;
    JButton nuovo, modifica, elimina;

    FinestraPrinc(Vector<Persona> temp) {
        f = new JFrame("Rubrica");
        // titoli colonne
        String[] colonna = {"Nome", "Cognome", "Numero"};
        // crea la tabella ed inserisce come righe le informazioni prese dalla funzione
        t = new JTable(getData(temp), colonna);
        t.setBounds(30, 40, 200, 300);
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
        } else if (e.getSource() == modifica) {
            System.out.println("modifica");
        } else if (e.getSource() == elimina) {
            System.out.println("elimina");
        }

    }
}


public class Main {
    public static void main(String[] args) {
        Vector<Persona> persone = new Vector<>();
        persone.add(new Persona("Giorgio", "Verdi", "3334414987", "Via Aranciotto 77", 23));
        new FinestraPrinc(persone);
    }
}
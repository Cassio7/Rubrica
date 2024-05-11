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

class FinestraModifica implements ActionListener {
    Persona persona;
    static int W = 400;
    JFrame f;
    JButton salva, annulla;
    JTextField nome, cognome, telefono, indirizzo, eta;

    FinestraModifica(Persona persona) {
        f = new JFrame("Editor");
        this.persona = persona;
        JToolBar toolBar = new JToolBar();
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        textArea(this.persona);
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

    private void textArea(Persona persona) {
        JPanel textarea = new JPanel(new FlowLayout());
        textarea.setPreferredSize(new Dimension(W, 320));
        // etichette laterali
        JLabel labnome = new JLabel("Nome:");
        JLabel labcognome = new JLabel("Cognome:");
        JLabel labtel = new JLabel("Telefono:");
        JLabel labind = new JLabel("Indirizzo:");
        JLabel labeta = new JLabel("Età:");
        // 5 campi inserimento
        nome = new JTextField(persona.nome, 32);
        cognome = new JTextField(persona.cognome, 32);
        telefono = new JTextField(persona.telefono, 32);
        indirizzo = new JTextField(persona.indirizzo, 32);
        eta = new JTextField("" + persona.eta, 32);

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
        if (e.getSource() == annulla) {
            FinestraPrinc.refreshforced = true;
            f.dispose();
        } else if (e.getSource() == salva) {
            // controllo se tutti i campi non sono vuoti
            if (!nome.getText().isEmpty() && !cognome.getText().isEmpty() && !telefono.getText().isEmpty() && !indirizzo.getText().isEmpty() && !eta.getText().isEmpty()) {
                // controllo se eta numero con espressioni regolari
                if (eta.getText().matches("-?\\d+(\\.\\d+)?") && telefono.getText().matches("-?\\d+(\\.\\d+)?")) {
                    modificaPersona();
                    FinestraPrinc.refreshforced = true;
                    f.dispose();
                } else JOptionPane.showMessageDialog(f, "Inserisci numeri nei campi età e telefono");
            } else JOptionPane.showMessageDialog(f, "Assicurati di inserire tutti i campi");
        }
    }

    void modificaPersona() {
        // controllo se devo modificare lo stesso file perchè nome e cognome non sono cambiati
        if (nome.getText().equalsIgnoreCase(persona.nome) && cognome.getText().equalsIgnoreCase(persona.cognome)) {
            try {
                // creo nuovo file che sostituisce precedente modificandolo
                File file = new File("src/informazioni/" + persona.nome.toUpperCase() + "-" + persona.cognome.toUpperCase() + ".txt");
                Scanner reader = new Scanner(file);
                File tempFile = new File("src/informazioni/temp.txt");
                FileWriter myWriter = new FileWriter(tempFile);
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    String[] data = line.split(";");
                    String tempeta = Integer.toString(persona.eta);
                    // il controllo linea viene fatto su persona no sulla casella che è gia cambiata
                    if (data[2].equalsIgnoreCase(persona.indirizzo) && data[3].equals(persona.telefono) && data[4].equalsIgnoreCase(tempeta)) {
                        // controllo per evitare di avere una linea vuota nel file
                        if (reader.hasNextLine()) {
                            myWriter.write(nome.getText() + ";" +
                                    cognome.getText() + ";" +
                                    indirizzo.getText() + ";" +
                                    telefono.getText() + ";" +
                                    eta.getText() + "\n");
                        } else myWriter.write(nome.getText() + ";" +
                                cognome.getText() + ";" +
                                indirizzo.getText() + ";" +
                                telefono.getText() + ";" +
                                eta.getText());
                        continue;
                    }
                    if (reader.hasNextLine()) {
                        myWriter.write(line + "\n");
                    } else myWriter.write(line);
                }
                reader.close();
                myWriter.close();
                // elimino file precedente e rinomino il file nuovo
                File swap = new File(file.toString());
                file.delete();
                tempFile.renameTo(swap);
            } catch (IOException e) {
                System.out.println(e);
            }
            JOptionPane.showMessageDialog(f, "Modificato contatto");
        }
        // caso in cui vengono modificati nome o cognome o entrambi
        else {
            try {
                // attuo modifica eliminando dal vecchio
                File file = new File("src/informazioni/" + persona.nome.toUpperCase() + "-" + persona.cognome.toUpperCase() + ".txt");
                Scanner reader = new Scanner(file);
                File tempFile = new File("src/informazioni/temp.txt");
                FileWriter myWriter = new FileWriter(tempFile);
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    String[] data = line.split(";");
                    String tempeta = Integer.toString(persona.eta);
                    // il controllo linea viene fatto su persona no sulla casella che è gia cambiata
                    if (data[2].equalsIgnoreCase(persona.indirizzo) && data[3].equals(persona.telefono) && data[4].equalsIgnoreCase(tempeta)) {
                        // elimino riga
                        continue;
                    }
                    // se il numero non corrisponde riscrivo sul file di appoggio
                    if (reader.hasNextLine()) {
                        myWriter.write(line + "\n");
                    } else myWriter.write(line);
                }
                reader.close();
                myWriter.close();
                // elimino file precedente e rinomino il file nuovo
                File swap = new File(file.toString());
                file.delete();
                tempFile.renameTo(swap);
            } catch (IOException e) {
                System.out.println(e);
            }
            // creo nuovo contatto da modifica nome o cognome o entrambi
            String[][] fileName = FinestraNuovo.getFileTitles();
            boolean flag = true;
            System.out.println(fileName.length);
            for (int i = 0; i < fileName.length; i++) {
                // persona già esistente quindi accodo nuovi dati
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
                        System.out.println("Scritto file esistente");
                    } catch (IOException e) {
                        System.out.println("An error occurred." + e);
                    }
                    JOptionPane.showMessageDialog(f, "Modificato ed aggiunto ad esistente!");
                    // interrompo ciclo e la flag = false impedisce inserimento sottostante
                    flag = false;
                    break;
                }
            }
            // caso in cui i dati inseriti siano di una persona nuova, creo nuovo file
            if (flag) {
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
                    System.out.println("An error occurred." + e);
                }
                JOptionPane.showMessageDialog(f, "Modificato ed aggiunto nuovo");
            }
        }
    }
}

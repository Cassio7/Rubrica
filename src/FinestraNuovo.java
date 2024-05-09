import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FinestraNuovo implements ActionListener {
    static int W = 400;
    JFrame f;
    JButton salva, annulla;
    JTextField nome, cognome, telefono, indirizzo, eta;

    FinestraNuovo(){
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
    private void textArea(){
        JPanel textarea = new JPanel(new FlowLayout());
        textarea.setPreferredSize(new Dimension(W, 350));
        // etichette laterali
        JLabel labnome = new JLabel("Nome:");
        JLabel labcognome = new JLabel("Cognome:");
        JLabel labtel = new JLabel("Telefono:");
        JLabel labind = new JLabel("Indirizzo:");
        JLabel labeta = new JLabel("Età:");
        // 5 campi inserimento
        nome = new JTextField(32);
        cognome = new JTextField(32);
        telefono= new JTextField(32);
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
        if (e.getSource() == annulla)
            f.dispose();
        else if (e.getSource() == salva) {
            // controllo se tutti i campi  non sono vuoti
            if (!nome.getText().isEmpty() && !cognome.getText().isEmpty() && !telefono.getText().isEmpty() && !indirizzo.getText().isEmpty()&& !eta.getText().isEmpty()){

                System.out.println("passato");

            }
        }
    }
}

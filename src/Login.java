import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

class Login implements ActionListener{
    static int W = 400;
    JFrame f;
    JButton log;
    JTextField user;
    // password
    JPasswordField password;
    private volatile boolean accesso;

    Login() {
        this.accesso = false;
        f = new JFrame("Login");

        // area di inserimento
        JPanel textarea = new JPanel(new FlowLayout());
        textarea.setPreferredSize(new Dimension(W, 200));
        JLabel usern = new JLabel("Username:");
        JLabel psw = new JLabel("Password:");
        user = new JTextField(32);
        password = new JPasswordField(32);
        textarea.add(usern);
        textarea.add(user);
        textarea.add(psw);
        textarea.add(password);
        f.getContentPane().add(textarea, BorderLayout.NORTH);

        // pannello per bottone
        JPanel panel = new JPanel(new FlowLayout());
        panel.setPreferredSize(new Dimension(W, 100));
        log = new JButton("LOGIN");
        log.setPreferredSize(new Dimension(80, 30));
        log.addActionListener(this);
        panel.add(log);
        f.getContentPane().add(panel, BorderLayout.SOUTH);

        // imposto finestra
        f.setSize(W, 400);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        // chiude finestra ed interrompe applicazione
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // evento onclick login
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == log) {
            // controllo se i campi non sono vuoti
            if (!user.getText().isEmpty() && !password.getText().isEmpty()) {
                // controllo se il login è corretto

                if (checkLog()){
                    JOptionPane.showMessageDialog(f, "Benvenuto "+user.getText()+"!!!");
                    setAccesso();
                    f.dispose();
                }
                // se login non va a buon fine mando messaggio di errore
                else JOptionPane.showMessageDialog(f, "Login errato");
            }
        }
    }

    // funzione che controlla se utente è presente e se la password inserita è corretta
    boolean checkLog(){
        // prendo i dati dal file con user e pass
        File file = new File("src/private/users.txt");
        Scanner reader = null;
        // salvo il contenuto su una HashMap per una ricerca semplificata
        HashMap<String,String> data = new HashMap<>();
        try {
            reader = new Scanner(file);
            while (reader.hasNextLine()) {
                // divido per la ,
                String[] temp = reader.nextLine().split(",");
                // inserisco nell' HashMap i dati in ordine
                data.put(temp[0],temp[1]);
            }
            reader.close();
            // salvo input utente
            String temp = data.get(user.getText());
            // controllo se user esiste
            if (temp != null){
                // controllo se la password corrisponde con user inserito
                if (temp.equals(password.getText())){
                    return true;
                }
                // non corrisponde
                else return false;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean isAccesso() {
        return this.accesso;
    }

    public String getUser(){
        String text = user.getText();
        return text;
    }
    public void setAccesso() {
        this.accesso = true;
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.security.Key;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

class Login implements ActionListener {
    static int W = 400;
    // tipo di algoritmo
    private static final String ALGORITHM = "AES";
    // chiave segreta da 16 bytes
    private static final String SECRET_KEY = "Rubricapsw123456";
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
                if (checkLog()) {
                    JOptionPane.showMessageDialog(f, "Benvenuto " + user.getText() + "!!!");
                    setAccesso();
                    f.dispose();
                }
                // se login non va a buon fine mando messaggio di errore
                else JOptionPane.showMessageDialog(f, "Login errato");
            }
        }
    }

    // funzione che controlla se utente è presente e se la password inserita è corretta
    boolean checkLog() {
        // prendo i dati dal file con user e pass
        File file = new File("src/private/users.txt");
        Scanner reader = null;
        // salvo il contenuto su una HashMap per una ricerca semplificata
        HashMap<String, String> data = new HashMap<>();
        try {
            reader = new Scanner(file);
            while (reader.hasNextLine()) {
                // divido per la ;
                String[] temp = reader.nextLine().split(";");
                // inserisco nell'HashMap i dati in ordine
                data.put(temp[0], temp[1]);
            }
            reader.close();
            // salvo input utente
            String temp = data.get(user.getText());
            // controllo se user esiste
            if (temp != null) {
                // controllo se la password corrisponde con user inserito
                if (pwsCheck(temp)) {
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

    boolean pwsCheck(String pswreal) {
        try {
            // creazione della chiave segreta utilizzando l'algoritmo AES e la chiave segreta come array di byte
            Key secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            // cifrario per crittografia
            Cipher cifrario = Cipher.getInstance(ALGORITHM);
            cifrario.init(Cipher.ENCRYPT_MODE, secretKey);
            // crittografia della password inserita dall'utente con cifrario
            byte[] encryptedPasswordBytes = cifrario.doFinal(password.getText().getBytes());
            // conversione a stringa
            String encryptedPasswordBase64 = Base64.getEncoder().encodeToString(encryptedPasswordBytes);
            // controllo se password inserita e crittografata è uguale a quella salvata
            if (encryptedPasswordBase64.equals(pswreal)) {
                return true;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }
    // controllo se se l'accesso è andata a buon fine
    public boolean isAccesso() {
        return this.accesso;
    }
    // setto accesso a true
    public void setAccesso() {
        this.accesso = true;
    }
    // ritorno nome utente
    public String getUser() {
        String text = user.getText();
        return text;
    }

}
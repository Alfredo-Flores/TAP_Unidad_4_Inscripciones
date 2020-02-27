package Views;

/*

    Hecho por Alfredo Flores Garcia
    26 de febrero del 2020
    Clinica Dental

*/

import Model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Login extends JFrame implements ActionListener {

    JFrame dashboard;

    JPanel LoginPanel = new JPanel();
    ImageIcon image = new ImageIcon(new ImageIcon("src/Views/assets/login_welcome.jpg").getImage().getScaledInstance(575, 480, Image.SCALE_SMOOTH));
    ImageIcon imagebrand = new ImageIcon(new ImageIcon("src/Views/assets/brand.png").getImage().getScaledInstance(220, 75, Image.SCALE_SMOOTH));
    Image imagetooth = new ImageIcon("src/Views/assets/logo.png").getImage();
    JLabel LabelTitulo = new JLabel(imagebrand);
    JLabel LabelImagen = new JLabel(image);
    JLabel LabelUser = new JLabel("Usuario: ");
    JLabel LabelPassword = new JLabel("Contraseña: ");
    JTextField FieldUser = new JTextField(10);
    JPasswordField FieldPassword = new JPasswordField(10);
    JButton ButtonAttemp = new JButton("Iniciar Sesión");
    File montserratregular = new File("src/Views/assets/Montserrat-Regular.ttf");
    File montserratbold = new File("src/Views/assets/Montserrat-Bold.ttf");
    Font fontregular = Font.createFont(Font.TRUETYPE_FONT, montserratregular);
    Font fontbold = Font.createFont(Font.TRUETYPE_FONT, montserratbold);

    Login() throws IOException, FontFormatException {
        super("Iniciar sesión");
        setSize(850, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(imagetooth);
        Font sizedFont = fontbold.deriveFont(16f);
        Font sizedFontRegular = fontregular.deriveFont(14f);
        ButtonAttemp.addActionListener(this);
        LoginPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(50, 10, 0, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = inset;
        LoginPanel.add(LabelTitulo, c);
        inset = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 6;
        c.insets = inset;
        c.fill = GridBagConstraints.BOTH;
        LoginPanel.add(LabelImagen, c);
        inset = new Insets(120, 30, 5, 10);
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.insets = inset;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        LabelUser.setHorizontalAlignment(JLabel.CENTER);
        LabelUser.setFont(sizedFontRegular);
        LoginPanel.add(LabelUser, c);
        inset = new Insets(5, 30, 5, 30);
        c.gridx = 0;
        c.gridy = 2;
        c.insets = inset;
        c.fill = GridBagConstraints.HORIZONTAL;
        FieldUser.setFont(sizedFontRegular);
        LoginPanel.add(FieldUser, c);
        c.gridx = 0;
        c.gridy = 3;
        c.insets = inset;
        c.fill = GridBagConstraints.NONE;
        LabelPassword.setHorizontalAlignment(JLabel.CENTER);
        LabelPassword.setFont(sizedFontRegular);
        LoginPanel.add(LabelPassword, c);
        c.gridx = 0;
        c.gridy = 4;
        c.insets = inset;
        c.fill = GridBagConstraints.HORIZONTAL;
        FieldPassword.setFont(sizedFont);
        LoginPanel.add(FieldPassword, c);

        FieldPassword.addActionListener(this);

        inset = new Insets(20, 30, 50, 30);

        c.insets = inset;
        c.gridx = 0;
        c.gridy = 5;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        ButtonAttemp.setFont(sizedFont);
        LoginPanel.add(ButtonAttemp, c);

        setContentPane(LoginPanel);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object Objecto = actionEvent.getSource();

        if (Objecto == ButtonAttemp) {
            try {
                this.login();
            } catch (IOException | FontFormatException e) {
                e.printStackTrace();
            }
        } if (Objecto == FieldPassword) {
            try {
                this.login();
            } catch (IOException | FontFormatException e) {
                e.printStackTrace();
            }
        }

        repaint();
    }

    private void login() throws IOException, FontFormatException {

            int response = this.attempLogin();

            if (response == 1) {
                this.dispose();
                dashboard = new RecepcionistaView();
            } else if (response == 2) {
                this.dispose();
                dashboard = new DuenoView();
            } else {
                JOptionPane.showMessageDialog(LoginPanel, "Estas credenciales no existen, intente de nuevo");
            }

    }

    private void createRecepcionsita(String username, String password) {

        // Info Usuario
        Usuario nuevousuario;

        HashMap<Integer, Usuario> usuarios = null;

        try {
            // En caso de que haya un archivo existente con usuario
            FileInputStream filestream = new FileInputStream("usuarios.obj");
            ObjectInputStream objectstream = new ObjectInputStream(filestream);
            usuarios = (HashMap) objectstream.readObject();
            objectstream.close();
            filestream.close();

            int id = usuarios.size() + 2;

            nuevousuario = new Usuario(id, username, password, 0);

            usuarios.put(id, nuevousuario);
        } catch (Exception e) {
            // En caso de que no exista archivo
            usuarios = new HashMap<Integer, Usuario>();
            nuevousuario = new Usuario(1, username, password, 0);
            usuarios.put(1, nuevousuario);
        }

        // Sobreescribir / Escribir archivo
        try {
            File file = new File("usuarios.obj");
            FileOutputStream fileStream = new FileOutputStream(file);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

            objectStream.writeObject(usuarios);

            fileStream.close();
            objectStream.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(LoginPanel, "Ocurrio un error inesperado");
            return;
        }
    }

    private void createDueno(String username, String password) {

        // Info Usuario
        Usuario nuevousuario;

        HashMap<Integer, Usuario> usuarios = null;

        try {
            // En caso de que haya un archivo existente con usuario
            FileInputStream filestream = new FileInputStream("usuarios.obj");
            ObjectInputStream objectstream = new ObjectInputStream(filestream);
            usuarios = (HashMap) objectstream.readObject();
            objectstream.close();
            filestream.close();

            int id = usuarios.size() + 2;

            nuevousuario = new Usuario(id, username, password, 1);

            usuarios.put(id, nuevousuario);
        } catch (Exception e) {
            // En caso de que no exista archivo
            usuarios = new HashMap<Integer, Usuario>();
            nuevousuario = new Usuario(1, username, password, 1);
            usuarios.put(0, nuevousuario);
        }

        // Sobreescribir / Escribir archivo
        try {
            File file = new File("usuarios.obj");
            FileOutputStream fileStream = new FileOutputStream(file);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

            objectStream.writeObject(usuarios);

            fileStream.close();
            objectStream.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(LoginPanel, "Ocurrio un error inesperado 1");
            return;
        }
    }

    private int attempLogin() {
        String username, password;
        char[] password_encrypted;
        try {
            username = this.FieldUser.getText();
        } catch (Exception error) {
            JOptionPane.showMessageDialog(LoginPanel, "Escriba el usuario correctamente");
            return 0;
        }
        try {
            password_encrypted = this.FieldPassword.getPassword();
            password = new String(password_encrypted);
        } catch (Exception error) {
            JOptionPane.showMessageDialog(LoginPanel, "Escriba la contraseña correctamente");
            return 0;
        }
        createDueno("root", "root");
        createRecepcionsita("alfredo", "alfredo");
        HashMap<Integer, Usuario> usuarios;
        try {
            FileInputStream fis = new FileInputStream("usuarios.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            usuarios = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(LoginPanel, "Ocurrio un error inesperado, no existen usuario registrados");
            return 0;
        }
        for (Map.Entry<Integer, Usuario> entry : usuarios.entrySet()) {
            if (entry.getValue().getUsername().equals(username) && entry.getValue().getPassword().equals(password)) {
                if (entry.getValue().getTipo() == 0) {
                    return 1;
                } else {
                    return 2;
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) throws IOException, FontFormatException {
        JFrame login = new Login();
        login.setVisible(true);
    }
}

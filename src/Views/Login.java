package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Login extends JFrame implements ActionListener {

    JFrame recepcionistaview;

    JPanel LoginPanel = new JPanel();

    JLabel LabelTitulo = new JLabel("OdontoClinic");

    ImageIcon image = new ImageIcon(new ImageIcon("src/Views/assets/login_welcome.jpg").getImage().getScaledInstance(575, 480, Image.SCALE_DEFAULT));
    JLabel LabelImagen = new JLabel(image);

    JLabel LabelUser = new JLabel("Usuario: ");
    JLabel LabelPassword = new JLabel("Contraseña: ");

    JTextField FieldUser = new JTextField(10);
    JPasswordField FieldPassword = new JPasswordField(10);

    JButton ButtonAttemp = new JButton("Iniciar Sesión");

    Login() {
        super("Iniciar sesión");
        setSize(850, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        ButtonAttemp.addActionListener(this);

        LoginPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        Insets inset = new Insets(50, 10, 20, 10);

        String fontname = LabelTitulo.getFont().getName();
        LabelTitulo.setFont(new Font(fontname, Font.BOLD, 20));

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

        inset = new Insets(180, 10, 5, 10);

        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.insets = inset;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.1;
        c.weighty = 0.1;
        LoginPanel.add(LabelUser, c);

        inset = new Insets(5, 30, 5, 30);
        c.gridx = 0;
        c.gridy = 2;
        c.insets = inset;
        c.fill = GridBagConstraints.HORIZONTAL;
        LoginPanel.add(FieldUser, c);

        c.gridx = 0;
        c.gridy = 3;
        c.insets = inset;
        c.fill = GridBagConstraints.NONE;
        LoginPanel.add(LabelPassword, c);

        c.gridx = 0;
        c.gridy = 4;
        c.insets = inset;
        c.fill = GridBagConstraints.HORIZONTAL;
        LoginPanel.add(FieldPassword, c);

        FieldPassword.addActionListener(this);

        inset = new Insets(5, 30, 50, 30);

        c.insets = inset;
        c.gridx = 0;
        c.gridy = 5;
        c.fill = GridBagConstraints.NONE;
        LoginPanel.add(ButtonAttemp, c);

        setContentPane(LoginPanel);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object Objecto = actionEvent.getSource();

        if (Objecto == ButtonAttemp) {
            this.login();
        } if (Objecto == FieldPassword) {
            this.login();
        }
        repaint();
    }

    private void login() {
        try {

            boolean response = this.attempLogin();

            if (response) {
                // Borrar Views.Login Frame
                this.dispose();

                // Abrir Views.Dashboard Frame
                recepcionistaview = new RecepcionistaView();
                recepcionistaview.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(LoginPanel, "Estas credenciales no existen, intente de nuevo");
            }

        } catch (Exception ignored) {
        }
    }


    private void createUser(String username, String password) {

        // Info Usuario
        String[] user = {username, password};

        HashMap<Integer, String[]> users = null;

        try {
            // En caso de que haya un archivo existente con usuario
            FileInputStream fis = new FileInputStream("usuarios.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = (HashMap) ois.readObject();
            ois.close();
            fis.close();

            users.put(users.size() + 2, user);
        } catch (Exception e) {
            // En caso de que no exista archivo
            users = new HashMap<Integer, String[]>();
            users.put(0, user);
        }

        // Sobreescribir / Escribir archivo
        try {
            File file = new File("usuarios.obj");
            FileOutputStream fileStream = new FileOutputStream(file);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(users);
            fileStream.close();
            objectStream.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(LoginPanel, "Ocurrio un error inesperado");
            return;
        }
    }

    private boolean attempLogin() {
        String username, password;
        char[] password_encrypted;

        // Validación
        try {
            username = this.FieldUser.getText();
        } catch (Exception error) {
            JOptionPane.showMessageDialog(LoginPanel, "Escriba el usuario correctamente");
            return false;
        }

        try {
            password_encrypted = this.FieldPassword.getPassword();
            password = new String(password_encrypted);
        } catch (Exception error) {
            JOptionPane.showMessageDialog(LoginPanel, "Escriba la contraseña correctamente");
            return false;
        }

        // Obtener Usuarios
        HashMap<Integer, String[]> users;
        try {
            FileInputStream fis = new FileInputStream("usuarios.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(LoginPanel, "Ocurrio un error inesperado, no existen usuario registrados");
            return false;
        }

        // Intento de login
        String[] user = {username, password};

        for (Map.Entry<Integer, String[]> entry : users.entrySet()) {
            if (entry.getValue()[0].equals(user[0]) && entry.getValue()[1].equals(user[1])) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        JFrame login = new Login();
        login.setVisible(true);
    }
}

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

    JLabel LabelUser = new JLabel("Usuario: ");
    JLabel LabelPassword = new JLabel("Contraseña: ");

    JTextField FieldUser = new JTextField(10);
    JPasswordField FieldPassword = new JPasswordField(10);

    JButton ButtonAttemp = new JButton("Iniciar Sesión");


    Login () {
        super("Iniciar sesión");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ButtonAttemp.addActionListener(this);
        ButtonAttemp.setToolTipText("Registrar trabajador");

        LoginPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        LoginPanel.add(LabelUser, c);

        c.gridx = 1;
        c.gridy = 0;
        LoginPanel.add(FieldUser, c);

        c.gridx = 0;
        c.gridy = 1;
        LoginPanel.add(LabelPassword, c);

        c.gridx = 1;
        c.gridy = 1;
        LoginPanel.add(FieldPassword, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        LoginPanel.add(ButtonAttemp, c);

        setContentPane(LoginPanel);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object Objecto = actionEvent.getSource();

        if (Objecto == ButtonAttemp) {
            try {
                this.createUser("alfredo", "123456");
                this.createUser("root", "root");

                boolean response = this.attempLogin();

                if (response) {
                    // Borrar Login Frame
                    this.dispose();

                    // Abrir Dashboard Frame
                    dashboard = new Dashboard();
                    dashboard.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginPanel, "Estas credenciales no existen, intente de nuevo");
                }

            } catch (Exception ignored) { }
        }
        repaint();
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

        for (Map.Entry<Integer,String[]> entry : users.entrySet()) {
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

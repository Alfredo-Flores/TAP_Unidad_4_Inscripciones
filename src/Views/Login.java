package Views;

/*

    Hecho por Alfredo Flores Garcia
    26 de febrero del 2020
    Clinica Dental

*/

import Handlers.BCrypt;
import Model.Conectar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends JFrame implements ActionListener {

    JFrame dashboard;

    Conectar con = new Conectar();
    Connection reg = con.conexion();

    JPanel LoginPanel = new JPanel();
    ImageIcon image = new ImageIcon(new ImageIcon("src/Views/assets/login_welcome.jpg").getImage().getScaledInstance(575, 480, Image.SCALE_SMOOTH));
    ImageIcon imagebrand = new ImageIcon(new ImageIcon("src/Views/assets/brand.png").getImage().getScaledInstance(100, 75, Image.SCALE_SMOOTH));
    Image imagetooth = new ImageIcon("src/Views/assets/brand.png").getImage();
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

    public void crearAdmin () {
        UUID uuidobj = UUID.randomUUID();
        String uuid = uuidobj.toString();

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        String timestamp = currentTimestamp.toString();

        String sql, username = "q@q.com", password = "admin";
        password = BCrypt.hashpw(password, BCrypt.gensalt());

        sql = "insert into users (uuid, username, password, created_at, updated_at ) values (?,?,?,?,?)";

        try {
            PreparedStatement ps = reg.prepareStatement(sql);
            ps.setString(1, uuid);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, timestamp);
            ps.setString(5, timestamp);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro Guardado");
        } catch (SQLException er) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error");
            er.printStackTrace();
        }
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
        boolean response = this.attempLogin();
        if (response) {
            this.dispose();
            dashboard = new MainView();
        } else {
            JOptionPane.showMessageDialog(LoginPanel, "Estas credenciales no existen, intente de nuevo");
        }
    }

    private boolean attempLogin() {
        String username, password;
        char[] password_encrypted;
        try {
            username = this.FieldUser.getText();
        } catch (Exception error) {
            JOptionPane.showMessageDialog(LoginPanel, "Escriba el correo correctamente");
            return false;
        }
        try {
            password_encrypted = this.FieldPassword.getPassword();
            password = new String(password_encrypted);
        } catch (Exception error) {
            JOptionPane.showMessageDialog(LoginPanel, "Escriba la contraseña correctamente");
            return false;
        }

        try {
            String query = "select username, password from users where username = '" + username + "' limit 1";

            java.sql.ResultSet rs = reg.prepareStatement(query).executeQuery();
            if (rs.next()) {
                String sqlusername = rs.getString("username");
                String sqlpassword = rs.getString("password");

                return sqlusername.equals(username) && BCrypt.checkpw(password, sqlpassword);
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws IOException, FontFormatException {
        JFrame login = new Login();
        login.setVisible(true);
    }
}

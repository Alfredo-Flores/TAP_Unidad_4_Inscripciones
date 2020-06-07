package Views;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Recibo extends JFrame {

    String name, lastname, age, father_name, father_lastname, father_RFC, direction, level, grade, enrollment, payment, extra;

    JTextField fieldnombre = new JTextField(), fieldapellidos = new JTextField();
    JTextField fieldage = new JTextField();
    JTextField fieldfathername = new JTextField();
    JTextField fieldfatherlastname = new JTextField();
    JTextField fieldfatherRFC = new JTextField();
    JTextField fielddirection = new JTextField();
    JTextField fieldlevel = new JTextField();
    JTextField fieldgrade = new JTextField();
    JTextField fieldenrollment = new JTextField();
    JTextField fieldpayment = new JTextField();
    JTextField fieldextra = new JTextField();
    JTextField fieldsearch = new JTextField();

    File montserratregular = new File("src/Views/assets/Montserrat-Regular.ttf");
    File montserratbold = new File("src/Views/assets/Montserrat-Bold.ttf");
    Font fontregular = Font.createFont(Font.TRUETYPE_FONT, montserratregular);
    Font fontbold = Font.createFont(Font.TRUETYPE_FONT, montserratbold);
    Font sizedFontBold = fontbold.deriveFont(12f);
    Font sizedFontRegular = fontregular.deriveFont(12f);

    Recibo(String name, String lastname, String age, String father_name, String father_lastname, String father_RFC, String direction, String level, String grade, String enrollment, String payment, String extra) throws IOException, FontFormatException {
        super("Recibo");  setSize(500, 720);
        setMinimumSize(new Dimension(500, 500));    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);    setResizable(true);

        this.fieldnombre.setText(name);
        this.fieldapellidos.setText(lastname);
        this.fieldage.setText(age);
        this.fieldfathername.setText(father_name);
        this.fieldfatherlastname.setText(father_lastname);
        this.fieldfatherRFC.setText(father_RFC);
        this.fielddirection.setText(direction);
        this.fieldlevel.setText(level);
        this.fieldgrade.setText(grade);
        this.fieldenrollment.setText(enrollment);
        this.fieldpayment.setText(payment);
        this.fieldextra.setText(extra);

        this.fieldnombre.setEditable(false);
        this.fieldapellidos.setEditable(false);
        this.fieldage.setEditable(false);
        this.fieldfathername.setEditable(false);
        this.fieldfatherlastname.setEditable(false);
        this.fieldfatherRFC.setEditable(false);
        this.fielddirection.setEditable(false);
        this.fieldlevel.setEditable(false);
        this.fieldgrade.setEditable(false);
        this.fieldenrollment.setEditable(false);
        this.fieldpayment.setEditable(false);
        this.fieldextra.setEditable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel labelnombre = new JLabel("Nombre: "), labelapellidos = new JLabel("Apellidos: ");
        JLabel labelage = new JLabel("Edad");
        JLabel labelfathername = new JLabel("Nombre del padre");
        JLabel labelfatherlastname = new JLabel("Apellidos del padre");
        JLabel labelfatherRFC = new JLabel("RFC del padre");
        JLabel labeldirection = new JLabel("Direccion de casa");
        JLabel labellevel = new JLabel("Nivel de educacion");
        JLabel labelgrade = new JLabel("Grado");
        JLabel labelenrollment = new JLabel("Matricula");
        JLabel labelpayment = new JLabel("Pago");
        JLabel labelextra = new JLabel("Extracurricular");

        labelnombre.setFont(sizedFontRegular);
        labelapellidos.setFont(sizedFontRegular);
        labelage.setFont(sizedFontRegular);
        labelfathername.setFont(sizedFontRegular);
        labelfatherlastname.setFont(sizedFontRegular);
        labelfatherRFC.setFont(sizedFontRegular);
        labeldirection.setFont(sizedFontRegular);
        labellevel.setFont(sizedFontRegular);
        labelgrade.setFont(sizedFontRegular);
        labelenrollment.setFont(sizedFontRegular);
        labelpayment.setFont(sizedFontRegular);
        labelextra.setFont(sizedFontRegular);
        fieldnombre.setFont(sizedFontBold);
        fieldapellidos.setFont(sizedFontBold);
        fieldage.setFont(sizedFontBold);
        fieldfathername.setFont(sizedFontBold);
        fieldfatherlastname.setFont(sizedFontBold);
        fieldfatherRFC.setFont(sizedFontBold);
        fielddirection.setFont(sizedFontBold);
        fieldlevel.setFont(sizedFontBold);
        fieldgrade.setFont(sizedFontBold);
        fieldenrollment.setFont(sizedFontBold);
        fieldpayment.setFont(sizedFontBold);
        fieldextra.setFont(sizedFontBold);

        Insets inset = new Insets(5, 5, 5, 5);
        c.gridy = 0;
        c.insets = inset;
        c.weightx = 0.1;
        c.weighty = 0.1;
        panel.add(labelnombre, c);
        c.gridy = 1;    panel.add(fieldnombre, c);
        c.gridy = 2;    panel.add(labelapellidos, c);
        c.gridy = 3;    panel.add(fieldapellidos, c);
        c.gridy = 4;    panel.add(labelage, c);
        c.gridy = 5;    panel.add(fieldage, c);
        c.gridy = 6;    panel.add(labelfathername, c);
        c.gridy = 7;    panel.add(fieldfathername, c);
        c.gridy = 8;    panel.add(labelfatherlastname, c);
        c.gridy = 9;    panel.add(fieldfatherlastname, c);
        c.gridy = 10;   panel.add(labelfatherRFC, c);
        c.gridy = 11;   panel.add(fieldfatherRFC, c);
        c.gridx = 1;
        c.gridy = 0;   panel.add(labeldirection, c);
        c.gridy = 1;   panel.add(fielddirection, c);
        c.gridy = 2;   panel.add(labellevel, c);
        c.gridy = 3;   panel.add(fieldlevel, c);
        c.gridy = 4;   panel.add(labelgrade, c);
        c.gridy = 5;   panel.add(fieldgrade, c);
        c.gridy = 6;   panel.add(labelenrollment, c);
        c.gridy = 7;   panel.add(fieldenrollment, c);
        c.gridy = 8;   panel.add(labelpayment, c);
        c.gridy = 9;   panel.add(fieldpayment, c);
        c.gridy = 10;   panel.add(labelextra, c);
        c.gridy = 11;   panel.add(fieldextra, c);

        setContentPane(panel);
        setVisible(true);
    }
}

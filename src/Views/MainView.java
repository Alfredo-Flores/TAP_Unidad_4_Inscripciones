package Views;

import Model.Conectar;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MainView extends JFrame implements ActionListener, KeyListener {

    Conectar con = new Conectar();
    Connection reg = con.conexion();

    boolean modify = false;
    String currentUuid = "";
    JSplitPane splitPane = new JSplitPane();
    Panel sidebar = new Panel(), mainpane = new Panel();
    JPanel innerpane = new JPanel();
    File montserratregular = new File("src/Views/assets/Montserrat-Regular.ttf");
    File montserratbold = new File("src/Views/assets/Montserrat-Bold.ttf");
    Font fontregular = Font.createFont(Font.TRUETYPE_FONT, montserratregular);
    Font fontbold = Font.createFont(Font.TRUETYPE_FONT, montserratbold);
    Font sizedFontBold = fontbold.deriveFont(14f);
    Font sizedFontRegular = fontregular.deriveFont(12f);
    Image imagetoothicon = new ImageIcon("src/Views/assets/brand.png").getImage();
    ImageIcon imagetooth = new ImageIcon(new ImageIcon("src/Views/assets/brand.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    ImageIcon imagebrand = new ImageIcon(new ImageIcon("src/Views/assets/brand.png").getImage().getScaledInstance(60, 40, Image.SCALE_SMOOTH));

    ImageIcon imageusuarios = new ImageIcon(new ImageIcon("src/Views/assets/client.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

    ImageIcon imageshrink = new ImageIcon(new ImageIcon("src/Views/assets/arrow-left-solid.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    ImageIcon imageexpand = new ImageIcon(new ImageIcon("src/Views/assets/arrow-right-solid.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    JButton tabusuarios = new JButton("Inscripciones", imageusuarios), buttonshrink = new JButton(imageshrink);

    JButton buttonregisteruser = new JButton("Inscribir");

    JTextField fieldnombre = new JTextField(), fieldapellidos = new JTextField();
    JTextField fieldage = new JTextField();
    JTextField fieldfathername = new JTextField();
    JTextField fieldfatherlastname = new JTextField();
    JTextField fieldfatherRFC = new JTextField();
    JTextField fielddirection = new JTextField();
    JComboBox<String> fieldlevel = new JComboBox<>(new String[]{"Primaria", "Secundaria", "Preparatoria"});
    JComboBox<String> fieldgrade = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6"});
    JTextField fieldenrollment = new JTextField();
    JTextField fieldpayment = new JTextField();
    JTextField fieldextra = new JTextField();
    JTextField fieldsearch = new JTextField();

    String[] titulos = new String[0];
    Object[][] data = new Object[0][0];
    DefaultTableModel modelo = new DefaultTableModel(data, titulos);
    JTable tabla = new JTable(modelo);

    int pestanaactual = 1;
    boolean banderasidebar = false;

    MainView() throws IOException, FontFormatException {
        super("Colgio REX");  setSize(1152, 640);
        setMinimumSize(new Dimension(850, 480));    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);    setResizable(true);     setIconImage(imagetoothicon);
        this.setSideBar(1); this.setInscripcionesPane();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainpane);
        splitPane.setEnabled(false);    getContentPane().add(splitPane);
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(null,
                        "¿Estas seguro que quieres salir del sistema?", "Cerrar ventana",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == 0){
                    System.exit(0);
                }
            }
        });
    }

    @Override
    public void actionPerformed (ActionEvent actionEvent) {
        Object Objecto = actionEvent.getSource();

        try {
            if (Objecto == tabusuarios && pestanaactual != 1) {
                int response = JOptionPane.showConfirmDialog(this, "¿Estas seguro de salir de este panel? podria perderse progreso", "Cambiar de panel", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == 0) {
                    this.removeSidebar();   this.removeCurrentPane();
                    this.setSideBar(1);     this.setInscripcionesPane();
                } else {
                    return;
                }
            }
            if (Objecto == buttonshrink) {
                this.removeSidebar();   this.removeCurrentPane();
                this.changeSideBar();   this.setSideBar(this.pestanaactual);

                if (this.pestanaactual == 1) {
                    this.setInscripcionesPane();
                } else if (this.pestanaactual == 2) {
                    return;
                } else if (this.pestanaactual == 3) {
                    return;
                }

            } if (Objecto == buttonregisteruser) {
                int confirm;

                if (!modify) {
                    confirm = JOptionPane.showConfirmDialog(this, "¿Estas seguro de registrar esta inscripcion?", "Registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                } else {
                    confirm = JOptionPane.showConfirmDialog(this, "¿Estas seguro de modificar esta inscripcion?", "Modificar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                }

                if (confirm == 0) {
                    if (!modify) {
                        this.createInscripcion();
                    } else {
                        this.modifyInscripcion(currentUuid);
                    }
                } else {
                    return;
                }
            }
            if (Objecto == fieldlevel || Objecto == fieldgrade) {
                refreshComboGrade();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        repaint();
    }

    public void setInscripcionesPane () {
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
        fieldnombre.setFont(sizedFontRegular);
        fieldapellidos.setFont(sizedFontRegular);
        fieldage.setFont(sizedFontRegular);
        fieldfathername.setFont(sizedFontRegular);
        fieldfatherlastname.setFont(sizedFontRegular);
        fieldfatherRFC.setFont(sizedFontRegular);
        fielddirection.setFont(sizedFontRegular);
        fieldlevel.setFont(sizedFontRegular);
        fieldgrade.setFont(sizedFontRegular);
        fieldenrollment.setFont(sizedFontRegular);
        fieldpayment.setFont(sizedFontRegular);
        fieldpayment.setEditable(false);
        fieldextra.setFont(sizedFontRegular);
        buttonregisteruser.setFont(sizedFontRegular);

        innerpane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(5, 5, 5, 5);
        c.gridy = 0;    c.insets = inset;   c.ipady = 0;
        c.ipadx = 0;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.HORIZONTAL;
        innerpane.add(labelnombre, c);
        c.gridy = 1;    innerpane.add(fieldnombre, c);
        c.gridy = 2;    innerpane.add(labelapellidos, c);
        c.gridy = 3;    innerpane.add(fieldapellidos, c);
        c.gridy = 5;    innerpane.add(labelage, c);
        c.gridy = 6;    innerpane.add(fieldage, c);
        c.gridy = 7;    innerpane.add(labelfathername, c);
        c.gridy = 8;    innerpane.add(fieldfathername, c);
        c.gridy = 9;    innerpane.add(labelfatherlastname, c);
        c.gridy = 10;   innerpane.add(fieldfatherlastname, c);
        c.gridy = 11;   innerpane.add(labelfatherRFC, c);
        c.gridy = 12;   innerpane.add(fieldfatherRFC, c);
        c.gridy = 13;   innerpane.add(labeldirection, c);
        c.gridy = 14;   innerpane.add(fielddirection, c);
        c.gridy = 15;   innerpane.add(labellevel, c);
        c.gridy = 16;   innerpane.add(fieldlevel, c);
        c.gridy = 17;   innerpane.add(labelgrade, c);
        c.gridy = 18;   innerpane.add(fieldgrade, c);
        c.gridy = 19;   innerpane.add(labelenrollment, c);
        c.gridy = 20;   innerpane.add(fieldenrollment, c);
        c.gridy = 21;   innerpane.add(labelpayment, c);
        c.gridy = 22;   innerpane.add(fieldpayment, c);
        c.gridy = 23;   innerpane.add(labelextra, c);
        c.gridy = 24;   innerpane.add(fieldextra, c);
        c.gridy = 25;
        fieldlevel.removeActionListener(this);
        fieldlevel.addActionListener(this);
        fieldlevel.setFont(sizedFontRegular);
        fieldgrade.removeActionListener(this);
        fieldgrade.addActionListener(this);
        fieldgrade.setFont(sizedFontRegular);
        buttonregisteruser.removeActionListener(this);
        buttonregisteruser.addActionListener(this);
        buttonregisteruser.setFont(sizedFontBold);
        innerpane.add(buttonregisteruser, c);


        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Nueva inscripcion", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        JScrollPane innerscroll = new JScrollPane(innerpane);
        innerscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        innerscroll.setBorder(border);

        inset = new Insets(5, 5, 5, 5);
        c.gridx = 0;    c.gridy = 2;
        c.insets = inset;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        mainpane.add(innerscroll, c);

        JLabel labelsearch = new JLabel("Buscar: ");
        labelsearch.setFont(sizedFontRegular);
        fieldsearch.setFont(sizedFontRegular);
        fieldsearch.removeKeyListener(this);
        fieldsearch.addKeyListener(this);
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.001;
        c.weighty = 0.001;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.gridx = 1;    c.gridy = 1;
        mainpane.add(labelsearch, c);
        c.gridx = 2;    c.gridy = 1;
        mainpane.add(fieldsearch, c);


        inset = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 1; c.gridy = 2;
        c.gridwidth = 2;
        c.insets = inset; c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.2;
        c.weighty = 0.2;
        this.refreshTablaInscripciones();
        tabla.setFont(sizedFontRegular);
        JScrollPane scrollusuarios = new JScrollPane(tabla);
        border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Inscripciones registradas", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        scrollusuarios.setBorder(border);
        mainpane.add(scrollusuarios, c);
    }

    public void refreshTablaInscripciones () {
        try {
            String[] titulos = {"UUID", "Nombre", "Apellidos", "Nivel", "Grado", "Matricula", "Ver Recibo", "Modificar", "Borrar"};
            Object[][] data = new Object[0][0];
            DefaultTableModel newmodelo = new DefaultTableModel(data, titulos);

            String query = "";

            query = "select uuid, name, lastname, level, grade, enrollment from inscriptions where deleted_at is null";

            java.sql.ResultSet rs = reg.prepareStatement(query).executeQuery();

            while (tabla.getRowCount() > 0) {
                ((DefaultTableModel) tabla.getModel()).removeRow(0);
            }

            int columns = rs.getMetaData().getColumnCount();

            while (rs.next())
            {
                Object[] row = new Object[columns + 3];
                for (int i = 1; i <= columns; i++)
                {
                    row[i - 1] = rs.getObject(i);
                }
                row[columns] = "Ver Recibo";
                row[columns + 1] = "Modificar";
                row[columns + 2] = "Borrar";

                newmodelo.insertRow(rs.getRow() - 1,row);
            }

            tabla.setModel(newmodelo);
            tabla.getTableHeader().setReorderingAllowed(false);
            tabla.getColumn("Ver Recibo").setCellRenderer(new ButtonRenderer());
            tabla.getColumn("Ver Recibo").setCellEditor(new ButtonEditor(new JCheckBox(), this, this.tabla));
            tabla.getColumn("Modificar").setCellRenderer(new ButtonRenderer());
            tabla.getColumn("Modificar").setCellEditor(new ButtonEditor(new JCheckBox(), this, this.tabla));
            tabla.getColumn("Borrar").setCellRenderer(new ButtonRenderer());
            tabla.getColumn("Borrar").setCellEditor(new ButtonEditor(new JCheckBox(), this, this.tabla));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createInscripcion () {
        UUID uuidobj = UUID.randomUUID();
        String uuid = uuidobj.toString();

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        String timestamp = currentTimestamp.toString();

        String sql, name, lastname, age, father_name, father_lastname, father_RFC, direction, level, grade, enrollment, payment, extra;

        if (this.valirdarString(this.fieldnombre.getText())) {
            name = this.fieldnombre.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el nombre correctamente"); return;
        }
        if (this.valirdarString(this.fieldapellidos.getText())) {
            lastname = this.fieldapellidos.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba los apellidos correctamente");   return;
        }
        if (this.valirdarString(this.fieldage.getText())) {
            age = this.fieldage.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba la edad correctamente");   return;
        }
        if (this.valirdarString(this.fieldfathername.getText())) {
            father_name = this.fieldfathername.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el nombre del padre correctamente");   return;
        }
        if (this.valirdarString(this.fieldfatherlastname.getText())) {
            father_lastname = this.fieldfatherlastname.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba los apellidos del padre correctamente");   return;
        }
        if (this.valirdarString(this.fieldfatherRFC.getText())) {
            father_RFC = this.fieldfatherRFC.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el RFC del padre correctamente");   return;
        }
        if (this.valirdarString(this.fielddirection.getText())) {
            direction = this.fielddirection.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba la direccion correctamente");   return;
        }
        if (this.valirdarString(this.fieldlevel.getSelectedItem().toString())) {
            level = this.fieldlevel.getSelectedItem().toString();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el nivel academico correctamente");   return;
        }
        if (this.valirdarString(this.fieldgrade.getSelectedItem().toString())) {
            grade = this.fieldgrade.getSelectedItem().toString();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el grado correctamente");   return;
        }
        if (this.valirdarString(this.fieldenrollment.getText())) {
            enrollment = this.fieldenrollment.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba la matricula correctamente");   return;
        }
        if (this.valirdarString(this.fieldpayment.getText())) {
            payment = this.fieldpayment.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el pago correctamente");   return;
        }
        if (this.valirdarString(this.fieldextra.getText())) {
            extra = this.fieldextra.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el extracurricular correctamente");   return;
        }


        sql = "insert into inscriptions (uuid, name, lastname, age, father_name, father_lastname, father_RFC, direction, level, grade, enrollment, payment, extra, created_at, updated_at ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = reg.prepareStatement(sql);
            ps.setString(1, uuid);
            ps.setString(2, name);
            ps.setString(3, lastname);
            ps.setString(4, age);
            ps.setString(5, father_name);
            ps.setString(6, father_lastname);
            ps.setString(7, father_RFC);
            ps.setString(8, direction);
            ps.setString(9, level);
            ps.setString(10, grade);
            ps.setString(11, enrollment);
            ps.setString(12, payment);
            ps.setString(13, extra);
            ps.setString(14, timestamp);
            ps.setString(15, timestamp);
            ps.executeUpdate();
            this.fieldnombre.setText("");
            this.fieldapellidos.setText("");
            this.fieldage.setText("");
            this.fieldfathername.setText("");
            this.fieldfatherlastname.setText("");
            this.fieldfatherRFC.setText("");
            this.fielddirection.setText("");
            this.fieldlevel.setSelectedIndex(0);
            this.fieldgrade.setSelectedIndex(0);
            this.fieldenrollment.setText("");
            this.fieldpayment.setText("");
            this.fieldextra.setText("");
            refreshTablaInscripciones();
            JOptionPane.showMessageDialog(this, "Guardado correctamente");
            this.repaint();
        } catch (SQLException er) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error, intente con otro correo");
            er.printStackTrace();
        }
    }

    public void setInscripcion (String uuid) {
        String name, lastname, age, father_name, father_lastname, father_RFC, direction, level, grade, enrollment, payment, extra;


        String sql = "select name, lastname, age, father_name, father_lastname, father_RFC, direction, level, grade, enrollment, payment, extra from inscriptions where uuid = ? ";

        try {
            PreparedStatement ps = reg.prepareStatement(sql);
            ps.setString(1, uuid);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
                lastname = rs.getString("lastname");
                age = rs.getString("lastname");
                father_name = rs.getString("father_name");
                father_lastname = rs.getString("father_lastname");
                father_RFC = rs.getString("father_RFC");
                direction = rs.getString("direction");
                level = rs.getString("level");
                grade = rs.getString("grade");
                enrollment = rs.getString("enrollment");
                payment = rs.getString("payment");
                extra = rs.getString("extra");

                this.fieldnombre.setText(name);
                this.fieldapellidos.setText(lastname);
                this.fieldage.setText(age);
                this.fieldfathername.setText(father_name);
                this.fieldfatherlastname.setText(father_lastname);
                this.fieldfatherRFC.setText(father_RFC);
                this.fielddirection.setText(direction);
                this.fieldlevel.setSelectedItem(level);
                this.fieldgrade.setSelectedItem(grade);
                this.fieldenrollment.setText(enrollment);
                this.fieldpayment.setText(payment);
                this.fieldextra.setText(extra);
                modify = true;
                currentUuid = uuid;
                buttonregisteruser.setText("Modificar Inscripcion");
            } else {
                JOptionPane.showMessageDialog(null, "No existe la inscripcion");
            }
        } catch (SQLException er) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado");
            er.printStackTrace();
        }
    }

    public void modifyInscripcion (String uuid) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        String timestamp = currentTimestamp.toString();

        String sql, name, lastname, age, father_name, father_lastname, father_RFC, direction, level, grade, enrollment, payment, extra;

        if (this.valirdarString(this.fieldnombre.getText())) {
            name = this.fieldnombre.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el nombre correctamente"); return;
        }
        if (this.valirdarString(this.fieldapellidos.getText())) {
            lastname = this.fieldapellidos.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba los apellidos correctamente");   return;
        }
        if (this.valirdarString(this.fieldage.getText())) {
            age = this.fieldage.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba la edad correctamente");   return;
        }
        if (this.valirdarString(this.fieldfathername.getText())) {
            father_name = this.fieldfathername.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el nombre del padre correctamente");   return;
        }
        if (this.valirdarString(this.fieldfatherlastname.getText())) {
            father_lastname = this.fieldfatherlastname.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba los apellidos del padre correctamente");   return;
        }
        if (this.valirdarString(this.fieldfatherRFC.getText())) {
            father_RFC = this.fieldfatherRFC.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el RFC del padre correctamente");   return;
        }
        if (this.valirdarString(this.fielddirection.getText())) {
            direction = this.fielddirection.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba la direccion correctamente");   return;
        }
        if (this.valirdarString(this.fieldlevel.getSelectedItem().toString())) {
            level = this.fieldlevel.getSelectedItem().toString();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el nivel academico correctamente");   return;
        }
        if (this.valirdarString(this.fieldgrade.getSelectedItem().toString())) {
            grade = this.fieldgrade.getSelectedItem().toString();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el grado correctamente");   return;
        }
        if (this.valirdarString(this.fieldenrollment.getText())) {
            enrollment = this.fieldenrollment.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba la matricula correctamente");   return;
        }
        if (this.valirdarString(this.fieldpayment.getText())) {
            payment = this.fieldpayment.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el pago correctamente");   return;
        }
        if (this.valirdarString(this.fieldextra.getText())) {
            extra = this.fieldextra.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el extracurricular correctamente");   return;
        }

        sql = "update inscriptions set name = ?, lastname = ?, age = ?, father_name = ?, father_lastname = ?, father_RFC = ?, direction = ?, level = ?, grade = ?, enrollment = ?, payment = ?, extra = ?, updated_at = ? where uuid = ?";

        try {
            PreparedStatement ps = reg.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, lastname);
            ps.setString(3, age);
            ps.setString(4, father_name);
            ps.setString(5, father_lastname);
            ps.setString(6, father_RFC);
            ps.setString(7, direction);
            ps.setString(8, level);
            ps.setString(9, grade);
            ps.setString(10, enrollment);
            ps.setString(11, payment);
            ps.setString(12, extra);
            ps.setString(13, timestamp);
            ps.setString(14, uuid);

            ps.executeUpdate();
            this.fieldnombre.setText("");
            this.fieldapellidos.setText("");
            this.fieldage.setText("");
            this.fieldfathername.setText("");
            this.fieldfatherlastname.setText("");
            this.fieldfatherRFC.setText("");
            this.fielddirection.setText("");
            this.fieldlevel.setSelectedIndex(0);
            this.fieldgrade.setSelectedIndex(0);
            this.fieldenrollment.setText("");
            this.fieldpayment.setText("");
            this.fieldextra.setText("");
            refreshTablaInscripciones();
            modify = false;
            currentUuid = "";
            buttonregisteruser.setText("Guardar Inscripcion");
            JOptionPane.showMessageDialog(this, "Modificado correctamente");
        } catch (SQLException er) {
            JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado");
            er.printStackTrace();
        }
    }

    public void deleteInscripcion (String uuid) {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Estas seguro de borrar esta inscripcion?", "Borrar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == 0) {
            String sql = "update inscriptions set deleted_at = ? where uuid = ?";

            try {
                Calendar calendar = Calendar.getInstance();
                java.util.Date now = calendar.getTime();
                java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
                String timestamp = currentTimestamp.toString();

                PreparedStatement ps = reg.prepareStatement(sql);
                ps.setString(1, timestamp);
                ps.setString(2, uuid);
                ps.executeUpdate();
                refreshTablaInscripciones();
                JOptionPane.showMessageDialog(this, "Borrado correctamente");
            } catch (SQLException er) {
                JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado");
                er.printStackTrace();
            }
        }
    }

    public void printInscripcion (String uuid) {
        String name, lastname, age, father_name, father_lastname, father_RFC, direction, level, grade, enrollment, payment, extra;


        String sql = "select name, lastname, age, father_name, father_lastname, father_RFC, direction, level, grade, enrollment, payment, extra from inscriptions where uuid = ? ";

        try {
            PreparedStatement ps = reg.prepareStatement(sql);
            ps.setString(1, uuid);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
                lastname = rs.getString("lastname");
                age = rs.getString("lastname");
                father_name = rs.getString("father_name");
                father_lastname = rs.getString("father_lastname");
                father_RFC = rs.getString("father_RFC");
                direction = rs.getString("direction");
                level = rs.getString("level");
                grade = rs.getString("grade");
                enrollment = rs.getString("enrollment");
                payment = rs.getString("payment");
                extra = rs.getString("extra");

                new Recibo(name, lastname, age, father_name, father_lastname, father_RFC, direction, level, grade, enrollment, payment, extra);
            } else {
                JOptionPane.showMessageDialog(null, "No existe la inscripcion");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado");
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSideBar (int tab) {
        this.pestanaactual = tab;
        GridLayout gridLayout = new GridLayout(10, 1, 5, 10);
        sidebar.setLayout(gridLayout);
        JLabel LabelTitulo = new JLabel("Agenda");
        String fontname = LabelTitulo.getFont().getName();
        LabelTitulo.setFont(new Font(fontname, Font.BOLD, 18));
        Font sizedFont = fontbold.deriveFont(14f);
        if (banderasidebar) {
            splitPane.setDividerLocation(50);
            LabelTitulo = new JLabel(imagetooth);
            LabelTitulo.setHorizontalAlignment(JLabel.CENTER);
            sidebar.add(LabelTitulo);
            tabusuarios = new JButton(imageusuarios);
        } else {
            splitPane.setDividerLocation(195);
            LabelTitulo = new JLabel(imagebrand);
            LabelTitulo.setHorizontalAlignment(JLabel.CENTER);
            sidebar.add(LabelTitulo);
            tabusuarios = new JButton("INSCRIPCIONES", imageusuarios);

            tabusuarios.setFont(sizedFont);
        }

        tabusuarios.removeActionListener(this);

        tabusuarios.addActionListener(this);

        sidebar.add(tabusuarios);
        sizedFont = fontbold.deriveFont(20f);
        JLabel LabelPanel = new JLabel("default");
        if (tab == 1) {
            LabelPanel = new JLabel("Inscripciones");
            LabelPanel.setFont(sizedFont);
        }
        mainpane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(5, 5, 5, 5);
        if (banderasidebar) {
            buttonshrink = new JButton(imageexpand);
        } else {
            buttonshrink = new JButton(imageshrink);
        }
        buttonshrink.removeActionListener(this);
        buttonshrink.addActionListener(this);
        c.weightx = 0.001;
        c.weighty = 0.001;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 30;
        c.ipadx = 30;
        c.insets = inset;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        buttonshrink.setBorder(null);
        buttonshrink.setBorderPainted(false);
        buttonshrink.setContentAreaFilled(false);
        buttonshrink.setOpaque(false);
        mainpane.add(buttonshrink, c);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        mainpane.add(LabelPanel, c);
    }

    private void removeSidebar () {
        sidebar.removeAll();
    }

    private void removeCurrentPane () {
        tabla.removeAll();
        innerpane.removeAll();
        mainpane.removeAll();
    }

    private void changeSideBar() {
        this.banderasidebar = !this.banderasidebar;
    }

    private boolean valirdarString(String string) {
        return string != null && !string.equals("");
    }

    public void searchCliente () {


        String tosearch = this.fieldsearch.getText();

        if (tosearch.equals("")) {
            refreshTablaInscripciones();
        }

        while (tabla.getRowCount() > 0) {
            ((DefaultTableModel) tabla.getModel()).removeRow(0);
        }

        String[] titulos = {"UUID", "Nombre", "Apellidos", "Nivel", "Grado", "Matricula", "Ver Recibo", "Modificar", "Borrar"};
        Object[][] data = new Object[0][0];
        DefaultTableModel newmodelo = new DefaultTableModel(data, titulos);
        try {
            String query = "select uuid, name, lastname, level, grade, enrollment from inscriptions where name like '%" + tosearch + "%' and deleted_at is null";
            java.sql.ResultSet rs = reg.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                search(rs, newmodelo);
                System.out.println("1");
                return;
            }

            query = "select uuid, name, lastname, level, grade, enrollment from inscriptions where lastname like '%" + tosearch + "%' and deleted_at is null";
            rs = reg.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                search(rs, newmodelo);
                System.out.println("2");
                return;
            }

            query = "select uuid, name, lastname, level, grade, enrollment from inscriptions where level like '%" + tosearch + "%' and deleted_at is null";
            rs = reg.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                search(rs, newmodelo);
                System.out.println("3");
                return;
            }

            query = "select uuid, name, lastname, level, grade, enrollment from inscriptions where grade like '%" + tosearch + "%' and deleted_at is null";
            rs = reg.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                search(rs, newmodelo);
                System.out.println("4");
                return;
            }

            query = "select uuid, name, lastname, level, grade, enrollment from inscriptions where enrollment like '%" + tosearch + "%' and deleted_at is null";
            rs = reg.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                search(rs, newmodelo);
                System.out.println("5");
                return;
            }

            refreshTablaInscripciones();

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    public void search (java.sql.ResultSet rs, DefaultTableModel newmodelo){
        try {
            int columns = rs.getMetaData().getColumnCount();

            while (rs.next())
            {
                Object[] row = new Object[columns + 3];
                for (int i = 1; i <= columns; i++)
                {
                    row[i - 1] = rs.getObject(i);
                }
                row[columns] = "Ver Recibo";
                row[columns + 1] = "Modificar";
                row[columns + 2] = "Borrar";


                newmodelo.insertRow(rs.getRow() - 1,row);
            }

            tabla.setModel(newmodelo);
            tabla.getTableHeader().setReorderingAllowed(false);
            tabla.getColumn("Ver Recibo").setCellRenderer(new ButtonRenderer());
            tabla.getColumn("Ver Recibo").setCellEditor(new ButtonEditor(new JCheckBox(), this, this.tabla));
            tabla.getColumn("Modificar").setCellRenderer(new ButtonRenderer());
            tabla.getColumn("Modificar").setCellEditor(new ButtonEditor(new JCheckBox(), this, this.tabla));
            tabla.getColumn("Borrar").setCellRenderer(new ButtonRenderer());
            tabla.getColumn("Borrar").setCellEditor(new ButtonEditor(new JCheckBox(), this, this.tabla));

            return;
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    public void refreshComboGrade () {
        String level = fieldlevel.getSelectedItem().toString();
        String grade = fieldgrade.getSelectedItem().toString();
        String[] menor = {"1", "2", "3"};
        String[] mayor = {"1", "2", "3", "4", "5", "6"};
        switch (level) {
            case "Primaria": fieldgrade.setModel(new DefaultComboBoxModel(mayor));
            if (Integer.parseInt(grade) < 4) fieldpayment.setText("1500");
            else fieldpayment.setText("2000");
            break;
            case "Secundaria": fieldgrade.setModel(new DefaultComboBoxModel(menor));
                fieldpayment.setText("2500");
                break;
            case "Preparatoria": fieldgrade.setModel(new DefaultComboBoxModel(mayor));
            fieldpayment.setText("3000");
                break;

            default: return;
        }
        fieldlevel.setFont(sizedFontRegular);
        fieldgrade.setFont(sizedFontRegular);
        fieldgrade.setSelectedItem(grade);
        this.repaint();
    }

    @Override
    public void keyTyped (KeyEvent e) {
        Object Objecto = e.getSource();

        if (Objecto == fieldsearch) {
            searchCliente();
        }
    }

    @Override
    public void keyPressed (KeyEvent e) {

    }

    @Override
    public void keyReleased (KeyEvent e) {

    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private int row;
    private MainView view;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, MainView view, JTable table) {
        super(checkBox);
        this.view = view;
        this.table = table;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        this.row = row;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            String uuid = (String) this.table.getValueAt(this.row, 0);
            if (label.equals("Modificar")) {
                view.setInscripcion(uuid);
            } else if (label.equals("Borrar")) {
                view.deleteInscripcion(uuid);
            } else if (label.equals("Ver Recibo")) {
                view.printInscripcion(uuid);
            }
        }
        isPushed = false;
        return label;
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    public boolean isCellEditable(EventObject e){ return true; }
}
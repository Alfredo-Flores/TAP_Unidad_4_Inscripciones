package Views;

import Model.Cita;
import Model.Cliente;
import Model.Trabajador;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RecepcionistaView extends JFrame implements ActionListener {
    JSplitPane splitPane = new JSplitPane();    Panel sidebar = new Panel(), mainpane = new Panel();
    JPanel innerpane = new JPanel();
    File montserratregular = new File("src/Views/assets/Montserrat-Regular.ttf");
    File montserratbold = new File("src/Views/assets/Montserrat-Bold.ttf");
    Font fontregular = Font.createFont(Font.TRUETYPE_FONT, montserratregular);
    Font fontbold = Font.createFont(Font.TRUETYPE_FONT, montserratbold);
    Font sizedFontBold = fontbold.deriveFont(14f);
    Font sizedFontRegular = fontregular.deriveFont(12f);
    Image imagetoothicon = new ImageIcon("src/Views/assets/logo.png").getImage();
    ImageIcon imagetooth = new ImageIcon(new ImageIcon("src/Views/assets/logo.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    ImageIcon imagebrand = new ImageIcon(new ImageIcon("src/Views/assets/brand.png").getImage().getScaledInstance(120, 40, Image.SCALE_SMOOTH));
    ImageIcon imageclientes = new ImageIcon(new ImageIcon("src/Views/assets/client.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
    ImageIcon imagecitas = new ImageIcon(new ImageIcon("src/Views/assets/documents.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon imagetrabajadores = new ImageIcon(new ImageIcon("src/Views/assets/employees.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
    ImageIcon imageshrink = new ImageIcon(new ImageIcon("src/Views/assets/arrow-left-solid.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    ImageIcon imageexpand = new ImageIcon(new ImageIcon("src/Views/assets/arrow-right-solid.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    JButton tabclientes = new JButton("Clientes", imageclientes), tabcitas = new JButton("Citas", imagecitas);
    JButton tabtrabajadores = new JButton("Trabajadores", imagetrabajadores), buttonshrink = new JButton(imageshrink);
    JButton buttonregistrarcliente = new JButton("Registrar"), buttonregistrartrabajador = new JButton("Registrar");
    JButton buttonregistrarcita = new JButton("Registrar");
    JTextField fieldnombre = new JTextField(), fieldapellidopaterno = new JTextField(), fieldapellidomaterno = new JTextField();
    JTextField fieldtelefono = new JTextField(), fielddireccion = new JTextField(), fieldprocedimiento = new JTextField();
    JTextField fieldcosto = new JTextField(), fieldsalario = new JTextField();
    String[] anosarray = {"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
    String[] mesesarray = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    String[] diasarray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    String[] horasarray = {"06:00:00", "07:00:00", "08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00", "21:00:00", "22:00:00", "23:00:00", "00:00:00", "01:00:00", "02:00:00", "03:00:00", "04:00:00", "05:00:00"};
    String[] especialidades = {"Dentista", "Dentista pediátrico", "Endodoncista", "Cirujano Oral", "Cirujano Maxilofacial", "Ortodoncista", "Periodoncista", "Prostodoncista", "Asistente", "Practicante"};
    DefaultComboBoxModel modeloano = new DefaultComboBoxModel(anosarray), modelomeses = new DefaultComboBoxModel(mesesarray);
    DefaultComboBoxModel modelodias = new DefaultComboBoxModel(diasarray), modelohoras = new DefaultComboBoxModel(horasarray);
    JComboBox comboano = new JComboBox(modeloano), combomeses = new JComboBox(modelomeses);
    JComboBox combodias = new JComboBox(modelodias), combohoras = new JComboBox(modelohoras);
    JComboBox combocliente = new JComboBox(), combotrabajador = new JComboBox();
    JComboBox comboespecialidad = new JComboBox(especialidades);
    String[] titulos = new String[0];
    Object[][] data = new Object[0][0];
    DefaultTableModel modelo = new DefaultTableModel(data, titulos) { public boolean isCellEditable(int row, int column) { return false; }};
    JTable tabla = new JTable(modelo);
    int pestanaactual = 1;
    boolean banderasidebar = false;

    RecepcionistaView() throws IOException, FontFormatException {
        super("OdontoClinic - Recepción");  setSize(1152, 640);
        setMinimumSize(new Dimension(850, 480));    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);    setResizable(true);     setIconImage(imagetoothicon);
        this.setSideBar(1); this.setClientesPane();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainpane);
        splitPane.setEnabled(false);    getContentPane().add(splitPane);
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(null,
                        "¿Estas seguro que quieres salir del administrador?", "Cerrar ventana",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == 0){
                    System.exit(0);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object Objecto = actionEvent.getSource();

        try {
            if (Objecto == tabclientes && pestanaactual != 1) {
                int response = JOptionPane.showConfirmDialog(this, "¿Estas seguro de salir de este panel? podria perderse progreso", "Cambiar de panel", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == 0) {
                    this.removeSidebar();   this.removeCurrentPane();
                    this.setSideBar(1);     this.setClientesPane();
                } else {
                    return;
                }
            }
            if (Objecto == tabcitas  && pestanaactual != 2) {
                int response = JOptionPane.showConfirmDialog(this, "¿Estas seguro de salir de este panel? podria perderse progreso", "Cambiar de panel", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == 0) {
                    this.removeSidebar();   this.removeCurrentPane();
                    this.setSideBar(2);     this.setCitasPane();
                    innerpane.repaint();    innerpane.revalidate();
                    mainpane.repaint();    mainpane.revalidate();
                } else {
                    return;
                }
            }
            if (Objecto == tabtrabajadores  && pestanaactual != 3) {
                int response = JOptionPane.showConfirmDialog(this, "¿Estas seguro de salir de este panel? podria perderse progreso", "Cambiar de panel", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (response == 0) {
                    this.removeSidebar();   this.removeCurrentPane();
                    this.setSideBar(3); this.setTrabajadoresPane();
                } else {
                    return;
                }
            }
            if (Objecto == buttonshrink) {
                this.removeSidebar();   this.removeCurrentPane();
                this.changeSideBar();   this.setSideBar(this.pestanaactual);

                if (this.pestanaactual == 1) {
                    this.setClientesPane();
                } else if (this.pestanaactual == 2) {
                    this.setCitasPane();
                } else if (this.pestanaactual == 3) {
                    this.setTrabajadoresPane();
                }

            } if (Objecto == buttonregistrarcliente) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Estas seguro de registrar este cliente?", "Registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == 0) {
                    boolean response = this.createCliente();
                    if (response) {
                        JOptionPane.showMessageDialog(this, "Se ha creado el cliente correctamente");
                        try {
                            this.refreshTablaClientes();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    return;
                }
            } if (Objecto == buttonregistrartrabajador) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Estas seguro de registrar este trabajador?", "Registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == 0) {
                    boolean response = this.createTrabajador();
                    if (response) {
                        JOptionPane.showMessageDialog(this, "Se ha creado el trabajador correctamente");
                        try {
                            this.refreshTablaTrabajadores();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    return;
                }
            }
            if (Objecto == buttonregistrarcita) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Estas seguro de registrar esta cita?", "Registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == 0) {
                    boolean response = this.createCita();
                    if (response) {
                        JOptionPane.showMessageDialog(this, "Se ha creado la cita correctamente");
                        try {
                            this.refreshTablaCitas();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    return;
                }
            }
            if (Objecto == combomeses) {
                refreshComboDias();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        repaint();
    }

    public void setClientesPane() throws IOException {
        JLabel labelnombre = new JLabel("Nombre: "), labelapellidopaterno = new JLabel("Apellido Paterno: ");
        JLabel labelapellidomaterno = new JLabel("Apellido Materno: "), labeltelefono = new JLabel("Telefono Celular: ");
        JLabel labeldireccion = new JLabel("Dirección");

        labelnombre.setFont(sizedFontRegular);
        labelapellidopaterno.setFont(sizedFontRegular);
        labelapellidomaterno.setFont(sizedFontRegular);
        labeltelefono.setFont(sizedFontRegular);
        labeldireccion.setFont(sizedFontRegular);
        fieldnombre.setFont(sizedFontRegular);
        fieldapellidopaterno.setFont(sizedFontRegular);
        fieldapellidomaterno.setFont(sizedFontRegular);
        fieldtelefono.setFont(sizedFontRegular);
        fielddireccion.setFont(sizedFontRegular);
        buttonregistrarcliente.setFont(sizedFontRegular);

        innerpane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(5, 5, 5, 5);
        c.gridy = 0;    c.insets = inset;   c.ipady = 0;
        c.ipadx = 0;    c.weightx = 0.1;    c.weighty = 0.1;
        c.fill = GridBagConstraints.HORIZONTAL;
        innerpane.add(labelnombre, c);
        c.gridy = 1;    innerpane.add(fieldnombre, c);
        c.gridy = 2;    innerpane.add(labelapellidopaterno, c);
        c.gridy = 3;    innerpane.add(fieldapellidopaterno, c);
        c.gridy = 5;    innerpane.add(labelapellidomaterno, c);
        c.gridy = 6;    innerpane.add(fieldapellidomaterno, c);
        c.gridy = 7;    innerpane.add(labeltelefono, c);
        c.gridy = 8;    innerpane.add(fieldtelefono, c);
        c.gridy = 9;    innerpane.add(labeldireccion, c);
        c.gridy = 10;   innerpane.add(fielddireccion, c);
        c.gridy = 11;
        buttonregistrarcliente.removeActionListener(this);
        buttonregistrarcliente.addActionListener(this);
        buttonregistrarcliente.setFont(sizedFontBold);
        innerpane.add(buttonregistrarcliente, c);
        inset = new Insets(5, 10, 50, 10);
        c.gridx = 0;    c.gridy = 1;
        c.insets = inset;   c.anchor = GridBagConstraints.FIRST_LINE_START;
        JScrollPane scrollcitas = new JScrollPane(tabla);
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Nuevo Cliente", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        innerpane.setBorder(border);
        mainpane.add(innerpane, c);
        inset = new Insets(5, 10, 5, 10);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 1; c.gridy = 1;
        c.insets = inset; c.fill = GridBagConstraints.BOTH;
        this.refreshTablaClientes();
        tabla.setFont(sizedFontRegular);
        JScrollPane scrollclientes = new JScrollPane(tabla);
        border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Clientes", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        scrollclientes.setBorder(border);
        mainpane.add(scrollclientes, c);
    }

    public void setCitasPane() throws IOException {
        GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(5, 10, 5, 10);
        c.weightx = 0.1;    c.weighty = 0.1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 0;    c.gridy = 1;
        c.insets = inset;   c.fill = GridBagConstraints.BOTH;
        this.refreshTablaCitas();
        this.refreshCombos();
        combomeses.addActionListener(this);
        tabla.setFont(sizedFontRegular);
        JScrollPane scrollcitas = new JScrollPane(tabla);
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Citas", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        scrollcitas.setBorder(border);
        mainpane.add(scrollcitas, c);

        JLabel labelcliente = new JLabel("Cliente: ");
        JLabel labelespecialista = new JLabel("Especialista: ");
        JLabel labelprocedimiento = new JLabel("Procedimiento: ");
        JLabel labelcosto = new JLabel("Costo: ");
        JLabel labelano = new JLabel("Año: ");
        JLabel labelmes = new JLabel("Meses: ");
        JLabel labeldia = new JLabel("Días: ");
        JLabel labelhora = new JLabel("Hora: ");

        labelcliente.setFont(sizedFontRegular);
        labelespecialista.setFont(sizedFontRegular);
        labelprocedimiento.setFont(sizedFontRegular);
        labelcosto.setFont(sizedFontRegular);
        labelano.setFont(sizedFontRegular);
        labelmes.setFont(sizedFontRegular);
        labeldia.setFont(sizedFontRegular);
        labelhora.setFont(sizedFontRegular);
        fieldprocedimiento.setFont(sizedFontRegular);
        fieldcosto.setFont(sizedFontRegular);
        comboano.setFont(sizedFontRegular);
        combomeses.setFont(sizedFontRegular);
        combodias.setFont(sizedFontRegular);
        combohoras.setFont(sizedFontRegular);

        GridBagConstraints ce = new GridBagConstraints();
        innerpane.setLayout(new GridBagLayout());
        ce.fill = GridBagConstraints.HORIZONTAL;    ce.gridwidth = 4;
        ce.insets = inset;
        ce.gridy = 0;   innerpane.add(labelcliente, ce);
        ce.gridy = 1;   innerpane.add(combocliente, ce);
        ce.gridy = 2;   innerpane.add(labelespecialista, ce);
        ce.gridy = 3;   innerpane.add(combotrabajador, ce);
        ce.gridy = 5;   innerpane.add(labelprocedimiento, ce);
        ce.gridy = 6;   innerpane.add(fieldprocedimiento, ce);
        ce.gridy = 7;   innerpane.add(labelcosto, ce);
        ce.gridy = 8;   innerpane.add(fieldcosto, ce);
        ce.gridy = 9;   ce.gridwidth = 1;
        ce.gridy = 10;  ce.gridx = 0;
        innerpane.add(labelano, ce);
        ce.gridy = 11;  ce.gridx = 0;
        innerpane.add(comboano, ce);
        ce.gridy = 10;  ce.gridx = 1;
        innerpane.add(labelmes, ce);
        ce.gridy = 11;  ce.gridx = 1;
        innerpane.add(combomeses, ce);
        ce.gridy = 10;  ce.gridx = 2;
        innerpane.add(labeldia, ce);
        ce.gridy = 11;  ce.gridx = 2;
        innerpane.add(combodias, ce);
        ce.gridy = 10;  ce.gridx = 3;
        innerpane.add(labelhora, ce);
        ce.gridy = 11;  ce.gridx = 3;
        innerpane.add(combohoras, ce);
        buttonregistrarcita.removeActionListener(this);
        buttonregistrarcita.addActionListener(this);
        buttonregistrarcita.setFont(sizedFontBold);
        ce.gridwidth = 4;
        ce.gridy = 14;  ce.gridx = 0;
        innerpane.add(buttonregistrarcita, ce);
        ce.gridx = 1;   ce.gridy = 1;
        ce.insets = inset;
        ce.anchor = GridBagConstraints.FIRST_LINE_START;    ce.fill = GridBagConstraints.HORIZONTAL;
        border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Nueva Cita", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        innerpane.setBorder(border);
        mainpane.add(innerpane, ce);
    }

    public void setTrabajadoresPane() throws IOException {
        JLabel labelnombre = new JLabel("Nombre: "), labelapellidopaterno = new JLabel("Apellido Paterno: ");
        JLabel labelapellidomaterno = new JLabel("Apellido Materno: "), labeltelefono = new JLabel("Telefono Celular: ");;
        JLabel labeldireccion = new JLabel("Dirección"), labelsalario = new JLabel("Salario Mensual");;
        JLabel labelespecialidad = new JLabel("Especilidad");
        labelnombre.setFont(sizedFontRegular);
        labelapellidopaterno.setFont(sizedFontRegular);
        labelapellidomaterno.setFont(sizedFontRegular);
        labeltelefono.setFont(sizedFontRegular);
        labeldireccion.setFont(sizedFontRegular);
        labelsalario.setFont(sizedFontRegular);
        labelespecialidad.setFont(sizedFontRegular);
        fieldnombre.setFont(sizedFontRegular);
        fieldapellidopaterno.setFont(sizedFontRegular);
        fieldapellidomaterno.setFont(sizedFontRegular);
        fieldtelefono.setFont(sizedFontRegular);
        fielddireccion.setFont(sizedFontRegular);
        fieldsalario.setFont(sizedFontRegular);
        comboespecialidad.setFont(sizedFontRegular);
        innerpane.setLayout(new GridBagLayout());  GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(5, 5, 5, 5);
        c.gridy = 0;    c.insets = inset;   c.ipady = 0;
        c.ipadx = 0;    c.weightx = 0.1;    c.weighty = 0.1;
        c.fill = GridBagConstraints.HORIZONTAL;
        innerpane.add(labelnombre, c);
        c.gridy = 1;    innerpane.add(fieldnombre, c);
        c.gridy = 2;    innerpane.add(labelapellidopaterno, c);
        c.gridy = 3;    innerpane.add(fieldapellidopaterno, c);
        c.gridy = 5;    innerpane.add(labelapellidomaterno, c);
        c.gridy = 6;    innerpane.add(fieldapellidomaterno, c);
        c.gridy = 7;    innerpane.add(labeltelefono, c);
        c.gridy = 8;    innerpane.add(fieldtelefono, c);
        c.gridy = 9;    innerpane.add(labeldireccion, c);
        c.gridy = 10;   innerpane.add(fielddireccion, c);
        c.gridy = 11;   innerpane.add(labelsalario, c);
        c.gridy = 12;   innerpane.add(fieldsalario, c);
        c.gridy = 13;   innerpane.add(labelespecialidad, c);
        c.gridy = 14;   innerpane.add(comboespecialidad, c);
        c.gridy = 15;
        buttonregistrartrabajador.removeActionListener(this);
        buttonregistrartrabajador.addActionListener(this);
        buttonregistrartrabajador.setFont(sizedFontBold);
        innerpane.add(buttonregistrartrabajador, c);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Nuevo Trabajador", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        innerpane.setBorder(border);
        c.gridy = 1;    mainpane.add(innerpane, c);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;    c.gridy = 1;
        this.refreshTablaTrabajadores();
        tabla.setFont(sizedFontRegular);
        JScrollPane scrolltrabajadores = new JScrollPane(tabla);
        border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Trabajadores", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        scrolltrabajadores.setBorder(border);
        mainpane.add(scrolltrabajadores, c);
    }

    public void refreshTablaClientes() throws IOException {
        HashMap<Integer, Cliente> clientes;
        String[] titulos = {"Nombre Completo", "Telefono", "Dirección"};
        Object[][] data = new Object[0][0];
        DefaultTableModel newmodelo = new DefaultTableModel(data, titulos) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            FileInputStream fis = new FileInputStream("clientes.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            clientes = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            File file = new File("clientes.obj");
            FileOutputStream fileStream = new FileOutputStream(file);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject("");
            fileStream.close();
            objectStream.close();
            return;
        }

        Cliente cliente;
        for (Map.Entry<Integer, Cliente> entry : clientes.entrySet()) {
            cliente = entry.getValue();
            newmodelo.addRow(new Object[]{cliente.getNombre() + " " + cliente.getApellidoPaterno() + " " + cliente.getApellidoMaterno(), cliente.getTelefono(), cliente.getDireccion()});
        }
        tabla.setModel(newmodelo);
        tabla.setRowSelectionAllowed(false);
        tabla.setColumnSelectionAllowed(false);
        tabla.getTableHeader().setReorderingAllowed(false);
    }

    public void refreshTablaCitas() throws IOException {
        HashMap<Integer, Cita> citas;   HashMap<Integer, Cliente> clientes;     HashMap<Integer, Trabajador> trabajadores;
        String[] titulos = {"Cliente", "Trabajador", "Procedimiento", "Costo", "Fecha y Hora"};
        Object[][] data = new Object[0][0];
        DefaultTableModel newmodelo = new DefaultTableModel(data, titulos) {public boolean isCellEditable(int row, int column) { return false; }};
        try {
            FileInputStream fis = new FileInputStream("citas.obj"); ObjectInputStream ois = new ObjectInputStream(fis);
            citas = (HashMap) ois.readObject();
            ois.close();    fis.close();
        } catch (Exception e) {
            File file = new File("citas.obj");
            FileOutputStream fileStream = new FileOutputStream(file);   ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject("");
            fileStream.close(); objectStream.close();
            return;
        }
        Cita cita;  Cliente cliente;    Trabajador trabajador;
        String nombrecliente = "", nombretrabajador = "";
        int idcliente, idtrabajador;
        for (Map.Entry<Integer, Cita> entry : citas.entrySet()) {
            cita = entry.getValue();    idcliente = cita.getIdcliente();    idtrabajador = cita.getIdtrabajador();
            try {
                FileInputStream fis = new FileInputStream("clientes.obj");  ObjectInputStream ois = new ObjectInputStream(fis);
                clientes = (HashMap) ois.readObject();
                ois.close();    fis.close();
                for (Map.Entry<Integer, Cliente> entrycliente : clientes.entrySet()) {
                    cliente = entrycliente.getValue();
                    if (cliente.getId() == idcliente) {
                        nombrecliente = cliente.getNombre() + " " + cliente.getApellidoPaterno() + " " + cliente.getApellidoMaterno();  break;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al ver clientes");
                return;
            }
            try {
                FileInputStream fis = new FileInputStream("trabajadores.obj");  ObjectInputStream ois = new ObjectInputStream(fis);
                trabajadores = (HashMap) ois.readObject();
                ois.close();    fis.close();
                for (Map.Entry<Integer, Trabajador> entrytrabajador : trabajadores.entrySet()) {
                    trabajador = entrytrabajador.getValue();
                    if (trabajador.getId() == idtrabajador) {
                        nombretrabajador = trabajador.getNombre() + " " + trabajador.getApellidopaterno() + " " + trabajador.getApellidomaterno();
                        break;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al ver trabajadores");
                return;
            }
            newmodelo.addRow(new Object[]{ nombrecliente, nombretrabajador, cita.getProcedimiento(), cita.getCosto(), cita.getFecha().toString()});
        }
        tabla.setModel(newmodelo);  tabla.setRowSelectionAllowed(false);
        tabla.setColumnSelectionAllowed(false); tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getColumnModel().getColumn(0).setMinWidth(100);   tabla.getColumnModel().getColumn(1).setMinWidth(100);   tabla.getColumnModel().getColumn(4).setMinWidth(120);
    }

    public void refreshTablaTrabajadores() throws IOException {
        HashMap<Integer, Trabajador> trabajadores;  String[] titulos = {"Nombre Completo", "Telefono", "Dirección", "Salario Mensual", "Especialidad / Rol"};
        Object[][] data = new Object[0][0]; DefaultTableModel newmodelo = new DefaultTableModel(data, titulos) { public boolean isCellEditable(int row, int column) { return false; }};
        try {
            FileInputStream fis = new FileInputStream("trabajadores.obj");  ObjectInputStream ois = new ObjectInputStream(fis);
            trabajadores = (HashMap) ois.readObject();
            ois.close();    fis.close();
        } catch (Exception e) {
            File file = new File("trabajadores.obj");
            FileOutputStream fileStream = new FileOutputStream(file);   ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject("");
            fileStream.close(); objectStream.close();
            return;
        }
        Trabajador trabajador;
        String tipo;
        for (Map.Entry<Integer, Trabajador> entry : trabajadores.entrySet()) {
            trabajador = entry.getValue();
            newmodelo.addRow(new Object[]{ trabajador.getNombre() +  " " + trabajador.getApellidopaterno() + " " + trabajador.getApellidomaterno(), trabajador.getTelefono(), trabajador.getDireccion(), trabajador.getSalario() + ".00 $", trabajador.getTipo()});
        }
        tabla.setModel(newmodelo);  tabla.setRowSelectionAllowed(false);
        tabla.setColumnSelectionAllowed(false); tabla.getTableHeader().setReorderingAllowed(false);
    }

    public boolean createCliente() {
        String nombre = "", apellidoPaterno = "", apellidoMaterno = "", telefono = "", direccion = "";

        if (this.valirdarString(this.fieldnombre.getText())) {
            nombre = this.fieldnombre.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el nombre correctamente"); return false;
        }
        if (this.valirdarString(this.fieldapellidopaterno.getText())) {
            apellidoPaterno = this.fieldapellidopaterno.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el apellido paterno correctamente");   return false;
        }
        if (this.valirdarString(this.fieldapellidomaterno.getText())) {
            apellidoMaterno = this.fieldapellidomaterno.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el apellido materno correctamente");   return false;
        }
        try {
            telefono = this.fieldtelefono.getText();
            if (telefono == null || telefono.equals("") || telefono.length() < 10) {
                JOptionPane.showMessageDialog(this, "Escriba un telefono mayor de 10 digitos"); return false;
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Escriba el telefono correctamente");   return false;
        }
        if (this.valirdarString(this.fielddireccion.getText())) {
            direccion = this.fielddireccion.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba la dirección correctamente");  return false;
        }

        Cliente nuevocliente;
        HashMap<Integer, Cliente> clientes = null;

        try {
            FileInputStream filestream = new FileInputStream("clientes.obj");   ObjectInputStream objectstream = new ObjectInputStream(filestream);
            clientes = (HashMap) objectstream.readObject();
            objectstream.close();   filestream.close();
            int id = clientes.size() + 1;
            nuevocliente = new Cliente(id, nombre, apellidoPaterno, apellidoMaterno, telefono, direccion);
            clientes.put(id, nuevocliente);
        } catch (Exception e) {
            clientes = new HashMap<Integer, Cliente>();
            nuevocliente = new Cliente(1, nombre, apellidoPaterno, apellidoMaterno, telefono, direccion);
            clientes.put(1, nuevocliente);
        }

        try {
            File file = new File("clientes.obj");
            FileOutputStream fileStream = new FileOutputStream(file);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(clientes);
            fileStream.close();
            objectStream.close();

            fieldnombre.setText("");    fieldapellidopaterno.setText("");
            fieldapellidomaterno.setText("");   fieldtelefono.setText("");
            fielddireccion.setText("");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado");
            return false;
        }
    }

    public boolean createTrabajador() {
        String nombre = "", apellidoPaterno = "", apellidoMaterno = "", telefono = "", direccion = "", salariostring = "", tipotrabajadorstring = "";
        int salario, tipotrabajador;
        if (this.valirdarString(this.fieldnombre.getText())) {
            nombre = this.fieldnombre.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el nombre correctamente");
        }
        if (this.valirdarString(this.fieldapellidopaterno.getText())) {
            apellidoPaterno = this.fieldapellidopaterno.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el apellido paterno correctamente");   return false;
        }
        if (this.valirdarString(this.fieldapellidomaterno.getText())) {
            apellidoMaterno = this.fieldapellidomaterno.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el apellido materno correctamente");   return false;
        }
        try {
            telefono = this.fieldtelefono.getText();
            if (telefono == null || telefono.equals("") || telefono.length() < 10) {
                JOptionPane.showMessageDialog(this, "Escriba un telefono mayor de 10 digitos"); return false;
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Escriba el telefono correctamente");   return false;
        }
        if (this.valirdarString(this.fielddireccion.getText())) {
            direccion = this.fielddireccion.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba la dirección correctamente");  return false;
        }
        if (this.valirdarString(this.fieldsalario.getText())) {
            salariostring = this.fieldsalario.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el salario correctamente");    return false;
        }
        tipotrabajadorstring = comboespecialidad.getSelectedItem().toString();
        Trabajador nuevocliente;    HashMap<Integer, Trabajador> nuevotrabajador = null;
        try {
            FileInputStream filestream = new FileInputStream("trabajadores.obj");   ObjectInputStream objectstream = new ObjectInputStream(filestream);
            nuevotrabajador = (HashMap) objectstream.readObject();
            objectstream.close();   filestream.close();
            int id = nuevotrabajador.size() + 1;
            nuevocliente = new Trabajador(id, nombre, apellidoPaterno, apellidoMaterno, telefono, direccion, salariostring, tipotrabajadorstring);
            nuevotrabajador.put(id, nuevocliente);
        } catch (Exception e) {
            nuevotrabajador = new HashMap<Integer, Trabajador>();
            nuevocliente = new Trabajador(1, nombre, apellidoPaterno, apellidoMaterno, telefono, direccion, salariostring, tipotrabajadorstring);
            nuevotrabajador.put(1, nuevocliente);
        }
        try {
            File file = new File("trabajadores.obj");
            FileOutputStream fileStream = new FileOutputStream(file);   ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(nuevotrabajador);
            fileStream.close(); objectStream.close();
            fieldnombre.setText("");   fieldapellidopaterno.setText("");
            fieldapellidomaterno.setText("");  fieldtelefono.setText("");
            fielddireccion.setText("");    fieldsalario.setText("");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado");    return false;
        }
    }

    public boolean createCita() {
        String procedimiento = "", costo = "", estado = "";     int idcliente, idtrabajador;
        Timestamp timestamp;    String ano, mes, dia, hora, timestampstring;
        try {
            idcliente = Character.getNumericValue(combocliente.getSelectedItem().toString().charAt(0));
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado con el cliente");  return false;
        }
        try {
            idtrabajador = Character.getNumericValue(combotrabajador.getSelectedItem().toString().charAt(0));
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado con el trabajador");   return false;
        }
        if (this.valirdarString(fieldprocedimiento.getText())) {
            procedimiento = fieldprocedimiento.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el procedimiento correctamente");    return false;
        }
        if (this.valirdarInt(fieldcosto.getText())) {
            costo = fieldcosto.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Escriba el costo correctamente");    return false;
        }

        try {
            ano = comboano.getSelectedItem().toString();    mes = combomeses.getSelectedItem().toString();
            dia = combodias.getSelectedItem().toString();   hora = combohoras.getSelectedItem().toString();
            if (dia == null || dia == "") {
                JOptionPane.showMessageDialog(this, "Elija el mes, para poder escojer el día");
                return false;
            }
            switch (mes) {
                case "Enero": mes = "01"; break;
                case "Febrero": mes = "02"; break;
                case "Marzo": mes = "03"; break;
                case "Abril": mes = "04"; break;
                case "Mayo": mes = "05"; break;
                case "Junio": mes = "06"; break;
                case "Julio": mes = "07"; break;
                case "Agosto": mes = "08"; break;
                case "Septiembre": mes = "09"; break;
                case "Octubre": mes = "10"; break;
                case "Nomviembre": mes = "11"; break;
                case "Diciembre": mes = "12"; break;
                default: JOptionPane.showMessageDialog(this, "Ocurrio un error en los meses"); return false;
            }
            hora = hora + ".000";
            timestampstring = ano + "-" + mes + "-" + dia + " " + hora;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(timestampstring);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Escoja una fecha y horario correctamente");    return false;
        }
        try {
            estado = combotrabajador.getSelectedItem().toString();
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Escriba el costo correctamente");  return false;
        }
        Cita nuevacita; HashMap<Integer, Cita> citas = null;
        try {
            FileInputStream filestream = new FileInputStream("citas.obj");  ObjectInputStream objectstream = new ObjectInputStream(filestream);
            citas = (HashMap) objectstream.readObject();
            objectstream.close();   filestream.close();
            int id = citas.size() + 1;
            nuevacita = new Cita(id, idcliente, idtrabajador, procedimiento, costo, timestamp, estado);
            citas.put(id, nuevacita);
        } catch (Exception e) {
            citas = new HashMap<Integer, Cita>();
            nuevacita = new Cita(0, idcliente, idtrabajador, procedimiento, costo, timestamp, estado);
            citas.put(0, nuevacita);
        }
        try {
            File file = new File("citas.obj");
            FileOutputStream fileStream = new FileOutputStream(file);   ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(citas);
            fileStream.close(); objectStream.close();
            fieldprocedimiento.setText(""); fieldcosto.setText("");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado"); return false;
        }
    }

    public void refreshCombos() {
        HashMap<Integer, Cliente> clientes; HashMap<Integer, Trabajador> trabajadores;
        try {
            FileInputStream fis = new FileInputStream("clientes.obj");  ObjectInputStream ois = new ObjectInputStream(fis);
            clientes = (HashMap) ois.readObject();
            ois.close();    fis.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No existen clientes"); return;
        }
        try {
            FileInputStream fis = new FileInputStream("trabajadores.obj");  ObjectInputStream ois = new ObjectInputStream(fis);
            trabajadores = (HashMap) ois.readObject();
            ois.close();    fis.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No existen trabajadores"); return;
        }
        Cliente cliente;    Trabajador trabajador;  String row; int i;
        String[] newClientes = new String[clientes.size()], newTrabajadores = new String[trabajadores.size()];
        for (Map.Entry<Integer, Cliente> entry : clientes.entrySet()) {
            cliente = entry.getValue();
            i = entry.getKey() - 1;
            row = cliente.getId() + " " + cliente.getNombre() +  " " + cliente.getApellidoPaterno() + " " + cliente.getApellidoMaterno();
            newClientes[i] = row;
        }
        for (Map.Entry<Integer, Trabajador> entry : trabajadores.entrySet()) {
            trabajador = entry.getValue();
            i = entry.getKey() - 1;
            row = trabajador.getId() + " " + trabajador.getNombre() +  " " + trabajador.getApellidopaterno() + " " + trabajador.getApellidomaterno();
            newTrabajadores[i] = row;
        }
        this.combocliente = new JComboBox(newClientes);
        this.combotrabajador = new JComboBox(newTrabajadores);
        combocliente.setFont(sizedFontRegular);
        combotrabajador.setFont(sizedFontRegular);
    }

    public void refreshComboDias() {
        String mes = combomeses.getSelectedItem().toString();
        String ano = comboano.getSelectedItem().toString();
        String[] mesbisiesto = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};
        String[] mespar = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
        String[] mesnon = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        switch (mes) {
            case "Enero": combodias.setModel(new DefaultComboBoxModel(mesnon)); break;
            case "Febrero": combodias.setModel(new DefaultComboBoxModel(mespar)); break;
            case "Marzo": combodias.setModel(new DefaultComboBoxModel(mesnon)); break;
            case "Abril": combodias.setModel(new DefaultComboBoxModel(mespar)); break;
            case "Mayo": combodias.setModel(new DefaultComboBoxModel(mesnon)); break;
            case "Junio": combodias.setModel(new DefaultComboBoxModel(mespar)); break;
            case "Julio": combodias.setModel(new DefaultComboBoxModel(mesnon)); break;
            case "Agosto": combodias.setModel(new DefaultComboBoxModel(mesnon)); break;
            case "Septiembre": combodias.setModel(new DefaultComboBoxModel(mespar)); break;
            case "Octubre": combodias.setModel(new DefaultComboBoxModel(mesnon)); break;
            case "Nomviembre": combodias.setModel(new DefaultComboBoxModel(mespar)); break;
            case "Diciembre": combodias.setModel(new DefaultComboBoxModel(mesnon)); break;
            default: return;
        }
        combodias.setFont(sizedFontRegular);
    }

    public void setSideBar(int tab) {
        this.pestanaactual = tab;
        GridLayout gridLayout = new GridLayout(10, 1, 5, 10);
        sidebar.setLayout(gridLayout);
        JLabel LabelTitulo = new JLabel("OdontoClinic");
        String fontname = LabelTitulo.getFont().getName();
        LabelTitulo.setFont(new Font(fontname, Font.BOLD, 18));
        Font sizedFont = fontbold.deriveFont(14f);
        Font sizedFontRegular = fontregular.deriveFont(12f);
        if (banderasidebar) {
            splitPane.setDividerLocation(50);
            LabelTitulo = new JLabel(imagetooth);
            LabelTitulo.setHorizontalAlignment(JLabel.CENTER);
            sidebar.add(LabelTitulo);
            tabclientes = new JButton(imageclientes);
            tabcitas = new JButton(imagecitas);
            tabtrabajadores = new JButton(imagetrabajadores);
        } else {
            splitPane.setDividerLocation(195);
            LabelTitulo = new JLabel(imagebrand);
            LabelTitulo.setHorizontalAlignment(JLabel.CENTER);
            sidebar.add(LabelTitulo);
            tabclientes = new JButton("CLIENTES", imageclientes);
            tabcitas = new JButton("CITAS", imagecitas);
            tabtrabajadores = new JButton("TRABAJADORES", imagetrabajadores);

            tabclientes.setIconTextGap(60);
            tabcitas.setIconTextGap(85);

            tabclientes.setFont(sizedFont);
            tabcitas.setFont(sizedFont);
            tabtrabajadores.setFont(sizedFont);
        }

        tabclientes.removeActionListener(this);
        tabcitas.removeActionListener(this);
        tabtrabajadores.removeActionListener(this);

        tabclientes.addActionListener(this);
        tabcitas.addActionListener(this);
        tabtrabajadores.addActionListener(this);

        sidebar.add(tabclientes);
        sidebar.add(tabcitas);
        sidebar.add(tabtrabajadores);
        sizedFont = fontbold.deriveFont(20f);
        JLabel LabelPanel = new JLabel("NOPE");
        if (tab == 1) {
            LabelPanel = new JLabel("Clientes");
            LabelPanel.setFont(sizedFont);
        } if (tab == 2) {
            LabelPanel = new JLabel("Citas");
            LabelPanel.setFont(sizedFont);
        } if (tab == 3) {
            LabelPanel = new JLabel("Trabajadores");
            LabelPanel.setFont(sizedFont);
        }
        mainpane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(5, 10, 5, 10);
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
        mainpane.add(LabelPanel, c);
    }

    private void removeSidebar() {
        sidebar.removeAll();
    }

    private void removeCurrentPane() {
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

    private boolean valirdarInt(String string) {
        try {
            if (string != null && !string.equals("")){
                Integer.parseInt(string);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
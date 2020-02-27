package Views;

import Model.Cita;
import Model.Cliente;
import Model.Trabajador;
import Model.Usuario;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DuenoView extends JFrame implements ActionListener {

    JSplitPane splitPane = new JSplitPane(); Panel sidebar = new Panel(), mainpane = new Panel();
    JPanel borderform = new JPanel();
    File montserratregular = new File("src/Views/assets/Montserrat-Regular.ttf");
    File montserratbold = new File("src/Views/assets/Montserrat-Bold.ttf");
    Font fontregular = Font.createFont(Font.TRUETYPE_FONT, montserratregular);
    Font fontbold = Font.createFont(Font.TRUETYPE_FONT, montserratbold);
    Font sizedFontBold = fontbold.deriveFont(14f);
    Font sizedFontRegular = fontregular.deriveFont(12f);
    Image imagetoothicon = new ImageIcon("src/Views/assets/logo.png").getImage();
    ImageIcon imagetooth = new ImageIcon(new ImageIcon("src/Views/assets/logo.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    ImageIcon imagebrand = new ImageIcon(new ImageIcon("src/Views/assets/brand.png").getImage().getScaledInstance(120, 40, Image.SCALE_SMOOTH));
    ImageIcon imagefinanzas = new ImageIcon(new ImageIcon("src/Views/assets/money.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
    ImageIcon imageregistros = new ImageIcon(new ImageIcon("src/Views/assets/documents.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
    ImageIcon imageusers = new ImageIcon(new ImageIcon("src/Views/assets/dog.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
    ImageIcon imageexpand = new ImageIcon(new ImageIcon("src/Views/assets/arrow-right-solid.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    ImageIcon imageshrink = new ImageIcon(new ImageIcon("src/Views/assets/arrow-left-solid.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

    JButton tabfinanzas;
    JButton tabregistros;
    JButton tabusers;
    JButton buttonshrink;
    JButton buttonregistrarusuario = new JButton("Registrar");
    JLabel labelsalarios;
    JLabel labelingresos;
    JTextField fieldusername = new JTextField();
    JTextField fieldpassword = new JTextField();
    String[] estadosarray = {"Recepcionista", "Administrador"};
    JComboBox combotipousuario = new JComboBox(estadosarray);


    String[] titulos = new String[0];
    Object[][] data = new Object[0][0];

    DefaultTableModel modelousuarios = new DefaultTableModel(data, titulos) {
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    };
    DefaultTableModel modeloclientes = new DefaultTableModel(data, titulos) {
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    };
    DefaultTableModel modelotrabajadores = new DefaultTableModel(data, titulos) {
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    };
    DefaultTableModel modelocitas = new DefaultTableModel(data, titulos) {
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    };
    JTable tablausuarios = new JTable(modelousuarios);
    JTable tablaclientes = new JTable(modeloclientes);
    JTable tablatrabajadores = new JTable(modelotrabajadores);
    JTable tablacitas = new JTable(modelocitas);

    int tab = 1;
    boolean shrinkedsidebar = false;

    DuenoView() throws IOException, FontFormatException {
        super("OdontoClinic - Administración");
        setSize(1152, 640);
        setMinimumSize(new Dimension(850, 480));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setIconImage(imagetoothicon);

        this.setSideBar(1);
        this.setFinanzasTab();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainpane);
        splitPane.setEnabled(false);
        getContentPane().add(splitPane);

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
            if (Objecto == tabfinanzas) {
                if (tab == 3) {
                    int response = JOptionPane.showConfirmDialog(this, "¿Estas seguro de salir de este panel? podria perderse progreso", "Cambiar de panel", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (response == 0) {
                        this.removesidebar();
                        this.removeTab();
                        this.setSideBar(1);
                        this.setFinanzasTab();
                    } else {
                        return;
                    }
                } else {
                    this.removesidebar();
                    this.removeTab();
                    this.setSideBar(1);
                    this.setFinanzasTab();
                }
            }
            if (Objecto == tabregistros) {
                if (tab == 3) {
                    int response = JOptionPane.showConfirmDialog(this, "¿Estas seguro de salir de este panel? podria perderse progreso", "Cambiar de panel", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (response == 0) {
                        this.removesidebar();
                        this.removeTab();
                        this.setSideBar(2);
                        this.setRegistrosTab();
                    } else {
                        return;
                    }
                } else {
                    this.removesidebar();
                    this.removeTab();
                    this.setSideBar(2);
                    this.setRegistrosTab();
                }
            }
            if (Objecto == tabusers && tab != 3) {
                this.removesidebar();
                this.removeTab();
                this.setSideBar(3);
                this.setUsuariosTab();
            }
            if (Objecto == buttonshrink) {
                this.removesidebar();
                this.removeTab();
                this.changeSideBar();
                this.setSideBar(this.tab);

                if (this.tab == 1) {
                    this.setFinanzasTab();
                } else if (this.tab == 2) {
                    this.setRegistrosTab();
                } else if (this.tab == 3) {
                    this.setUsuariosTab();
                }
            } if (Objecto == buttonregistrarusuario) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Estas seguro de registrar este usuario?", "Registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (confirm == 0) {
                    boolean response = this.createUsuario();

                    if (response) {
                        JOptionPane.showMessageDialog(this, "Se ha creado el usuario correctamente");
                        try {
                            this.refreshTablaUsuarios();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        revalidate();
        repaint();
    }

    public void setSideBar(int tab) {
        this.tab = tab;
        GridLayout gridLayout = new GridLayout(10, 1, 5, 10);
        sidebar.setLayout(gridLayout);
        JLabel LabelTitulo = new JLabel("OdontoClinic");
        String fontname = LabelTitulo.getFont().getName();
        LabelTitulo.setFont(new Font(fontname, Font.BOLD, 18));
        Font sizedFont = fontbold.deriveFont(14f);
        Font sizedFontRegular = fontregular.deriveFont(12f);
        if (shrinkedsidebar) {
            splitPane.setDividerLocation(50);
            LabelTitulo = new JLabel(imagetooth);
            LabelTitulo.setHorizontalAlignment(JLabel.CENTER);
            sidebar.add(LabelTitulo);
            tabfinanzas = new JButton(imagefinanzas);
            tabregistros = new JButton(imageregistros);
            tabusers = new JButton(imageusers);
        } else {
            splitPane.setDividerLocation(195);
            LabelTitulo = new JLabel(imagebrand);
            LabelTitulo.setHorizontalAlignment(JLabel.CENTER);
            sidebar.add(LabelTitulo);
            tabfinanzas = new JButton("FINANZAS", imagefinanzas);
            tabregistros = new JButton("REGISTROS", imageregistros);
            tabusers = new JButton("USUARIOS", imageusers);

            tabfinanzas.setIconTextGap(45);
            tabregistros.setIconTextGap(40);
            tabusers.setIconTextGap(45);

            tabfinanzas.setFont(sizedFont);
            tabregistros.setFont(sizedFont);
            tabusers.setFont(sizedFont);
        }

        tabfinanzas.removeActionListener(this);
        tabregistros.removeActionListener(this);
        tabusers.removeActionListener(this);

        tabfinanzas.addActionListener(this);
        tabregistros.addActionListener(this);
        tabusers.addActionListener(this);

        sidebar.add(tabfinanzas);
        sidebar.add(tabregistros);
        sidebar.add(tabusers);
        sizedFont = fontbold.deriveFont(20f);
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
        if (shrinkedsidebar) {
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

    public void removesidebar() {
        sidebar.removeAll();
    }

    public void removeTab() {
        tablausuarios.removeAll();
        borderform.removeAll();
        mainpane.removeAll();
    }

    public void changeSideBar() {
        this.shrinkedsidebar = !this.shrinkedsidebar;
    }

    public void setFinanzasTab() throws IOException {
        this.refreshFinanzasTablas();
        borderform.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(5, 5, 5, 5);
        Font sizedFont = fontbold.deriveFont(18f);
        Font sizedFontRegular = fontregular.deriveFont(12f);
        c.insets = inset;
        c.ipady = 0;
        c.ipadx = 0;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridy = 1;
        c.gridx = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        labelsalarios.setFont(sizedFont);
        labelsalarios.setHorizontalAlignment(JLabel.CENTER);
        mainpane.add(labelsalarios, c);
        c.gridx = 1;
        labelingresos.setFont(sizedFont);
        labelingresos.setHorizontalAlignment(JLabel.CENTER);
        mainpane.add(labelingresos, c);
        inset = new Insets(5, 10, 5, 10);
        c.gridx = 0;
        c.gridy = 2;
        c.insets = inset;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        tablacitas.setFont(sizedFontRegular);
        JScrollPane scrollcitas = new JScrollPane(tablacitas);
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Citas", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        scrollcitas.setBorder(border);
        mainpane.add(scrollcitas, c);
        c.gridy = 3;
        tablatrabajadores.setFont(sizedFontRegular);
        JScrollPane scrolltrabajadores = new JScrollPane(tablatrabajadores);
        border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Trabajadores", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        scrolltrabajadores.setBorder(border);
        mainpane.add(scrolltrabajadores, c);
    }

    public void setRegistrosTab() throws IOException {
        this.refreshAllTablas();

        borderform.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(5, 5, 5, 5);
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = inset;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        JScrollPane scrollcitas = new JScrollPane(tablacitas);
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Citas", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        scrollcitas.setBorder(border);
        mainpane.add(scrollcitas, c);
        c.gridy = 2;
        JScrollPane scrolltrabajadores = new JScrollPane(tablatrabajadores);
        border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Trabajadores", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        scrolltrabajadores.setBorder(border);
        mainpane.add(scrolltrabajadores, c);
        c.gridy = 3;
        JScrollPane scrollclientes = new JScrollPane(tablaclientes);
        border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Clientes", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        scrollclientes.setBorder(border);
        mainpane.add(scrollclientes, c);
    }

    public void setUsuariosTab() throws IOException {
        JLabel labelusername = new JLabel("Nombre de usuario: ");
        JLabel labelpassword = new JLabel("Contraseña: ");
        JLabel labelrol = new JLabel("Rol: ");

        labelusername.setFont(sizedFontRegular);
        labelpassword.setFont(sizedFontRegular);
        labelrol.setFont(sizedFontRegular);
        fieldusername.setFont(sizedFontRegular);
        fieldpassword.setFont(sizedFontRegular);
        combotipousuario.setFont(sizedFontRegular);
        buttonregistrarusuario.setFont(sizedFontRegular);
        borderform.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets inset = new Insets(5, 5, 5, 5);
        c.gridy = 0;
        c.insets = inset;
        c.ipady = 0;
        c.ipadx = 0;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.HORIZONTAL;
        borderform.add(labelusername, c);
        c.gridy = 1;
        borderform.add(fieldusername, c);
        c.gridy = 2;
        borderform.add(labelpassword, c);
        c.gridy = 3;
        borderform.add(fieldpassword, c);
        c.gridy = 5;
        borderform.add(labelrol, c);
        c.gridy = 6;
        borderform.add(combotipousuario, c);
        buttonregistrarusuario.removeActionListener(this);
        buttonregistrarusuario.addActionListener(this);
        c.gridy = 7;
        borderform.add(buttonregistrarusuario, c);
        c.weightx = 0.1;
        c.weighty = 0.1;
        inset = new Insets(5, 10, 50, 10);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = inset;
        c.ipady = 0;
        c.ipadx = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Nuevo Usuario", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        borderform.setBorder(border);
        mainpane.add(borderform, c);
        inset = new Insets(5, 10, 5, 10);
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 1;
        c.gridy = 1;
        c.insets = inset;
        c.gridheight = 6;
        this.refreshTablaUsuarios();
        tablausuarios.setFont(sizedFontRegular);
        JScrollPane scrollusuarios = new JScrollPane(tablausuarios);
        border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Usuarios", TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(sizedFontRegular);
        scrollusuarios.setBorder(border);
        scrollusuarios.setMinimumSize(new Dimension(400, 400));
        mainpane.add(scrollusuarios, c);
    }

    public void refreshAllTablas(){
        HashMap<Integer, Cliente> clientes;
        HashMap<Integer, Trabajador> trabajadores;
        HashMap<Integer, Cita> citas;
        String[] titulos = {"Nombre Completo", "Telefono", "Dirección"};
        Object[][] data = new Object[0][0];
        DefaultTableModel newmodeloclientes = new DefaultTableModel(data, titulos) {
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        titulos = new String[]{"Nombre Completo", "Telefono", "Dirección", "Salario"};
        DefaultTableModel newmodelotrabajadores = new DefaultTableModel(data, titulos) {
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        titulos = new String[]{"Cliente", "Trabajador", "Procedimiento", "Costo", "Fecha y Hora"};
        DefaultTableModel newmodelocitas = new DefaultTableModel(data, titulos) {
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        try {
            FileInputStream fis = new FileInputStream("clientes.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            clientes = (HashMap) ois.readObject();
            ois.close();
            fis.close();

            fis = new FileInputStream("citas.obj");
            ois = new ObjectInputStream(fis);
            citas = (HashMap) ois.readObject();
            ois.close();
            fis.close();

            fis = new FileInputStream("trabajadores.obj");
            ois = new ObjectInputStream(fis);
            trabajadores = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error, alguna tabla no existe");
            return;
        }

        Cliente cliente;
        for (Map.Entry<Integer, Cliente> entry : clientes.entrySet()) {
            cliente = entry.getValue();
            newmodeloclientes.addRow(new Object[]{ cliente.getNombre() +  " " + cliente.getApellidoPaterno() + " " + cliente.getApellidoMaterno(), cliente.getTelefono(), cliente.getDireccion(), "Ver Expediente"});
        }

        Trabajador trabajador;
        for (Map.Entry<Integer, Trabajador> entry : trabajadores.entrySet()) {
            trabajador = entry.getValue();
            newmodelotrabajadores.addRow(new Object[]{ trabajador.getNombre() +  " " + trabajador.getApellidopaterno() + " " + trabajador.getApellidomaterno(), trabajador.getTelefono(), trabajador.getDireccion(), trabajador.getSalario(), "Ver Citas"});
        }

        Cita cita;
        String nombrecliente = "", nombretrabajador = "";
        int idcliente, idtrabajador;
        for (Map.Entry<Integer, Cita> entry : citas.entrySet()) {
            cita = entry.getValue();
            idcliente = cita.getIdcliente();
            idtrabajador = cita.getIdtrabajador();
            try {
                for (Map.Entry<Integer, Cliente> entrycliente : clientes.entrySet()) {
                    cliente = entrycliente.getValue();
                    if (cliente.getId() == idcliente) {
                        nombrecliente = cliente.getNombre() + " " + cliente.getApellidoPaterno() + " " + cliente.getApellidoMaterno();
                        break;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al ver clientes");
                return;
            }
            try {
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
            newmodelocitas.addRow(new Object[]{ nombrecliente, nombretrabajador, cita.getProcedimiento(), cita.getCosto(), cita.getFecha().toString()});
        }

        tablaclientes.setModel(newmodeloclientes);
        tablaclientes.setRowSelectionAllowed(false);
        tablaclientes.setColumnSelectionAllowed(false);
        tablaclientes.getTableHeader().setReorderingAllowed(false);

        tablatrabajadores.setModel(newmodelotrabajadores);
        tablatrabajadores.setRowSelectionAllowed(false);
        tablatrabajadores.setColumnSelectionAllowed(false);
        tablatrabajadores.getTableHeader().setReorderingAllowed(false);

        tablacitas.setModel(newmodelocitas);
        tablacitas.setRowSelectionAllowed(false);
        tablacitas.setColumnSelectionAllowed(false);
        tablacitas.getTableHeader().setReorderingAllowed(false);

    }

    public void refreshFinanzasTablas(){
        HashMap<Integer, Cliente> clientes;
        HashMap<Integer, Trabajador> trabajadores;
        HashMap<Integer, Cita> citas;
        String[] titulos = {"Nombre Completo", "Telefono", "Salario Mensual", "Salario Anual"};
        Object[][] data = new Object[0][0];
        DefaultTableModel newmodelotrabajadores = new DefaultTableModel(data, titulos) {
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        titulos = new String[]{"Cliente", "Trabajador", "Procedimiento", "Costo", "Fecha y Hora"};
        DefaultTableModel newmodelocitas = new DefaultTableModel(data, titulos) {
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        long sumatoriaingreso = 0, sumatoriapagomensual = 0;
        try {
            FileInputStream fis = new FileInputStream("citas.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            citas = (HashMap) ois.readObject();
            ois.close();
            fis.close();

            fis = new FileInputStream("trabajadores.obj");
            ois = new ObjectInputStream(fis);
            trabajadores = (HashMap) ois.readObject();
            ois.close();
            fis.close();

            fis = new FileInputStream("clientes.obj");
            ois = new ObjectInputStream(fis);
            clientes = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error, no existen aun tablas");
            return;
        }
        Trabajador trabajador;
        for (Map.Entry<Integer, Trabajador> entry : trabajadores.entrySet()) {
            trabajador = entry.getValue();
            sumatoriapagomensual += Long.parseLong(trabajador.getSalario());
            newmodelotrabajadores.addRow(new Object[]{ trabajador.getNombre() +  " " + trabajador.getApellidopaterno() + " " + trabajador.getApellidomaterno(), trabajador.getTelefono(), trabajador.getSalario(),  Long.parseLong(trabajador.getSalario()) * 12});
        }

        Cita cita;
        Cliente cliente;
        String nombrecliente = "", nombretrabajador = "";
        int idcliente, idtrabajador;
        for (Map.Entry<Integer, Cita> entry : citas.entrySet()) {
            cita = entry.getValue();
            idcliente = cita.getIdcliente();
            idtrabajador = cita.getIdtrabajador();
            try {
                for (Map.Entry<Integer, Cliente> entrycliente : clientes.entrySet()) {
                    cliente = entrycliente.getValue();
                    if (cliente.getId() == idcliente) {
                        nombrecliente = cliente.getNombre() + " " + cliente.getApellidoPaterno() + " " + cliente.getApellidoMaterno();
                        break;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al ver clientes");
                return;
            }
            try {
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
            sumatoriaingreso += Long.parseLong(cita.getCosto());
            newmodelocitas.addRow(new Object[]{ nombrecliente, nombretrabajador, cita.getProcedimiento(), cita.getCosto(), cita.getFecha().toString()});
        }

        labelsalarios = new JLabel("Salarios a pagar mensualmente: " + sumatoriapagomensual);
        labelingresos = new JLabel("Ingreso total: " + sumatoriaingreso);
        tablatrabajadores.setModel(newmodelotrabajadores);
        tablatrabajadores.setRowSelectionAllowed(false);
        tablatrabajadores.setColumnSelectionAllowed(false);
        tablatrabajadores.getTableHeader().setReorderingAllowed(false);

        tablacitas.setModel(newmodelocitas);
        tablacitas.setRowSelectionAllowed(false);
        tablacitas.setColumnSelectionAllowed(false);
        tablacitas.getTableHeader().setReorderingAllowed(false);

    }

    public void refreshTablaUsuarios() throws IOException {
        HashMap<Integer, Usuario> usuarios;
        String[] titulos = {"Nombre de usuario", "Contraseña", "Rol"};
        Object[][] data = new Object[0][0];
        DefaultTableModel newmodelo = new DefaultTableModel(data, titulos) {
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        try {
            FileInputStream fis = new FileInputStream("usuarios.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            usuarios = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            File file = new File("usuarios.obj");
            FileOutputStream fileStream = new FileOutputStream(file);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

            objectStream.writeObject("");

            fileStream.close();
            objectStream.close();

            return;
        }

        Usuario cliente;
        int tipo;
        String tipostring = "";
        for (Map.Entry<Integer, Usuario> entry : usuarios.entrySet()) {
            cliente = entry.getValue();

            tipo = cliente.getTipo();
            if (tipo == 0) {
                tipostring = "Recepcionista";
            } else if (tipo == 1) {
                tipostring = "Dueño";
            } else {
                tipostring = "Desconocido";
            }

                newmodelo.addRow(new Object[]{ cliente.getUsername(), cliente.getPassword(), tipostring});
        }

        tablausuarios.setModel(newmodelo);
        tablausuarios.setRowSelectionAllowed(false);
        tablausuarios.setColumnSelectionAllowed(false);
        tablausuarios.getTableHeader().setReorderingAllowed(false);
    }

    public boolean createUsuario() {
        String username, password;  int tipo;
        try {
            username = this.fieldusername.getText();
            if (username == null || username.equals("")) {
                JOptionPane.showMessageDialog(this, "Escriba un nombre de usuario");
                return false;
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Escriba el nombre de usuario");
            return false;
        }

        try {
            password = this.fieldpassword.getText();
            if (password == null || password.equals("")) {
                JOptionPane.showMessageDialog(this, "Escriba una contraseña");
                return false;
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Escriba una contraseña valida");
            return false;
        }
        tipo = combotipousuario.getSelectedIndex();
        if (tipo == 0) {
            this.createRecepcionsita(username, password);
            return true;
        } else if (tipo == 1) {
            this.createDueno(username, password);
            return true;
        } else {
            return false;
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
            int id = usuarios.size() + 1;
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
            JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado " + e);
            return;
        }
    }

    private void createDueno(String username, String password) {
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
            JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado 1");
            return;
        }
    }
}
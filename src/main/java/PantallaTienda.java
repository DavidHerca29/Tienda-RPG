/*
Instituto Tecnológico de Costa Rica
Escuela de Computacióm
Grupo 3
Programación Orientada a Objetos
Realizado por: David Hernández Calvo y Javier Fonseca Mora
Fecha de inicio: 17/09/2020
fecha de entrega: 13/10/2020
*/
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class PantallaTienda extends JFrame{
    /*
    En esta clase se crea todo lo relaciondado a la tienda con la GUI
    Tambien instancia a la API por lo que mientras obtiene la informacion dura un poco en cargar
    */
    private JLabel jugadorLabel;
    private JLabel armaduraLabel;
    private JLabel ataqueLabel;
    private JLabel saludLabel;
    private JLabel rangoLabel;
    private JLabel nivelLabel;
    private JPanel panelFondo = new FondoTienda();
    private SeccionesP seccionesPanel;
    private StatsP statsPanel;
    private OroL oroPanel;
    private DescripcionLab descripcionPanel;
    private JLabel preAtaque = new JLabel("Pre");
    private JLabel preSalud = new JLabel("Pre");
    private JLabel preRango = new JLabel("Pre");
    private JLabel preArmadura = new JLabel("Pre");
    private JLabel preNivel = new JLabel("Pre");

    private JLabel oroLabel;
    private byte contEspDor = 1;
    private byte contEspBronce = 0;
    private byte contEspFuego = 0;
    private byte contArmaduraEspinas = 1;
    private byte contArmaduraDiamante = 0;
    private byte contArmaduraPlata = 0;
    private byte contPosHielo = 1;
    private byte contPosHiervas = 0;
    private byte contBayas = 0;

    private ObjetosEquipados equipadoP;
    private ObjetosComprar objetosAComprarP;
    private ObjetosVender objetosAVenderP;

    private JButton botonComprar = new JButton("Comprar");
    private JButton botonVender = new JButton("Vender");
    private JButton botonEquipar = new JButton("Equipar/Consumir");
    private JButton botonDesequipar = new JButton("Desequipar");

    private JTextArea descripcionItem = new JTextArea("Aquí se mostrará la descripción del objeto seleccionado.");

    private JList<Items> listaComprar = new JList<>();
    private DefaultListModel<Items> modelComprar = new DefaultListModel<>();
    private JList<Items> listaEquipado = new JList<>();
    private DefaultListModel<Items> modelEquipado = new DefaultListModel<>();
    private JList<Items> listaInventario = new JList<>();
    private DefaultListModel<Items> modelInventario = new DefaultListModel<>();
    private Personaje personaje= new Personaje(100, 10, 10, 100, 5000, 10);

    JScrollPane JSPEquipado = new JScrollPane();
    JScrollPane JSPInventario = new JScrollPane();
    JScrollPane JSPComprar = new JScrollPane();
    boolean banderaVender;
    int indiceS;

    public PantallaTienda(String nombreJugador) throws Exception {
        setTitle("Tienda RPG");
        setSize(900, 595);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // conexion a la API
        API api = new API();
        for (int i = 0; i < 3; i++) {
            Thread.sleep(1000);//restriccion de llamadas al api
            if (i==0) {
                Items item = api.crearArma("Espada Dorada", "Con esta arma hará que la muerte de tus enemigos reluzca");
                modelComprar.addElement(item);
                modelInventario.addElement(item);
            }else if (i==1){
                Items item = api.crearArma("Espada de Bronce", "Con esta arma hará que tus enemigos se aturdan.");
                modelComprar.addElement(item);
            }else {
                Items item = api.crearArma("Espada de Fuego", "Con esta arma hará que la muerte de tus enemigos sea más candente.");
                modelComprar.addElement(item);
            }
        }
        for (int i = 0; i < 3; i++) {
            Thread.sleep(1000);//restriccion de llamadas al api
            if (i==0){
                Items item = api.crearArmadura("Armadura de Espinas", "Esta armadura te protegerá mejor de los ataques cuerpo a cuerpo con sus\npuntiagudos picos.");
                modelComprar.addElement(item);
                modelInventario.addElement(item);
            }else if (i==1){
                Items item = api.crearArmadura("Armadura de Plata", "Esta armadura te protegerá mejor ante los golpes de flechas y espadas.");
                modelComprar.addElement(item);
            }else {
                Items item = api.crearArmadura("Armadura de Diamante Puro", "Esta armadura ha sido forjado con diamante puro y te protegerá\n mejor de manera abrumadora.");
                modelComprar.addElement(item);
            }
        }
        for (int i = 0; i < 3; i++) {
            Thread.sleep(1000);//restriccion de llamadas al api
            if (i==0){
                Items item = api.crearConsumible("Posión de Hielo", "Con esta posión obtendrás más salud y una refrescante subida de nivel.");
                modelComprar.addElement(item);
                modelInventario.addElement(item);
            }else if (i==1){
                Items item = api.crearConsumible("Posión de Hiervas", "Con esta posión obtendrás más salud y una refrescante\nsubida de nivel significativa.");
                modelComprar.addElement(item);
            }else {
                Items item = api.crearConsumible("Bayas", "Con estas bayas podrás obtener una salud incrementada y te sentirás\nmucho más fuerte.");
                modelComprar.addElement(item);
            }
        }
        // Se añaden los items a las listas
        listaComprar.setModel(modelComprar);
        listaComprar.setDragEnabled(false);
        listaEquipado.setModel(modelEquipado);
        listaEquipado.add(new JScrollBar(0));
        listaInventario.setModel(modelInventario);
        // caracteristicas de las listas
        listaComprar.setVisible(true);
        listaInventario.setVisible(true);
        listaEquipado.setVisible(true);
        listaEquipado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaComprar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaInventario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        oroPanel = new OroL();
        descripcionPanel = new DescripcionLab();
        seccionesPanel = new SeccionesP();
        equipadoP = new ObjetosEquipados();
        objetosAComprarP = new ObjetosComprar();
        objetosAVenderP = new ObjetosVender();
        statsPanel = new StatsP();
        panelFondo.setVisible(false);
        // Se crea el panel  label del nombre
        BotonesVentana botones= new BotonesVentana();
        jugadorLabel = new JLabel(nombreJugador);
        jugadorLabel.setBounds(0, 0, 100, 20);
        jugadorLabel.setFont(new Font ("Arial", Font.ITALIC, 20));
        jugadorLabel.setForeground(new Color(184, 134, 35));
        jugadorLabel.setVisible(true);
        JPanel panelNombre = new JPanel();
        panelNombre.setBounds(0, 0, 140, 40);
        panelNombre.setVisible(true);
        panelNombre.setBackground(new Color(123, 84, 7));
        // se añaden las listas a los paneles
        JSPEquipado.setViewportView(listaEquipado);
        listaEquipado.setLayoutOrientation(JList.VERTICAL);
        JSPEquipado.setBounds(35,15,350,250);
        equipadoP.add(JSPEquipado);
        JSPInventario.setViewportView(listaInventario);
        listaInventario.setLayoutOrientation(JList.VERTICAL);
        JSPInventario.setBounds(35,15,350,250);
        objetosAVenderP.add(JSPInventario);
        JSPComprar.setViewportView(listaComprar);
        listaComprar.setLayoutOrientation(JList.VERTICAL);
        JSPComprar.setBounds(35,15,350,250);
        objetosAComprarP.add(JSPComprar);
        // Acciones de las listas al ser seleccionadas
        listaComprar.getSelectionModel().addListSelectionListener(e->{
            if (e.getValueIsAdjusting()) {
                botonComprar.setEnabled(true);
                botonVender.setEnabled(false);
                if (listaComprar.getSelectedValue() instanceof Arma) {
                    String text = "El precio de la compra por el item " +((String) listaComprar.getSelectedValue().getNombre()) + " es: " + listaComprar.getSelectedValue().getPrecioCompra() + "\nDescripción: " + listaComprar.getSelectedValue().getDescripcion();
                    descripcionItem.setText(text);
                    if (listaComprar.getSelectedValue().getNombre()=="Espada Dorada"){
                        statsPanel.cantidadLabel.setText("Cantidad de "+listaComprar.getSelectedValue().getNombre()+" en el inventario: " + contEspDor);
                    }
                    else if (listaComprar.getSelectedValue().getNombre()=="Espada de Bronce"){
                        statsPanel.cantidadLabel.setText("Cantidad de "+listaComprar.getSelectedValue().getNombre()+" en el inventario: " + contEspBronce);
                    }
                    else {
                        statsPanel.cantidadLabel.setText("Cantidad de "+listaComprar.getSelectedValue().getNombre()+" en el inventario: " + contEspFuego);
                    }
                    botones.setPrecio(listaComprar.getSelectedValue().getPrecioCompra());
                    preAtaque.setText(">> " + (personaje.getAtaque() + ((Arma) listaComprar.getSelectedValue()).getMasAtaque()));
                    preSalud.setText("");
                    preNivel.setText("");
                    preRango.setText(">> " + (personaje.getRango() + ((Arma) listaComprar.getSelectedValue()).getMasRango()));
                    preArmadura.setText("");
                }
                else if (listaComprar.getSelectedValue() instanceof Armadura){
                    String text = "El precio de la compra por el item " + listaComprar.getSelectedValue().getNombre() + " es: " + listaComprar.getSelectedValue().getPrecioCompra() + "\nDescripción: " + listaComprar.getSelectedValue().getDescripcion();
                    descripcionItem.setText(text);
                    if (listaComprar.getSelectedValue().getNombre()=="Armadura de Espinas"){
                        statsPanel.cantidadLabel.setText("Cantidad de "+listaComprar.getSelectedValue().getNombre()+" en el inventario: " + contArmaduraEspinas);
                    }
                    else if (listaComprar.getSelectedValue().getNombre()=="Armadura de Diamante Puro"){
                        statsPanel.cantidadLabel.setText("Cantidad de "+listaComprar.getSelectedValue().getNombre()+" en el inventario: " + contArmaduraDiamante);
                    }
                    else {
                        statsPanel.cantidadLabel.setText("Cantidad de "+listaComprar.getSelectedValue().getNombre()+" en el inventario: " + contArmaduraPlata);
                    }
                    botones.setPrecio(listaComprar.getSelectedValue().getPrecioCompra());
                    preArmadura.setText(">> " + (personaje.getArmadura() + ((Armadura) listaComprar.getSelectedValue()).getMasArmadura()));
                    preSalud.setText("");
                    preNivel.setText("");
                    preRango.setText("");
                    preAtaque.setText("");
                }
                else {
                    String text = "El precio de la compra por el item " + listaComprar.getSelectedValue().getNombre() + " es: " + listaComprar.getSelectedValue().getPrecioCompra() + "\nDescripción: "+ listaComprar.getSelectedValue().getDescripcion();
                    descripcionItem.setText(text);
                    if (listaComprar.getSelectedValue().getNombre()=="Posión de Hielo"){
                        statsPanel.cantidadLabel.setText("Cantidad de "+listaComprar.getSelectedValue().getNombre()+" en el inventario: " + contPosHielo);
                    }else if (listaComprar.getSelectedValue().getNombre()=="Posión de Hiervas"){
                        statsPanel.cantidadLabel.setText("Cantidad de "+listaComprar.getSelectedValue().getNombre()+" en el inventario: " + contPosHiervas);
                    }else {
                        statsPanel.cantidadLabel.setText("Cantidad de "+listaComprar.getSelectedValue().getNombre()+" en el inventario: " + contBayas);
                    }

                    botones.setPrecio(listaComprar.getSelectedValue().getPrecioCompra());
                    preArmadura.setText("");
                    preSalud.setText(">> " + (personaje.getSalud()+((Consumibles) listaComprar.getSelectedValue()).getMasSalud()));
                    preNivel.setText(">> " + (personaje.getNivel()+((Consumibles) listaComprar.getSelectedValue()).getMasNivel()));
                    preRango.setText("");
                    preAtaque.setText("");
                }
            }
        });
        listaInventario.getSelectionModel().addListSelectionListener(e->{
            if (e.getValueIsAdjusting()) {
                botonVender.setEnabled(true);
                botonComprar.setEnabled(false);
                botonEquipar.setEnabled(true);
                if (listaInventario.getSelectedValue() instanceof Arma) {
                    String text = "El precio de la venta por el item " + listaInventario.getSelectedValue().getNombre() + " es de: " + listaInventario.getSelectedValue().getPrecioVenta() + "\nDescripción: " + listaInventario.getSelectedValue().getDescripcion();
                    descripcionItem.setText(text);
                    botones.setPrecio(listaInventario.getSelectedValue().getPrecioVenta());
                    preArmadura.setText("");
                    preSalud.setText("");
                    preAtaque.setText(">> " + (personaje.getAtaque() + ((Arma) listaInventario.getSelectedValue()).getMasAtaque()));
                    preNivel.setText("");
                    preRango.setText(">> " + (personaje.getRango() + ((Arma) listaInventario.getSelectedValue()).getMasRango()));
                }
                else if (listaInventario.getSelectedValue() instanceof Armadura){
                    String text = "El precio de la venta por el item " + listaInventario.getSelectedValue().getNombre() + " es de: " + listaInventario.getSelectedValue().getPrecioVenta() + "\nDescripción: " + listaInventario.getSelectedValue().getDescripcion();
                    descripcionItem.setText(text);
                    botones.setPrecio(listaInventario.getSelectedValue().getPrecioVenta());
                    preArmadura.setText(">> " + (personaje.getArmadura() + ((Armadura) listaInventario.getSelectedValue()).getMasArmadura()));
                    preSalud.setText("");
                    preAtaque.setText("");
                    preNivel.setText("");
                    preRango.setText("");
                }
                else {
                    String text = "El precio de la venta por el item " + listaInventario.getSelectedValue().getNombre() + " es de: " + listaInventario.getSelectedValue().getPrecioVenta() + "\nDescripción: " + listaInventario.getSelectedValue().getDescripcion();
                    descripcionItem.setText(text);
                    botones.setPrecio(listaInventario.getSelectedValue().getPrecioVenta());
                    preArmadura.setText("");
                    preSalud.setText(">> " + (personaje.getSalud() + ((Consumibles) listaInventario.getSelectedValue()).getMasSalud()));
                    preAtaque.setText("");
                    preNivel.setText(">> " + (personaje.getNivel() + ((Consumibles) listaInventario.getSelectedValue()).getMasNivel()));
                    preRango.setText("");
                }
            }
        });
        listaEquipado.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                botonVender.setEnabled(true);
                botonComprar.setEnabled(false);
                botonDesequipar.setEnabled(true);
                if (listaEquipado.getSelectedValue() instanceof Arma) {
                    String text = "El precio de la venta por el item " + listaEquipado.getSelectedValue().getNombre() + " es de: " + listaEquipado.getSelectedValue().getPrecioVenta() + "\nDescripción: " + listaEquipado.getSelectedValue().getDescripcion();
                    descripcionItem.setText(text);
                    botones.setPrecio(listaEquipado.getSelectedValue().getPrecioVenta());
                    preArmadura.setText("");
                    preSalud.setText("");
                    preAtaque.setText(">> " + (personaje.getAtaque() - ((Arma)listaEquipado.getSelectedValue()).getMasAtaque()));
                    preNivel.setText("");
                    preRango.setText(">> " + (personaje.getRango() - ((Arma) listaEquipado.getSelectedValue()).getMasRango()));
                }
                else {
                    String text ="El precio de la venta por el item " + listaEquipado.getSelectedValue().getNombre() + " es de: " + listaEquipado.getSelectedValue().getPrecioVenta() + "\nDescripción: " + listaEquipado.getSelectedValue().getDescripcion();
                    descripcionItem.setText(text);
                    botones.setPrecio(listaEquipado.getSelectedValue().getPrecioVenta());
                    preArmadura.setText(">> " + (personaje.getArmadura() - ((Armadura) listaEquipado.getSelectedValue()).getMasArmadura()));
                    preSalud.setText("");
                    preAtaque.setText("");
                    preNivel.setText("");
                    preRango.setText("");
                }
            }
        });
        panelNombre.add(jugadorLabel);
        add(panelNombre);
        add(statsPanel);
        add(seccionesPanel);
        add(oroPanel);
        add(descripcionPanel);
        add(botones);
        add(equipadoP);
        add(objetosAVenderP);
        add(objetosAComprarP);
        add(panelFondo);
    }
    public class ObjetosVender extends JPanel{
        public ObjetosVender(){
            setVisible(false);
            setBounds(0, 235, 500, 390);
            setBackground(new Color(108, 37, 37));
            setLayout(null);
        }
    }
    public class ObjetosComprar extends JPanel{
        public ObjetosComprar(){
            setVisible(true);
            setBounds(0, 235, 500, 390);
            setBackground(new Color(108, 37, 37));
            setLayout(null);
        }
    }
    public class ObjetosEquipados extends JPanel{
        public ObjetosEquipados(){
            setVisible(false);
            setBounds(0, 235, 500, 390);
            setBackground(new Color(108, 37, 37));
            setLayout(null);
        }
    }
    public class StatsP extends JPanel implements ActionListener{
        private JLabel ataqueLabel = new JLabel("Ataque: "+ personaje.getAtaque());
        private JLabel saludLabel = new JLabel("Salud: "+personaje.getSalud());
        private JLabel rangoLabel = new JLabel("Rango: "+ personaje.getRango());
        private JLabel armaduraLabel = new JLabel("Armadura: "+ personaje.getArmadura());
        private JLabel nivelLabel = new JLabel("Nivel: "+ personaje.getNivel());
        private JLabel personajeL = new JLabel("Stats del personaje");
        private JTextArea cantidadLabel = new JTextArea("");
        public StatsP(){
            setBounds(500, 165, 387, 393);
            setVisible(true);
            setBackground(new Color(158, 104, 44, 255));
            setLayout(null);

            personajeL.setBounds(75, 50, 250, 50);
            personajeL.setVisible(true);
            personajeL.setFont(new Font("Century Gothic", Font.ITALIC, 20));

            ataqueLabel.setBounds(50, 105, 150, 30);
            saludLabel.setBounds(50, 140, 150, 30);
            rangoLabel.setBounds(50, 175, 150, 30);
            armaduraLabel.setBounds(50, 210, 150, 30);
            nivelLabel.setBounds(50, 250, 150, 30);
            cantidadLabel.setBounds(20, 310, 340, 70);
            ataqueLabel.setFont(new Font("Arial", Font.ITALIC, 20));
            saludLabel.setFont(new Font("Arial", Font.ITALIC, 20));
            rangoLabel.setFont(new Font("Arial", Font.ITALIC, 20));
            armaduraLabel.setFont(new Font("Arial", Font.ITALIC, 20));
            nivelLabel.setFont(new Font("Arial", Font.ITALIC, 20));
            cantidadLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            ataqueLabel.setVisible(true);
            saludLabel.setVisible(true);
            rangoLabel.setVisible(true);
            armaduraLabel.setVisible(true);
            nivelLabel.setVisible(true);
            cantidadLabel.setVisible(true);
            cantidadLabel.setEditable(false);

            preAtaque.setBounds(210, 105, 100, 30);
            preSalud.setBounds(210, 140, 100, 30);
            preRango.setBounds(210, 175, 100, 30);
            preArmadura.setBounds(210, 210, 100, 30);
            preNivel.setBounds(210, 250, 100, 30);
            preArmadura.setVisible(true);
            preAtaque.setVisible(true);
            preRango.setVisible(true);
            preSalud.setVisible(true);
            preNivel.setVisible(true);
            preArmadura.setFont(new Font("Arial", Font.ITALIC, 20));
            preNivel.setFont(new Font("Arial", Font.ITALIC, 20));
            preAtaque.setFont(new Font("Arial", Font.ITALIC, 20));
            preRango.setFont(new Font("Arial", Font.ITALIC, 20));
            preSalud.setFont(new Font("Arial", Font.ITALIC, 20));

            botonDesequipar.setVisible(true);
            botonEquipar.setVisible(true);
            botonEquipar.setBounds(30, 30, 150, 25);
            botonDesequipar.setBounds(200, 30, 150, 25);
            botonEquipar.addActionListener(this);
            botonDesequipar.addActionListener(this);
            botonEquipar.setEnabled(false);
            botonDesequipar.setEnabled(false);

            add(preNivel);
            add(cantidadLabel);
            add(nivelLabel);
            add(personajeL);
            add(ataqueLabel);
            add(saludLabel);
            add(rangoLabel);
            add(armaduraLabel);
            add(preArmadura);
            add(preAtaque);
            add(preSalud);
            add(preRango);
            add(botonEquipar);
            add(botonDesequipar);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object botonP = e.getSource();
            if (botonP==botonEquipar){
                botonVender.setEnabled(false);
                Items p = listaInventario.getSelectedValue();
                if (p instanceof Arma) {
                    if (personaje.isArmaEquipada()) {
                        descripcionItem.setText("Ya hay un arma equipada y solo se puede portar 1 arma a la vez.");
                    }
                    else {
                        modelEquipado.addElement(new Arma(p.getPrecioCompra(),p.getPrecioVenta(),p.getNombre(),p.getDescripcion(),((Arma) p).getMasAtaque(),((Arma) p).getMasRango()));
                        personaje.setAtaque(personaje.getAtaque() + ((Arma) p).getMasAtaque());
                        personaje.setRango(personaje.getRango() + ((Arma) p).getMasRango());
                        ataqueLabel.setText("Ataque: " + personaje.getAtaque());
                        rangoLabel.setText("Rango: " + personaje.getRango());
                        personaje.setArmaEquipada(true);
                        indiceS = listaInventario.getSelectedIndex();
                        modelInventario.remove(indiceS);
                    }
                }
                else if (p instanceof Armadura){
                    if (personaje.isArmaduraEquipada()){
                        descripcionItem.setText("Ya hay una armadura equipada y solo se puede portar 1 armadura a la vez.");
                    }
                    else {
                        modelEquipado.addElement(new Armadura(p.getPrecioCompra(),p.getPrecioVenta(),p.getNombre(),p.getDescripcion(),((Armadura) p).getMasArmadura()));
                        personaje.setArmadura(personaje.getArmadura() + ((Armadura) p).getMasArmadura());
                        armaduraLabel.setText("Armadura: " + personaje.getArmadura());
                        personaje.setArmaduraEquipada(true);
                        indiceS = listaInventario.getSelectedIndex();
                        listaInventario.clearSelection();
                        modelInventario.remove(indiceS);
                        botonEquipar.setEnabled(false);
                    }
                }
                else {
                    if (p.getNombre()=="Bayas"){
                        contBayas--;
                    }
                    else if (p.getNombre()=="Posión de Hielo"){
                        contPosHielo--;
                    }
                    else {
                        contPosHiervas--;
                    }
                    personaje.setSalud(personaje.getSalud()+((Consumibles) p).getMasSalud());
                    personaje.setNivel(personaje.getNivel()+((Consumibles) p).getMasNivel());
                    saludLabel.setText("Salud: " + personaje.getSalud());
                    nivelLabel.setText("Nivel: " + personaje.getNivel());
                    indiceS = listaInventario.getSelectedIndex();
                    modelInventario.remove(indiceS);
                }
                listaInventario.clearSelection();
                botonEquipar.setEnabled(false);
            }
            else{
                Items p = listaEquipado.getSelectedValue();
                botonDesequipar.setEnabled(false);
                botonVender.setEnabled(false);
                if (p instanceof Arma) {
                    modelInventario.addElement(new Arma(p.getPrecioCompra(), p.getPrecioVenta(), p.getNombre(), p.getDescripcion(), ((Arma) p).getMasAtaque(), ((Arma) p).getMasRango()));
                    personaje.setAtaque(personaje.getAtaque()-((Arma) p).getMasAtaque());
                    personaje.setRango(personaje.getRango()-((Arma) p).getMasRango());
                    ataqueLabel.setText("Ataque: " + personaje.getAtaque());
                    rangoLabel.setText("Rango: " + personaje.getRango());
                    personaje.setArmaEquipada(false);
                }
                else {
                    modelInventario.addElement(new Armadura(p.getPrecioCompra(), p.getPrecioVenta(), p.getNombre(), p.getDescripcion(), ((Armadura) p).getMasArmadura()));
                    personaje.setArmadura(personaje.getArmadura()-((Armadura) p).getMasArmadura());
                    armaduraLabel.setText("Armadura: " + personaje.getArmadura());
                    personaje.setArmaduraEquipada(false);
                }
                indiceS = listaEquipado.getSelectedIndex();
                listaEquipado.clearSelection();
                modelEquipado.remove(indiceS);
            }
            preNivel.setText("");
            preArmadura.setText("");
            preAtaque.setText("");
            preRango.setText("");
            preSalud.setText("");
        }
    }
    public class SeccionesP extends JPanel implements ActionListener {
        private final JButton inventario = new JButton("Inventario");
        private final JButton comprar = new JButton("Comprar");
        private JButton equipado = new JButton("Equipado");

        public SeccionesP() {
            setBounds(0, 165, 370, 70);
            setVisible(true);
            setBackground(new Color(45, 122, 184));
            setBorder(BorderFactory.createTitledBorder("Secciones"));

            inventario.setBounds(5, 55, 25, 15);
            inventario.setVisible(true);
            inventario.addActionListener(this);

            comprar.setBounds(100, 55, 25, 15);
            comprar.setVisible(true);
            comprar.addActionListener(this);

            equipado.setBounds(180, 55, 25, 15);
            equipado.setVisible(true);
            equipado.addActionListener(this);

            add(comprar);
            add(inventario);
            add(equipado);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object objetoEvento = e.getSource();
            if (objetoEvento == inventario) {
                objetosAVenderP.setVisible(true);
                objetosAComprarP.setVisible(false);
                equipadoP.setVisible(false);
                botonComprar.setEnabled(false);
                botonVender.setEnabled(false);
                botonEquipar.setEnabled(false);
                botonDesequipar.setEnabled(false);
                banderaVender=true;
            }else if (objetoEvento == comprar) {
                objetosAVenderP.setVisible(false);
                objetosAComprarP.setVisible(true);
                equipadoP.setVisible(false);
                botonVender.setEnabled(false);
                botonComprar.setEnabled(false);
                botonEquipar.setEnabled(false);
                botonDesequipar.setEnabled(false);
                banderaVender=false;
            } else {
                objetosAVenderP.setVisible(false);
                objetosAComprarP.setVisible(false);
                equipadoP.setVisible(true);
                botonComprar.setEnabled(false);
                botonVender.setEnabled(false);
                botonEquipar.setEnabled(false);
                botonDesequipar.setEnabled(false);
                banderaVender=false;
            }
            listaEquipado.clearSelection();
            listaInventario.clearSelection();
            listaComprar.clearSelection();
            descripcionItem.setText("");
            preArmadura.setText("");
            preSalud.setText("");
            preAtaque.setText("");
            preNivel.setText("");
            preRango.setText("");
            statsPanel.cantidadLabel.setText("");
        }
    }
    public class OroL extends JPanel{
        public OroL(){
            setBounds(650, 2, 120, 35);
            setVisible(true);
            setBackground(new Color(45, 122, 184));

            oroLabel = new JLabel("Oro: "+personaje.getOro());
            oroLabel.setVisible(true);
            add(oroLabel);
        }
    }
    public class DescripcionLab extends JPanel{
        public DescripcionLab() {
            setBounds(0, 40, 900, 125);
            setVisible(true);
            setBackground(Color.gray);
            setLayout(null);
            descripcionItem.setVisible(true);
            descripcionItem.setBounds(0, 0, 900, 125);
            descripcionItem.setFont(new Font("Arial", Font.ITALIC, 20));
            descripcionItem.setEditable(false);
            descripcionItem.setBackground(new Color(173, 120, 44, 255));
            setBackground(new Color(45, 122, 184));

            add(descripcionItem);
        }
    }
    public class BotonesVentana extends JPanel implements ActionListener {
        private int precio = 0;
        public BotonesVentana(){
            setVisible(true);
            setLocation(370, 165);
            setSize(130, 70);
            setBackground(new Color(18, 83, 111));
            setBorder(BorderFactory.createLineBorder(Color.black));

            botonComprar.setBackground(new Color(87, 10, 31));
            botonComprar.setForeground(new Color(217, 134, 33));
            botonComprar.setSize(15, 10);
            botonComprar.setLocation(10, 0);
            botonComprar.setVisible(true);
            botonComprar.setEnabled(false);
            botonComprar.addActionListener(this);

            botonVender.setBackground(new Color(87, 10, 31));
            botonVender.setForeground(new Color(217, 134, 33));
            botonVender.setSize(15, 10);
            botonVender.setLocation(10, 30);
            botonVender.setVisible(true);
            botonVender.addActionListener(this);
            botonVender.setEnabled(false);

            add(botonComprar);
            add(botonVender);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            Object botonPulsado = e.getSource();
            if (botonPulsado==botonComprar){
                if ((personaje.getOro()-precio)>0) {
                    personaje.setOro(personaje.getOro() - precio);
                    Items p = listaComprar.getSelectedValue();
                    if (p instanceof Arma) {
                        modelInventario.addElement(new Arma(p.getPrecioCompra(), p.getPrecioVenta(), p.getNombre(), p.getDescripcion(), ((Arma) p).getMasAtaque(), ((Arma) p).getMasRango()));
                        if (p.getNombre() == "Espada Dorada") {
                            contEspDor++;
                        } else if (p.getNombre() == "Espada de Bronce") {
                            contEspBronce++;
                        } else {
                            contEspFuego++;
                        }
                    } else if (p instanceof Armadura) {
                        modelInventario.addElement(new Armadura(p.getPrecioCompra(), p.getPrecioVenta(), p.getNombre(), p.getDescripcion(), ((Armadura) p).getMasArmadura()));
                        if (p.getNombre() == "Armadura de Espinas") {
                            contArmaduraEspinas++;
                        } else if (p.getNombre() == "Armadura de Plata") {
                            contArmaduraPlata++;
                        } else {
                            contArmaduraDiamante++;
                        }
                    } else {
                        modelInventario.addElement(new Consumibles(p.getPrecioCompra(), p.getPrecioVenta(), p.getNombre(), p.getDescripcion(), ((Consumibles) p).getMasNivel(), ((Consumibles) p).getMasSalud()));
                        if (p.getNombre() == "Bayas") {
                            contBayas++;
                        } else if (p.getNombre() == "Posión de Hiervas") {
                            contPosHiervas++;
                        } else {
                            contPosHielo++;
                        }
                    }
                }
                else {
                    descripcionItem.setText("Error al comprar: No tiene suficiente oro para realizar la compra.");
                    botonComprar.setEnabled(false);
                }
            }
            else{
                botonVender.setEnabled(false);
                personaje.setOro(personaje.getOro()+precio);
                if (banderaVender) {
                    if (listaInventario.getSelectedValue() instanceof Arma){
                        if (listaInventario.getSelectedValue().getNombre()=="Espada Dorada"){
                            contEspDor--;
                        }
                        else if (listaInventario.getSelectedValue().getNombre()=="Espada de Bronce"){
                            contEspBronce--;
                        }
                        else {
                            contEspFuego--;
                        }
                    }
                    else if (listaInventario.getSelectedValue() instanceof Armadura){
                        if (listaInventario.getSelectedValue().getNombre()=="Armadura de Espinas"){
                            contArmaduraEspinas--;
                        }
                        else if (listaInventario.getSelectedValue().getNombre()=="Armadura de Plata"){
                            contArmaduraPlata--;
                        }
                        else {
                            contArmaduraDiamante--;
                        }
                    }
                    else {
                        if (listaInventario.getSelectedValue().getNombre()=="Bayas"){
                            contBayas--;
                        }
                        else if (listaInventario.getSelectedValue().getNombre()=="Posión de Hielo"){
                            contPosHielo--;
                        }
                        else {
                            contPosHiervas--;
                        }
                    }
                    indiceS = listaInventario.getSelectedIndex();
                    descripcionItem.setText("");
                    listaInventario.clearSelection();
                    modelInventario.remove(indiceS);
                }
                else{
                    Items p = listaEquipado.getSelectedValue();
                    if (p instanceof Arma){
                        personaje.setAtaque(personaje.getAtaque() - ((Arma) p).getMasAtaque());
                        personaje.setRango(personaje.getRango() - ((Arma) p).getMasRango());
                        personaje.setArmaEquipada(false);
                        if (p.getNombre()=="Espada Dorada"){
                            contEspDor--;
                        }
                        else if (p.getNombre()=="Espada de Bronce"){
                            contEspBronce--;
                        }
                        else {
                            contEspFuego--;
                        }
                    }
                    else {
                        personaje.setArmadura(personaje.getArmadura() - ((Armadura) p).getMasArmadura());
                        personaje.setArmaduraEquipada(false);
                        if (p.getNombre()=="Armadura de Espinas"){
                            contArmaduraEspinas--;
                        }
                        else if (p.getNombre()=="Armadura de Plata"){
                            contArmaduraPlata--;
                        }
                        else {
                            contArmaduraDiamante--;
                        }
                    }
                    indiceS = listaEquipado.getSelectedIndex();
                    descripcionItem.setText("");
                    listaEquipado.clearSelection();
                    modelEquipado.remove(indiceS);
                }
            }
            listaEquipado.clearSelection();
            listaInventario.clearSelection();
            listaComprar.clearSelection();
            statsPanel.armaduraLabel.setText("Armadura: " + personaje.getArmadura());
            statsPanel.ataqueLabel.setText("Ataque: "+personaje.getAtaque());
            statsPanel.rangoLabel.setText("Rango: "+personaje.getRango());
            oroLabel.setText("Oro: "+personaje.getOro());
            statsPanel.cantidadLabel.setText("");
            botonEquipar.setEnabled(false);
            botonDesequipar.setEnabled(false);
            botonComprar.setEnabled(false);
            preNivel.setText("");
            preArmadura.setText("");
            preAtaque.setText("");
            preRango.setText("");
            preSalud.setText("");
        }

        public int getPrecio() {
            return precio;
        }

        public void setPrecio(int precio) {
            this.precio = precio;
        }
    }
}

class FondoTienda extends JPanel {
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        try{
            imagen = ImageIO.read(new File("Codigo/Tienda-RPG/src/Fondo Tienda.png"));
        }
        catch (IOException p){
            System.out.println("La imagen no ha podido encontrarse.");
        }
        g.drawImage(imagen, 0, 0, null);

    }
    private Image imagen;
}


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

class miMarco extends JFrame{

    public miMarco(){
        setTitle("Tienda.Tienda RPG");
        setSize(900, 595);
        setLocationRelativeTo(null);
        setResizable(false);
        Lamina fondo = new Lamina();

        JLabel bienvenida = new JLabel("Hola, guerrero. Ingresa tu nombre.");
        bienvenida.setBounds(20, 30, 480, 40);
        bienvenida.setVisible(true);
        bienvenida.setFont(new Font ("Arial", Font.ITALIC, 20));
        bienvenida.setForeground(new Color(155, 221, 239));

        FrameBotones laminaBotones = new FrameBotones();
        laminaBotones.setBackground(new Color(87, 10, 31));
        laminaBotones.setVisible(true);
        laminaBotones.setBounds(30,95,70, 35);

        nombreText.setVisible(true);
        nombreText.setBounds(20, 70, 200, 20);

        add(nombreText);
        add(bienvenida);
        add(laminaBotones);
        add(fondo);
    }
    class FrameBotones extends JPanel implements ActionListener{

        JButton botonIngresar = new JButton("Ingresar");
        public FrameBotones(){
            botonIngresar.setBackground(new Color(87, 10, 31));
            botonIngresar.setForeground(new Color(217, 134, 33));
            botonIngresar.setSize(80, 35);
            botonIngresar.setLocation(10, 0);
            botonIngresar.setVisible(true);
            botonIngresar.addActionListener(this);
            add(botonIngresar);
        }
        private void validarNombre(String nom){
            if (nom.isBlank()){
                nombreUsuario="Player";
            }
        }
        public void actionPerformed(ActionEvent e){
            nombreUsuario=nombreText.getText();
            validarNombre(nombreUsuario);
            dispose();
            try {
                new PantallaTienda(nombreUsuario);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }

    }
    String nombreUsuario;
    private final JTextPane nombreText = new JTextPane();
}



class Lamina extends JPanel{

    public void paintComponent(Graphics g){
        super.paintComponent(g);


        try{
            imagen = ImageIO.read(new File("src/main/CastilloFondo.jpg"));
        }
        catch (IOException p){
            System.out.println("La imagen no ha podido encontrarse.");
        }
        g.drawImage(imagen, 0, 0, null);

    }
    private Image imagen;
}
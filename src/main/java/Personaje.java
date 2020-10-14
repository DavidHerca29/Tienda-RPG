/*
Instituto Tecnológico de Costa Rica
Escuela de Computacióm
Grupo 3
Programación Orientada a Objetos
Realizado por: David Hernández Calvo y Javier Fonseca Mora
Fecha de inicio: 17/09/2020
fecha de entrega: 13/10/2020
*/
public class Personaje {
    //Esta clase proporciona la base de jugador
    private int ataque;
    private double rango;
    private int armadura;
    private int salud;
    private int oro;
    private int nivel;
    private boolean armaEquipada = false;
    private boolean armaduraEquipada = false;

    public Personaje(int ataque, double rango, int armadura, int salud, int oro, int nivel) {
        this.ataque = ataque;
        this.rango = rango;
        this.armadura = armadura;
        this.salud = salud;
        this.oro = oro;
        this.nivel = nivel;
    }

    public boolean isArmaEquipada() {
        return armaEquipada;
    }

    public void setArmaEquipada(boolean armaEquipada) {
        this.armaEquipada = armaEquipada;
    }

    public boolean isArmaduraEquipada() {
        return armaduraEquipada;
    }

    public void setArmaduraEquipada(boolean armaduraEquipada) {
        this.armaduraEquipada = armaduraEquipada;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public double getRango() {
        return rango;
    }

    public void setRango(double rango) {
        this.rango = rango;
    }

    public int getArmadura() {
        return armadura;
    }

    public void setArmadura(int armadura) {
        this.armadura = armadura;
    }

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public int getOro() {
        return oro;
    }

    public void setOro(int oro) {
        this.oro = oro;
    }
}

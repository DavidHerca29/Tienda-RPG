public class Personaje {
    private int ataque;
    private double rango;
    private int armadura;
    private int salud;
    private int oro;
    private int nivel;

    public Personaje(int ataque, double rango, int armadura, int salud, int oro, int nivel) {
        this.ataque = ataque;
        this.rango = rango;
        this.armadura = armadura;
        this.salud = salud;
        this.oro = oro;
        this.nivel = nivel;
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

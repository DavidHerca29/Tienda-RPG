
public class Items {
    private int precioCompra;
    private int precioVenta;
    private String nombre;
    private String descripcion;

    public Items(int precioCompra, int precioVenta, String nombre, String descripcion) {
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(int precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(int precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
class Arma extends Items{
    private int masAtaque;
    private double masRango;

    public Arma(int precioCompra, int precioVenta, String nombre, String descripcion, int masAtaque, double masRango) {
        super(precioCompra, precioVenta, nombre, descripcion);
        this.masAtaque = masAtaque;
        this.masRango = masRango;
    }

    public int getMasAtaque() {
        return masAtaque;
    }

    public void setMasAtaque(int masAtaque) {
        this.masAtaque = masAtaque;
    }

    public double getMasRango() {
        return masRango;
    }

    public void setMasRango(double masRango) {
        this.masRango = masRango;
    }
}
class Armadura extends Items{
    private int masArmadura;

    public Armadura(int precioCompra, int precioVenta, String nombre, String descripcion, int masArmadura) {
        super(precioCompra, precioVenta, nombre, descripcion);
        this.masArmadura = masArmadura;
    }

    public int getMasArmadura() {
        return masArmadura;
    }

    public void setMasArmadura(int masArmadura) {
        this.masArmadura = masArmadura;
    }
}
class Consumibles extends Items{
    private int masNivel;
    private int masSalud;

    public Consumibles(int precioCompra, int precioVenta, String nombre, String descripcion, int masNivel, int masSalud) {
        super(precioCompra, precioVenta, nombre, descripcion);
        this.masNivel = masNivel;
        this.masSalud = masSalud;
    }
    public int getMasNivel() {
        return masNivel;
    }

    public void setMasNivel(int masNivel) {
        this.masNivel = masNivel;
    }

    public int getMasSalud() {
        return masSalud;
    }

    public void setMasSalud(int masSalud) {
        this.masSalud = masSalud;
    }

}
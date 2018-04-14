package com.yuliamz.so.s2.Modelo;

@lombok.Data
@lombok.AllArgsConstructor
public class Proceso implements Cloneable {
    
    private String nombre;
    private int tiempo;
    private int prioridad;
    private boolean isBloqueado;
    private boolean isDestruido;
    private boolean isComunicacion;
    
    protected Proceso clonar() throws CloneNotSupportedException {
        return (Proceso) clone();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return this.nombre + " " + this.tiempo + " " + this.isBloqueado + " " + this.prioridad ;
    }
    
}

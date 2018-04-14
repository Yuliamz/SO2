package com.yuliamz.so.s2.Test;

import com.yuliamz.so.s2.Modelo.AdministradorProcesos;
import com.yuliamz.so.s2.Modelo.Proceso;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {
    
    public static void main(String[] args) {
        ArrayList<Proceso> list = new ArrayList<>();
        //nombre, tiempo, prioridad, bloqueado, destruido,comunicación
        //Test de la prueba de escritorio
//        list.add(new Proceso("P10", 8, 3,false,true,false));
//        list.add(new Proceso("P20", 9, 2,true,false,false));
//        list.add(new Proceso("P30", 7, 6,false,false,false));
//        list.add(new Proceso("P40", 5, 4,false,true,false));
//        list.add(new Proceso("P50", 11, 7,false,false,true));
//        list.add(new Proceso("P60", 13, 5,false,false,false));
//        list.add(new Proceso("P70", 18, 1,false,false,false));
//        list.add(new Proceso("P80", 14, 9,false,false,false));
//        list.add(new Proceso("P90", 22, 10,true,true,false));
        list.add(new Proceso("P1", 22, 1,true,true,true));
        try {
            AdministradorProcesos administradorProcesos = new AdministradorProcesos(list);
            administradorProcesos.iniciarSecuencia();
            System.out.println("========Listos==========");
            mostrarLista(administradorProcesos.getListos());
            System.out.println("========Despachados======");
            mostrarLista(administradorProcesos.getDespachados());
            System.out.println("========Ejecucion======");
            mostrarLista(administradorProcesos.getEjecucion());
            System.out.println("========Expiracion de Tiempo======");
            mostrarLista(administradorProcesos.getExpiracionTiempo());
            System.out.println("========Bloqueando======");
            mostrarLista(administradorProcesos.getBloqueando());
            System.out.println("========Bloqueados======");
            mostrarLista(administradorProcesos.getBloqueados());
            System.out.println("========Despertados======");
            mostrarLista(administradorProcesos.getDespertados());
            System.out.println("========Finalizados======");
            mostrarLista(administradorProcesos.getFinalizados());
            System.out.println("========Destruidos======");
            mostrarLista(administradorProcesos.getDestruidos());
            System.out.println("========Comunicación======");
            mostrarLista(administradorProcesos.getComunicacion());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mostrarLista(ArrayList<Proceso> list) {
        list.forEach(System.out::println);
    }
    
}

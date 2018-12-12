/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Libro {
    
    private String codigo;
    private String nombre;
    private int cantidad;
    private int cantidad_p;
    private float costo3dias;
    private float costo_masdias;
    private float multa_diaria;
    private float valor_libro;
    
    public void prestar_libro(){
    
    }
    
    public void devolucion_libro(){
    
    }
    
    public float calcular_multa(int ndias){
        float multa = this.multa_diaria*ndias;
        return multa;
    }
    
    public float calcular_prestamo(int ndias){
        float prestamo = this.costo3dias;
        if (ndias>3){
            ndias += this.costo_masdias*(ndias-3);
        }
        return prestamo;
    }
    
    public Libro ingresar_libro(String codigo, String nombre, int cantidad,
                               int cantidad_p, float costo3dias, float costo_masdias,
                               float multa_diaria, float valor_libro){
        Libro libro_nuevo = null;
        String linea;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Libros.txt"));
            
            while((linea = br.readLine()) != null){
                if (linea.split(",")[0].equals(codigo) ||
                    linea.split(",")[1].equals(nombre)){
                    
                    System.out.print("El libro ya existe");
                    return null;
                }
            }
            
            
            libro_nuevo = new Libro();
            
            libro_nuevo.codigo = codigo;
            libro_nuevo.nombre = nombre;
            libro_nuevo.cantidad = cantidad;
            libro_nuevo.cantidad_p = cantidad_p;
            libro_nuevo.costo3dias = costo3dias;
            libro_nuevo.costo_masdias = costo_masdias;
            libro_nuevo.multa_diaria = multa_diaria;
            libro_nuevo.valor_libro = valor_libro;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return libro_nuevo;
    }
    
    public void aumentar_copias(String codigo, int copias){
        List<String> lineas = new ArrayList<>();
        String linea, libro_editado;
        BufferedReader br;
        
        try {
            br = new BufferedReader(new FileReader("Libros.txt"));
            while((linea = br.readLine()) != null){
                lineas.add(linea);
            }
            String[] l_libro = lineas.get(Integer.parseInt(codigo)-1).split(",");
            int copias_nuevas = Integer.parseInt(l_libro[2])+copias;
            libro_editado = String.format("%s,%s,%s,%s,%s,%s,%s,%s", codigo, 
                                          l_libro[1], copias_nuevas, l_libro[3],
                                          l_libro[4], l_libro[5], l_libro[6],
                                          l_libro[7]);
            lineas.set(Integer.parseInt(codigo)-1, libro_editado);
            
            br.close();
            
            PrintWriter pw = new PrintWriter(new FileWriter("Libros.txt"));
            lineas.stream().forEach(linea_print -> {
                pw.println(linea_print);
            });
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void reporte_libros_prestados(){
    
    }
    
    public void reporte_libros_1copia(){
    
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca;

import java.io.BufferedReader;
import java.io.File;
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
    
    public static void prestar_libro(){
    
    }
    
    public float devolucion_libro(String cliente, String libro, int ndias){
        List<String> lineas_libros = new ArrayList<>(); 
        List<String> lineas = new ArrayList<>();
        PrintWriter pw, pw2;
        BufferedReader br;
        float multa = this.calcular_multa(ndias);
        int index = 0;
        String linea;   
        
        try {
            br = new BufferedReader(new FileReader("Prestamos.txt"));
            while((linea = br.readLine()) != null){
                lineas.add(linea);
                if (linea.split(",")[0].equals(cliente)&&
                    linea.split(",")[1].equals(libro) ){
                    String p_mod = String.format("%s,%s,%s,%s,%s", cliente, libro,
                                                 linea.split(",")[2], ndias, 
                                                 "entregado");
                    lineas.set(index, p_mod);
                }
                index++;
            }
            br.close();
            
            // Codigo,Nombre,Cantidad_Disp,Cantidad_P,3dias,masde3,multa,precio
            br = new BufferedReader(new FileReader("Libros.txt"));
            index=0;
            while((linea = br.readLine()) != null){
                lineas_libros.add(linea);
                if (linea.split(",")[0].equals(libro)){
                    String[] linea_a = linea.split(",");
                    String p_mod = String.format("%s,%s,%s,%s,%s,%s,%s,%s",libro, 
                                                 linea_a[1],
                                                 Integer.parseInt(linea_a[2])+1,
                                                 Integer.parseInt(linea_a[3])-1,
                                                 linea_a[4],linea_a[5],linea_a[6],
                                                 linea_a[7]);
                    lineas_libros.set(index, p_mod);
                }
                index++;
            }
            br.close();
            
            pw2 = new PrintWriter(new FileWriter("Libros.txt"));
            lineas_libros.stream().forEach(linea_libro ->{
                pw2.println(linea_libro);
            });          
            pw2.close();
            
            pw = new PrintWriter(new FileWriter("Prestamos.txt"));
            lineas.stream().forEach(linea_print ->{
                pw.println(linea_print);
            });
            pw.close();
  
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return multa;
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
                                float costo3dias, float costo_masdias,
                                float multa_diaria, float valor_libro){
        BufferedReader br;
        PrintWriter pw;
        Libro libro_nuevo = null;
        String linea;
        try {
            if (!new File("Libros.txt").exists()){
                new File("Libros.txt").createNewFile();
            }
            
            br = new BufferedReader(new FileReader("Libros.txt"));
            
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
            libro_nuevo.cantidad_p = 0;
            libro_nuevo.costo3dias = costo3dias;
            libro_nuevo.costo_masdias = costo_masdias;
            libro_nuevo.multa_diaria = multa_diaria;
            libro_nuevo.valor_libro = valor_libro;
            
            br.close();
            pw = new PrintWriter(new FileWriter("Libros.txt",true));
            pw.println(String.format("%s,%s,%s,%s,%s,%s,%s,%s",codigo, nombre,
                                     cantidad, 0, costo3dias,
                                     costo_masdias, multa_diaria, valor_libro));
            
            pw.close();
            
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
            if (!new File("Libros.txt").exists()){
                new File("Libros.txt").createNewFile();
            }
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utilitarios {
    private int clientes_activos;
    private int numero_libros;
    
    public Utilitarios(){
        this.clientes_activos = this.getClientes_activos();
    }
    
    public static void cambiar_estado_prestamo(String cliente, String libro){
        BufferedReader br;
        FileReader fr;
        FileWriter fw;
        PrintWriter pw;
        try{
            List<String> lineas = new ArrayList<>();
            String linea;
            int nlineas=0;
            
            fr = new FileReader("Prestamos.txt");
            br = new BufferedReader(fr);
            
            while((linea = br.readLine())!= null){
                nlineas++;
                lineas.add(linea);
                String[] linea_actual = linea.split(",");
                if (linea_actual[0].equals(cliente)&& 
                    linea_actual[1].equals(libro)){
                    
                    linea_actual[4] = "entregado";
                    lineas.set(nlineas-1, String.format("%s,%s,%s,%s,%s", 
                               linea_actual[0],linea_actual[1],linea_actual[2],
                               linea_actual[3],linea_actual[4]));
                }
            }
            br.close();
            fr.close();
            
            fw = new FileWriter("Prestamos");
            pw = new PrintWriter(fw);
            
            lineas.stream().forEach((linea_1) -> {
                pw.println(linea_1);
            });
            
            pw.close();
            fw.close();
            
        }catch(Exception e){
            
        }
    }
    // C_C, C_L, Dias_P, Dia_E, Estado
    public static void nuevo_prestamo(String cliente, String libro, String dias){
        String linea;
        FileReader fr;
        BufferedReader br;
        try{
                
            if(Cliente.contar_prestamos_clientes(cliente)<3){
                fr = new FileReader("Prestamos.txt");
                br = new BufferedReader(fr);
                
                while((linea = br.readLine()) != null){
                    if(cliente.equals(linea.split(",")[0]) && 
                       libro.equals(linea.split(",")[1]) &&
                       "en espera".equals(linea.split(",")[4])){
                        System.out.println("El cliente ya tiene en espera ese libro");
                        return;
                    }
                }
                
                br.close();
                fr.close();
                
                FileWriter fw = new FileWriter("Prestamos",true);
                PrintWriter pw = new PrintWriter(fw);
                
                pw.println(String.format("%s,%s,%s,%s,%s", cliente, libro,
                           dias, "0", "en espera"));
            }
        }catch (IOException e){
            
        }
    }
    
    public static int contar_prestamos_activos(boolean opcion){
        List<String> activos = new ArrayList<>();
        int cantidad = 0, index = 0;
        String linea, linea2;
        
        try {
            FileReader fr = new FileReader("Prestamos.txt");
            FileReader fr2 = new FileReader("Libros.txt");
            BufferedReader br = new BufferedReader(fr);
            BufferedReader br2 = new BufferedReader(fr2);
            
            while((linea = br.readLine())!= null){
                if(linea.split(",")[4].equals("en espera")){
                    cantidad++;
                    if(opcion){
                        while((linea2 = br2.readLine())!= null){
                            if(linea2.split(",")[index].equals(linea.split(",")[1])){
                                activos.add(linea2);
                            }
                        }
                    }
                }
                index++;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Utilitarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(opcion){
            activos.stream().forEach(activo -> {
                String[] linea_a = activo.split(",");
                System.out.println(String.format("%s,%s",linea_a[0],linea_a[1]));
            });
        }
        return cantidad;
    }

    /**
     * @return the clientes_activos
     */
    private int getClientes_activos() {
        return clientes_activos;
    }

    /**
     * @param clientes_activos the clientes_activos to set
     */
    public void setClientes_activos(int clientes_activos) {
        this.clientes_activos = clientes_activos;
    }

    /**
     * @return the numero_libros
     */
    public int getNumero_libros() {
        return numero_libros;
    }

    /**
     * @param numero_libros the numero_libros to set
     */
    public void setNumero_libros(int numero_libros) {
        this.numero_libros = numero_libros;
    }
}

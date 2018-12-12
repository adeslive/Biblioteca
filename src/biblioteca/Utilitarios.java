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

public class Utilitarios {
    int clientes_activos;
    int numero_libros;
    FileWriter fw;
    PrintWriter pw;
    FileReader fr;
    BufferedReader br;
    
    public Utilitarios(){
    
    }
    
    public void cambiar_estado_prestamo(String cliente, String libro){
        try{
            List<String> lineas = new ArrayList<>();
            String linea;
            String linea_editada;
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
    public void nuevo_prestamo(String cliente, String libro, String dias){
        String linea;
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
                
                fw = new FileWriter("Prestamos",true);
                pw = new PrintWriter(fw);
                
                pw.println(String.format("%s,%s,%s,%s,%s", cliente, libro,
                           dias, "0", "en espera"));
            }
        }catch (IOException e){
        }
    }
    
}

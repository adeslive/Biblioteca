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
            
            for(String linea_1 : lineas){
                pw.println(linea_1);
            }
            
            pw.close();
            fw.close();
            
        }catch(Exception e){
            
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca;

import com.sun.media.jfxmedia.logging.Logger;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private int codigo;
    private String nombre;
    private int telefono;
    private int celular;
    
    public void agregar_cliente(String codigo, String nombre, 
                                int telefono, int celular)
    {
        try{
            
            if (!buscar_cliente(codigo)){
                FileWriter fw = new FileWriter("Clientes.txt");
                PrintWriter pw = new PrintWriter(fw);
                
                pw.println(String.format("%s,%s,%d,%d", codigo, nombre,
                                         telefono, celular));
                
                pw.flush();
                pw.close();
                fw.close();
                
            } 
        }catch (Exception e){
            
        }
    }
    
    public static boolean buscar_cliente(String codigo){
        try{
            String linea;
            FileReader fr = new FileReader("Clientes.txt");
            BufferedReader br = new BufferedReader(fr);
            
            while((linea = br.readLine())!= null){
                if(linea.equals(linea.split(",")[0])){
                    return true;
                }
            }

            br.close();
            fr.close();
        }catch (FileNotFoundException e){

        }catch (IOException ex){

        }
        return false;
    }
    
    public Cliente informacion_cliente(int codigo){
        Cliente cliente_busq = new Cliente();
        try{
            String linea;
            FileReader fr = new FileReader("Clientes.txt");
            BufferedReader br = new BufferedReader(fr);
            
            while((linea = br.readLine())!= null){
                if(linea.equals(linea.split(",")[0])){
                    cliente_busq.codigo = codigo;
                    cliente_busq.nombre = linea.split(",")[1];
                    cliente_busq.telefono = Integer.parseInt(linea.split(",")[2]);
                    cliente_busq.celular = Integer.parseInt(linea.split(",")[3]);
                    
                    br.close();
                    fr.close();
                    return cliente_busq;
                }
            }         
        }catch (FileNotFoundException e){
            Logger.logMsg(codigo, e.getMessage());
        }catch (IOException ex){
            Logger.logMsg(codigo, ex.getMessage());
        }
        return null;
    }
    
    public void editar_cliente(String numero){
        List<String> lineas = new ArrayList<>();
        String cliente_editado;
        try {
           
            if (buscar_cliente(numero)){
                FileReader fr = new FileReader("Clientes.txt");
                BufferedReader br = new BufferedReader(fr);

                while (br.ready()) {
                    lineas.add(br.readLine());
                }
                br.close();
                fr.close();
                
                cliente_editado = String.format("%d,%s,%d,%d", numero, nombre, telefono, celular);
                lineas.set(Integer.parseInt(numero)-1, cliente_editado);
                
                FileWriter fw = new FileWriter("Clientes.txt");
                PrintWriter pw = new PrintWriter(fw);
                
                lineas.stream().forEach((linea) -> {
                    pw.println(linea);
                });

                fw.flush();
                pw.close();
                fw.close();      
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
    
    public static int contar_prestamos_clientes(String numero){
        String linea;
        int cant=0;
        if (buscar_cliente(numero)){
            try{
                FileReader fr = new FileReader("Prestamos.txt");
                BufferedReader br = new BufferedReader(fr);
                
                while((linea = br.readLine()) != null){
                    if(numero.equals(linea.split(",")[0])){
                        if(linea.split(",")[4].equals("en espera")){
                            cant++;
                        }
                    }
                }
                
                br.close();
                fr.close();
            }catch (IOException e){
                
            }
        }
        return cant;
    }
    
    // Funciona como prestamos activos y como historial al cambiar la condicion
    public void prestamos_condicion_cliente(int numero, String condicion){
        List<String> lineas = new ArrayList<>();
        String linea, nombre_cliente = null, libro = null;
        FileReader fr, fr2;
        BufferedReader br, br2;
        try{
            fr2 = new FileReader("Clientes.txt");
            br2 = new BufferedReader(fr2);
            while((linea = br2.readLine()) != null){
                if(numero == Integer.parseInt(linea.split(",")[0])){
                    nombre_cliente=linea.split(",")[1];
                }
            }
            
            br2.close();
            fr2.close();
            
            fr = new FileReader("Prestamos.txt");
            br = new BufferedReader(fr);
            
            while((linea = br.readLine()) != null){
                if(numero == Integer.parseInt(linea.split(",")[0])){
                    if(linea.split(",")[2].equals(condicion)){  //Cambia la condicion a "en espera"
                        lineas.add(linea);                      // o entregado
                    }     
                }
            }
            br.close();
            fr.close();
                       
            fr = new FileReader("Libros.txt");
            br = new BufferedReader(fr);
            
            while((linea = br.readLine()) != null){
                if(numero == Integer.parseInt(linea.split(",")[0])){
                    libro=linea.split(",")[1];
                }
            }     
            br.close();
            fr.close();
            
            for (String linea_iterador : lineas){
                String[] linea_actual = linea_iterador.split(",");
                System.out.println(String.format("%s,%s,%s,%s", nombre_cliente,libro,
                                                linea_actual[2],linea_actual[3]));
            }
            
        }catch (IOException e){
        
        }
    }
    
 
    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the telefono
     */
    public int getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the celular
     */
    public int getCelular() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void setCelular(int celular) {
        this.celular = celular;
    }

}

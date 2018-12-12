/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca;

import com.sun.media.jfxmedia.logging.Logger;
import java.io.BufferedReader;
import java.io.File;
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
    private FileWriter fw;
    private PrintWriter pw;
    private FileReader fr;
    private BufferedReader br;
    
    public void agregar_cliente(String codigo, String nombre, 
                                int telefono, int celular){
        try{
            if (!new File("Clientes.txt").exists()){
                new File("Clientes.txt").createNewFile();
            } 
            if (buscar_cliente(codigo)==false){
                fw = new FileWriter("Clientes.txt",true);
                pw = new PrintWriter(fw);
                
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
        FileReader fr;
        BufferedReader br;
        try{
            String linea;
            fr = new FileReader("Clientes.txt");
            br = new BufferedReader(fr);
            
            while((linea = br.readLine())!= null){
                if(codigo.equals(linea.split(",")[0])){
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
            fr = new FileReader("Clientes.txt");
            br = new BufferedReader(fr);
            
            while((linea = br.readLine())!= null){
                if(linea.equals(linea.split(",")[0])){
                    cliente_busq.codigo = codigo;
                    cliente_busq.nombre = linea.split(",")[1];
                    cliente_busq.telefono = Integer.parseInt(linea.split(",")[2]);
                    cliente_busq.celular = Integer.parseInt(linea.split(",")[3]);
                    
                    fr.close();
                    br.close();
                    return cliente_busq;
                }
            }
            fr.close();
            br.close();
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
            if (!new File("Clientes.txt").exists()){
                new File("Clientes.txt").createNewFile();
            } 
            if (buscar_cliente(numero)){
                fr = new FileReader("Clientes.txt");
                br = new BufferedReader(fr);

                while (br.ready()) {
                    lineas.add(br.readLine());
                }
                br.close();
                fr.close();
                
                cliente_editado = String.format("%s,%s,%d,%d", numero, nombre, telefono, celular);
                lineas.set(Integer.parseInt(numero)-1, cliente_editado);
                
                fw = new FileWriter("Clientes.txt");
                pw = new PrintWriter(fw);
                
                lineas.stream().forEach((linea) -> {
                    pw.println(linea);
                });

                fw.flush();
                pw.close();
                fw.close();      
            }
        } catch (IOException | NumberFormatException e) {
            System.out.print(e.getMessage());
        }
    }
    
    public static int contar_prestamos_clientes(String numero){
        String linea;
        int cant=0;
        FileReader fr;
        BufferedReader br;
        if (buscar_cliente(numero)){
            try{
                fr = new FileReader("Prestamos.txt");
                br = new BufferedReader(fr);
                
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
    public void prestamos_activos_cliente(int numero, String condicion){
        List<String> lineas = new ArrayList<>();
        String linea, nombre_cliente = null;
        int index = 0;        
        try{
            if (!new File("Clientes.txt").exists()){
                new File("Clientes.txt").createNewFile();
            }           
            fr = new FileReader("Clientes.txt");
            br = new BufferedReader(fr);
            while((linea = br.readLine()) != null){
                if(numero == Integer.parseInt(linea.split(",")[0])){
                    nombre_cliente=linea.split(",")[1];
                }
            }
            
            br.close();
            fr.close();
            
            fr = new FileReader("Prestamos.txt");
            br = new BufferedReader(fr);
            
            while((linea = br.readLine()) != null){
                if(numero == Integer.parseInt(linea.split(",")[0])){
                    if(linea.split(",")[2].equals("en espera")){
                        lineas.add(linea);
                    }     
                }
            }
            br.close();
            fr.close();
                       
            fr = new FileReader("Libros.txt");
            br = new BufferedReader(fr);
            
            
            while((linea = br.readLine()) != null){
                if(lineas.get(index).split(",")[1].equals(linea.split(",")[0])){
                    
                    String linea_t = String.format("%s,%s", linea.split(",")[1],
                                                   lineas.get(index).split(",")[2]);
                    lineas.set(index, linea_t);
                }
                index++;
            }     
            br.close();
            fr.close();
            
            for (String linea_iterador : lineas){
                String[] linea_actual = linea_iterador.split(",");
                System.out.println(String.format("%s,%s,%s", nombre_cliente, 
                                                 linea_actual[1], linea_actual[2]));
            }
            
        }catch (IOException e){
        
        }
    }
    
    public void historial_cliente(int numero, String condicion){
        List<String> lineas = new ArrayList<>();
        String linea;
        int index = 0;

        try{
            fr = new FileReader("Prestamos.txt");
            br = new BufferedReader(fr);
            
            while((linea = br.readLine()) != null){
                if(numero == Integer.parseInt(linea.split(",")[0])){
                    if(linea.split(",")[2].equals("entregado")){  //Cambia la condicion a "en espera"
                        lineas.add(linea);                      // o entregado
                    }     
                }
            }
            br.close();
            fr.close();
                       
            fr = new FileReader("Libros.txt");
            br = new BufferedReader(fr);
            
            while((linea = br.readLine()) != null){
                if(lineas.get(index).split(",")[1].equals(linea.split(",")[0])){
                    lineas.set(index, linea.split(",")[1]);
                }
                index++;
            }
            
            br.close();
            fr.close();
            
            lineas.stream().forEach((linea_iterador) -> {
                System.out.println(linea_iterador.split(",")[0]);
            });
            
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

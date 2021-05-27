/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqltesting;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Gardel
 */
public class MysqlTesting {

    /**
     * @param args the command line arguments
     */
    
    Connection con;
    
    public MysqlTesting(){
        try
        {
          Class.forName("com.mysql.cj.jdbc.Driver");
            
           
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/apptarea?serverTimezone=UTC","root", "1234");         
            //System.out.println("Se conecto a la Base de datos");
            
        } 
        catch (Exception e)
        {
            System.err.println("Error " + e );
        }
    }
    
    public static void main(String[] args) throws SQLException  { 
             
        // TODO code application logic here /*  
        
        MysqlTesting mt = new MysqlTesting();
        
        Scanner sc = new Scanner(System.in);
        
        int Select = 1;
        do{
        System.out.println("\t ");
        System.out.println("Seleccione una opcion: ");        
        System.out.println("1 - Ver las tareas");
        System.out.println("2 - Agregar una tarea");
        System.out.println("3 - Modificar una tarea");
        System.out.println("4 - Borrar una tarea");
        System.out.println("0 - Salir");
        System.out.println("\t ");
                
        String Option = sc.next();
               Select = Integer.parseInt(Option);
                        
        if (Select == 1){ 
              System.out.println("Selecciono la opcion 1- Ver las tareas: ");
                    SHOW();
                
        }else{
        if (Select == 2){
              System.out.println("Selecciono la opcion 2 - Agregar una tarea: ");
              System.out.println("Ingrese los datos de la nueva tarea: NOMBRE, DESCRIPCION y ESTADO(1=activo - 0=inactivo) ");
              String Name = sc.next();
              String Description = sc.next();
              String condition = sc.next();
              int condition2 = Integer.parseInt(condition);
                    ADD(Name , Description , condition2);                
               
             } else{   
        if (Select == 3) {
              System.out.println("Selecciono la opcion 3 - Modificar una tarea: ");
              System.out.println("Ingrese el id y el estado de la tarea a modificar: ");
              
              String Id = sc.next();
              int Id3 = Integer.parseInt(Id);
              String Condition = sc.next();
              int Condition3 = Integer.parseInt(Condition);
              
                   UPDATE(Id3 , Condition3);
                            
             }else{  
        if (Select == 4) {
              System.out.println("Selecciono la opcion 4 - Borrar una tarea");
              System.out.println("Ingresar el Id de la tarea a borrar: ");
              String ingreso4 = sc.next();
              int idTareas4 = Integer.parseInt(ingreso4);
              DELETE(idTareas4);            
           
           }else{        
        if (Select > 4 ) { 
          // } else {
               System.out.println("Selecciono una opcion NO valida");
            
                    } 
                   }
                  }
                 }
                }
    
               mt.con.close();
           }   while(Select != 0);
               System.out.println("SALIO DEL PROGRAMA");
  
       }
    
    
    //FUNCIONES
        
    //MOSTRAR TODAS LAS TAREAS.    
    public static void SHOW (){
        
        Statement st;
        ResultSet rs;
        MysqlTesting mt = new MysqlTesting();
        
        try 
        {
            st = mt.con.createStatement();
            rs = st.executeQuery("SELECT * FROM tareas");

               System.out.println("Id     NOMBRE         DESCRIPCION      ESTADO");
               
            while(rs.next())
            {               
               System.out.println(rs.getInt("idTareas") + "       " + rs.getString("nombre") + "          " + 
                       rs.getString("descripcion") + "            " + rs.getInt("estado"));
            } 
            
              mt.con.close();
          } 
        
        catch (Exception e)
         {
            System.err.println("ERROR AL OBTENER LOS DATOS. " + e );
         }
    }      
    
    
    //AGREGAR UNA TAREA    
    public static void ADD (String Name , String Description , int Condition){
        
        Statement st;
        PreparedStatement ps;
        MysqlTesting mt = new MysqlTesting();
        
        try 
        {   
            st = mt.con.createStatement();
            String Q = "INSERT INTO tareas (`nombre`, `descripcion`, `estado`) VALUES ('" + Name + "' , '" + 
                                              Description + "' , '" + Condition + "' );";
            
            ps = mt.con.prepareStatement(Q);
            int resultRowCount = ps.executeUpdate();
            
            if(resultRowCount > 0) {
               System.out.println("La tarea se agrego correctamente -> " + resultRowCount);
            }else{
               System.out.println("NO se agrego ninguna tarea");
            }   
            
             mt.con.close();   
         } 
        
        catch (Exception e)
         {
            System.err.println("ERROR AL OBTENER LOS DATOS. " + e );
         }
    } 
    
    //MODIFICAR UNA TAREA
    public static void UPDATE ( int Id  , int Condition){
        
        Statement st;
        PreparedStatement ps;
        MysqlTesting mt = new MysqlTesting();
        
        try 
        {
            st = mt.con.createStatement();
            String Q = "UPDATE tareas SET estado =? WHERE idTareas =? " ;
            
            ps = mt.con.prepareStatement(Q);
            ps.setInt(1 , Condition );
            ps.setInt(2 , Id );
            
            int resultRowCount = ps.executeUpdate();
           
            if(resultRowCount > 0) {
               System.out.println("La tarea se modifico correctamente -> " + resultRowCount);
            }else{
               System.out.println("La tarea NO se modifico");
            }
            
            mt.con.close();   
         } 
        
        catch (Exception e)
         {
            System.err.println("ERROR AL OBTENER LOS DATOS. " + e );
         }
       }
     
    //BORRAR UNA TAREA
    public static void DELETE ( int N){
        
        Statement st;
        PreparedStatement ps;
        MysqlTesting mt = new MysqlTesting();
        
        try 
        {
            st = mt.con.createStatement();
            String Q = "DELETE FROM tareas WHERE idTareas = ?;" ;
            
            ps = mt.con.prepareStatement(Q);
            ps.setInt(1 , N);
            
            int resultRowCount = ps.executeUpdate();
            
            if(resultRowCount > 0) {
               System.out.println("La tarea se borro correctamente -> " + resultRowCount);
            }else{
               System.out.println("La tarea NO se borro");
            }
            
            mt.con.close();   
        } 
        
        catch (Exception e)
         {
            System.err.println("ERROR AL OBTENER LOS DATOS. " + e );
         }
       }  
    
    }


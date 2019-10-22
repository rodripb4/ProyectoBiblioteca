/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author rodri
 */
public class Prestamo extends Conexion{
    
     Connection con;
      CallableStatement cstmt = null;
      
              public void Crear_Prestamo(int id_libro, int id_user, String clase){
                       try {

              con=Conexion.getConnection();
              cstmt= con.prepareCall("{call Crear_Prestamo(?,?,?)}");
                    cstmt.setInt(1, id_libro);
              cstmt.setInt(2, id_user);
       
              cstmt.setString(3, clase);
              cstmt.execute();
              cstmt.close();
              con.close();
          } catch (SQLException ex) {
            
          }
        
                   }
              
      public DefaultTableModel listar_prestamo(){
         String aux;
           int t=0;
                 String[] headers = { "ID_PRESTAMO","FECHA_INICIO","FECHA_LIMITE","ID_USUARIO","ID_LIBRO","PENALIZADO"};
                 DefaultTableModel tabla = new DefaultTableModel();
                    try {
              con=Conexion.getConnection();
              this.cstmt=con.prepareCall("{call CONSULTA PRESTAMOS.obtener_prestamos(?)}");
              cstmt.registerOutParameter(1, OracleTypes.CURSOR);
              cstmt.executeQuery();
              
              ResultSet cursor= (ResultSet) cstmt.getObject(1);
              while(cursor.next()){
                  t++;
              }
               cursor.close();
		    cstmt.close();
		    con.close();
          } catch (SQLException ex) {
              Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
          }
                        String[][] filas = new String[t][7];
                 
                 int id_prestamo, id_user, id_libro, penalizado;
                 String fecha_inicio, fecha_devolucion;
                 
               
                 
          try {
              con=Conexion.getConnection();
               this.cstmt=con.prepareCall("{call CONSULTA PRESTAMOS.obtener_prestamos(?)}");
              cstmt.registerOutParameter(1, OracleTypes.CURSOR);
              cstmt.executeQuery();
              
              ResultSet cursor= (ResultSet) cstmt.getObject(1);
              int i=0;
              while(cursor.next()){
                  id_prestamo=cursor.getInt(1);
               
                  fecha_inicio=cursor.getString(2);
                
                  fecha_devolucion= cursor.getString(3);
               
                  id_user=cursor.getInt(6);
           
                  id_libro=cursor.getInt(5);
                   penalizado=cursor.getInt(7);
                  
                
                  filas[i][0]=Integer.toString(id_prestamo);
                    filas[i][1]=fecha_inicio;
                  filas[i][2]=fecha_devolucion;
                  
            
                  filas[i][5]=Integer.toString( id_libro);
                  //segun salga 1 o 0 vamos a poner si esta o no disponible ese libro en la tabla 
               
                  
                  filas[i][3]=Integer.toString( id_user);
                     if(penalizado==1){
                      aux =" ";
                  }else{
                        aux ="Penalizado";
                  }
                   filas[i][4]=aux;
                    i++;
              }
             cursor.close();
		    cstmt.close();
		    con.close();
              tabla.setDataVector(filas, headers);
          } catch (SQLException ex) {
              Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
          }
             return tabla;
               }
               
    
      
           
       public int listar_DIAS_CLASE(String clase){
           
                    String p;
             int dia = 0;
                    try{
                        con=Conexion.getConnection();
              this.cstmt=con.prepareCall("{call CONSULTA CLASES.obtener_clases(?)}");
              cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            
              cstmt.execute();
               cstmt.executeQuery();
       
           
                ResultSet cursor= (ResultSet) cstmt.getObject(1);
                  while(cursor.next()){
                   p=cursor.getString(1);
                   
             dia=cursor.getInt(2);
   
                if(p.equalsIgnoreCase(clase)){
                    break;
                }
              }
                
                cursor.close();
		    cstmt.close();
		    con.close();
                    }catch (SQLException ex){
                        
                    }
          return dia;
                    
                }
                 
       
    
     
     
          
     public DefaultTableModel listar_historicos(){
         String aux;
           int t=0;
                 String[] headers = { "ID_PRESTAMO","FECHA_INICIO","FECHA_LIMITE","FECHA_ENTREGA","ID_USUARIO","ID_LIBRO"};
                 DefaultTableModel tabla = new DefaultTableModel();
                    try {
              con=Conexion.getConnection();
              this.cstmt=con.prepareCall("{call CONSULTA HISTORICO.obtener_historico(?)}");
              cstmt.registerOutParameter(1, OracleTypes.CURSOR);
              cstmt.executeQuery();
              
              ResultSet cursor= (ResultSet) cstmt.getObject(1);
              while(cursor.next()){
                  t++;
              }
               cursor.close();
		    cstmt.close();
		    con.close();
          } catch (SQLException ex) {
              Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
          }
                        String[][] filas = new String[t][7];
                 
                 int id_prestamo, id_user, id_libro;
                 String fecha_inicio, f_entrega, f_limite;
                 
               
                 
          try {
              con=Conexion.getConnection();
               this.cstmt=con.prepareCall("{call CONSULTA HISTORICO.obtener_historico(?)}");
              cstmt.registerOutParameter(1, OracleTypes.CURSOR);
              cstmt.executeQuery();
              
              ResultSet cursor= (ResultSet) cstmt.getObject(1);
              int i=0;
              while(cursor.next()){
                  id_prestamo=cursor.getInt(1);
               
                  fecha_inicio=cursor.getString(2);
                   f_limite=cursor.getString(3);
                  f_entrega= cursor.getString(4);
               
                       id_libro=cursor.getInt(5);
                  id_user=cursor.getInt(6);
          
                  
                
                  filas[i][0]=Integer.toString(id_prestamo);
                    filas[i][1]=fecha_inicio;
                  filas[i][2]=f_limite;
                 filas[i][3]=f_entrega ;
             filas[i][4]=Integer.toString(id_user); 
                  filas[i][5]=Integer.toString(id_libro);
                  //segun salga 1 o 0 vamos a poner si esta o no disponible ese libro en la tabla 
               
           
                
                    i++;
              }
             cursor.close();
		    cstmt.close();
		    con.close();
              tabla.setDataVector(filas, headers);
          } catch (SQLException ex) {
              Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
          }
             return tabla;
               }
          
          
          
      }
    

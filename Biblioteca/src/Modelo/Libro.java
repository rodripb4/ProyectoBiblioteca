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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author rodri
 */
public class Libro extends  Conexion{
    
     Connection con;
      CallableStatement cstmt = null;
       public String[] listarLibros(){
                   
                    String[] info = new String[6];
                    try{
                        con=Conexion.getConnection();
              this.cstmt=con.prepareCall("{call CONSULTA_LIBROS.obtener_libros_ID(?)}");
              cstmt.registerOutParameter(1, OracleTypes.CURSOR);
              cstmt.execute();
               cstmt.executeQuery();
       
             int p;
                ResultSet cursor= (ResultSet) cstmt.getObject(1);
                  while(cursor.next()){
                   p=cursor.getInt(1);
                   System.out.print("id libro "+p);
             info[0]=cursor.getString(2);
               info[1]=cursor.getString(3);
                        
                 info[2]=cursor.getString(4);
                 info[3]=cursor.getString(5);
                
                info[4]=cursor.getString(6);
                info[5]=Integer.toString(cursor.getInt(7));
                System.out.print("1"+info[1]);

              }
                
                cursor.close();
		    cstmt.close();
		    con.close();
                    }catch (SQLException ex){
                        
                    }
          return info;
                    
                }  
      
      
              public DefaultTableModel listarlibros(){
                 int t=0;
                 String[] headers = { "ID_LIBRO","Autor","Titulo","Editorial","Clase","Disponibilidad" };
                 DefaultTableModel tabla = new DefaultTableModel();
                
          try {
              con=Conexion.getConnection();
              this.cstmt=con.prepareCall("{call CONSULTA_LIBROS.obtener_libros(?)}");
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
                 
                 int ID_LIBRO,disponibilidad;
                 String autor,titulo,editorial,clase;
               
                 
          try {
              con=Conexion.getConnection();
               this.cstmt=con.prepareCall("{call CONSULTA_LIBROS.obtener_libros(?)}");
              cstmt.registerOutParameter(1, OracleTypes.CURSOR);
              cstmt.executeQuery();
              
              ResultSet cursor= (ResultSet) cstmt.getObject(1);
              int i=0;
              while(cursor.next()){
                  ID_LIBRO=cursor.getInt(1);
                  autor=cursor.getString(2);
                  titulo=cursor.getString(3);
                  editorial= cursor.getString(4);
                  clase=cursor.getString(5);
                  disponibilidad=cursor.getInt(6);
                System.out.print("id "+ID_LIBRO);
                  filas[i][0]=Integer.toString(ID_LIBRO);
                  filas[i][1]=autor;
                    filas[i][2]=titulo;
                  filas[i][3]=editorial;
                  filas[i][4]=clase;
                  filas[i][5]=Integer.toString(disponibilidad);
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
               //Metodo para crear un nuevo libro
               public void crear_libro( String autor,String titulo,String editorial, String clase, int disponibilidad){
          try {
     
              con=Conexion.getConnection();
              cstmt= con.prepareCall("{call CREAR_LIBRO(?,?,?,?,?,?)}");
              cstmt.setString(1,autor);
                    cstmt.setString(2, titulo);
              cstmt.setString(3, editorial);
              cstmt.setString(4, clase);
              cstmt.setInt(5, disponibilidad);
              cstmt.execute();
              cstmt.close();
              con.close();
          } catch (SQLException ex) {
              System.out.print("error "+ex);
          }
        
                  
}
               
               //Metodo para eliminar un libro
                 public void eliminar_libro(int id_libro) throws SQLException {
           
              con=Conexion.getConnection();
                System.out.print("id a eliminar "+id_libro);
              cstmt= con.prepareCall("{call ELIMINAR_LIBRO(?)}");
                cstmt.setInt(1, id_libro);
             	  
         cstmt.execute();
         cstmt.close();
	 con.close();
 
         } 
                
                 public DefaultComboBoxModel Listar_Clases(){
                     
                     ArrayList<String> ids = new ArrayList<String>();
        try {
              con=Conexion.getConnection();
               this.cstmt=con.prepareCall("{call CONSULTA CLASES.obtener_clases(?)}");
              cstmt.registerOutParameter(1, OracleTypes.CURSOR);
              cstmt.executeQuery();
               ResultSet cursor= (ResultSet) cstmt.getObject(1);
           
               String tipo;
               

             while(cursor.next()){
                 tipo=cursor.getString(1);
                  
                      ids.add(tipo);
                  }
             cursor.close();
		    cstmt.close();
		    con.close();
           
          } catch (SQLException ex) {
            Logger.getLogger(Prestamo.class.getName()).log(Level.SEVERE, null, ex);
                          }
               
           return new DefaultComboBoxModel(ids.toArray());
                 }
                 
                 
                 //Metodo para devolver libro
               public void devolver_libro(int id_libro, int id_prestamo){
         try {
             con=Conexion.getConnection();
             this.cstmt=con.prepareCall("{call DEVOLVER_LIBRO(?,?)}");
               cstmt.setInt(1, id_libro);
               cstmt.setInt(2,id_prestamo);
              cstmt.executeQuery();
              JOptionPane.showMessageDialog(null,"Libro: "+id_libro+" Ha sido devuelto");
         } catch (SQLException ex) {
             Logger.getLogger(Libro.class.getName()).log(Level.SEVERE, null, ex);
         }
               
               }
    
    
}

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
public class Usuario extends Conexion{
      Connection con;
      CallableStatement cstmt = null;
         int Ncarnet;
	 String direccion;
	 String Nombre;
	 int penalizacion;
         String dni;
         public Usuario(int Ncarnet, String dni, String direccion, String Nombre, int penalizacion) {
		super();
		this.Ncarnet=Ncarnet;
               this.dni=dni;
                this.direccion=direccion;
                this.Nombre=Nombre;
                this.penalizacion=penalizacion;
	}
         public Usuario(){
             super();
         }

                public String[] listar(){
                   
                    String[] info = new String[6];
                    try{
                        con=Conexion.getConnection();
              this.cstmt=con.prepareCall("{call CONSULTA USUARIOS.obtener_usuarios_ID(?)}");
              cstmt.registerOutParameter(1, OracleTypes.CURSOR);
              cstmt.execute();
               cstmt.executeQuery();
       
             int p;
                ResultSet cursor= (ResultSet) cstmt.getObject(1);
                  while(cursor.next()){
                   p=cursor.getInt(1);
                   
             info[0]=cursor.getString(2);
              
          
                info[1]=cursor.getString(3);
         
                 info[2]=cursor.getString(4);
                 info[3]=cursor.getString(5);
                
                info[4]=Integer.toString(cursor.getInt(6));
                info[5]=Integer.toString(cursor.getInt(7));
         
              }
                
                cursor.close();
		    cstmt.close();
		    con.close();
                    }catch (SQLException ex){
                        
                    }
          return info;
                    
                }
         
               public DefaultTableModel listarlectores(){
                 int t=0;
                 String[] headers = { "Num Carnet","DNI","Nombre","Calle","Ciudad","Cp","Penalizacion" };
                 DefaultTableModel tabla = new DefaultTableModel();
                
          try {
              con=Conexion.getConnection();
              this.cstmt=con.prepareCall("{call CONSULTA USUARIOS.obtener_usuarios(?)}");
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
                 
                 int Ncarnet,cp,penalizacion;
                 String nombre, calle, ciudad,dni;
               
                 
          try {
              con=Conexion.getConnection();
               this.cstmt=con.prepareCall("{call CONSULTA USUARIOS.obtener_usuarios(?)}");
              cstmt.registerOutParameter(1, OracleTypes.CURSOR);
              cstmt.executeQuery();
              
              ResultSet cursor= (ResultSet) cstmt.getObject(1);
              int i=0;
              String aux = null;
              while(cursor.next()){
                  Ncarnet=cursor.getInt(1);
                  dni=cursor.getString(3);
                  nombre= cursor.getString(2);
                  calle=cursor.getString(4);
                  ciudad=cursor.getString(5);
                  cp=cursor.getInt(6);
                  penalizacion=cursor.getInt(7);
                
                  filas[i][0]=Integer.toString(Ncarnet);
                    filas[i][1]=dni;
                  filas[i][2]=nombre;
                  filas[i][3]=calle;
                  filas[i][4]=ciudad;
                  filas[i][5]=Integer.toString(cp);
                  if(penalizacion==0){
                      aux="Penalizado";
                  }else{
                      aux=" ";
                  }
                  filas[i][6]=aux;
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
               
               
           //Metodo para crear un nuevo usuario
               public void crear_usuario( String dni,String nombre, String calle, String ciudad, int cp){
          try {
              System.out.println("entra "+dni+nombre+calle+ciudad+cp);
              con=Conexion.getConnection();
              cstmt= con.prepareCall("{call Insertar_Usuario(?,?,?,?,?)}");
                    cstmt.setString(1, dni);
              cstmt.setString(2, nombre);
       
              cstmt.setString(3, calle);
              cstmt.setString(4, ciudad);
              cstmt.setInt(5, cp);
       
              cstmt.execute();
              cstmt.close();
              con.close();
          } catch (SQLException ex) {
              Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
          }
                   
               }
               
               
           //Editar ususario
               
           public void Editar_Usuario(String nombre, String DNI, String calle,String ciudad, int cp, int penalizacion){
                 try {
             
              con=Conexion.getConnection();
              cstmt= con.prepareCall("{call Editar_Usuario(?,?,?,?,?,?)}");
              cstmt.setString(1, nombre);
              cstmt.setString(2, dni);
              cstmt.setString(3, calle);
              cstmt.setString(4, ciudad);
              cstmt.setInt(5, cp);
              cstmt.setInt(6, penalizacion);
              cstmt.execute();
              cstmt.close();
              con.close();
          } catch (SQLException ex) {
              Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
          }
           }    
           
           
           //Metodo para eliminar usuario
         public void eliminar_ususario(int ncarnet)throws SQLException {
           
              con=Conexion.getConnection();
              cstmt= con.prepareCall("{call Eliminar_Usuarios (?)}");
                cstmt.setInt(1, ncarnet);

         cstmt.execute();
         cstmt.close();
	 con.close();
            listarlectores();

         }
                
}
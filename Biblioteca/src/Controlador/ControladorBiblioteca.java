/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Gestor;
import Modelo.Libro;
import Modelo.Prestamo;
import Modelo.Usuario;
import Vista.VistaB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author HP
 */
public class ControladorBiblioteca implements ActionListener,MouseListener {
    
     Usuario u = new Usuario();
    Libro l = new Libro();
    Gestor g ;
    VistaB vista ;
    Prestamo p= new Prestamo();
    
     public enum AccionMVC {
		__GOTO_USUARIOS, __INSERTAR_LECTOR,__GOTO_CREARUSER, __ELIMINAR_USER,
                //-----------------------------------LIBROS----------------------------------------------\\\\\
               __GOTO_LIBROS, __CREAR_LIBROS, __ELIMINAR_LIBROS,
               //----------------------------------PRESTAMO----------------------------
               __PRESTAMO, __IR_CREAR_PRESTAMO, __NUEVO_PRESTAMO, 
                __DEVOLVER,__VER_HISTORICO,
              // ------------------------------volver-------------------------
               __VOLVER_INICIO, __VOLVER_PRESTAMO,
                       
		}
    public ControladorBiblioteca(VistaB v){
        this.vista= v;
        this.g= new Gestor(v);
    }
   
        public void iniciar(){
           // Skin tipo WINDOWS
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vista);
            vista.setVisible(true);
        } catch (UnsupportedLookAndFeelException ex) {}
          catch (ClassNotFoundException ex) {}
          catch (InstantiationException ex) {}
          catch (IllegalAccessException ex) {}
 //declara una acción y añade un escucha al evento producido por el componente
     
      this.vista.btn_Usuarios.setActionCommand("__GOTO_USUARIOS");
      this.vista.btn_Usuarios.addActionListener(this);
      
      
      
      this.vista.btn_CrearU.setActionCommand("__INSERTAR_LECTOR");
      this.vista.btn_CrearU.addActionListener(this);
      
      
      this.vista.btn_BorrarU.setActionCommand("__ELIMINAR_USER");
      this.vista.btn_BorrarU.addActionListener(this);
      
      this.vista.btn_Libros.setActionCommand("_GOTO_LIBROS");
      this.vista.btn_Libros.addActionListener(this);
      
      this.vista.btn_CrearL.setActionCommand("__CREAR_LIBROS");
      this.vista.btn_CrearL.addActionListener(this);
      
      this.vista.btn_BorrarL.setActionCommand("__ELIMINAR_LIBROS");
      this.vista.btn_BorrarL.addActionListener(this);
      
       this.vista.btn_Prestamos.setActionCommand("__PRESTAMO");
       this.vista.btn_Prestamos.addActionListener(this);
       
       this.vista.btn_crear_prestamo.setActionCommand("__CREAR_PRESTAMO");
       this.vista.btn_crear_prestamo.addActionListener(this);

       
       
       this.vista.btn_Devolucion.setActionCommand("__DEVOLVER");
       this.vista.btn_Devolucion.addActionListener(this);
       
       this.vista.btn_VerHistoricos.setActionCommand("__VER_HISTORICO");
       this.vista.btn_VerHistoricos.addActionListener(this);
       
       this.vista.VOLVER_LIBRO.setActionCommand("__VOLVER_INICIO");
       this.vista.VOLVER_LIBRO.addActionListener(this);
       
       this.vista.VOLVER_P.setActionCommand("__VOLVER_PRESTAMO");
       this.vista.VOLVER_P.addActionListener(this);
       
       this.vista.VOLVER_USER.setActionCommand("__VOLVER_INICIO");
       this.vista.VOLVER_USER.addActionListener(this);
       
       this.vista.Volver_Prestamo.setActionCommand("__VOLVER_PRESTAMO");
       this.vista.Volver_Prestamo.addActionListener(this);
       
               }
      
 
	public void actionPerformed(ActionEvent e) {
		switch (AccionMVC.valueOf(e.getActionCommand())) {
		
		case __GOTO_USUARIOS:
                 
                     g.cambiarinterfazes(vista.panel_Usuario);
                
                this.vista.table_Usuarios.setModel(u.listarlectores());
                break;
               
			
			
                case __INSERTAR_LECTOR:
                  
               
                    try{
                        String nombre=this.vista.txt_NombreU.getText();
                        String dni=this.vista.txt_Dni.getText();
                        String calle= this.vista.txt_Calle.getText();
                        String ciudad=this.vista.txt_Ciudad.getText();
                       int cod_post=Integer.parseInt( this.vista.txt_Cp.getText().trim());
                     
             
                      u.crear_usuario(nombre,dni, calle, ciudad, cod_post);
                    }catch(NumberFormatException a){
                      JOptionPane.showMessageDialog(null, "Datos incorrectos, por favor rellene todos los campos");
                      a.printStackTrace();
                 
	            } 
                    g.cambiarinterfazes(vista.panel_Usuario);
                
                this.vista.table_Usuarios.setModel(u.listarlectores());
                    break;
                    
                case __ELIMINAR_USER:
                  
                 int ncarnet= Integer.parseInt(vista.txt_IdB.getText().trim());
                 
               
                    try {
                        u.eliminar_ususario(ncarnet);
                     
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                
                  g.cambiarinterfazes(vista.panel_Usuario);
                
                this.vista.table_Usuarios.setModel(u.listarlectores());
                    break;

              
                case __GOTO_LIBROS:
                    g.cambiarinterfazes(vista.panel_Libros);
                    
                    //this.vista.clase_libro_combo.setModel(l.Listar_Clases());
                     this.vista.tabla_Libros.setModel( l.listarlibros());
                  
                    break;
                case  __CREAR_LIBROS:
                    String autor = vista.txt_Autor.getText();
                    String titulo= vista.txt_Titulo.getText();
                    String editorial= vista.txt_Editorial.getText();
                    String clase = (String) vista.clase_libro_combo.getSelectedItem();
                    int disponibilidad= 0;
                    l.crear_libro(autor,titulo, editorial, clase, disponibilidad);
                    g.cambiarinterfazes(vista.panel_Libros);
                     this.vista.tabla_Libros.setModel( l.listarlibros());
                    break;
                case __ELIMINAR_LIBROS:
                    int iden= Integer.parseInt(vista.txt_Bl.getText());
                
                    try {
                        l.eliminar_libro(iden);
                        
                    } catch (SQLException ex) {
                        
                        Logger.getLogger(ControladorBiblioteca.class.getName()).log(Level.SEVERE, null, ex);
                    }
                           g.cambiarinterfazes(vista.panel_Libros);
                     this.vista.tabla_Libros.setModel( l.listarlibros());
                     
                    break;
                    
                case __PRESTAMO:
                    g.cambiarinterfazes(vista.panel_Prestamo);
                 
                 vista.tabla_Prestamos.setModel( p.listar_prestamo());
           
         
                    break;
               
               
          
                case __NUEVO_PRESTAMO:
              int id_u;
              int id_l; 
              String c;
              id_u=Integer.parseInt((String)vista.ID_USUARIOS_P.getSelectedItem()) ;
              id_l=Integer.parseInt((String)vista.ID_LIBRO_P.getSelectedItem());
              c=vista.txt_ClaseP.getText();
              
               p.Crear_Prestamo(id_l, id_u, c);
           
      g.cambiarinterfazes(vista.panel_Prestamo);
                 
                 vista.tabla_Prestamos.setModel( p.listar_prestamo());
            
                    break;

                  
                    
                case __VER_HISTORICO:
                    vista.tabla_historicos.setModel(p.listar_historicos());
                    g.cambiarinterfazes(vista.panel_Historicos);
                    break;
                case __DEVOLVER:
            
                 l.devolver_libro(Integer.parseInt(vista.txt_IdPL.getText()) ,Integer.parseInt(vista.txt_IdPrest.getText()) );
                 
                    vista.tabla_Prestamos.setModel( p.listar_prestamo());
                    break;
                case __VOLVER_PRESTAMO:
                    g.cambiarinterfazes(vista.panel_Prestamo);
                    break;
                case  __VOLVER_INICIO:
                    g.cambiarinterfazes(vista.panel_Menu);
                    break;
                }
                
                
        }
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
    
}

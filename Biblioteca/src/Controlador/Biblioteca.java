/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.sql.SQLException;
import Modelo.Usuario;
import Modelo.Libro;
import Modelo.Prestamo;
import Vista.VistaB;
/**
 *
 * @author rodri
 */
public class Biblioteca {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                 Usuario u=new Usuario();
		 Libro l = new Libro();
                 Prestamo p= new Prestamo();
		  new ControladorBiblioteca(new VistaB()).iniciar();
    }
    
}

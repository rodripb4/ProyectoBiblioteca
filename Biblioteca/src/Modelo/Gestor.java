/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Component;
import Vista.VistaB;

/**
 *
 * @author rodri
 */
public class Gestor {
      VistaB interfazes=null;
    
    public Gestor(VistaB interfazes){
        this.interfazes=interfazes;
    };

  
    public void cambiarinterfazes(Component c){
        interfazes.panel_Padre.removeAll();
        interfazes.panel_Padre.add(c);
        interfazes.panel_Padre.repaint();
        interfazes.panel_Padre.revalidate();
        interfazes.pack();
    }
    
}

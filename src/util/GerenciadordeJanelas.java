/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 *
 * @author Pituba
 */
public class GerenciadordeJanelas {
    
     private static JDesktopPane jdesktoppane;
    
    public GerenciadordeJanelas(JDesktopPane jdesktoppane){
    
          GerenciadordeJanelas.jdesktoppane = jdesktoppane;
    
    }
    
    
    public void abrirjanelas(JInternalFrame jinternalframe) throws PropertyVetoException{
        
        Dimension dimensao = Toolkit.getDefaultToolkit().getScreenSize();
        jinternalframe.setBounds((dimensao.width - dimensao.width),
                               1,
                               dimensao.width,
                               dimensao.height - 118);
        jinternalframe.setMaximizable(true);
    
         if(jinternalframe.isVisible()){
            jinternalframe.toFront();
            jinternalframe.requestFocus();
         }else{
            jdesktoppane.add(jinternalframe);
            jinternalframe.setVisible(true);
            jinternalframe.setMaximum(true);
         }    
    }
    
    public void abrirlogin(JInternalFrame jinternalframe) throws PropertyVetoException{
        
        Dimension dimensao = Toolkit.getDefaultToolkit().getScreenSize();
        jinternalframe.setBounds(((dimensao.width)-770)/2,
                               ((dimensao.height)-735)/2,
                               770,
                               468);
        jinternalframe.setMaximizable(false);
    
         if(jinternalframe.isVisible()){
            jinternalframe.toFront();
            jinternalframe.requestFocus();
         }else{
            jdesktoppane.add(jinternalframe);
            jinternalframe.setVisible(true);
            jinternalframe.setSelected(true);
         }  
    }
    
    public void abrirentrar(JInternalFrame jinternalframe) throws PropertyVetoException{
        
        Dimension dimensao = Toolkit.getDefaultToolkit().getScreenSize();
        jinternalframe.setBounds(((dimensao.width)-390)/2,
                               ((dimensao.height)-735)/2,
                               390,
                               468);
        jinternalframe.setMaximizable(false);
    
         if(jinternalframe.isVisible()){
            jinternalframe.toFront();
            jinternalframe.requestFocus();
         }else{
            jdesktoppane.add(jinternalframe);
            jinternalframe.setVisible(true);
            jinternalframe.setSelected(true);
         }  
    }
    
    public void abriradministrador(JInternalFrame jinternalframe) throws PropertyVetoException{
        
        Dimension dimensao = Toolkit.getDefaultToolkit().getScreenSize();
        jinternalframe.setBounds(((dimensao.width)-390)/2,
                               ((dimensao.height)-735)/2,
                               390,
                               468);
        jinternalframe.setMaximizable(false);
    
         if(jinternalframe.isVisible()){
            jinternalframe.toFront();
            jinternalframe.requestFocus();
         }else{
            jdesktoppane.add(jinternalframe);
            jinternalframe.setVisible(true);
            jinternalframe.setSelected(true);
         }  
    }
    
     public void fecharjanelas(JInternalFrame jinternalframe){
         if(jinternalframe.isVisible() && jinternalframe.isSelected()){
 
            jinternalframe.dispose();
            jdesktoppane.remove(jinternalframe);
         }
    }
}

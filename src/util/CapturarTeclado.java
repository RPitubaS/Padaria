/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import static view.frmMovimento.btnFecharcaixa;

/**
 *
 * @author Pituba
 */
public class CapturarTeclado extends JFrame{
    
    public CapturarTeclado(){
        addKeyListener(new KeyAdapter(){
            public void KeyPressed(KeyEvent e){
                    int codigo = e.getKeyCode();
                    int tecla = KeyEvent.VK_ALT;
                 if(codigo == tecla){
                   btnFecharcaixa.doClick();
                 }
            }
        });
    }
}

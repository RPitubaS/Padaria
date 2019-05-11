/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Pituba
 */
public class GuardarUrl {
    
    private int contador;

    public void setContador(int contador) {
        this.contador = contador;
    }
    
     public static Properties prop = new Properties();
    
    public void SaveProp(String nome, String arquivo){
         
        
        try {
            prop.setProperty(nome, arquivo);
            prop.store(new FileOutputStream("C:\\Meusprogramas\\Url"), null);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao salvar URL!");
        }
    }
    
    public String GetProp(String nome){
            String valor = "";
            
            try{
                prop.load(new FileInputStream("C:\\Meusprogramas\\Url"));
                valor = prop.getProperty(nome);
            }catch(IOException ex){
                switch(contador){
                    case 0:
                         JOptionPane.showMessageDialog(null, "Erro: \n" + ex + 
                         "\nPor favor selecione seu bando de dados!");
                         contador = 1;
                        break;
                    case 1:
                         contador = 0;
                        break;
                }

            }
                 return valor;
        }
    
}

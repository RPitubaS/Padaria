/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Pituba
 */
public class ConfigDB {
    String resultado;
    public String porta_bd(){    
        GuardarUrl guardarurl = new GuardarUrl();
        resultado = guardarurl.GetProp("conectarbkp");
        String resultadoreduz = resultado.replaceAll("MOVIMENTO.FDB", "");
      return resultadoreduz;  
    }
    
    public String getResultado() {
        return resultado;
    }
}

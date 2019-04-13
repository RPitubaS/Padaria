/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.bean;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Pituba
 */
public class Entradas {
    private int idponto, iddata, idusuario;
    private Time horaagora, horasaida;
    private String usuario;
    private float valorinicialcedula, valorinicialmoedas, caixasaida;
    private Date data;
    
    public int getIdponto() {
        return idponto;
    }

    public void setIdponto(int idponto) {
        this.idponto = idponto;
    }
    
    public int getIddata(){
      return iddata;
    }
    
    public void setIddata(int iddata){
       this.iddata = iddata;
    }
    
    public Date getData(){
       return data;
    }
    
    public void setData(Date data){
       this.data = data;
    }
    
    public int getIdusuario(){
       return idusuario;
    }
    
    public void setIdusuario(int idusuario){
       this.idusuario = idusuario;
    }
    
    public String getUsuario(){
       return usuario;
    }
    
    public void setUsuario(String usuario){
       this.usuario = usuario;
    }
    
    public Time getHoraagora(){
       return horaagora;
    }
    
    public void setHoraagora(Time horaagora){
       this.horaagora = horaagora;
    }
    
    public float getValorinicialcedula(){
       return valorinicialcedula;
    }
    
    public void setValorinicialcedula(float valorinicialcedula){
       this.valorinicialcedula = valorinicialcedula;
    }
    
    public float getValorinicialmoedas(){
       return valorinicialmoedas;
    }
    
    public void setValorinicialmoedas(float valorinicialmoedas){
       this.valorinicialmoedas = valorinicialmoedas;
    }
    
    public Time getHorasaida() {
        return horasaida;
    }

    public void setHorasaida(Time horasaida) {
        this.horasaida = horasaida;
    }
    
    public float getCaixasaida() {
        return caixasaida;
    }

    public void setCaixasaida(float caixasaida) {
        this.caixasaida = caixasaida;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.bean;

import java.sql.Date;

/**
 *
 * @author Pituba
 */
public class ReservaDeCaixa {
    private int idreserva, reseridponto;
    private float notas, moedas;
    private Date data;

    public int getIdreserva() {
        return idreserva;
    }

    public void setIdreserva(int idreserva) {
        this.idreserva = idreserva;
    }

    public int getReseriponto() {
        return reseridponto;
    }

    public void setReseridponto(int reseridponto) {
        this.reseridponto = reseridponto;
    }

    public float getNotas() {
        return notas;
    }

    public void setNotas(float notas) {
        this.notas = notas;
    }

    public float getMoedas() {
        return moedas;
    }

    public void setMoedas(float moedas) {
        this.moedas = moedas;
    }  
    
    public Date getData(){
       return data;
    }
    
       public void setData(Date data){
       this.data = data;
    }
    
}

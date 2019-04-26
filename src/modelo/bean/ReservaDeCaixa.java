/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.bean;

/**
 *
 * @author Pituba
 */
public class ReservaDeCaixa {
    int idreserva, reseridmovimento;
    float notas, moedas;

    public int getIdreserva() {
        return idreserva;
    }

    public void setIdreserva(int idreserva) {
        this.idreserva = idreserva;
    }

    public int getReseridmovimento() {
        return reseridmovimento;
    }

    public void setReseridmovimento(int reseridmovimento) {
        this.reseridmovimento = reseridmovimento;
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
    
}

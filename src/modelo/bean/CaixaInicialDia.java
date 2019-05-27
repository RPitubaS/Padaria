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
public class CaixaInicialDia {
    float notas, moedas, total;
    int iddata, idponto;

    public int getIddata() {
        return iddata;
    }

    public void setIddata(int iddata) {
        this.iddata = iddata;
    }

    public int getIdponto() {
        return idponto;
    }

    public void setIdponto(int idponto) {
        this.idponto = idponto;
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
    
    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}

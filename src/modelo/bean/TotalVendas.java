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
public class TotalVendas {
    int iddata;
    float vendasavista, vendasmaisentregas, somamovimento;

    public int getIddata() {
        return iddata;
    }

    public void setIddata(int iddata) {
        this.iddata = iddata;
    }
    
    public float getVendasavista() {
        return vendasavista;
    }

    public void setVendasavista(float vendasavista) {
        this.vendasavista = vendasavista;
    }

    public float getVendasmaisentregas() {
        return vendasmaisentregas;
    }

    public void setVendasmaisentregas(float vendasmaisentregas) {
        this.vendasmaisentregas = vendasmaisentregas;
    }

    public float getSomamovimento() {
        return somamovimento;
    }

    public void setSomamovimento(float somamovimento) {
        this.somamovimento = somamovimento;
    }
    
    
}

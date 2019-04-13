/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.bean;

import java.sql.Time;

/**
 *
 * @author Pituba
 */
public class Movimento {
    private float vendaavista, entrega, recebapraso, cartao, vale, saque, pagamentos, movimento;
    private int idmovimento, movidponto;
    private Time hora;
    
    public int getIdmovimento() {
        return idmovimento;
    }

    public void setIdmovimento(int idmovimento) {
        this.idmovimento = idmovimento;
    }
    
    public int getMovidponto() {
        return movidponto;
    }

    public void setMovidponto(int movidponto) {
        this.movidponto = movidponto;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public float getVendaavista() {
        return vendaavista;
    }

    public void setVendaavista(float vendaavista) {
        this.vendaavista = vendaavista;
    }

    public float getEntrega() {
        return entrega;
    }

    public void setEntrega(float entrega) {
        this.entrega = entrega;
    }

    public float getRecebapraso() {
        return recebapraso;
    }

    public void setRecebapraso(float recebapraso) {
        this.recebapraso = recebapraso;
    }

    public float getCartao() {
        return cartao;
    }

    public void setCartao(float cartao) {
        this.cartao = cartao;
    }
    
    public float getVale() {
        return vale;
    }

    public void setVale(float vale) {
        this.vale = vale;
    }

    public float getSaque() {
        return saque;
    }

    public void setSaque(float saque) {
        this.saque = saque;
    }

    public float getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(float pagamentos) {
        this.pagamentos = pagamentos;
    }

    public float getMovimento() {
        return movimento;
    }

    public void setMovimento(float movimento) {
        this.movimento = movimento;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.bean.ReservaDeCaixa;
import modelo.dao.MovimentoDAO;
import produzconexao.RefazerConexao;
import static view.frmMovimento.ftxtMoedasreserva;
import static view.frmMovimento.ftxtNotasreserva;
import static view.frmMovimento.txtMoedasinicio;
import static view.frmMovimento.txtNotasinicio;

/**
 *
 * @author Pituba
 */
public class SelecionarReservaDeCaixa {
    public void SelecionarReservaDeCaixa(int iddata){
        List<ReservaDeCaixa> selecionareservadecaixa = new ArrayList<>();
        RefazerConexao refc14 = new RefazerConexao();
        refc14.refazerconexao();
        MovimentoDAO movdao33 = new MovimentoDAO();
        selecionareservadecaixa = movdao33.selecionarreservadecaixa(iddata);
               if(!selecionareservadecaixa.isEmpty()){
                  for(ReservaDeCaixa reservadecaixa : selecionareservadecaixa){
                      ftxtNotasreserva.setText(String.format("%,.2f",reservadecaixa.getNotas()));
                      ftxtMoedasreserva.setText(String.format("%,.2f",reservadecaixa.getMoedas()));
                  }
              }
    }
    
    public void SelecionarUltimoReservaDeCaixa(){
        List<ReservaDeCaixa> selecionareservadecaixa = new ArrayList<>();
        RefazerConexao refc14 = new RefazerConexao();
        refc14.refazerconexao();
        MovimentoDAO movdao33 = new MovimentoDAO();
        selecionareservadecaixa = movdao33.selecionarultimoreservadecaixa();
               if(!selecionareservadecaixa.isEmpty()){
                  for(ReservaDeCaixa reservadecaixa : selecionareservadecaixa){
                      ftxtNotasreserva.setText(String.format("%,.2f",reservadecaixa.getNotas()));
                      ftxtMoedasreserva.setText(String.format("%,.2f",reservadecaixa.getMoedas()));
                  }
              }
    }
    
}

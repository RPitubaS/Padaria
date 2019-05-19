/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.bean.ReservaDeCaixa;
import modelo.dao.MovimentoDAO;
import produzconexao.RefazerConexao;
import static view.frmMovimento.ftxtMoedasreserva;
import static view.frmMovimento.ftxtNotasreserva;
import static view.frmMovimento.lblReservadoem;
import static view.frmReservaDeCaixa.lblDatadareserva;
import static view.frmReservaDeCaixa.txtReservaemmoedas;
import static view.frmReservaDeCaixa.txtReservaemnotas;

/**
 *
 * @author Pituba
 */
public class SelecionarReservaDeCaixa {
    
    SimpleDateFormat formatbr = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatbr2 = new SimpleDateFormat("dd.MM.yyyy");
    
    public void SelecionarReservaDeCaixa(int iddata, String hoje){
        List<ReservaDeCaixa> selecionareservadecaixa = new ArrayList<>();
        RefazerConexao refc14 = new RefazerConexao();
        refc14.refazerconexao();
        MovimentoDAO movdao33 = new MovimentoDAO();
        selecionareservadecaixa = movdao33.selecionarreservadecaixa(iddata);
               if(!selecionareservadecaixa.isEmpty()){
                  for(ReservaDeCaixa reservadecaixa : selecionareservadecaixa){
                      if(hoje.equals(formatbr2.format(reservadecaixa.getData()))){
                         ftxtNotasreserva.setText(String.format("%,.2f",reservadecaixa.getNotas()));
                         ftxtMoedasreserva.setText(String.format("%,.2f",reservadecaixa.getMoedas()));
                         lblReservadoem.setText("Reservado em: " + formatbr.format(reservadecaixa.getData()));
                      }
                  }
              }
    }
    
    public void SelecionarUltimoReservaDeCaixa(String hoje){
        List<ReservaDeCaixa> selecionareservadecaixa = new ArrayList<>();
        RefazerConexao refc14 = new RefazerConexao();
        refc14.refazerconexao();
        MovimentoDAO movdao33 = new MovimentoDAO();
        selecionareservadecaixa = movdao33.selecionarultimoreservadecaixa();
               if(!selecionareservadecaixa.isEmpty()){
                  for(ReservaDeCaixa reservadecaixa : selecionareservadecaixa){
                      if(hoje.equals(formatbr2.format(reservadecaixa.getData()))){
                         ftxtNotasreserva.setText(String.format("%,.2f",reservadecaixa.getNotas()));
                         ftxtMoedasreserva.setText(String.format("%,.2f",reservadecaixa.getMoedas()));
                         lblReservadoem.setText("Reservado em: " + formatbr.format(reservadecaixa.getData()));
                      }
                  }
              }
    }
    
    public void SelecionarUltimoCaixaReservado(){
        List<ReservaDeCaixa> selecionareservadecaixa = new ArrayList<>();
        int selecionaridpontoultimocaixareservado = 0;
        RefazerConexao refc14 = new RefazerConexao();
        refc14.refazerconexao();
        MovimentoDAO movdao33 = new MovimentoDAO();
        selecionareservadecaixa = movdao33.selecionarultimoreservadecaixa();
               if(!selecionareservadecaixa.isEmpty()){
                  for(ReservaDeCaixa reservadecaixa : selecionareservadecaixa){
                      txtReservaemnotas.setText(String.format("%,.2f",reservadecaixa.getNotas()));
                      txtReservaemmoedas.setText(String.format("%,.2f",reservadecaixa.getMoedas()));
                      lblDatadareserva.setText("Reservado em: " + formatbr.format(reservadecaixa.getData()));
                  }
              }
    }
    
    public int SelecionaridpontoUltimoCaixaReservado(){
        List<ReservaDeCaixa> selecionareservadecaixa = new ArrayList<>();
        int selecionaridpontoultimocaixareservado = 0;
        RefazerConexao refc14 = new RefazerConexao();
        refc14.refazerconexao();
        MovimentoDAO movdao33 = new MovimentoDAO();
        selecionareservadecaixa = movdao33.selecionarultimoreservadecaixa();
               if(!selecionareservadecaixa.isEmpty()){
                  for(ReservaDeCaixa reservadecaixa : selecionareservadecaixa){
                      selecionaridpontoultimocaixareservado = reservadecaixa.getReseriponto();
                  }
              }
               return selecionaridpontoultimocaixareservado;
    }
    
//    public int selecionaridpontoreservadecaixa (int iddata){
//        
//        int selecionaridpontoreservadecaixa = 0;
//        List<ReservaDeCaixa> selecionareservadecaixa = new ArrayList<>();
//        RefazerConexao refc14 = new RefazerConexao();
//        refc14.refazerconexao();
//        MovimentoDAO movdao33 = new MovimentoDAO();
//        selecionareservadecaixa = movdao33.selecionarreservadecaixa(iddata);
//               if(!selecionareservadecaixa.isEmpty()){
//                  for(ReservaDeCaixa reservadecaixa : selecionareservadecaixa){
//                      selecionaridpontoreservadecaixa = reservadecaixa.getReseriponto();
//                  }
//              }
//               return selecionaridpontoreservadecaixa;
//    }
}

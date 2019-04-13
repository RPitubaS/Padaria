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
public class Datas {
    private int id;
    private Date data;
    
    public int getId(){
       return id;
    }
    
    public void setId(int id){
       this.id = id;
    }
    
    public Date getData(){
       return data;
    }
    
    public void setData(Date data){
       this.data = data;
    }
}

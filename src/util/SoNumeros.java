/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Pituba
 */
public class SoNumeros extends PlainDocument{
    @Override
    public void insertString(int off, String str, AttributeSet a) throws BadLocationException{
           super.insertString(off, str.replaceAll("[^0-9-,-\\.]", ""), a);
    }
}

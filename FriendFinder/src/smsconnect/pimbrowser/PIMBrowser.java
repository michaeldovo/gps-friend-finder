/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smsconnect.pimbrowser;

import java.util.Enumeration;
import javax.microedition.lcdui.List;
import javax.microedition.pim.Contact;
import javax.microedition.pim.ContactList;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMException;
import javax.microedition.pim.PIMItem;

/**
 *
 * @author Chris2u
 */
public class PIMBrowser extends List {

    public PIMBrowser() {
        super ("Kontakt auswählen", EXCLUSIVE);
        new Thread(new FillList()).start();
    }
    
    private class FillList implements Runnable {
        
        public void run() {
            try {
                ContactList addressbook = (ContactList) (PIM.getInstance().openPIMList(
                PIM.CONTACT_LIST, PIM.READ_ONLY));
                Enumeration items = addressbook.items();
                Contact contact;
                while(items.hasMoreElements()) {
                  System.out.println("\n *** new item ***");
                  contact = (Contact)(items.nextElement());
                  int[] fields = contact.getFields();
                  for(int i = 0; i < fields.length; i++) {
                    int fieldIndex = fields[i];
                    System.out.println(" field " + fieldIndex + ": "
                        + addressbook.getFieldLabel(fieldIndex));
                    int dataType = addressbook.getFieldDataType(fieldIndex);
                    System.out.println(" * data type: " + dataType);
                    if(dataType == PIMItem.STRING) {
                      for(int j = 0; j < contact.countValues(fieldIndex); j++) {
                        int attr = contact.getAttributes(fieldIndex, j);
                        System.out.print(" " + j + ". (");
                        System.out.print(addressbook.getAttributeLabel(attr) + "): ");
                        System.out.println(contact.getString(fieldIndex, j));
                      }
                    }
                  }
                }
            } catch (PIMException ex) {
                ex.printStackTrace();
            }
            
        }
        
    }
    
    

}

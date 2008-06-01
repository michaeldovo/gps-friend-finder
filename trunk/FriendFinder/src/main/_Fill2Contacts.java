package main;

import java.util.Enumeration;

import javax.microedition.pim.*;

/**
 * A simple test utility that inserts three contacts in
 * the virtual addressbock if this is empty
 * @author Chris2u
*/
public class _Fill2Contacts extends Thread {

  public void run() {
      if (true) return;
      try {
        ContactList addressbook
            = (ContactList)(PIM.getInstance().openPIMList(
            PIM.CONTACT_LIST, PIM.READ_WRITE));
         // delete all contacts
//         while (addressbook.items().hasMoreElements()) {
//             addressbook.removeContact((Contact) addressbook.items().nextElement());      
//         }
        if (addressbook.items().hasMoreElements()) return;
        Contact contact = null;

        // Each PIMItem — new or found — is associated with
        // a particular PIMList.
        contact = addressbook.createContact();
        if(addressbook.isSupportedField(Contact.FORMATTED_NAME)) {
          contact.addString(Contact.FORMATTED_NAME, Contact.ATTR_NONE,
             "Tine Winkler");
        }
        if(addressbook.isSupportedField(Contact.TEL)) {
          contact.addString(Contact.TEL, Contact.ATTR_HOME,
             "555-HOME-NUMBER");
          contact.addString(Contact.TEL, Contact.ATTR_MOBILE,
             "+4917622382168");
        }
        // Here’s a quick search to see if this contact
        // is already present in the addressbook:
        Enumeration matching = addressbook.items(contact);
        if(matching.hasMoreElements()) {
          System.out.println("found the first contact");
        } else {
          System.out.println("adding the first contact");
          contact.commit();
        }
      
        // Let’s create one more contact:
        contact = addressbook.createContact();
        if(addressbook.isSupportedField(Contact.FORMATTED_NAME)) {
          contact.addString(Contact.FORMATTED_NAME, Contact.ATTR_NONE,
             "Christian Winkler");
        }
        if(addressbook.isSupportedField(Contact.TEL)) {
          contact.addString(Contact.TEL, Contact.ATTR_HOME,
             "5555-HOME-NUMBER");
          contact.addString(Contact.TEL, Contact.ATTR_MOBILE,
             "+4917622281150");
        }
        matching = addressbook.items(contact);
        if(matching.hasMoreElements()) {
          System.out.println("found the second contact");
        } else {
          System.out.println("adding the second contact");
          contact.commit();
        }
            
        // Now print the contents of the addressbook:
        Enumeration items = addressbook.items();
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
      } catch(Exception e) {
        e.printStackTrace();
      }
  }
  
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smsconnect;

import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.pim.Contact;
import javax.microedition.pim.PIMItem;
import javax.microedition.pim.PIMList;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;
import main.Person;
import main.Property;

/**
 *
 * @author Chris2u
 * Singleton
 */
public class SMSRequest {
    private String sessionId;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSessionId() {
        return sessionId;
    }
    private String phoneNumber; // the source or target number
        
        
    public SMSRequest(Contact c,String sessionId) {
        this(sessionId);
        if (c.getPIMList().isSupportedField(c.TEL)
           && c.getPIMList().isSupportedAttribute(c.TEL, c.ATTR_MOBILE) ) {
           phoneNumber = getNumber(c);
           Person.other().setMobilenumber(phoneNumber);
           //phoneNumber = numbers[0];
           System.out.println("Encode request for "+phoneNumber);
        } else throw new IllegalArgumentException("No mobile number found.");
    }
    
    public SMSRequest(String sessionId) {
        this.sessionId = sessionId;
    }

    
    public void setPhoneNumber(String number) {
        phoneNumber = number;
    }

       
    public String send() throws IOException {
        if (phoneNumber == null) {
            throw new IllegalArgumentException("Mobile number wasn't right.");
        }
        String address = "sms://" + phoneNumber + ":" + Property.portNum;
        String message = toString();
        MessageConnection smsconn = null;
        /** Open the message connection. */
        smsconn = (MessageConnection) Connector.open(address);
        TextMessage txtmessage = (TextMessage) smsconn.newMessage(MessageConnection.TEXT_MESSAGE);
        txtmessage.setAddress(address);
        txtmessage.setPayloadText(message);
        smsconn.send(txtmessage);
        smsconn.close();
        System.out.println("SMS sent");
        return message;
    }
    
    public static SMSRequest decode(String request) {
        if (request.startsWith(Property.AppId)) // FriendFinder request
            return new SMSRequest(request.substring(request.lastIndexOf('_')+1));
        else throw new IllegalArgumentException("Request wasn't a friend finder request.");
    }

    private String getNumber(Contact contact) {
        // get corresponding addressbook
        PIMList addressbook = contact.getPIMList();
        // walk all fields
        int[] fields = contact.getFields();
        for(int i = 0; i < fields.length; i++) {
            int fieldIndex = fields[i];
            // look only for telephone-fields
            if (fieldIndex!=contact.TEL) continue;
            System.out.println(" field " + fieldIndex + ": "
                + addressbook.getFieldLabel(fieldIndex));
            int dataType = addressbook.getFieldDataType(fieldIndex);
            System.out.println(" * data type: " + dataType);
            if(dataType == PIMItem.STRING) {
              for(int j = 0; j < contact.countValues(fieldIndex); j++) {
                int attr = contact.getAttributes(fieldIndex, j);
                // look only for mobile-numbers
                if ((attr & contact.ATTR_MOBILE) == attr)
                    return contact.getString(fieldIndex, j);
              }
            }
        }
        throw new IllegalArgumentException("No mobile number found.");
    }

    public String toString() {
        return Property.AppId+Property.VersionId+sessionId;
    }
    
    

}

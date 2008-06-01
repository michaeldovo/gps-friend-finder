package io;

import gps.GPSposition;
import java.io.IOException;
//import java.security.MessageDigest;
import java.util.Random;
import javax.microedition.pim.Contact;
import main.MainMIDlet;
import main.Person;
import smsconnect.SMSRequest;

/**
 * Singleton-Implemenation of the connection between
 * @author Chris2u
 */
public class P2PConnection {
    private static P2PConnection inst;
       
    private String sessionId;
    private SMSRequest request;
    private HTTPConnection conn;
    private boolean connectionEstablished = false;
       
    public static P2PConnection getInstance() {
        if (inst==null) throw new IllegalStateException("The Connection hasn't been initialized.");
        return inst;
    }
    
    /**
     * Initiate a FriendFinder-Guide from Person A
     * @param contact
     * @return
     * @throws java.io.IOException
     */
    public static P2PConnection request(Contact contact) throws IOException {
        return inst = new P2PConnection(contact);
    }
    
    /**
     * Prepare for confirm of FF-Guide received from Person A at Person B
     * @param request
     * @return
     */
    public static P2PConnection establish(SMSRequest request) {
        Person.other().setMobilenumber(request.getPhoneNumber());
        // ask the user for confirmation
        MainMIDlet.getInstance().newFFrequest();
        return inst = new P2PConnection(request.getSessionId());
    }
    
    public void confirm() {
        conn.writeGPS(sessionId);
    }

    public boolean isConnectionEstablished() {
        return connectionEstablished;
    }

    public void setConnectionEstablished(boolean connectionEstablished) {
        this.connectionEstablished = connectionEstablished;
    }
    
 
    public void write(String message) {
        conn.send(Person.me().getPosition().toString(), message);
    }
    
    public void readUpdate() {
        /**
         * [0] GPSString
         * [1] message or null if no message available
         */
        String[] answer = conn.read();
        // TODO: What is returned if the connection is dead or the session lost? Is there an exception or are the values just null?
        if (answer[0]!=null) {
            setConnectionEstablished(true);
            Person.other().setPosition(new GPSposition(answer[0]));
            Person.other().setMessage(answer[1]);
        }
    }

    public Person getMe() {
        return Person.me();
    }

    public Person getOther() {
        return Person.other();
    }

    public String getSessionId() {
        return sessionId;
    }


    
   
    /**
     * See .request()
     * @param contact
     * @throws java.io.IOException
     */
    private P2PConnection(Contact contact) throws IOException {
        this(createSessionId());
        request = new SMSRequest(contact, sessionId);
        String message = request.send();
    }

    /**
     * See .establish()
     * @param sessionId
     */
    private P2PConnection(String sessionId) {
        this.sessionId = sessionId;
        try {
            conn = new HTTPConnection(sessionId);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private static String createSessionId() {
        return new Random().nextLong()+"";
        
        /* We don't need a real Hash, do we?
        
        MessageDigest mdAlgorithm = MessageDigest.getInstance("MD5");
        mdAlgorithm.update(randomKey.getBytes(), 0, randomKey.length());

        byte[] digest = mdAlgorithm.digest();
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < digest.length; i++) {
            plainText = Integer.toHexString(0xFF & digest[i]);

            if (plainText.length() < 2) {
                plainText = "0" + plainText;
            }

            hexString.append(plainText);
        }*/
    }


}

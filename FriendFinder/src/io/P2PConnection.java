package io;

import gps.GPSposition;
import java.io.IOException;
//import java.security.MessageDigest;
import java.util.Random;
import javax.microedition.pim.Contact;
import main.Person;
import smsconnect.SMSRequest;

/**
 * Singleton-Implemenation of the connection between
 * @author Chris2u
 */
public class P2PConnection {
    private static P2PConnection inst;
    public static P2PConnection testConnectionFromOther;
    
    private String sessionId;
    private SMSRequest request;
    private HTTPConnection conn;
       
    public static P2PConnection getInstance() {
        if (inst==null) throw new IllegalStateException("The Connection hasn't been initialized.");
        return inst;
    }
    
    public static P2PConnection request(Contact contact) throws IOException {
        return inst = new P2PConnection(contact);
    }
    
    public static P2PConnection establish(String sessionId) {
        return inst = new P2PConnection(sessionId);
    }
    
    public void update() {
        
    }
    
    public boolean write(String message) {
        conn.send((Person.me().getPosition().toString(), message);
    }
    
    public void read() {
        /**
         * [0] GPSString
         * [1] message or null if no message available
         */
        String[] answer = conn.read();
        Person.other().setPosition(new GPSposition(answer[0]));
        Person.other().setMessage(answer[1]));
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


    
    private P2PConnection() {
    }
    
    private P2PConnection(Contact contact) throws IOException {
        createSessionId();
        request = new SMSRequest(contact, sessionId);
        String message = request.send();
    }

    private P2PConnection(String sessionId) {
        this.sessionId = sessionId;
    }
    
    private void createSessionId() {
        sessionId = new Random().nextInt(Integer.MAX_VALUE)+"";
        
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

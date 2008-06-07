/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import com.sun.kvem.netmon.HttpAgent;
import gps.GPSposition;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.ServerSocketConnection;

/**
 *
 * @author Chris2u
 */
public class Person {
    /**
     * May be useful for extra information on first request and
     * to show your "target" more nicely than with just the mobilenumber
     */
    private String name;
    private String mobilenumber;
    private volatile GPSposition position;
    private volatile String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        if (message!=null && message.length() > 0)
            FriendFinder.getInstance().getMessageTicker().setString("Nachricht von "+getMobilenumber()+": "+message);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mn) {
        if (mn != null) {
            // replace land-codes like +49 with 0
            if (mn.startsWith("+")) mn = "0"+mn.substring(3);
            if (mn.endsWith(":0")) mn = mn.substring(0, mn.length()-2);
        }
        this.mobilenumber = mn;
    }

    public GPSposition getPosition() {
        return position;
    }

    public void setPosition(GPSposition position) {
        this.position = position;
    }
    
    private static Person me;
    private static Person other;

    public static Person me() {
        if (me==null)
            me = new Person();
        return me;
    }
    public static Person other() {
        if (other==null)
            other = new Person();
        return other;
    }
    
    private Person() {
    }
    
}

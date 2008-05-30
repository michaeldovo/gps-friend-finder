/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import gps.GPSposition;

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

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
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

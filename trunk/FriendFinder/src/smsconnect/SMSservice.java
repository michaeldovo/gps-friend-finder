/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smsconnect;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.PushRegistry;
import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.SocketConnection;
import main.MainMIDlet;
import main.Property;

/**
 *
 * @author Chris2u
 */
public class SMSservice {

    public static void register() {
        if (true) return;
        //  MIDlet class name.
        String midletClassName = MainMIDlet.class.getName();
        //  Register a static connection.
        String url = "sms://:"+Property.portNum;
        //  Use an unrestricted filter.
        String filter = "*";

        try {
            // Open the connection.
//            ServerSocketConnection ssc =
//                (ServerSocketConnection)Connector.open(url);
            // Register the connection now so that when this
            // MIDlet exits (is destroyed), the AMS can activate
            // our MIDlet when network activity is detected.
            // The AMS will remember the registered URL
            // even when the MIDlet is not active.
            PushRegistry.registerConnection(url, midletClassName, filter);
            // Now wait for inbound network activity.
//            SocketConnection sc =
//            (SocketConnection)ssc.acceptAndOpen();
            // Read data from inbound connection.
//            InputStream is = sc.openInputStream();
            //  ..... read from the input stream.

            // Here process the inbound data.
            //  .....
        }
        catch(SecurityException e) {
            System.out.println("SecurityException, insufficient permissions");
            e.printStackTrace();
        }
        catch(ClassNotFoundException e) {
            System.out.println("ClassNotFoundException, MIDlet name not found");
            e.printStackTrace();
        }
        catch(IOException e) {
            System.out.println("IOException, possibly port already in use.");
            e.printStackTrace();
        }
    }
}

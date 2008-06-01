/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smsconnect;

import io.P2PConnection;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

/**
 *  Thread of execution responsible of receiving and 
 *  processing messages
 * @author Sun WMA-examples
 */
public class MessageReceiver implements Runnable {

    Thread th = new Thread(this);
    MessageConnection mc; // MessageConnection to handle
    boolean done; // if true, thread must exit
    boolean singlepass; // if true, read and process 1 message only, exit

    /**
     * Constructor for multi-pass MessageProcessor
     * @param mc is the MessageConnection for this message 
     * processor
     */
    public MessageReceiver(MessageConnection mc) {
        this.mc = mc;
        th.start();
    }

    /**
     * Constructor for single-pass MessageProcessor
     * @param mc is the MessageConnection for this message 
     * processor
     * @param singlepass if true this processor must 
     * process only one 
     *  message and exit
     */
    public MessageReceiver(MessageConnection mc, boolean 
      singlepass) {
        this.mc = mc;
        this.singlepass = singlepass;
        th.start();
    }

    /** Notify the message processor to exit */
    public void notifyDone() {
        done = true;
    }

    /** Thread's run method to wait for and process 
     *  received messages.
     */
    public void run() {
        if (singlepass == true)
            processMessage();
        else
            loopForMessages();
    }

    /** Loop for messages until done */
    public void loopForMessages() {
        while (!done)
            processMessage();
    }

    /** processMessage reads and processes a Message */
    public void processMessage() {
        Message msg = null;
        //  Try reading (maybe block for) a message
        try {
            msg = mc.receive();
        }
        catch (Exception e) {
            // Handle reading errors
            System.out.println("processMessage.receive " + e);
        }
        // Process the received message
        if (msg instanceof TextMessage) {
            TextMessage tmsg = (TextMessage)msg;
            System.out.println("RECEIVED SMS WITH: "+tmsg);
            //  Handle the text message...
            SMSRequest r = SMSRequest.decode(tmsg.getPayloadText());
            r.setPhoneNumber(tmsg.getAddress());
            P2PConnection.establish(r);
        }
        else {
            // process received message
            if (msg instanceof BinaryMessage) {
                throw new IllegalArgumentException("Application received a binary sms message.");
            }
            else {
                //  Ignore
            }
        }
    }

}
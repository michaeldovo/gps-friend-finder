package io;

/* TODO Hier arbeitet Boris, oder? */

import gps.GPScalculations;
import main.Property;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;


/**
 * create BlueTooth connection to GPS receiver and read data
 *
 * @author Alireza Sahami
 * Pervasive Computing Group, Univeristy of Duisburg-Essen
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

public class BTConnection extends Thread implements DiscoveryListener   {
    
    private LocalDevice myDevice=null;
    private String sensorAdr=null;
    private DiscoveryAgent discover_agent=null;
    private StreamConnection stream_connection=null;
    private DataInputStream input_stream=null;
    private OutputStream output_stream=null;
    
    private Listener listener=null;
    
    /** Creates a new instance of BTConnectionManager */
    public BTConnection(Listener l) {
        try {
            myDevice= LocalDevice.getLocalDevice();
            this.listener=l;
        } catch (BluetoothStateException ex) {
            ex.printStackTrace();
        }
    }
    
    public void startConnection(){
        this.start();
    }
    
    public void run(){
        try {
//            listener.printMsg("Searching...");
            discover_agent=LocalDevice.getLocalDevice().getDiscoveryAgent();
            discover_agent.startInquiry(DiscoveryAgent.GIAC, this);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            discover_agent.cancelInquiry(this);
            
            if (sensorAdr!=null){
                /* device is found*/
//                listener.printMsg("Device is found");
                
                //open the input and output stream
                connectToDevice();
//                listener.printMsg("Connection established!");
            }
            GPScalculations.getPosition();
        } catch (BluetoothStateException ex) {
            ex.printStackTrace();
        }
    }
    
    public String readGPSData(){

        String strData="";
        boolean readOn=true;
        
        int input;

//        listener.printMsg("Start reading data");
        while (readOn) {
            try {
                if(input_stream==null){
                    return "0";
                }
                while ((input = input_stream.read()) != 13) {
                    strData += (char) input;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if(strData.indexOf("GPGGA")>=0){
                readOn=false;
            }
        }

        return strData;
    }
    
    public void connectToDevice(){
        
        String URL = "btspp://"+sensorAdr+":1";
        System.out.println("URL: "+URL);
        
        try{
            //connect to the device and open input output stream connection
            stream_connection=(StreamConnection)Connector.open(URL,Connector.READ_WRITE);
            
            input_stream= stream_connection.openDataInputStream();
            
            output_stream= stream_connection.openOutputStream();
            
            
        }catch(Exception e){
            
        }
    }
    
    public DataInputStream get_inputstream(){
        return input_stream;
    }
    
    public OutputStream get_ouputstream(){
        return output_stream;
    }
    
    public void closeInputStream(){
        try {
            
            input_stream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void closeOutputStream(){
        try {
            output_stream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void closeConnection(){
        
        closeInputStream();
        closeOutputStream();
        try {
            stream_connection.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
        
        try {
            if(remoteDevice.getFriendlyName(false).equals(Property.btName)){
                this.sensorAdr = remoteDevice.getBluetoothAddress();
                
//                listener.printMsg("Device is found!");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void servicesDiscovered(int i, ServiceRecord[] serviceRecord) {
    }
    
    public void serviceSearchCompleted(int i, int i0) {
    }
    
    public void inquiryCompleted(int i) {
    }
}

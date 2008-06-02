package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.rms.RecordStore;
import main.Person;
import java.util.Vector;
import javax.microedition.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import java.io.*;

/**
 * create HTTP connection to upload data to server
 *
 * @author Alireza Sahami
 * Pervasive Computing Group, Univeristy of Duisburg-Essen
 * 
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

public class HTTPConnection implements Listener{
    
    private int mode;
    private static final String CrLf="\r\n";
    private HttpConnection hc= null;
    //private String url = null;
    //private byte[] buffer;
    private OutputStream out=null;
    private InputStream in=null;
    private Listener httpl=null;
    /**
     * The sessionId that represents this connection on the server
     */
    private String sessionId;
    
    /** Creates a new instance of HTTPConnection */
    
    public HTTPConnection(String sessionId) throws IOException {
        
        this.sessionId = sessionId;
        httpl = this;
        // TODO establishHTTP(url);
    }
    
    
    public void setDataToDB(String type, String content){
   
        HttpConnection hc = null;
        InputStream in = null;
        OutputStream out = null;
        
        try{
            
            String url = "http://web94.trinity-media.de/friendfinder/set_"+type+".php?sid="+this.sessionId+"&my_id="+ Person.me().getMobilenumber()+"content="+content;
            hc = (HttpConnection)Connector.open(url);
            hc.setRequestMethod(HttpConnection.POST);
            hc.setRequestProperty("Content-Type", "application/x-ww-form-urlencoded");
            //hc.setRequestProperty("Content-Length", Integer.toString(message.length()));
            out= hc.openOutputStream();
            //out.write(message.getBytes());
            in = hc.openInputStream();
            int length = 200;
            byte[] data = new byte[length];
            in.read(data);
            String response = new String(data);
            StringItem stringItem = new StringItem(null, response);
            
            
            String[] splittResponse = split(response, "§§§§!§§§§",2);
            System.out.println("URL: "+url+" | Rueckgabe: "+splittResponse[0]);
        }
        catch(IOException ioe){
            StringItem stringItem = new StringItem(null, ioe.toString());
             
        }
        finally{
            try{
                if(out != null)  out.close();
                if(in != null)   in.close();
                if(hc != null)   hc.close();
            }
            catch (IOException ioe){}
            
        }
    }
    
    public String[] getDataFromDB(String type, String content) throws IOException{
   
        HttpConnection hc = null;
        InputStream in = null;
        OutputStream out = null;
        String []receivedData = new String[2];
        
        try{
            String []url = new String[2];
            url[0] = "http://web94.trinity-media.de/friendfinder/get_gps.php?sid="+this.sessionId+"&friend="+ Person.other().getMobilenumber();
            url[1] = "http://web94.trinity-media.de/friendfinder/get_mail.php?sid="+this.sessionId+"&friend="+ Person.other().getMobilenumber();        
            for( int i=0; i<=1; i++)
            {
                hc = (HttpConnection)Connector.open(url[i]);
                hc.setRequestMethod(HttpConnection.POST);
                hc.setRequestProperty("Content-Type", "application/x-ww-form-urlencoded");
                //hc.setRequestProperty("Content-Length", Integer.toString(message.length()));
                out= hc.openOutputStream();
                //out.write(message.getBytes());
                in = hc.openInputStream();
                int length = 200;
                byte[] data = new byte[length];
                in.read(data);
                String response = new String(data);
                StringItem stringItem = new StringItem(null, response);


                String[] splittResponse = split(response, "§§§§!§§§§",2);
                System.out.println("URL: "+url[i]+" | Rueckgabe: "+splittResponse[0]);
                if(i == 0){
                    System.out.println("Timestamp: "+splittResponse[1]);
                    long nachrichtenAlter = (System.currentTimeMillis() / 1000) - Integer.valueOf(splittResponse[0]).intValue();
                    if( nachrichtenAlter > 240 )  throw new IOException("Connection lost");  
                }
                receivedData[i] = splittResponse[0];
            }
        }
        catch(IOException ioe){
            StringItem stringItem = new StringItem(null, ioe.toString());
             
        }
        finally{
            try{
                if(out != null)  out.close();
                if(in != null)   in.close();
                if(hc != null)   hc.close();
            }
            catch (IOException ioe){}
            
        }
        return receivedData;
    }
    
    
    
    
    
    /*@ param buffer
     *              byte[] should upload via HTTPConnection
     *@ param mode
     *              int 0 or 1
     *              0: upload string data
     *              1: upload an image
     */
    
    public boolean sendPacket(byte[] buffer ,int mode){
        
        // upload data
        if(mode==0){
            try{
                httpl.printMsg("Connect to server");
                String postData = "";
                
                // create header
                
                String message1 = "";
                message1 += "-----------------------------4664151417711" + CrLf;
                // message1 += "Content-Disposition: form-data; name=\"uploadedfile\"; filename=\"1.jpg\"" + CrLf;
                message1 += "Content-Type: application/x-www-form-urlencoded" + CrLf;
                message1 += CrLf;
                
                
                String message2 = "";
                //message2 += CrLf + "-----------------------------4664151417711--" + CrLf;
                
                // hc.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------4664151417711");
                hc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; boundary=---------------------------4664151417711");
                hc.setRequestProperty("Content-Length", String.valueOf((message1.length() + message2.length() + buffer.length)));
                
                httpl.printMsg("Sending data");
                out = hc.openOutputStream();
                

                out.write(message1.getBytes());
                
                int index = 0;
                int size = 1024;
                int i=0;
                do{
                    
                    if((index+size)>buffer.length){
                        size = buffer.length - index;
                    }
                    
                    out.write(buffer, index, size);
                    
                    index+=size;
                    //  i++;
                }while(index<buffer.length);
                //httpl.httpMsg("repated:"+i);
                out.write(message2.getBytes());
                out.flush();
                out.close();
                
                //read sserver response
                in = hc.openInputStream();
                String serverResponse="";
                
                char buff = 512;
                int len;
                byte []data = new byte[buff];
                
                do{
                    System.out.println("READ");
                    len = in.read(data);
                    
                    if(len > 0){
                        serverResponse=(new String(data, 0, len));
                    }
                }while(len>0);
                //String serverResponse= new String(data,0,data.length);
                
                httpl.printMsg("Server Response: "+ serverResponse);
                
                System.out.println("Upload is done.");
                
                in.close();
                hc.close();
                return true;
            }catch(Exception e){
                System.out.println("http!");
                e.printStackTrace();
                
            }
        }
        
        // upload image
        else if (mode==1){
            
            String fileName="";
            try{
                
                
                httpl.printMsg("Connect to server");
                String postData = "";
                
                String message1 = "";
                message1 += "-----------------------------4664151417711" + CrLf;
                message1 += "Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""+fileName+"\"" + CrLf;
                message1 += "Content-Type: image/png" + CrLf;
                message1 += CrLf;
                
                
                
                String message2 = "";
                //message2 += CrLf + "-----------------------------4664151417711--" + CrLf;
                
                hc.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------4664151417711");
                //hc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; boundary=---------------------------4664151417711");
                hc.setRequestProperty("Content-Length", String.valueOf((message1.length() + message2.length() + buffer.length)));
                
                httpl.printMsg("Sending data");
                out = hc.openOutputStream();
                
                out.write(message1.getBytes());
                
           
                int index = 0;
                int size = 1024;
                int i=0;
                do{
                    
                    if((index+size)>buffer.length){
                        size = buffer.length - index;
                    }
                    
                    out.write(buffer, index, size);
                    
                    index+=size;
                    //  i++;
                }while(index<buffer.length);

                out.write(message2.getBytes());
                out.flush();
                out.close();
                
                // read server response
                in = hc.openInputStream();
                String serverResponse="";
                
                char buff = 512;
                int len;
                byte []data = new byte[buff];
                
                do{
                    System.out.println("READ");
                    len = in.read(data);
                    
                    if(len > 0){
                        serverResponse=(new String(data, 0, len));
                    }
                }while(len>0);
                //String serverResponse= new String(data,0,data.length);
                
                httpl.printMsg("Server Response: "+ serverResponse);
                
                System.out.println("Upload is done.");
                in.close();
                hc.close();
                return true;
            }catch(Exception e){
                System.out.println("http!");
                e.printStackTrace();
            }
        }
        return false;
    }
    
    /*
     *@param url
     *          String include the php link to make HTTP Connection
     */
    
    public void establishHTTP(String url) throws IOException
    {    
        hc = (HttpConnection)Connector.open(url);
        hc.setRequestMethod(HttpConnection.POST);
        establishHTTP(url);   
    }
    
private static RecordStore recordstore = null;
    
   
    private static void openRecStore(String RecordName)
	{
		try
		{
			// The second parameter indicates that the record store
			// should be created if it does not exist
			recordstore = RecordStore.openRecordStore(RecordName, true );
		}
		catch (Exception e)
		{
			db(e.toString());
		}
	}
    
    private static void writeMessageToRecord(String inhalt){
		
		boolean write = true;
		try {
			
                        emptyRecStore("MessageRecordStore");
                        
                        openRecStore("MessageRecordStore");
                        
                        byte[] rec = inhalt.getBytes();
                        
                        recordstore.addRecord(rec, 0, rec.length);//WRITE SCORE
                     
                        closeRecStore();
                        System.out.println("Record geschrieben: " +inhalt);
			
		}catch (Exception e){
			db(e.toString());
		}
    }
    
    private static void writeGPSToRecord(String inhalt){
		
		boolean write = true;
		try {
			
                        emptyRecStore("GPSRecordStore");
                        
                        openRecStore("GPSRecordStore");
                        
                        byte[] rec = inhalt.getBytes();
                        
                        recordstore.addRecord(rec, 0, rec.length);//WRITE SCORE
                     
                        closeRecStore();
                        System.out.println("Record geschrieben: " +inhalt);
			
		}catch (Exception e){
			db(e.toString());
		}
    }
    
    public static String readMessage(){
        /**
         *  Record wird bei jedem schreiben gelöscht und 
         *  und neue Nachricht gespeichet    
         *  => immer nur die letzte NAchricht gesaved    
         */
       
        String nachricht = "";
        try {
                openRecStore("MessageRecordStore");

                int len;
                for (int i = 1 ; i <= recordstore.getNumRecords() ; i++ )
                {
                        byte[] recData = new byte[recordstore.getRecordSize(i)];
                        len = recordstore.getRecord(i, recData, 0);
                        
                        nachricht = new String(recData, 0, len);
                }
                closeRecStore();
        }
        catch (Exception e)
        {
                db(e.toString());
        }
        // TODO alle nachrichten loeschen
       return nachricht;
    }
    
    private static void closeRecStore(){
		try //CLOSE RECORESTORE
		{
			recordstore.closeRecordStore();
		}
		catch (Exception e)
		{
			db(e.toString());
		}
    }
    
    public static void emptyRecStore(String RecordName){
		if (RecordStore.listRecordStores() != null){
			openRecStore("GPSRecordStore");
			closeRecStore();
			try {
				RecordStore.deleteRecordStore(RecordName);
			}
			catch (Exception e)
			{
				db(e.toString());
			}
		}
    }
    
    private static void db(String str)
    {
		System.err.println("EXCEPTION: " + str);
    }
    
    public void writeMessage(String inhalt){
        writeMessageToRecord(inhalt);
       
    }
    
    public void writeGPS(String inhalt){
        writeGPSToRecord(inhalt);
    }
    
    public String readGPS(){
        String standort = "";
        try {
                openRecStore("GPSRecordStore");

                int len;
                for (int i = 1 ; i <= recordstore.getNumRecords() ; i++ )
                {
                        byte[] recData = new byte[recordstore.getRecordSize(i)];
                        len = recordstore.getRecord(i, recData, 0);
                        
                        standort = new String(recData, 0, len);
                }
                closeRecStore();
        }
        catch (Exception e)
        {
                db(e.toString());
        }
        
        return standort;
    }
    
    public void send (String GPSPosition, String message){
        setDataToDB("gps", GPSPosition);
        setDataToDB("mail", message);
        
        //writeGPSToRecord(GPSPosition);
        //writeMessageToRecord(message);
    }
    
    public String[] read() throws IOException{
        String []daten = new String[2];
        /*
        daten[0] = readGPS();
        daten[1] = readMessage();
        
        daten[0] = Person.other().getPosition().toString();
        System.out.println("Friend: "+daten[0]);
        System.out.println("Friends Message: "+daten[1]);
        */
        if(daten[0] == "")  throw new IOException("Waiting for Connection");
        if(daten[1] == "")  daten[1] = null;
                
        return daten;
    }

    public void printMsg(String s) {
        System.out.println(s);
    }    
        
   public static String[] split(String splitStr, String delimiter, int limit) {
// some input validation / short-circuiting
        if (delimiter == null || delimiter.length() == 0) {
            return new String[] { splitStr };
        } else if (splitStr == null) {
            return new String[0];
        }

// enabling switches based on the 'limit' parameter
        boolean arrayCanHaveAnyLength = false;
        int maximumSplits = Integer.MAX_VALUE;
        boolean dropTailingDelimiters = true;
        if (limit < 0) {
            arrayCanHaveAnyLength = true;
            maximumSplits = Integer.MAX_VALUE;
            dropTailingDelimiters = false;
        } else if (limit > 0) {
            arrayCanHaveAnyLength = false;
            maximumSplits = limit - 1;
            dropTailingDelimiters = false;
        }

        StringBuffer token = new StringBuffer();
        Vector tokens = new Vector();
        char[] chars = splitStr.toCharArray();
        boolean lastWasDelimiter = false;
        int splitCounter = 0;
        for (int i = 0; i < chars.length; i++) {
// check for a delimiter
            if (i + delimiter.length() <= chars.length && splitCounter < maximumSplits) {
                String candidate = new java.lang.String(chars, i, delimiter.length());
                if (candidate.equals(delimiter)) {
                    tokens.addElement(token.toString());
                    token.setLength(0);

                    lastWasDelimiter = true;
                    splitCounter++;
                    i = i + delimiter.length() - 1;

                    continue; // continue the for-loop
                }
            }

// this character does not start a delimiter -> append to the token
            token.append(chars[i]);
            lastWasDelimiter = false;
        }

// don't forget the "tail"...
        if (token.length() > 0 || (lastWasDelimiter && !dropTailingDelimiters)) {
            tokens.addElement(token.toString());
        }

// convert the vector into an array
        String[] splitArray = new String[tokens.size()];
        for (int i = 0; i < splitArray.length; i++) {
            splitArray[i] = (String) tokens.elementAt(i);
        }

        return splitArray;
    }
    
}

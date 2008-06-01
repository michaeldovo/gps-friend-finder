package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.rms.RecordStore;

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

public class HTTPConnection{
    
    private int mode;
    private static final String CrLf="\r\n";
    private HttpConnection hc= null;
    private String url = null;
    //private byte[] buffer;
    private OutputStream out=null;
    private InputStream in=null;
    private Listener httpl=null;
    
    /** Creates a new instance of HTTPConnection */
    
    public HTTPConnection(Listener h) {
        
        this.httpl=h;
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
    
    public void establishHTTP(String url){
        try {
            
            
            hc = (HttpConnection)Connector.open(url);
            hc.setRequestMethod(HttpConnection.POST);
        } catch (IOException ex) {
            try {
                //ex.printStackTrace();
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                //ex.printStackTrace();
            }
            establishHTTP(url);
        }
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
        writeGPSToRecord(GPSPosition);
        writeMessageToRecord(message);
    }
    
    public String[] read(){
        String []daten = new String[2];
        daten[0] = readGPS();
        daten[1] = readMessage();
        
        daten[0] = "TESTWERT"; /* HIER einen Dummywert fuer den Friend eintragen*/
        System.out.println("Friend: "+daten[0]);
        System.out.println("Friends Message: "+daten[1]);
        return daten;
    }
}

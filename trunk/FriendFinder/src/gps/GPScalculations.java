package gps;

import io.BTConnection;
import io.Listener;

/**
 *
 * @author CoolCat
 */
public class GPScalculations {
    
    private GPSposition currentpos;
    private GPSposition oldpos;
    private GPSposition targetpos;
    private TestData test=new TestData();
    private String btString;
    BTConnection bt;
    private Listener listen;
    
    //Initialisierung von targetpos und currentpos
    public void start(){  
        bt=new BTConnection(listen);
        bt.start();
        bt.run();       
        test.start();
        if(targetpos==null){
            setTargetPosition();
        }
        if(currentpos==null){
            setCurrentPosition();
        }
    }
    
    //Überprüfung ob Ziel erreicht
    public boolean targetreached(){
        if(currentpos.getLatitude()==targetpos.getLatitude() && currentpos.getLongitude()==targetpos.getLongitude()){
            return true;
        }
        else return false;
    }
    
    public GPSposition getPosition() {
        return currentpos;
    }

    //Ausgabe der Richtung zum Ziel
    //Gemessen in(0-360) Grad im Uhrzeigersinn 
    //von der Laufrichtung
    public double getDirection() {
        oldpos=currentpos;
        setCurrentPosition();
        double targetDirection=calcTargetDir();
        double moveDirection=calcMoveDir();
        double direction=moveDirection-targetDirection;
        if(direction<0){
            direction=direction+360;
        }
        return direction;
    }
    /**
     * 
     * @return Distance in km
     */
    public double getDistance() {
        return calcDistance();
    }
    
    private void setCurrentPosition(){
        currentpos=getPosGPS();
    }
    
    private void setTargetPosition(){
        targetpos=getTargetPos();
    }
    
    private GPSposition getPosGPS(){
        GPSposition posGPS=new GPSposition();
        double latitude;
        double longitude;
        double grad;
        double min;
        btString=bt.readGPSData();
        int count=btString.indexOf(",");
        count=btString.indexOf(",", count+1);
        int count2=btString.indexOf(",", count+1);
        latitude=Double.valueOf(btString.substring(count+1, count2).trim()).doubleValue();
        grad=Math.floor(latitude/100);
        min=latitude-grad*100;
        latitude=grad+min*60;
        if(btString.substring(count2+1, count2+2).equalsIgnoreCase("S")){
            latitude=-1*latitude;
        }
        count=btString.indexOf(",", count2+1);
        count2=btString.indexOf(",", count+1);
        longitude=Double.valueOf(btString.substring(count+1, count2).trim()).doubleValue();
        grad=Math.floor(longitude/100);
        min=longitude-grad*100;
        longitude=grad+min/60;
        if(btString.substring(count2+1, count2+2).equalsIgnoreCase("W")){
            longitude=-1*longitude;
        }
        posGPS.setLatitude(latitude);
        posGPS.setLongitude(longitude);
        return posGPS;
    }
    
    //Noch zu Implementieren
    //Daten vom Ziel vom Server
    private GPSposition getTargetPos(){
        return test.getTargetpos();
    }
    
    //Berechnung der Distanz
    private double calcDistance(){
        double distance;
        distance= mMath.acos(Math.sin(currentpos.getLatitude())*Math.sin(targetpos.getLatitude())+Math.cos(currentpos.getLatitude())*Math.cos(targetpos.getLatitude())*Math.cos(currentpos.getLongitude()-targetpos.getLongitude()));
        distance= distance*6378.137;
        return distance;
    }
    
    //Berechnung der Richtung zum Ziel
    private double calcTargetDir(){
        double longDist;
        double dist;
        double dir;
        longDist=mMath.acos(Math.sin(currentpos.getLatitude())*Math.sin(currentpos.getLatitude())+Math.cos(currentpos.getLatitude())*Math.cos(currentpos.getLatitude())*Math.cos(currentpos.getLongitude()-targetpos.getLongitude()));
        longDist=longDist*6378.137;
        dist=calcDistance();
        longDist=longDist/dist;
        dir=Math.cos(longDist);
        
        //Überprüfung auf Quadrant zur Korrektur
        if(targetpos.getLongitude()>=currentpos.getLongitude()){
            if(targetpos.getLatitude()<currentpos.getLatitude()){
                dir=360-dir; //4. Quadrant
            }
        }
        else if(targetpos.getLatitude()>=currentpos.getLatitude()){
            dir=180-dir; //2. Quadrant
        }
        else dir=dir+180; //3. Quadrant
        
        return dir;
    }
    
    //Berechnung der Laufrichtung
    private double calcMoveDir(){
        double longDist;
        double dist;
        double dir;
        longDist=mMath.acos(Math.sin(currentpos.getLatitude())*Math.sin(currentpos.getLatitude())+Math.cos(currentpos.getLatitude())*Math.cos(currentpos.getLatitude())*Math.cos(currentpos.getLongitude()-oldpos.getLongitude()));
        longDist=longDist*6378.137;
        dist= mMath.acos(Math.sin(currentpos.getLatitude())*Math.sin(oldpos.getLatitude())+Math.cos(currentpos.getLatitude())*Math.cos(oldpos.getLatitude())*Math.cos(currentpos.getLongitude()-oldpos.getLongitude()));
        longDist=longDist/dist;
        dir=Math.cos(longDist);
        
        //Überprüfung auf Quadrant zur Korrektur
        if(currentpos.getLongitude()>=oldpos.getLongitude()){
            if(currentpos.getLatitude()<oldpos.getLatitude()){
                dir=360-dir; //4. Quadrant
            }
        }
        else if(currentpos.getLatitude()>=oldpos.getLatitude()){
            dir=180-dir; //2.Quadrant
        }
        else dir=dir+180; //3.Quadrant
        
        return dir;
    }

}

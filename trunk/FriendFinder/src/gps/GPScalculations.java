package gps;

import io.BTConnection;
import io.Listener;
import main.Person;

/**
 *
 * @author CoolCat
 */
public class GPScalculations {
    
    private static GPSposition oldpos;
    private static TestData test;
    private static String btString;
    private static BTConnection bt;
    private static Listener listen;
    
    //Initialisierung von targetpos und currentpos
    public static void start(){  
        bt=new BTConnection(listen);
        bt.start();
        test = new TestData();
        test.startTest();
        if(Person.me().getPosition()==null){
            setCurrentPosition();
        }
    }
    
    //Überprüfung ob Ziel erreicht
    public boolean targetreached(){
        if(Person.me().getPosition().getLatitude()==Person.other().getPosition().getLatitude() && Person.me().getPosition().getLongitude()==Person.other().getPosition().getLongitude()){
            return true;
        }
        else return false;
    }
    
    public GPSposition getPosition() {
        setCurrentPosition();
        return Person.me().getPosition();
    }

   /**
    * Ausgabe der Richtung zum Ziel
    * Gemessen in(0-360) Grad im Uhrzeigersinn 
    * von der Laufrichtung
    */
    public static short getDirection() {
        if(oldpos.getLatitude()!=Person.me().getPosition().getLatitude()&&oldpos.getLongitude()!=Person.me().getPosition().getLongitude()){
            oldpos=Person.me().getPosition();
            setCurrentPosition();   
        }
        double targetDirection=calcTargetDir();
        double moveDirection=calcMoveDir();
        double direction=moveDirection-targetDirection;
        if(direction<0){
            direction=direction+360;
        }
        return (short) Math.ceil(direction);
    }
    /**
     * 
     * @return Distance in km
     */
    public static double getDistance() {
        return calcDistance();
    }
    
    private static void setCurrentPosition(){
        Person.me().setPosition(getPosGPS());
    }
    
    private static GPSposition getPosGPS(){
        GPSposition posGPS=new GPSposition();
//        double latitude;
//        double longitude;
//        double grad;
//        double min;
//        btString=bt.readGPSData();
//        int count=btString.indexOf(",");
//        count=btString.indexOf(",", count+1);
//        int count2=btString.indexOf(",", count+1);
//        latitude=Double.valueOf(btString.substring(count+1, count2).trim()).doubleValue();
//        grad=Math.floor(latitude/100);
//        min=latitude-grad*100;
//        latitude=grad+min*60;
//        if(btString.substring(count2+1, count2+2).equalsIgnoreCase("S")){
//            latitude=-1*latitude;
//        }
//        count=btString.indexOf(",", count2+1);
//        count2=btString.indexOf(",", count+1);
//        longitude=Double.valueOf(btString.substring(count+1, count2).trim()).doubleValue();
//        grad=Math.floor(longitude/100);
//        min=longitude-grad*100;
//        longitude=grad+min/60;
//        if(btString.substring(count2+1, count2+2).equalsIgnoreCase("W")){
//            longitude=-1*longitude;
//        }
//        posGPS.setLatitude(latitude);
//        posGPS.setLongitude(longitude);
        
        //Testwerte
        posGPS=test.getOwnpos();
        return posGPS;
    }
    
    /**
     * Berechnung der Distanz
     * @return TODO: [???;???]
     */
    private static double calcDistance(){
        double distance;
        distance= mMath.acos(Math.sin(Person.me().getPosition().getLatitude())*Math.sin(Person.other().getPosition().getLatitude())+Math.cos(Person.me().getPosition().getLatitude())*Math.cos(Person.other().getPosition().getLatitude())*Math.cos(Person.me().getPosition().getLongitude()-Person.other().getPosition().getLongitude()));
        distance= distance*6378.137;
        return distance;
    }
    
    //Berechnung der Richtung zum Ziel
    private static double calcTargetDir(){
        double longDist;
        double dist;
        double dir;
        longDist=mMath.acos(Math.sin(Person.me().getPosition().getLatitude())*Math.sin(Person.me().getPosition().getLatitude())+Math.cos(Person.me().getPosition().getLatitude())*Math.cos(Person.me().getPosition().getLatitude())*Math.cos(Person.me().getPosition().getLongitude()-Person.other().getPosition().getLongitude()));
        longDist=longDist*6378.137;
        dist=calcDistance();
        longDist=longDist/dist;
        dir=Math.cos(longDist);
        
        //Überprüfung auf Quadrant zur Korrektur
        if(Person.other().getPosition().getLongitude()>=Person.me().getPosition().getLongitude()){
            if(Person.other().getPosition().getLatitude()<Person.me().getPosition().getLatitude()){
                dir=360-dir; //4. Quadrant
            }
        }
        else if(Person.other().getPosition().getLatitude()>=Person.me().getPosition().getLatitude()){
            dir=180-dir; //2. Quadrant
        }
        else dir=dir+180; //3. Quadrant
        
        return dir;
    }
    
    //Berechnung der Laufrichtung
    private static double calcMoveDir(){
        double longDist;
        double dist;
        double dir;
        longDist=mMath.acos(Math.sin(Person.me().getPosition().getLatitude())*Math.sin(Person.me().getPosition().getLatitude())+Math.cos(Person.me().getPosition().getLatitude())*Math.cos(Person.me().getPosition().getLatitude())*Math.cos(Person.me().getPosition().getLongitude()-oldpos.getLongitude()));
        longDist=longDist*6378.137;
        dist= mMath.acos(Math.sin(Person.me().getPosition().getLatitude())*Math.sin(oldpos.getLatitude())+Math.cos(Person.me().getPosition().getLatitude())*Math.cos(oldpos.getLatitude())*Math.cos(Person.me().getPosition().getLongitude()-oldpos.getLongitude()));
        longDist=longDist/dist;
        dir=Math.cos(longDist);
        
        //Überprüfung auf Quadrant zur Korrektur
        if(Person.me().getPosition().getLongitude()>=oldpos.getLongitude()){
            if(Person.me().getPosition().getLatitude()<oldpos.getLatitude()){
                dir=360-dir; //4. Quadrant
            }
        }
        else if(Person.me().getPosition().getLatitude()>=oldpos.getLatitude()){
            dir=180-dir; //2.Quadrant
        }
        else dir=dir+180; //3.Quadrant
        
        return dir;
    }

}

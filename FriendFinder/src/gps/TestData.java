package gps;

/**
 *
 * @author CoolCat
 */
public class TestData {

    private int counter=0;
    private GPSposition[] ownpos=new GPSposition[10];
    private GPSposition targetpos=new GPSposition();

    public GPSposition getOwnpos() {
        counter++;
        return ownpos[counter-1];
    }

    public GPSposition getTargetpos() {
        return targetpos;
    }
;
    
    public void start(){
        targetpos.setLatitude(50.1234);
        targetpos.setLongitude(13.2345);
        ownpos[0].setLatitude(51.4567);
        ownpos[0].setLongitude(14.7654);
        ownpos[1].setLatitude(51.3086);
        ownpos[1].setLongitude(14.5953);
        ownpos[2].setLatitude(51.1604);
        ownpos[2].setLongitude(14.4252);
        ownpos[3].setLatitude(51.0123);
        ownpos[3].setLongitude(14.2551);
        ownpos[4].setLatitude(50.8641);
        ownpos[4].setLongitude(14.0850);
        ownpos[5].setLatitude(50.7160);
        ownpos[5].setLongitude(13.9149);
        ownpos[6].setLatitude(50.5678);
        ownpos[6].setLongitude(13.7448);
        ownpos[7].setLatitude(50.4197);
        ownpos[7].setLongitude(13.5747);
        ownpos[8].setLatitude(50.2715);
        ownpos[8].setLongitude(13.4046);
        ownpos[9].setLatitude(50.1234);
        ownpos[9].setLongitude(13.2345);
        
    }
    
}

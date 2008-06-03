package gps;

/**
 * 
 * @author CoolCat
 */
public class TestData {

    private int counter=0;
    private GPSposition[] ownpos=new GPSposition[20];
    private GPSposition targetpos=new GPSposition();

    
    public GPSposition getOwnpos() {
        if (counter>ownpos.length) 
            counter = 0;
        else
            counter++;
        return ownpos[counter-1];
    }

    public GPSposition getTargetpos() {
        return targetpos;
    }
;
    
    public TestData () {
        targetpos.setLatitude(50.1234);
        targetpos.setLongitude(13.2345);
        ownpos[0].setLatitude(51.4567);
        ownpos[0].setLongitude(14.7654);
        ownpos[1].setLatitude(51.4086);
        ownpos[1].setLongitude(14.5953);
        ownpos[2].setLatitude(51.4604);
        ownpos[2].setLongitude(14.5252);
        ownpos[3].setLatitude(51.4123);
        ownpos[3].setLongitude(14.4551);
        ownpos[4].setLatitude(51.3641);
        ownpos[4].setLongitude(14.3850);
        ownpos[5].setLatitude(51.2160);
        ownpos[5].setLongitude(14.3349);
        ownpos[6].setLatitude(51.2178);
        ownpos[6].setLongitude(14.1448);
        ownpos[7].setLatitude(51.2197);
        ownpos[7].setLongitude(14.0747);
        ownpos[8].setLatitude(51.1715);
        ownpos[8].setLongitude(14.0046);
        ownpos[9].setLatitude(51.1234);
        ownpos[9].setLongitude(14.1005);
        ownpos[10].setLatitude(51.0567);
        ownpos[10].setLongitude(14.0604);
        ownpos[11].setLatitude(50.9886);
        ownpos[11].setLongitude(14.0053);
        ownpos[12].setLatitude(50.9604);
        ownpos[12].setLongitude(13.9952);
        ownpos[13].setLatitude(50.8123);
        ownpos[13].setLongitude(13.7551);
        ownpos[14].setLatitude(50.8641);
        ownpos[14].setLongitude(13.7850);
        ownpos[15].setLatitude(50.7160);
        ownpos[15].setLongitude(13.8149);
        ownpos[16].setLatitude(50.4678);
        ownpos[16].setLongitude(13.7448);
        ownpos[17].setLatitude(50.5197);
        ownpos[17].setLongitude(13.5747);
        ownpos[18].setLatitude(50.3715);
        ownpos[18].setLongitude(13.3046);
        ownpos[19].setLatitude(50.1234);
        ownpos[19].setLongitude(13.2345);
    }
    
}

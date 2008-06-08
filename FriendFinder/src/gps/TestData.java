package gps;

import main.Person;

/**
 * 
 * @author CoolCat
 */
public class TestData {

    private int counter=0;
    private GPSposition[] ownpos;
    private GPSposition targetpos;

        
    public GPSposition getOwnpos() {
        if (counter>=ownpos.length) 
            counter = 1;
        else
            counter++;
        if(Person.other().getMobilenumber() !=null){
        if(Person.other().getMobilenumber().equals("0222")){
            System.out.println("Other Tel: " + Person.other().getMobilenumber());
        return ownpos[counter-1];    
        }
        else return ownpos[20-counter];
        }
        else return ownpos[counter-1];
    }

    public GPSposition getTargetpos() {
        return targetpos;
    }
;
    
    public TestData () {
        ownpos =new GPSposition[20];
        targetpos =new GPSposition(50.1234,13.2345);
        
        ownpos[0]=new GPSposition(51.4567,
        14.7654);
        ownpos[1]=new GPSposition(51.4086,
        14.5953);
        ownpos[2]=new GPSposition(51.4604,
        14.5252);
        ownpos[3]=new GPSposition(51.4123,
        14.4551);
        ownpos[4]=new GPSposition(51.3641,
        14.3850);
        ownpos[5]=new GPSposition(51.2160,
        14.3349);
        ownpos[6]=new GPSposition(51.2178,
        14.1448);
        ownpos[7]=new GPSposition(51.2197,
        14.0747);
        ownpos[8]=new GPSposition(51.1715,
        14.0046);
        ownpos[9]=new GPSposition(51.1234,
        14.1005);
        ownpos[10]=new GPSposition(51.0567,
        14.0604);
        ownpos[11]=new GPSposition(50.9886,
        14.0053);
        ownpos[12]=new GPSposition(50.9604,
        13.9952);
        ownpos[13]=new GPSposition(50.8123,
        13.7551);
        ownpos[14]=new GPSposition(50.8641,
        13.7850);
        ownpos[15]=new GPSposition(50.7160,
        13.8149);
        ownpos[16]=new GPSposition(50.4678,
        13.7448);
        ownpos[17]=new GPSposition(50.5197,
        13.5747);
        ownpos[18]=new GPSposition(50.3715,
        13.3046);
        ownpos[19]=new GPSposition(50.1234,
        13.2345);
    }
    
}

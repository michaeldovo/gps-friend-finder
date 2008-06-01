package gps;

public class GPSposition{
    
    private double Latitude;
    private double Longitude;

    public GPSposition(){}
    
    /**
     * 
     * @param gpsstring A GPS-coded string in the format Latitude;Longitude
     */
    public GPSposition(String gpsString) {
        int count=gpsString.indexOf(";");
        Latitude=Double.valueOf(gpsString.substring(0, count).trim()).doubleValue();
        Longitude=Double.valueOf(gpsString.substring(count+1, gpsString.length()).trim()).doubleValue();
    }
    
    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }
    
    public String toString(){
        String longlat=Double.toString(Latitude) + ";" + Double.toString(Longitude);
        return longlat;
    }
}

package gps;

public class GPSposition{
    
    private double latitude;
    private double longitude;

    public GPSposition(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    /**
     * 
     * @param gpsstring A GPS-coded string in the format Latitude;Longitude
     */
    public GPSposition(String gpsString) {
        int count=gpsString.indexOf(";");
        latitude=Double.valueOf(gpsString.substring(0, count).trim()).doubleValue();
        longitude=Double.valueOf(gpsString.substring(count+1, gpsString.length()).trim()).doubleValue();
    }
    
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double Latitude) {
        this.latitude = Latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double Longitude) {
        this.longitude = Longitude;
    }
    
    public String toString(){
        String longlat=Double.toString(latitude) + ";" + Double.toString(longitude);
        return longlat;
    }
}

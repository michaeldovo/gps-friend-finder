package gps;

public class GPSposition{
    
    private double Latitude;
    private double Longitude;

    public GPSposition(){}
    
    public GPSposition(String string) {
        int count=string.indexOf(";");
        Latitude=Double.valueOf(string.substring(0, count).trim()).doubleValue();
        Longitude=Double.valueOf(string.substring(count+1, string.length()).trim()).doubleValue();
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

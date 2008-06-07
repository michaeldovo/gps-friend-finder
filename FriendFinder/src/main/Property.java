package main;

/**
 * Gemeinsame Property-Datei 
 */

public class Property {
    
// ************
// BLUETOOTH
// ************
    // bluetoth device name
    public static final String btName="BT-GPS-380D4E";
    
// *****
// SMS
// *****
    // SMS port number
    public static final String portNum="15123";
    public static final String AppId = "_*_FriendFinder_*_";
    public static final String VersionId = "0.1_*_";
    
// ******
// HTTP
// ******
    public static final int updateInterval = 30;
    
    //******
    //Switch for testing
    //s=TRUE for Bluetooth connection
    //s=false for test data
    //******
    public static final int GPS_TEST = 0;
    public static final int GPS_BT = 1;
    public static final int GPS_N95 = 2;
    
    public static int GPS_MODE = 0;
    
}

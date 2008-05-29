/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gps;

/**
 *
 * @author CoolCat
 */
public class TestMain {

    public static void main(String[]args){
        GPScalculations tester=new GPScalculations();
        
        tester.start();
        System.out.print("Current Pos: " + tester.getPosition().getLatitude() + " Latitude, " + tester.getPosition().getLongitude() + " Longitude.");
        
        while(!tester.targetreached()){
            System.out.println(" Distance: " + tester.getDistance());
            System.out.println("Direction: " + tester.getDirection());
        }
        System.out.println("Target reached!");
    }
}

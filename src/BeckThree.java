import rxtxrobot.*;

import java.util.Scanner;
/*
 * author: estephanian
 * BECKY code -- sprint 3
 * KNW 2300
 * 
 * 
 * STEPS TO GET 70 POINTS:
 * 
 * 1. Leave the starting box (5)
 * 
 * 
 * . Find water (5)
 * . Measure turbidity -- within range, report value (10)
 * . Measure salinity -- within range, report value (10)
 * . Collect correct amount of balls -- turbidity 
 * . Collect correect amount of balls -- salinity
 * . Dispense the correct amount of balls -- turbidity (potentially 30)
 * . Dispense the correct amount of balls -- salinity (potentially 30)
 * 
 */


public class BeckThree {
	
	public static RXTXRobot becky;
	public static void main(String[] args){

		
		becky = new ArduinoNano();
		becky.setPort("/dev/tty.wch ch341 USB=>RS232 fd120"); //sets the port
        becky.connect();
    	becky.setVerbose(true);
    	boolean goBecky = true;
    	double collectTurbidity = 0;
    	double collectSalinity = 0;
    	
        AnalogPin conductivity = becky.getAnalogPin(4);
        AnalogPin voltage = becky.getAnalogPin(5);  
        AnalogPin lineSensor = becky.getAnalogPin(6);
        
        
        
        
        
	}
        
	
	

}

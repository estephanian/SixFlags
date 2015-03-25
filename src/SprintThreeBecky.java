/*
 * author: estephanian
 * BECKY code -- sprint 3
 * KNW 2300
 */
import rxtxrobot.*;

import java.util.Scanner; 


public class SprintThreeBecky {
/*
 * STEPS TO GET 70 POINTS:
 * 
 * 1. Leave the starting box (5)
 * 2. Find water (5)
 * 3. Measure turbidity -- within range, report value (10)
 * 4. Measure salinity -- within range, report value (10)
 * 5. Collect correct amount of balls -- turbidity 
 * 6. Collect correect amount of balls -- salinity
 * 7. Dispense the correct amount of balls -- turbidity (potentially 30)
 * 8. Dispense the correct amount of balls -- salinity (potentially 30)
 * 
 */
	
	public static RXTXRobot becky;
	public static void main(String[] args){

		
		becky = new ArduinoNano();
		becky.setPort("/dev/tty.wch ch341 USB=>RS232 fd120"); //sets the port
        becky.connect();
    	becky.setVerbose(true);
    	boolean goBecky = true;
    	double collectTurbidity = 0;
    	
        AnalogPin conductivity = becky.getAnalogPin(4);
        AnalogPin voltage = becky.getAnalogPin(5);  
        AnalogPin lineSensor = becky.getAnalogPin(6);
        
        
        for (int i = 0; i < 100; i++){ //goes through 100 times --> prints out value 100 times
        	becky.refreshAnalogPins(); //must refresh the Analog Pins each time 
        	System.out.println("Pin 3 has value: " + voltage.getValue()); //prints out the value
        	System.out.println("Pin 4 has value: " + conductivity.getValue()); //prints out the value
        	System.out.println("Pin 5 has value: " + lineSensor.getValue()); //prints out the value
        }
            
    	

    	while(goBecky){
    		System.out.println("Choose an option: ");
        	System.out.println("1. Leave the starting box");
        	System.out.println("2. Find water");
        	System.out.println("3. Test the water for tempurature");
        	System.out.println("4. Test the water for turbidity");
        	System.out.println("5. Test the water for salinity");
        	System.out.println("6. Collect the correct amount of balls -- turbidity");
        	System.out.println("7. Collect the correct amount of balls -- salinity");
        	System.out.println("8. Dispense the correct amount of balls -- turbidity");
        	System.out.println("9. Dispense the correct amount of balls -- salinity");
        	System.out.println("8. Exit");
        
        	Scanner scan = new Scanner(System.in);
            
            int choice = 0;
            choice = scan.nextInt();
            
            
            if(choice == 1){
            	/* 
            	 * LEAVE THE STARTING BOX
            	 * follow the line sensor
            	 */
            	
            	
            	
            	
            }
            else if(choice == 2){
            	/*
            	 * FIND WATER
            	 * using line sensor
            	 */
            	
            	
            	
            }
            else if(choice == 3){
            	/*
            	 * TEST TEMP
            	 * must use temperature sensor, must lower arm  
            	 */
            	
            	//temperatureSensor thermRead = new temperatureSensor();	   
                
           	 int sum = 0;
       		 int readingCount = 20;
       		 double average;
       		 double denom;
       		 double stuff;
       		 double tempCent;
       		// int thermistorReading = thermRead.getThermistorReading();
       		
       		 //Read the analog pin values ten times, adding to sum each time
       		 	for (int i = 1; i <= readingCount; i++){

       		 		//Refresh the analog pins so we get new readings
       		 		becky.refreshAnalogPins();
       		 		int reading = becky.getAnalogPin(0).getValue();
     
       		 		sum += reading;
       		 		//System.out.println("debug 1: " + sum);
       		 		average = sum / i;
       		 		//System.out.println("debug 2: " + average);
       		
       		 		//Return the average reading
       		 		System.out.println("The probe read the value: " + average);
       		 		System.out.println("In volts: " + (average * (5.0/1023.0)));
       		 		stuff = Math.log((1013.0/((1023.0/average) - 1.0))/986.8);
       		 		denom = ((1.0/300.7) + (1.0/3723.0)*stuff);
       		 		tempCent = 1.0/denom - 273.0;
       		 		System.out.println("The temperature is " + tempCent);
   		   		
       		 		becky.sleep(1000);       	
       		 }
            }
            
            else if(choice == 4){
            	/*
            	 * TEST TURBIDITY
            	 * must use turbidity sensor, must lower arm
            	 */
            	
            	 for(int i = 0; i < 5; i ++){
         	        becky.refreshAnalogPins();
         	        double v = voltage.getValue();
         	        double turbidity = 311.67*Math.exp(-1.562*v);
         	        System.out.println("Turbidity sensor has value" + turbidity);
         	        
         	        collectTurbidity = turbidity;
         	        
         	    }      	
            }
            
            else if(choice == 5){
            	/*
            	 * TEST SALINITY
            	 * must use salinity sensor, must lower arm
            	 */
            	
             	becky.refreshAnalogPins();
            	becky.refreshDigitalPins();


    	        
    	        becky.getConductivity();
    	        
    	        for(int i = 0; i < 3; i++){
    	//        	becky.refreshDigitalPins();
    	        	becky.refreshAnalogPins();
    	//        	double resistance = conductivity.getValue();
    	//        	double salinity = 1.673*Math.log(resistance) - 6.6388;
    	//        	double salinity = Math.abs(conductivity.getValue() - voltage.getValue());
    	        	System.out.println(1.673*Math.log(becky.getConductivity()) - 6.6388);
    	        	double salinity = Math.log(becky.getConductivity());
    	        	System.out.println("Conductivity: " + conductivity.getValue());
    	        	System.out.println(salinity);
    	        	System.out.println("Voltage" + voltage.getValue());
            	}
            }
            else if(choice == 6){
            	/*
            	 * COLLECT BALLS FOR TURBIDITY
            	 */
            	
            	int theServo = (int) (90 * collectTurbidity);
            	
            	becky.refreshDigitalPins();
            	
                becky.attachServo(RXTXRobot.SERVO1, 9); //Connect the servos to the Arduino 
                becky.moveServo(RXTXRobot.SERVO1, (int) collectTurbidity); //servo
               // becky.sleep(6000);
            	
            	
            }
            
            else if(choice == 7){
            	/*
            	 * COLLECT BALLS FOR SALINITY
            	 */
            	
            	
            }
            
            else if(choice == 8){
            	/*
            	 * DISPENSE BALLS FOR TURBIDITY
            	 */
            	
            	
            	
            }
            else if(choice == 9){
            	/*
            	 * DISPENSE BALLS FOR SALINITY
            	 * figure out how we are dispensing the balls
            	 */
            	
            	
            	
            	
            }
            else if (choice == 10){
            	/*
            	 * EXITS PROGRAM
            	 */
            	break;
            }
            
         	becky.close();
           	
    	}
    	
    	
    	/*
    	 * MOVEMENT SENSORS
    	 */
    	
    	//line sensor
    	AnalogPin line = becky.getAnalogPin(6);
    	
    	//bump sensor
    	AnalogPin bump = becky.getAnalogPin(1);
    	
    	
    	//ping sensor
    	AnalogPin ping = becky.getAnalogPin(7);
    	
    	
    	//
    	
    	
    	
    	
    	
    	
    	
    	
    	
		
	}
}	


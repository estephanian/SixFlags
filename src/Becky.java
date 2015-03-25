/*
 * @author estephanian
 * KNW 2300, First Year Design
 * Becky the robot 
 */
import rxtxrobot.*;


import java.util.Scanner; 

public class Becky {
	
	public static RXTXRobot becky;
	public static void main(String[] args){

	
		
		becky = new ArduinoNano();
		becky.setPort("/dev/tty.wch ch341 USB=>RS232 fd120"); //sets the port
        becky.connect();
    	becky.setVerbose(true);
            
        
        System.out.println("Choose an option: ");
        System.out.println("1. Motor turn 3 meters");
        System.out.println("2. Servo move certain angle");
        System.out.println("3. Read distance (with sensor)");
        System.out.println("4. Move motor indefinitely (until bump sensor is activated)");
        System.out.println("5. Test temperature");
        System.out.println("6. Test salinity");
        System.out.println("7. Move Becky autonomously to accrue 50 points");
        System.out.println("8. Locate well");
        System.out.println("9. Test well");
        System.out.println("10. Bring robot to home location");
        
        
    	Scanner scan = new Scanner(System.in);
        
        int choice = 0;
        choice = scan.nextInt();
        
        
        if(choice == 1){
        	
        	/*
             * FOR MOVING MOTOR
             * takes three arguments --> motor you want to run, the speed (0 to 255), and time (in milliseconds)
             * if you set a time for 3 seconds, the code will not continue until the motor is done (after 3 seconds)
             * if you set the time to 0, it will set the motor to run infinitely
             * to stop it from running infinitely --> becky.runMotor(RXTXRobot.MOTOR1, 0, 0);
             * for this you have to calculate the time/distance ratio to get 3 meters
             */
            
            becky.runMotor(RXTXRobot.MOTOR1,-500, RXTXRobot.MOTOR2, 500, 10000);
        	
        	//when collecting ping pong balls --> faster
            // slow for going towards water
            //slow for ramp
            
            
        }
        
        else if(choice == 2){

            /*
             * FOR MOVING SERVO
             * to make it move, just add degrees to position
             * to choose servo, just pick the one depending on where it is plugged in
             */

        	becky.refreshDigitalPins();
        	
            becky.attachServo(RXTXRobot.SERVO1, 9); //Connect the servos to the Arduino 
            becky.moveServo(RXTXRobot.SERVO1, 45); //servo
            becky.sleep(6000);

           // becky.moveServo(RXTXRobot.SERVO2, 180);    	
        }
        
        else if(choice == 3){
        	
        	/*
         	 * FOR PING SENSOR
         	 * reads distance
        	 */
        	becky.refreshDigitalPins();
        	
        //	boolean myPing = true;

    		for (int x=0; x < 60; ++x) 
    		{ 
    			
    			System.out.println("Response: " + becky.getPing(4) + " cm"); 
    			becky.sleep(300); 
    		} 
                 }
    
        else if(choice == 4){
        	
        	/*
             * FOR BUMP SENSOR (for when the motor is running)
             * when the bump sensor is triggered, the motor will stop
             */
            becky.refreshAnalogPins();
            
            boolean keepGoing = true;
            
            while(keepGoing){
               

               becky.refreshAnalogPins();
               becky.runMotor(RXTXRobot.MOTOR1, 495, RXTXRobot.MOTOR2, 495, 0);
               AnalogPin bump = becky.getAnalogPin(1);
               
               
               if( bump.getValue() <= 0 ){  // if the bump sensor is triggered
                 //  becky.runMotor(RXTXRobot.MOTOR1, 495, RXTXRobot.MOTOR2, 495, 1);	//stops the motor
                  // becky.refreshAnalogPins();
                  // bump = becky.getAnalogPin(1); 
            	   keepGoing = false;
               }
                   
               else if( bump.getValue() > 0){ // also stops the motor
                       keepGoing = true;

                   }  

               }  
            }

        
        else if(choice == 5){
        	
        	/*
             * FOR TEMPERATURE SENSOR
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
        
        else if(choice == 6){
        	
        	/*
             * FOR SALINITY SENSOR
             */
        	
        	becky.refreshAnalogPins();
        	becky.refreshDigitalPins();

	        AnalogPin conductivity = becky.getAnalogPin(4);
	        AnalogPin voltage = becky.getAnalogPin(5); 
	        
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
        else if(choice == 7){
        	/*
        	 * 
        	 * Move Becky autonomously to accrue 50 points
        	 * Make up of the 50 points --> ?
        	 * 
        	 */
        	
        	becky.refreshAnalogPins();
        	becky.refreshDigitalPins();
        	
        	
        	
        	
        }
        
        else if(choice == 8){
        	/*
        	 * 
        	 * Locate well
        	 * deals with 
        	 * 
        	 */
        	
        	
        	
        }
        
        else if(choice == 9){
        	/*
        	 * 
        	 * Test well
        	 * Test for temperature --> just copy code from earlier
        	 * Test for turbidity --> just copy code from earlier
        	 * Test for salinity --> just copy code from earlier
        	 * 
        	 */
        	
        	
        	
        	/*
             * FOR TEMPERATURE SENSOR
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
    		 
    		 
    		 /*
              * FOR SALINITY SENSOR
              */
         	
         	becky.refreshAnalogPins();
         	becky.refreshDigitalPins();

 	        AnalogPin conductivity = becky.getAnalogPin(4);
 	        AnalogPin voltage = becky.getAnalogPin(5); 
 	        
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
 	        
 	       
 	        /*
 	         * FOR TURBIDITY SENSOR
 	         */
 	        
 	        becky.refreshAnalogPins();
 	        becky.refreshDigitalPins();
 	        
 	        
 	        
 	        
        }
    		     
        else if(choice == 10){
        	/*
        	 * 
        	 * BECKY HOME
        	 * requres use of: dc motor, line sensor(?), ping sensor(?)
        	 * 
        	 */
        	
        	becky.refreshAnalogPins();
        	becky.refreshDigitalPins();
        }

        
        
        
        
       
        /*
         * 
         * FOR ANALOG PINS
         * each pin has a corresponding number (with the sensors)
         * if nothing is connected to the Arduino, you will get random values
         * 
         */
        
        /* AnalogPin conductivity = becky.getAnalogPin(4);
        AnalogPin voltage = becky.getAnalogPin(3); 
        
        for (int i = 0; i < 100; i++){ //goes through 100 times --> prints out value 100 times
        	becky.refreshAnalogPins(); //must refresh the Analog Pins each time 
        	AnalogPin myPin = becky.getAnalogPin(1);
        	System.out.println("Pin 1 has value: " + myPin.getValue()); //prints out the value
        }
         */   
  
        /*
         * 
         * FOR TURBIDITY SENSOR
         * 
         */
        
        for(int i = 0; i < 5; i ++){
	        becky.refreshAnalogPins();
	        double v = voltage.getValue();
	        double turbidity = 311.67*Math.exp(-1.562*v);
	        System.out.println("Turbidity sensor has value" + turbidity);
	    }
        
        
     
		becky.close(); //MAKES ROBOT STOP
	}
}
	



//This example shows how to use the RFID Sensor to get a scanned tag. This is a completely separate object from RXTXRobot.

import rxtxrobot.*;

public class RFIDExample {

	public static void main(String[] args){
		
		RFIDSensor becky = new RFIDSensor(); //create a RFIDSensor object
		becky.setPort("COM 5");
		becky.connect();
		while(!becky.hasTag()){
			becky.sleep(300);
		}
		
		System.out.println("Got tag: " + becky.getTag());
		
		becky.close();
		
		
	}
	
}

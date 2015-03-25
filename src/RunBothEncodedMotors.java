//This example shows how to run both DC encoded motors at the same time but at different distances
import rxtxrobot.*;


public class RunBothEncodedMotors {

	public static void main(String[] args){
		
		
		RXTXRobot becky = new ArduinoNano();
		becky.setPort("COM 5");
		becky.connect();
		//We don't have to attach anything because these motors are attached by default
		becky.runEncodedMotor(RXTXRobot.MOTOR1, 255, 100, RXTXRobot.MOTOR2, 255, 500); //Runs both motors forward, one for 100,000 ticks
		//and one for 50,000 ticks
		
		becky.close();
	}
}

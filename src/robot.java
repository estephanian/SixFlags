/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

/**
 *
 * @author typho_000
 */
import rxtxrobot.*;
public class robot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        RXTXRobot robot = new ArduinoNano();                                        //Arduino#1 
        robot.setPort("COM3");
        
        RXTXRobot r = new ArduinoNano();                                            //Arduino #2 (wrapped in green tape. goes in first usb port)
        r.setPort("COM4"); 
        
   
        robot.connect();
        //robot.setVerbose(true);
        
         robot.refreshAnalogPins();
            
        boolean keepGoing = true;
        while(keepGoing){
            
            robot.runMotor(RXTXRobot.MOTOR1, -280, RXTXRobot.MOTOR2, 295, 0);
            robot.refreshDigitalPins();
            DigitalPin bump = robot.getDigitalPin(7);
            
            
            if( bump.getValue() > 0 ){                                                            // initial forward movement, bump sensor
                robot.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
                robot.refreshDigitalPins();
                bump = robot.getDigitalPin(7);
                
                if( bump.getValue() > 0){
                    keepGoing = false;
                }
                keepGoing = false;
            }
        }
        
        
        r.connect();
      //r.setVerbose(true);
        r.runMotor(RXTXRobot.MOTOR1, 140, 1300);                                    //sensor arm movement 
        r.sleep(2000);

        AnalogPin conductivity = r.getAnalogPin(4);
        AnalogPin voltage = r.getAnalogPin(3); 
        for(int i = 0; i < 5; i++){
        r.refreshAnalogPins();
        double resistance = conductivity.getValue();
        double salinity = 1.673*Math.log(resistance) - 6.6388;
        System.out.println(salinity);
        }
       
       
        System.out.println();
        for(int i = 0; i < 5; i ++){
        r.refreshAnalogPins();
        double v = voltage.getValue();
        double turbidity = 311.67*Math.exp(-1.562*v);
        System.out.println(turbidity);
        }
        r.runMotor(RXTXRobot.MOTOR1, -140, 1300);                                   //arm moves down
        r.close();
        
        robot.runMotor(RXTXRobot.MOTOR1, 290, RXTXRobot.MOTOR2, -280, 1450);
    
        boolean cycle2 = true;
        while(cycle2){
            robot.runMotor(RXTXRobot.MOTOR1, 180, RXTXRobot.MOTOR2, -170, 0);  // 180, -170
            robot.refreshAnalogPins();
             AnalogPin line = robot.getAnalogPin(0);
            AnalogPin line2 = robot.getAnalogPin(1);
            AnalogPin line3 = robot.getAnalogPin(2);
                                                                                    //backwards line sensor based movement, turn 90 for 3770 s
            if(line.getValue() < 750 || line2.getValue() <750 || line3.getValue() < 750){
                robot.runMotor(RXTXRobot.MOTOR1, 280, RXTXRobot.MOTOR2, -250, 1400); //180, -150
                robot.runMotor(RXTXRobot.MOTOR1, -280, RXTXRobot.MOTOR2, -250, 1460);
                if(line.getValue() < 750 || line2.getValue() <750 || line3.getValue() < 750){
                    cycle2 = false;
                }
            }
        }
        
         AnalogPin line = robot.getAnalogPin(0);             //on the left
         AnalogPin line2 = robot.getAnalogPin(1);            // middle
         AnalogPin line3 = robot.getAnalogPin(2);            //on the right
           
            robot.refreshAnalogPins();
            robot.refreshDigitalPins();
            DigitalPin bump = robot.getDigitalPin(7);
            System.out.println(line.getValue() + " left side");
            System.out.println(line2.getValue() + " middle"); 
            System.out.println(line3.getValue() + " right side");
           
            
            //robot.runMotor(RXTXRobot.MOTOR1,-180, RXTXRobot.MOTOR2, 190, 0);
            boolean cycle = true;
            while(cycle){
                robot.refreshDigitalPins();
            robot.runMotor(RXTXRobot.MOTOR1,180, RXTXRobot.MOTOR2, -190, 0);         
            robot.refreshAnalogPins();
            
                int y = robot.getAnalogPin(0).getValue();
                int x = robot.getAnalogPin(2).getValue();
                while ( y > 800 && x > 800){/*do nothing */
                    robot.refreshAnalogPins();
                    y = robot.getAnalogPin(0).getValue();
                    x = robot.getAnalogPin(2).getValue();
                    System.out.println("WHILE: LINE1: " + robot.getAnalogPin(0).getValue() + ", LINE2: " + robot.getAnalogPin(2).getValue());
                }
                
                System.out.println("BREAK");
                robot.runMotor(RXTXRobot.MOTOR1,0, RXTXRobot.MOTOR2, 0, 0);

                if(y < 800)
                {
                    System.out.println("TURN RIGHT");
                    robot.runMotor(RXTXRobot.MOTOR1, 150, RXTXRobot.MOTOR2, 150, 100);  
                    robot.sleep(1000);
                    System.out.println(x + " " + y);
                } if(x  < 800){
                                           System.out.println("TURN LEFT");

                    robot.runMotor(RXTXRobot.MOTOR1, -150, RXTXRobot.MOTOR2, -150, 100);
                    robot.sleep(1000);
                }


                 if( robot.getPing(13) < 60 ){                                                            // initial forward movement, bump sensor
                    robot.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
                    

                    if( robot.getPing(13) < 40){
                        cycle = false;
                    }
                 }
            }
        
        
        boolean turn = true;
        while(turn){
            robot.runMotor(RXTXRobot.MOTOR1, 290, RXTXRobot.MOTOR2, -280, 0);      // front ping sensor stops robot before the dispensor, adjust distance first
            int g = robot.getPing(13);
            
            if(g <= 9){
                robot.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
                if(g <= 9){
                    turn = false;
                }
            }
        }
        
        
        robot.attachServo(RXTXRobot.SERVO1, 11);
        for(int y = 0; y< 3; y++){                                                 //moves ball whacker, which uses 360 SERVO on pin 11
            robot.moveServo(RXTXRobot.SERVO1, 45);
        }  
        r.sleep(6000);
        
        robot.runMotor(RXTXRobot.MOTOR1, -290, RXTXRobot.MOTOR2, 280, 3600);        //Move backwards and turn counterclockwise 90
        robot.runMotor(RXTXRobot.MOTOR1, -290, RXTXRobot.MOTOR2, -280, 1320);
   
        
        boolean cycle22 = true;
        while(cycle22){
            robot.runMotor(RXTXRobot.MOTOR1, 290, RXTXRobot.MOTOR2, -280, 0);
            robot.refreshAnalogPins();
             line = robot.getAnalogPin(0);
            line2 = robot.getAnalogPin(1);
             line3 = robot.getAnalogPin(2);
                                                                                    //backwards line sensor based movement, turn 90 for 3770 s
            if(line.getValue() < 750 || line2.getValue() <750 || line3.getValue() < 750){
                //robot.runMotor(RXTXRobot.MOTOR1, -290, RXTXRobot.MOTOR2, 280, 1000);
                robot.runMotor(RXTXRobot.MOTOR1, 280, RXTXRobot.MOTOR2, 250, 1320);
                if(line.getValue() < 750 || line2.getValue() <750 || line3.getValue() < 750){
                    cycle22 = false;
                }
            }
        }
        
         
            
            boolean cycle23 = true;
            while(cycle23){
                robot.refreshDigitalPins();
            robot.runMotor(RXTXRobot.MOTOR1,290, RXTXRobot.MOTOR2, -280, 0);         
            robot.refreshAnalogPins();
            
                int y = robot.getAnalogPin(0).getValue();
                int x = robot.getAnalogPin(2).getValue();
                while ( y > 800 && x > 800){ //ndo nothing 
                    robot.refreshAnalogPins();
                    y = robot.getAnalogPin(0).getValue();
                    x = robot.getAnalogPin(2).getValue();
                    System.out.println("WHILE: LINE1: " + robot.getAnalogPin(0).getValue() + ", LINE2: " + robot.getAnalogPin(2).getValue());
                }
                
                System.out.println("BREAK");
                robot.runMotor(RXTXRobot.MOTOR1,0, RXTXRobot.MOTOR2, 0, 0);

                if(y < 800)
                {
                    System.out.println("TURN RIGHT");
                    robot.runMotor(RXTXRobot.MOTOR1, 150, RXTXRobot.MOTOR2, 150, 100);  
                    robot.sleep(1000);
                    System.out.println(x + " " + y);
                } if(x  < 800){
                                           System.out.println("TURN LEFT");

                    robot.runMotor(RXTXRobot.MOTOR1, -150, RXTXRobot.MOTOR2, -150, 100);
                    robot.sleep(1000);
                }


                 if( robot.getPing(13) < 40 ){                                                            // initial forward movement, bump sensor
                    robot.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
                    

                    if( robot.getPing(13) < 40){
                        cycle23 = false;
                    }
                 }
            }
            
            boolean turn2 = true;
        while(turn2){
            robot.runMotor(RXTXRobot.MOTOR1, 290, RXTXRobot.MOTOR2, -280, 0);      // front ping sensor stops robot before the dispensor, adjust distance first
            int g = robot.getPing(13);
            
            if(g <= 10){
                robot.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
                if(g <= 10){
                    turn2 = false;
                }
            }
        }
        
         
        for(int y = 0; y< 10; y++){                                                 //moves ball whacker, which uses 360 SERVO on pin 11
            robot.moveServo(RXTXRobot.SERVO1, 45);
        }  
        r.sleep(5000);
        
        robot.runMotor(RXTXRobot.MOTOR1, 290, RXTXRobot.MOTOR2, -280, 3000);        //Move backwards and turn counterclockwise 90 *********************************
        robot.runMotor(RXTXRobot.MOTOR1, 290, RXTXRobot.MOTOR2, 280, 1390);
        
        boolean cycle222 = true;
        while(cycle222){
            robot.runMotor(RXTXRobot.MOTOR1, -290, RXTXRobot.MOTOR2, 280, 0);
            robot.refreshAnalogPins();
             line = robot.getAnalogPin(0);
            line2 = robot.getAnalogPin(1);
             line3 = robot.getAnalogPin(2);
                                                                                    //backwards line sensor based movement, turn 90 for 3770 s
            if(line.getValue() < 750 || line2.getValue() <750 || line3.getValue() < 750){
                //robot.runMotor(RXTXRobot.MOTOR1, -290, RXTXRobot.MOTOR2, 280, 1000);
                robot.runMotor(RXTXRobot.MOTOR1, -280, RXTXRobot.MOTOR2, -250, 1390);
                if(line.getValue() < 750 || line2.getValue() <750 || line3.getValue() < 750){
                    cycle222 = false;
                }
            }
        }
        
        robot.runMotor(RXTXRobot.MOTOR1, -290, RXTXRobot.MOTOR2, 280, 6000);
        
        boolean cycle223 = true;
            while(cycle223){
                robot.refreshDigitalPins();
            robot.runMotor(RXTXRobot.MOTOR1,-290, RXTXRobot.MOTOR2, 280, 0);         
            robot.refreshAnalogPins();
            
                int y = robot.getAnalogPin(0).getValue();
                int x = robot.getAnalogPin(2).getValue();
                while ( y > 800 && x > 800){/*do nothing */
                    robot.refreshAnalogPins();
                    y = robot.getAnalogPin(0).getValue();
                    x = robot.getAnalogPin(2).getValue();
                    System.out.println("WHILE: LINE1: " + robot.getAnalogPin(0).getValue() + ", LINE2: " + robot.getAnalogPin(2).getValue());
                }
                
                System.out.println("BREAK");
                robot.runMotor(RXTXRobot.MOTOR1,0, RXTXRobot.MOTOR2, 0, 0);

                if(y < 800)
                {
                    System.out.println("TURN RIGHT");
                    robot.runMotor(RXTXRobot.MOTOR1, 150, RXTXRobot.MOTOR2, 150, 100);  
                    robot.sleep(1000);
                    System.out.println(x + " " + y);
                } if(x  < 800){
                                           System.out.println("TURN LEFT");

                    robot.runMotor(RXTXRobot.MOTOR1, -150, RXTXRobot.MOTOR2, -150, 100);
                    robot.sleep(1000);
                }


                 if( robot.getPing(13) < 40 ){                                                            // initial forward movement, bump sensor
                    robot.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
                    

                    if( robot.getPing(13) < 40){
                        cycle223 = false;
                    }
                 }
            }
            
         boolean turn3 = true;
        while(turn3){
            robot.runMotor(RXTXRobot.MOTOR1, 290, RXTXRobot.MOTOR2, -280, 0);      // front ping sensor stops robot before the dispensor, adjust distance first
            int g = robot.getPing(13);
            
            if(g <= 10){
                robot.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
                if(g <= 10){
                    turn3 = false;
                }
            }
        } 
        
        
        for(int y = 0; y< 10; y++){                                                 //moves ball whacker, which uses 360 SERVO on pin 11
            robot.moveServo(RXTXRobot.SERVO1, 45);
        }  
        r.sleep(5000);
        
        robot.runMotor(RXTXRobot.MOTOR1, 290, RXTXRobot.MOTOR2, -280, 3000);        //Move backwards and turn counterclockwise 90
        robot.runMotor(RXTXRobot.MOTOR1, 290, RXTXRobot.MOTOR2, 280, 1390);
        
        boolean cycle2222 = true;
        while(cycle2222){
            robot.runMotor(RXTXRobot.MOTOR1, -290, RXTXRobot.MOTOR2, 280, 0);
            robot.refreshAnalogPins();
             line = robot.getAnalogPin(0);
            line2 = robot.getAnalogPin(1);
             line3 = robot.getAnalogPin(2);
                                                                                    //backwards line sensor based movement, turn 90 for 3770 s
            if(line.getValue() < 750 || line2.getValue() <750 || line3.getValue() < 750){
                //robot.runMotor(RXTXRobot.MOTOR1, -290, RXTXRobot.MOTOR2, 280, 1000);
                robot.runMotor(RXTXRobot.MOTOR1, -280, RXTXRobot.MOTOR2, -250, 1390);
                if(line.getValue() < 750 || line2.getValue() <750 || line3.getValue() < 750){
                    cycle2222 = false;
                }
            }
        }
        
        boolean cycle2223 = true;
            while(cycle2223){
                robot.refreshDigitalPins();
            robot.runMotor(RXTXRobot.MOTOR1,-290, RXTXRobot.MOTOR2, 280, 0);         
            robot.refreshAnalogPins();
            
                int y = robot.getAnalogPin(0).getValue();
                int x = robot.getAnalogPin(2).getValue();
                while ( y > 800 && x > 800){/*do nothing */
                    robot.refreshAnalogPins();
                    y = robot.getAnalogPin(0).getValue();
                    x = robot.getAnalogPin(2).getValue();
                    System.out.println("WHILE: LINE1: " + robot.getAnalogPin(0).getValue() + ", LINE2: " + robot.getAnalogPin(2).getValue());
                }
                
                System.out.println("BREAK");
                robot.runMotor(RXTXRobot.MOTOR1,0, RXTXRobot.MOTOR2, 0, 0);

                if(y < 800)
                {
                    System.out.println("TURN RIGHT");
                    robot.runMotor(RXTXRobot.MOTOR1, 150, RXTXRobot.MOTOR2, 150, 100);  
                    robot.sleep(1000);
                    System.out.println(x + " " + y);
                } if(x  < 800){
                                           System.out.println("TURN LEFT");

                    robot.runMotor(RXTXRobot.MOTOR1, -150, RXTXRobot.MOTOR2, -150, 100);
                    robot.sleep(1000);
                }


                 if( robot.getPing(13) < 40 ){                                                            // initial forward movement, bump sensor
                    robot.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
                    

                    if( robot.getPing(13) < 40){
                        cycle2223 = false;
                    }
                 }
            }
            
            boolean turn4 = true;
        while(turn4){
            robot.runMotor(RXTXRobot.MOTOR1, 290, RXTXRobot.MOTOR2, -280, 0);      // front ping sensor stops robot before the dispensor, adjust distance first
            int g = robot.getPing(13);
            
            if(g <= 10){
                robot.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
                if(g <= 10){
                    turn4 = false;
                }
            }
        } 
        
        
        for(int y = 0; y< 10; y++){                                                 //moves ball whacker, which uses 360 SERVO on pin 11
            robot.moveServo(RXTXRobot.SERVO1, 45);
        }  
        r.sleep(5000);
        
       robot.runMotor(RXTXRobot.MOTOR1, 290, RXTXRobot.MOTOR2, -280, 500);
       robot.runMotor(RXTXRobot.MOTOR1, 280, RXTXRobot.MOTOR2, 250, 3300);
        
         
        boolean lastTurning = true;
        while(lastTurning){
            robot.runMotor(RXTXRobot.MOTOR1, -290, RXTXRobot.MOTOR2, 280, 0);      // Side ping sensor stops and turns robot when it senses dispensor
            int z = robot.getPing(12);
            
            if(z <= 24){
                robot.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
                robot.runMotor(RXTXRobot.MOTOR1, 280, RXTXRobot.MOTOR2, 290, 3770);
                if(z <= 24){
                    lastTurning = false;
                }
            }    
        }
        
        
        
        
        boolean keepOnTruckingOn3 = true;
        while(keepOnTruckingOn3){
            robot.runMotor(RXTXRobot.MOTOR1, 290, RXTXRobot.MOTOR2, -280, 0);
            robot.refreshDigitalPins();
             bump = robot.getDigitalPin(7);
            
            
            if( bump.getValue() > 0 ){                                                            // initial forward movement, bump sensor
                robot.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
                robot.refreshDigitalPins();
                bump = robot.getDigitalPin(7);
                
                if( bump.getValue() > 0){
                    keepOnTruckingOn3 = false;
                } 
            }
        }
        
        
        boolean cycle22222 = true;
        while(cycle22222){
            robot.runMotor(RXTXRobot.MOTOR1, -290, RXTXRobot.MOTOR2, 280, 0);
            robot.refreshAnalogPins();
             line = robot.getAnalogPin(0);
            line2 = robot.getAnalogPin(1);
             line3 = robot.getAnalogPin(2);
                                                                                    //backwards line sensor based movement, turn 90 for 3770 s
            if(line.getValue() < 750 || line2.getValue() <750 || line3.getValue() < 750){
                robot.runMotor(RXTXRobot.MOTOR1, -290, RXTXRobot.MOTOR2, 280, 1000);
                robot.runMotor(RXTXRobot.MOTOR1, 280, RXTXRobot.MOTOR2, 250, 1390);
                if(line.getValue() < 750 || line2.getValue() <750 || line3.getValue() < 750){
                    cycle22222 = false;
                }
            }
        }
        
        
        
        
        boolean keepOnTruckingOn4 = true;
        while(keepOnTruckingOn4){
            robot.runMotor(RXTXRobot.MOTOR1, -198, RXTXRobot.MOTOR2, 255, 0);
            int u = robot.getPing(13);
            
            if( u <= 24){                                                            // move forward until certain cm from water.
                robot.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
                if( u <=24 ){
                    keepOnTruckingOn4 = false;
                }
            }
        }
        
        
        robot.attachServo(RXTXRobot.SERVO2, 10);
        robot.moveServo(RXTXRobot.SERVO2, 180);                                    //moves door SERVO on pin 10
        robot.sleep(2000);

        
        robot.close();
        
    }
}
       




        

    
    


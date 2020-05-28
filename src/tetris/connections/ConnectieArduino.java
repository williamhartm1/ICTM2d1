package tetris.connections;

/*
Verbinding met de Arduino wordt opgezet en geopent. De library 'JSerialComm' wordt hiervoor gebruikt.
 */

import com.fazecast.jSerialComm.SerialPort;

public class ConnectieArduino {
    public static SerialPort usedPort; // de te gebruiken port

    public ConnectieArduino() {
        SerialPort availablePort;
        availablePort = SerialPort.getCommPort("COM3"); //Arduino (COM3)  gebruiken als port
        if (!(availablePort == null)){
            usedPort = availablePort;
            usedPort.openPort();        //open deze port

            if (usedPort.isOpen()) { //print status van verbinding
                System.out.println("Opened COM3 (Arduino)");
            } else {
                System.out.println("Port is not available");
            }
        } else {
            System.out.println("No available ports found.");
        }
    }
}

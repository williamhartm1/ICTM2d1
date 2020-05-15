package tetris.connections;

import com.fazecast.jSerialComm.SerialPort;

public class ConnectieArduino {
    public static SerialPort usedPort; // de te gebruiken port

    public ConnectieArduino() {
        SerialPort availablePort;
        availablePort = SerialPort.getCommPort("COM3"); //set Arduino (COM3) als port
        if (!(availablePort == null)){
            usedPort = availablePort;
            usedPort.openPort();                        //open deze port

            if (usedPort.isOpen()) {
                System.out.println("Opened COM3 (Arduino)");
            } else {
                System.out.println("Port is not available");
            }
        } else {
            System.out.println("No available ports found.");
        }
    }
}

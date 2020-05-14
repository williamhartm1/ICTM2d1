package tetris.connections;

import com.fazecast.jSerialComm.SerialPort;

public class ConnectieArduino {
    public static SerialPort usedPort; // de te gebruiken port

    public ConnectieArduino() {
        SerialPort availablePorts[];
        availablePorts = SerialPort.getCommPorts();  //check all available ports
        if (availablePorts.length != 0) {
            usedPort = availablePorts[0];    //take the first port (Arduino)
            usedPort.openPort();                        //open this port

            if (usedPort.isOpen()) {
                System.out.println("Opened available port: " + usedPort.getDescriptivePortName());
            } else {
                System.out.println("Port is not available");
            }
        } else {
            System.out.println("No available ports found.");
        }
    }
}

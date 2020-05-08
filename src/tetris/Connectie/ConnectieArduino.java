package tetris.Connectie;

import tetris.gui.*;

import tetris.game.Board;
import tetris.game.Game;
import com.fazecast.jSerialComm.*;

import java.nio.charset.StandardCharsets;

public class ConnectieArduino {
    public static SerialPort usedPort; // de te gebruiken port

    public void connectLoop(){
        //TekenPanel panel = new TekenPanel();
        Game game = new Game();

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


        usedPort.addDataListener(new SerialPortDataListener() { //make java listen for arduino input
            String serialString;
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {

                byte[] newData = new byte[usedPort.bytesAvailable()];       //aantal beschikbare bytes
                int numRead = usedPort.readBytes(newData, newData.length);  //lees aantal bytes
                String stringBuffer = new String(newData,0,numRead);

                serialString = stringBuffer;
                while (!stringBuffer.endsWith("\n")) {  //alles voor een newline wordt in serialString opgeslagen
                    numRead = usedPort.readBytes(newData, newData.length);
                    stringBuffer = new String(newData, 0, numRead, StandardCharsets.UTF_8);
                    serialString += stringBuffer;
                }


                if (serialString.equals("Left\r\n")) {
                    game.setLeftPressed(true);
                } else if (serialString.equals("Right\r\n")){
                    //game.setRightPressed();
                }
            }
        });
    }
}

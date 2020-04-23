import com.fazecast.jSerialComm.*;

public class Main {
    public static SerialPort usedPort; // de te gebruiken port
    public static String serialString = "";

    public static void main(String[] args) {

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
                        stringBuffer = new String(newData, 0, numRead);
                        serialString += stringBuffer;
                    }

                System.out.print(serialString); //print wat de arduino doorgeeft
            }
        });
    }
}

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

/**
 * Command line program (and class) which reads the output from a serial port and outputs to stdout.
 * Optionally adds a time stamp to all lines. Great for logging values coming in from an Arduino for example.
 *
 * Adapted from the TwoWaySerialComm example at http://rxtx.qbang.org/wiki/index.php/Event_based_two_way_Communication 
 * SerialPortEventListener to avoid polling.
 *
 */
public class LogSerial
{
    private PrintStream outputLog = null;
    private boolean addTimeStamps = false;
    private SerialPort serialPort = null;

    public LogSerial(PrintStream log, boolean addTimeStamps) {
        super();
	this.outputLog = log;
	this.addTimeStamps = addTimeStamps;
    }

    public LogSerial() {
        super();
	this.outputLog = System.out;
    }
    
    public void connect ( String portName, int baudRate ) throws Exception {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() ) {
            System.out.println("Error: Port is currently in use.");
        }
        else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort ) {
                serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(baudRate ,SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                
                InputStream in = serialPort.getInputStream();
                serialPort.addEventListener(new SerialReader(in, outputLog, addTimeStamps));
                serialPort.notifyOnDataAvailable(true);
            }
            else {
                System.out.println("Error: Only serial ports supported!");
            }
        }     
    }

    public void disconnet() {
	serialPort.removeEventListener();
	serialPort.close();
    }
    
    /**
     * Handles the input coming from the serial port. A new line character
     * is treated as the end of a block in this example. 
     */
    public static class SerialReader implements SerialPortEventListener {
        private InputStream in;
	private PrintStream out;
	private boolean timestamps;
        private byte[] buffer = new byte[1024];
        
        public SerialReader (InputStream in, PrintStream out, boolean timestamps) {
            this.in = in;
	    this.out = out;
	    this.timestamps = timestamps;
        }
        
        public void serialEvent(SerialPortEvent arg0) {
            int data;          
            try {
                int len = 0;
                while ( ( data = in.read()) > -1 ) {
                    if ( data == '\n' ) {
                        break;
                    }
                    buffer[len++] = (byte) data;
                }
		if(timestamps)
		    out.print((new Date()).toString() + ",");
                out.print(new String(buffer,0,len)+"\r\n");
            }
            catch ( IOException e ) {
                e.printStackTrace();
                System.exit(-1);
            }             
        }
    }

    private static void usage() {
	 System.err.println("USAGE: java LogSerial <serial device> <baudrate> [-t for added timestamps] [logfile]");
    }

    public static void main ( String[] args ) {

	// Process command line options
	//	
	String deviceName = args[0];
	int baudRate = -1;
	boolean timestamps = false;
	PrintStream out = System.out;

	if(args.length < 2) {
	    usage();
	    return;
	}

	try { 
	    baudRate = Integer.parseInt(args[1]); 
	} catch(NumberFormatException e) {
	    System.err.println("Baudrate must be an integer number.");
	    usage();
	    return;
	}

	if(args.length >= 3 && args[2].equals("-t"))
	    timestamps = true;

	if(args.length == 3 && !args[2].equals("-t"))
	    try {
		out = new PrintStream(new FileOutputStream(args[2]), true);
	    } catch(FileNotFoundException e) {
		System.err.println("File " + args[2] + " is not a valid file.");
		usage();
		return;
	    }

	if(args.length == 4)
	    try {
		out = new PrintStream(new FileOutputStream(args[3]), true);
	    } catch(FileNotFoundException e) {
		System.err.println("File " + args[3] + " is not a valid file.");
		usage();
		return;
	    }


        try {
            (new LogSerial(out, timestamps)).connect(deviceName, baudRate);
        } catch ( Exception e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
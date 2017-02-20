package networkslab3;

/*
 * TextSender.java
 *
 * Created on 15 January 2003, 15:29
 */

/**
 *
 * @author  abj
 */
import CMPC3M06.AudioRecorder;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import uk.ac.uea.cmp.voip.*;

public class TextSenderThread implements Runnable{
    
    static DatagramSocket sending_socket;
    
    
    public void start(){
        Thread thread = new Thread(this);
	thread.start();
    }
    
    public void run (){
        AudioRecorder recorder = null;
        try {
            recorder = new AudioRecorder();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(TextSenderThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        //***************************************************
        //Port to send to
        int PORT = 55555;
        //IP ADDRESS to send to
        InetAddress clientIP = null;
	try {
		clientIP = InetAddress.getByName("localhost");  //CHANGE localhost to IP or NAME of client machine
	} catch (UnknownHostException e) {
                System.out.println("ERROR: TextSender: Could not find client IP");
		e.printStackTrace();
                System.exit(0);
	}
        //***************************************************
        
        //***************************************************
        //Open a socket to send from
        //We dont need to know its port number as we never send anything to it.
        //We need the try and catch block to make sure no errors occur.
        
        //DatagramSocket sending_socket;
        try{
		sending_socket = new DatagramSocket();
	} catch (SocketException e){
                System.out.println("ERROR: TextSender: Could not open UDP socket to send from.");
		e.printStackTrace();
                System.exit(0);
	}
        //***************************************************
      
        //***************************************************
        //Get a handle to the Standard Input (console) so we can read user input
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //***************************************************
        
        //***************************************************
        //Main loop.
        
//        boolean running = true;
        
//        int counter = 0;
        int i = 0;
//        int sequence = 0;
//        Integer count = 12;
        while (i < 1000){
            i++;
            try{
                //Read in a string from the standard input
                //String str = in.readLine();
//                String str = "This is test: " + counter++;
                try {
                    Thread.sleep(32);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TextSenderThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //Convert it to an array of bytes
//                byte[] buffer = str.getBytes();


                
//                byte[] buffer = new byte[516];
                byte[] buffer = recorder.getBlock();
//                ByteBuffer buffer2 = ByteBuffer.allocate(4);
//                byte[] bf2 = buffer2.putInt(i).array();
//                System.arraycopy(bf2, 0, buffer, 0, buffer2.capacity());
                               
                //Make a DatagramPacket from it, with client address and port number
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, clientIP, PORT);
            
                //Send it
                sending_socket.send(packet);
                
                //The user can type EXIT to quit
//                if (str.equals("EXIT")){
//                    running=false;
//                }

            } catch (IOException e){
                System.out.println("ERROR: TextSender: Some random IO error occured!");
                e.printStackTrace();
            }
        }
        //Close the socket
        sending_socket.close();
        //***************************************************
    }
} 

package networkslab3;

/*
 * TextReceiver.java
 *
 * Created on 15 January 2003, 15:43
 */

/**
 *
 * @author  abj
 */
import CMPC3M06.AudioPlayer;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import uk.ac.uea.cmp.voip.*;

public class TextReceiverThread implements Runnable{
    
    static DatagramSocket receiving_socket;
    
    public void start(){
        Thread thread = new Thread(this);
	thread.start();
    }
    
    public void run (){
        AudioPlayer player = null;
        try {
            player = new AudioPlayer();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(TextReceiverThread.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        ArrayList perfArray = new ArrayList();
        ArrayList printArray = new ArrayList();
        for (int i = 1; i <= 1001; i++){
            perfArray.add(i);
        }
        
        //***************************************************
        //Port to open socket on
        int PORT = 55555;
        //***************************************************
        
        //***************************************************
        //Open a socket to receive from on port PORT
        
        //DatagramSocket receiving_socket;
        try{
		receiving_socket = new DatagramSocket(PORT);
                receiving_socket.setSoTimeout(5000);
	} catch (SocketException e){
                System.out.println("ERROR: TextReceiver: Could not open UDP socket to receive from.");
		e.printStackTrace();
                System.exit(0);
	}
        //***************************************************
        
        //***************************************************
        //Main loop.
        
        boolean running = true;
        
//        int totalPacketLoss = 0;
//        int burstPacketLoss = 0;
        int packetNum = 0;
        ArrayList dataArray = new ArrayList();
        int j = 0;
        
        while (running){
            try{
               
                //Receive a DatagramPacket (note that the string cant be more than 80 chars)
                byte[] buffer = new byte[516];
                DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
                
                receiving_socket.receive(packet);
                player.playBlock(buffer);
                
                //Get a string from the byte buffer
//                String str = new String(buffer);

                byte[] bf2 = new byte[516];
                ByteBuffer buffer2 = ByteBuffer.allocate(4);
                for (int i = 0; i < buffer2.capacity(); i++){
                    bf2[i] = buffer[i];
                }
                ByteBuffer wrapper = ByteBuffer.wrap(bf2);
                packetNum = wrapper.getInt();
                System.out.println(packetNum);
                dataArray.add(packetNum);
//                System.out.print(dataArray.toString());
            } 
            
            
            
            catch (SocketTimeoutException e){
//                    totalPacketLoss++;
//                    burstPacketLoss++;
//                    System.out.println("Total Packet Loss: " + totalPacketLoss);
//                    System.out.println("Burst Packet Loss: " + burstPacketLoss);

                        for (int i = 1; i < dataArray.size(); i++){
//                            if (dataArray.contains(i)){
                                printArray.add(dataArray.get(i));
//                            }
//                            else{
//                                printArray.add(i * -1);
//                            }
                        }
                        System.out.println(dataArray.size());

                        System.out.println(printArray.toString());
//                    System.out.println(".");
            }
            catch (IOException e){
                System.out.println("ERROR: TextReceiver: Some random IO error occured!");
                e.printStackTrace();
            }
            
            
            
        }

        //Close the socket
        receiving_socket.close();
        //***************************************************
    }
}

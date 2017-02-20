/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkslab3;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @author ctf14tcu
 */
public class NewClass {
    public static void main (String[] args){
        byte[] buffer = new byte[516];
        ByteBuffer buffer2 = ByteBuffer.allocate(4);
        byte[] bf2 = buffer2.putInt(42).array();
        System.out.println(Arrays.toString(buffer));
        System.out.println(Arrays.toString(bf2));
        System.out.println(buffer2.capacity());
    }         
}

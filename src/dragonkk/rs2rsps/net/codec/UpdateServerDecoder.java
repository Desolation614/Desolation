package dragonkk.rs2rsps.net.codec;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import dragonkk.rs2rsps.io.InStream;
import dragonkk.rs2rsps.io.OutStream;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.rscache.CacheManager;
import dragonkk.rs2rsps.util.Constants;

public class UpdateServerDecoder {

    public static void decode(ConnectionHandler p, InStream in) {
        /**
         * Check version
         */
        if (p.getConnectionStage() == Constants.UPDATESERVER_PART1) {
        	if (in.remaining() < 4) {
                p.setConnectionStage(Constants.DISCONNECT);
                return;
            }
        	int revision = in.readInt();
        	OutStream outstream = new OutStream();
        	 if (revision != 614) {
        		 outstream.writeByte(6);
        		 p.write(outstream);
        		 p.setConnectionStage(Constants.DISCONNECT);
        		 return;
        	 }
        	 outstream.writeByte(0);
        	 p.write(outstream);
        	 p.setConnectionStage(Constants.UPDATESERVER_PART2);
        } else if (p.getConnectionStage() == Constants.UPDATESERVER_PART2) {
        	int opcode = in.readUnsignedByte();
        	if(opcode == 0 || opcode == 1) {
        		int data = in.read24BitInt();
        		int cache = data >> 16;
        		int file = data - (cache << 16);
        		if((cache == 255 && file == 255)) {
        			/*
        			byte[] bytes = Constants.idx255_File255;
        			OutStream outstream = new OutStream();
            		for (int key : bytes) {
                        outstream.writeByte(key);
                        in.skip(3);
                    }
                   in.skip(3);
                    p.write(outstream);
                   */ 
        		}else {
        			/*
        			byte[] bytes = Constants.idx255_File255;
        			OutStream outstream = new OutStream();
            		for (int key : bytes) {
                        outstream.writeByte(key);
                        in.skip(3);
                    }
                    p.write(outstream);
                    
        			byte[] bytes = CacheManager.getFile(cache, file);
        			OutStream outstream = new OutStream(bytes.length+8);
        			outstream.writeByte(cache);
        			outstream.writeShort(file);
        			outstream.writeByte(2);
        			outstream.writeInt(bytes.length);
            		for (byte key : bytes) {
                        outstream.writeByte(key);
                        in.skip(3);
                    }
                    
                    p.write(outstream);
                    */
                    
        		}
        	}else if(opcode == 2) {
            	if (in.remaining() < 1) {
                    p.setConnectionStage(Constants.DISCONNECT);
                    return;
                }
            	int status = in.readByte();
        	}else if(opcode == 3) {
            	if (in.remaining() < 1) {
                    p.setConnectionStage(Constants.DISCONNECT);
                    return;
                }
            	int status = in.readByte();
        	}else if(opcode == 6) {
            	if (in.remaining() < 1) {
                    p.setConnectionStage(Constants.DISCONNECT);
                    return;
                }
        		int status = in.readByte();
        	}else if(opcode == 7) {
        		p.setConnectionStage(Constants.DISCONNECT);
        	}else {
        		p.setConnectionStage(Constants.DISCONNECT);
        	}
        } else {
            p.setConnectionStage(Constants.DISCONNECT);
        }
    }
    
	public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
 
        // Get the size of the file
        long length = file.length();
    
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
    } 
    

}


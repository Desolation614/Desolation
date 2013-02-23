package dragonkk.rs2rsps.util;

import java.nio.ByteBuffer;

import dragonkk.rs2rsps.io.OutStream;

public class WorldList {

	private static OutStream worldConfigurationData;
	
	public WorldList() {
			Logger.lognoln(this, "Loading World List Configuration Data...");
			//worldConfigurationData = Client.getClientchannelhandler().getWorldListConfiguration();
			Logger.log(this, " Loaded World List Configuration Data.");
	}
	
    public static ByteBuffer getData(int request) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        boolean worldConfiguration = false;
        boolean worldStatus = false;
        if (request == 0) {
            worldConfiguration = true;
            worldStatus = true;
        } else if (request > 0 || request < 0) {
            worldConfiguration = false;
            worldStatus = true;
        }
        buffer.put((byte) (worldStatus ? 1 : 0));
        buffer.put((byte) (worldConfiguration ? 1 : 0));
        if (worldConfiguration)
        	buffer.put(worldConfigurationData.getBuffer());
       /* if (worldStatus)
				buffer.put(Client.getClientchannelhandler().getWorldListStatus().buffer());*/
        buffer.flip();
        ByteBuffer finalBuffer = ByteBuffer.allocate(buffer.limit() + 3);
        finalBuffer.put((byte) 0);
        finalBuffer.putShort((short) buffer.limit());
        finalBuffer.put(buffer);
        finalBuffer.flip();
        return finalBuffer;
    }
	
}

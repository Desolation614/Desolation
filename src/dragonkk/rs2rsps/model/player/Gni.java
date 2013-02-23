package dragonkk.rs2rsps.model.player;

import java.util.ArrayList;
import java.util.List;

import dragonkk.rs2rsps.io.OutStream;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.npc.Npc;


public class Gni {

	private Player player;
	private Npc[] localNpcs;
	private List<Integer> addedIndexes;
	private int newListIndex = 0;
	public Gni(Player player) {
		this.player = player;
		localNpcs = new Npc[256];
		addedIndexes = new ArrayList<Integer>();
	}
	
	public void sendUpdate() {
		player.getConnection().write(createPacket());
	}
	
	public OutStream createPacket() {
		OutStream packet = new OutStream();
		OutStream updateBlock = new OutStream();
		packet.writePacketVarShort(6);
		processGlobalNpcInform(packet, updateBlock);
		packet.writeBytes(updateBlock.buffer(), 0, updateBlock.offset());
		packet.endPacketVarShort();
		return packet;
	}
	
	private void processGlobalNpcInform(OutStream packet, OutStream updateBlock) {
		 processInScreenNpcs(packet, updateBlock);
		 addInScreenNpcs(packet, updateBlock);
	}
	
	
	private void addInScreenNpcs(OutStream packet,OutStream updateBlock) {
		for (Npc npc : World.getNpcs()) {
			if(newListIndex > 250)
				break;
			if(npc == null || addedIndexes.contains(npc.getIndex()) || !player.getLocation().withinDistance(npc.getLocation()))
				continue;
			packet.writeBits(15, npc.getIndex());
			packet.writeBits(14, npc.getId());
			packet.writeBits(1, 1);
	        int y = npc.getLocation().getY() - player.getLocation().getY();
	        if (y < 15)
	            y += 32;
	        int x = npc.getLocation().getX() - player.getLocation().getX();
	        if (x < 15)
	            x += 32;
			packet.writeBits(5, y);
			packet.writeBits(5, x);
			packet.writeBits(1, npc.getMask().isUpdateNeeded() ? 1 : 0);
			packet.writeBits(2, npc.getLocation().getZ());
			packet.writeBits(3, 0); //direction
			localNpcs[newListIndex++] = npc;
			addedIndexes.add(npc.getIndex());
			if (npc.getMask().isUpdateNeeded())
				appendUpdateBlock(npc, updateBlock);
		}
		if (updateBlock.offset() >= 3)
			packet.writeBits(15, 32767);
		packet.finishBitAccess();
	}
	
	private void processInScreenNpcs(OutStream packet,OutStream updateBlock) {
		int size = newListIndex;
		newListIndex = 0;
		packet.initBitAccess();
		packet.writeBits(8, size);
		for(int index = 0; index < size; index++) {
			Npc npc = localNpcs[index];
			if(npc == null || npc.isHidden() || !player.getLocation().withinDistance(npc.getLocation())) {
				packet.writeBits(1, 1);
				packet.writeBits(2, 3);
				if(npc != null)
					addedIndexes.remove((Object)npc.getIndex());
				continue;
			}
			localNpcs[newListIndex++] = npc;
			int sprite = npc.getWalk().getWalkDir();
			if(sprite == -1) {
				if(npc.getMask().isUpdateNeeded()) {
					packet.writeBits(1, 1);
					packet.writeBits(2, 0);
				} else {
					packet.writeBits(1, 0);
				}
			} else {
				packet.writeBits(1, 1);
				packet.writeBits(2, 1);
				packet.writeBits(3, sprite);
				packet.writeBits(1, npc.getMask().isUpdateNeeded() ? 1 : 0);
			}
			if (npc.getMask().isUpdateNeeded()) {
				appendUpdateBlock(npc, updateBlock);
			}
		}
	}
	
	private static void appendUpdateBlock(Npc n, OutStream stream) {
		int maskData = 0;
		if(n.getMask().isAnimationUpdate())
			maskData |= 0x4;
		if(n.getMask().isGraphicUpdate())
			maskData |= 0x10;
		if(n.getMask().isGraphic2Update())
			maskData |= 0x2000;
		if(n.getMask().isHit1Update())
			maskData |= 0x8;
		if(n.getMask().isHit2Update())
			maskData |= 0x400;
		if(n.getMask().isChatUpdate())
			maskData |= 0x80;
		
		
		if(maskData > 128)
			maskData |= 0x1;
		if(maskData > 32768) 
			maskData |= 0x800;
		stream.writeByte(maskData);
		if(maskData > 128)
			stream.writeByte(maskData >> 8);
		if(maskData > 32768)
			stream.writeByte(maskData  >> 16);
		
		
		if(n.getMask().isAnimationUpdate())
			applyAnimationMask(n, stream);
		if(n.getMask().isGraphicUpdate())
			applyGraphicMask(n, stream);
		if(n.getMask().isGraphic2Update())
			applyGraphic2Mask(n, stream);
		if(n.getMask().isHit1Update())
			applyHit1Mask(n, stream);
		if(n.getMask().isHit2Update())
			applyHit2Mask(n, stream);
		if(n.getMask().isChatUpdate())
			applyChatMask(n, stream);
	}
	
	
	private static void applyGraphicMask(Npc n, OutStream out) {
		out.writeShort128(n.getMask().getLastGraphics().getId());
		out.writeIntV1(n.getMask().getLastGraphics().getDelay());
		out.writeByte128(0);
	}
	
	private static void applyGraphic2Mask(Npc n, OutStream out) {
		out.writeShort(n.getMask().getLastGraphics().getId());
		out.writeIntV1(n.getMask().getLastGraphics().getDelay());
		out.writeByteC(100);
	}
	
	private static void applyHit1Mask(Npc n, OutStream out) {
		out.writeSmart(100);//n.getHits().getHitDamage1());
		out.writeByte128(3);
		int Amthp = n.getHp();
		int Maxhp = 100;
		if (Amthp > Maxhp)
			Amthp = Maxhp;
		out.writeByte128(Amthp*255/Maxhp);
	}
	
	private static void applyHit2Mask(Npc n, OutStream out) {
		out.writeSmart(100);
		out.writeByte(3);
	}

	private static void applyChatMask(Npc n, OutStream out) {
		out.writeString(n.getMask().getChatMessage().getChatText());
	}
	
	private static void applyAnimationMask(Npc n, OutStream outStream) {
		outStream.writeShortLE(n.getMask().getLastAnimation().getId());
		outStream.writeByte128(n.getMask().getLastAnimation().getDelay());
	}
	
}

package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i620 extends interfaceScript {

    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
       // System.out.println(packetId);
        int slot = buttonId2 / 6;
        World.getShopmanager().handleShopButtons(p, buttonId, packetId, slot);
    }
}

package dragonkk.rs2rsps.model.minigames;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.util.Misc;

/**
 *
 * @author James
 * @Modified by DownsX
 */

    public class DiceGame {

    	private transient static long lastRoll;
    	
    	public static void rollDice(Player player) {
        	if (!player.getInventory().contains(15098, 1)) {
                return;
        	}
        	if (System.currentTimeMillis() < lastRoll) {
        		return;
    		}
            int LOWEST = 1;
            int HIGHEST = 100;
            Random r = new Random();
    		player.animate(11900);
    		player.graphics(2075);
            generateNumber(LOWEST, HIGHEST, r, player);
            lastRoll = System.currentTimeMillis() + 2500;
        }

        private static void generateNumber(int lowest, int highest, Random r, Player player) {
            if (lowest > highest) {
                return;
            }
            long range = (long) highest - (long) lowest + 1;
            long fraction = (long) (range * r.nextDouble());
            int numberRolled = (int) (fraction + lowest);
            sendPrivateNumber(player, numberRolled);
            sendFriendsNumber(player, numberRolled);

        }

        private static void sendPrivateNumber(Player player, int numberRolled) {
        	player.getFrames().sendChatMessage(0, "You rolled a <col=E35252>" + numberRolled + "</col> " + "on the percentile dice");
        }
        private static void sendFriendsNumber(Player player, int numberRolled) {
        	for (Player pl : World.getPlayers()) {
        		if (pl.isDicer == false) {
        			pl.getFrames().sendChatMessage(0, "Player <col=E35252>" + Misc.formatPlayerNameForDisplay(player.getUsername())
                        + "</col> rolled a <col=E35252>" + numberRolled + "</col> " + "on the percentile dice");
        		}
            }
        }
    }
package dragonkk.rs2rsps.scripts.interfaces;


import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.Scripts;
import dragonkk.rs2rsps.scripts.interfaceScript;
import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.Task;

public class i193 extends interfaceScript {
			@Override
				public void actionButton(final Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
				switch(buttonId) {
				case 40: //Paddewwa teleport - goes to edge
					p.getCombatDefinitions().doEmote(8939, 1681, 1800);
					Server.getEntityExecutor().schedule(new Task() {
						@Override
						public void run() {
							p.getCombatDefinitions().doEmote(8941, 1681, 2400);
							p.getMask().getRegion().teleport(3087, 3496, 0, 0);
						}
					}, 1801);
						break;
			case 41: //Senntisten teleport - goes to unknown
				p.getCombatDefinitions().doEmote(8939, 1681, 1800);
				Server.getEntityExecutor().schedule(new Task() {
					@Override
					public void run() {
						p.getCombatDefinitions().doEmote(8941, 1681, 2400);
						p.getMask().getRegion().teleport(3322, 3337, 0, 0);
					}
				}, 1801);
					break;
			case 42: //Kharyrll teleport - goes to canifis
				p.getCombatDefinitions().doEmote(8939, 1681, 1800);
				Server.getEntityExecutor().schedule(new Task() {
					@Override
					public void run() {
						p.getCombatDefinitions().doEmote(8941, 1681, 2400);
						p.getMask().getRegion().teleport(3491, 3471, 0, 0);
					}
				}, 1801);
					break;	
			case 43: //Lasser teleport - goes to white mountion
				p.getCombatDefinitions().doEmote(8939, 1681, 1800);
				Server.getEntityExecutor().schedule(new Task() {
					@Override
					public void run() {
						p.getCombatDefinitions().doEmote(8941, 1681, 2400);
						p.getMask().getRegion().teleport(3006, 3480, 0, 0);
					}
				}, 1801);
					break;
			case 44: //Dareeyak teleport - goes to unknown
				p.getCombatDefinitions().doEmote(8939, 1681, 1800);
				Server.getEntityExecutor().schedule(new Task() {
					@Override
					public void run() {
						p.getCombatDefinitions().doEmote(8941, 1681, 2400);
						p.getMask().getRegion().teleport(2963, 3696, 0, 0);
					}
				}, 1801);
					break;
			case 45: //Carrallangar teleport - goes to unknown
				p.getCombatDefinitions().doEmote(8939, 1681, 1800);
				Server.getEntityExecutor().schedule(new Task() {
					@Override
					public void run() {
						p.getCombatDefinitions().doEmote(8941, 1681, 2400);
						p.getMask().getRegion().teleport(3156, 3666, 0, 0);
					}
				}, 1801);
					break;
			case 46: //Annakarl teleport
				p.getCombatDefinitions().doEmote(8939, 1681, 1800);
				Server.getEntityExecutor().schedule(new Task() {
					@Override
					public void run() {
						p.getCombatDefinitions().doEmote(8941, 1681, 2400);
						p.getMask().getRegion().teleport(3288, 3886, 0, 0);
					}
				}, 1801);
					break;
			case 47: //Ghorrock teleport - goes to west wilderness grave yard
				p.getCombatDefinitions().doEmote(8939, 1681, 1800);
				Server.getEntityExecutor().schedule(new Task() {
					@Override
					public void run() {
						p.getCombatDefinitions().doEmote(8941, 1681, 2400);
						p.getMask().getRegion().teleport(2979, 3751, 0, 0);
					}
				}, 1801);
					break;
			case 48: //home
				p.getCombatDefinitions().doEmote(8939, 1681, 1800);
				Server.getEntityExecutor().schedule(new Task() {
					@Override
					public void run() {
						p.getCombatDefinitions().doEmote(8941, 1681, 2400);
						p.getMask().getRegion().teleport(3087 ,3496, 0, 0);
					}
				}, 1801);
					break;
				}
			}
		}
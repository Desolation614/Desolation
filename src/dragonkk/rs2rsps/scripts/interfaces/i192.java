package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.Scripts;
import dragonkk.rs2rsps.scripts.interfaceScript;
import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.Task;

public class i192 extends interfaceScript {
			@Override
				public void actionButton(final Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
				switch(buttonId) {
			case 24:
				p.getCombatDefinitions().doEmote(8939, 1576, 1800);
				Server.getEntityExecutor().schedule(new Task() {
					@Override
					public void run() {
						p.getCombatDefinitions().doEmote(8941, 1577, 2400);
						p.getMask().getRegion().teleport(3087, 3496, 0, 0);
					}
				}, 1801);
					break;
			case 40:
				p.getCombatDefinitions().doEmote(8939, 1576, 1800);
				Server.getEntityExecutor().schedule(new Task() {
					@Override
					public void run() {
						p.getCombatDefinitions().doEmote(8941, 1577, 2400);
						p.getMask().getRegion().teleport(3212 ,3428, 0, 0);
					}
				}, 1801);
					break;
			case 72:
				p.getCombatDefinitions().doEmote(8939, 1576, 1800);
				Server.getEntityExecutor().schedule(new Task() {
					@Override
					public void run() {
						p.getCombatDefinitions().doEmote(8941, 1577, 2400);
						p.getMask().getRegion().teleport(2755 ,2784, 0, 0);
					}
				}, 1801);
					break;
			case 43:
				p.getCombatDefinitions().doEmote(8939, 1576, 1800);
				Server.getEntityExecutor().schedule(new Task() {
					@Override
					public void run() {
						p.getCombatDefinitions().doEmote(8941, 1577, 2400);
						p.getMask().getRegion().teleport(3221 ,3219, 0, 0);
					}
				}, 1801);
					break;
			case 46:
				p.getCombatDefinitions().doEmote(8939, 1576, 1800);
				Server.getEntityExecutor().schedule(new Task() {
					@Override
					public void run() {
						p.getCombatDefinitions().doEmote(8941, 1577, 2400);
						p.getMask().getRegion().teleport(2964 ,3380, 0, 0);
					}
				}, 1801);
					break;
			case 51:
				p.getCombatDefinitions().doEmote(8939, 1576, 1800);
				Server.getEntityExecutor().schedule(new Task() {
					@Override
					public void run() {
						p.getCombatDefinitions().doEmote(8941, 1577, 2400);
						p.getMask().getRegion().teleport(2757 ,3478, 0, 0);
					}
				}, 1801);
			case 57:
				p.getCombatDefinitions().doEmote(8939, 1576, 1800);
				Server.getEntityExecutor().schedule(new Task() {
					@Override
					public void run() {
						p.getCombatDefinitions().doEmote(8941, 1577, 2400);
						p.getMask().getRegion().teleport(2662 ,3308, 0, 0);
					}
				}, 1801);
					break;
			case 25://Wind Strike
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 3);
				p.getFrames().sendChatMessage(0, "You are now autocasting Wind strike.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Wind strike.");
				p.autocast = 0;
				}
				break;
			case 28://Water Strike
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 5);
				p.getFrames().sendChatMessage(0, "You are now autocasting Water strike.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Water strike.");
				p.autocast = 0;
				}
				break;
			case 30://Earth Strike
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 7);
				p.getFrames().sendChatMessage(0, "You are now autocasting Earth strike.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Earth strike.");
				p.autocast = 0;
				}
				break;
			case 32://Fire Strike
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 9);
				p.getFrames().sendChatMessage(0, "You are now autocasting Fire strike.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Fire strike.");
				p.autocast = 0;
				}
				break;
			case 34://Wind Bolt
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 11);
				p.getFrames().sendChatMessage(0, "You are now autocasting Wind bolt.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Wind bolt.");
				p.autocast = 0;
				}
				break;
			case 39://Water Bolt
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 13);
				p.getFrames().sendChatMessage(0, "You are now autocasting Water bolt.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Water bolt.");
				p.autocast = 0;
				}
				break;
			case 42://Earth Bolt
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 15);
				p.getFrames().sendChatMessage(0, "You are now autocasting Earth bolt.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Earth bolt.");
				p.autocast = 0;
				}
				break;
			case 45://Fire Bolt
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 17);
				p.getFrames().sendChatMessage(0, "You are now autocasting Fire bolt.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Fire bolt.");
				p.autocast = 0;
				}
				break;
			case 49://Wind Blast
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 19);
				p.getFrames().sendChatMessage(0, "You are now autocasting Wind blast.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Wind blast.");
				p.autocast = 0;
				}
				break;
			case 52://Water Blast
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 21);
				p.getFrames().sendChatMessage(0, "You are now autocasting Water blast.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Water blast.");
				p.autocast = 0;
				}
				break;
			case 58://Earth Blast
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 23);
				p.getFrames().sendChatMessage(0, "You are now autocasting Earth blast.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Earth blast.");
				p.autocast = 0;
				}
				break;
			case 38://Fire Blast
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 25);
				p.getFrames().sendChatMessage(0, "You are now autocasting Fire blast.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Fire blast.");
				p.autocast = 0;
				}
				break;
			case 70://Wind Wave
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 27);
				p.getFrames().sendChatMessage(0, "You are now autocasting Wind wave.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Wind wave.");
				p.autocast = 0;
				}
				break;
			case 73://Water Wave
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 29);
				p.getFrames().sendChatMessage(0, "You are now autocasting Water wave.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Water wave.");
				p.autocast = 0;
				}
				break;
			case 77://Earth Wave
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 31);
				p.getFrames().sendChatMessage(0, "You are now autocasting Earth wave.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Earth wave.");
				p.autocast = 0;
				}
				break;
			case 80://Fire Wave
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 33);
				p.getFrames().sendChatMessage(0, "You are now autocasting Fire wave.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Fire wave.");
				p.autocast = 0;
				}
				break;
			case 68://Zamorak flames
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 39);
				p.getFrames().sendChatMessage(0, "You are now autocasting Zamorak flames.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Zamorak flames.");
				p.autocast = 0;
				}
				break;
			case 67://Guthix claws
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 43);
				p.getFrames().sendChatMessage(0, "You are now autocasting Guthix claws.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Guthix claws.");
				p.autocast = 0;
				}
				break;
			case 66://Saradomin strike
				if(p.autocast == 0) {
				p.getFrames().sendConfig(108, 41);
				p.getFrames().sendChatMessage(0, "You are now autocasting Saradomin strike.");
				p.autocast = 1;
				} else if (p.autocast == 1) {
				p.getFrames().sendConfig(108, 0);
				p.getFrames().sendChatMessage(0, "You stop autocasting Saradomin strike.");
				p.autocast = 0;
				}
				break;
			case 84://Wind Surge
				if(p.autocast == 0) {
					p.getFrames().sendConfig(108, 47);
					p.getFrames().sendChatMessage(0, "You are now autocasting Wind Surge.");
					p.autocast = 1;
					} else if (p.autocast == 1) {
					p.getFrames().sendConfig(108, 0);
					p.getFrames().sendChatMessage(0, "You stop autocasting Wind Surge.");
					p.autocast = 0;
					}
				break;
			case 87://Water Surge
				if(p.autocast == 0) {
					p.getFrames().sendConfig(108, 49);
					p.getFrames().sendChatMessage(0, "You are now autocasting Water Surge.");
					p.autocast = 1;
					} else if (p.autocast == 1) {
					p.getFrames().sendConfig(108, 0);
					p.getFrames().sendChatMessage(0, "You stop autocasting Water Surge.");
					p.autocast = 0;
					}
				break;
			case 89://Earth Surge
				if(p.autocast == 0) {
					p.getFrames().sendConfig(108, 51);
					p.getFrames().sendChatMessage(0, "You are now autocasting Earth Surge.");
					p.autocast = 1;
					} else if (p.autocast == 1) {
					p.getFrames().sendConfig(108, 0);
					p.getFrames().sendChatMessage(0, "You stop autocasting Earth Surge.");
					p.autocast = 0;
					}
				break;
			case 91://Fire Surge
				if(p.autocast == 0) {
					p.getFrames().sendConfig(108, 53);
					p.getFrames().sendChatMessage(0, "You are now autocasting Fire Surge.");
					p.autocast = 1;
					} else if (p.autocast == 1) {
					p.getFrames().sendConfig(108, 0);
					p.getFrames().sendChatMessage(0, "You stop autocasting Fire Surge.");
					p.autocast = 0;
					}
				break;
		
				
			}
		}
	}
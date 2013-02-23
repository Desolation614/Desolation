package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i464 extends interfaceScript {

	@Override
	public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
		/*
		switch(buttonId) {
		case 2:
			p.getCombatDefinitions().doEmote(855, -1, 2000);
		break;
		case 3:
			p.getCombatDefinitions().doEmote(856, -1, 2000);
		break;
		case 4:
			p.getCombatDefinitions().doEmote(858, -1, 2000);
		break;
		case 5:
			p.getCombatDefinitions().doEmote(859, -1, 2000);
		break;
		case 6:
			p.getCombatDefinitions().doEmote(857, -1, 2000);
		break;
		case 7:
			p.getCombatDefinitions().doEmote(863, -1, 2000);
		break;
		case 8:
			p.getCombatDefinitions().doEmote(2113, -1, 2000);
		break;
		case 9:
			p.getCombatDefinitions().doEmote(862, -1, 2000);
		break;
		case 10:
			p.getCombatDefinitions().doEmote(864, -1, 2000);
		break;
		case 11:
			p.getCombatDefinitions().doEmote(2109, -1, 2000);
		break;
		case 12:
			p.getCombatDefinitions().doEmote(861, -1, 2000);
		break;
		case 13:
			p.getCombatDefinitions().doEmote(2111, -1, 2000);
		break;
		case 14:
			p.getCombatDefinitions().doEmote(866, -1, 2000);
		break;
		case 15:
			p.getCombatDefinitions().doEmote(2106, -1, 2000);
		break;
		case 16:
			p.getCombatDefinitions().doEmote(2107, -1, 2000);
		break;
		case 17:
			p.getCombatDefinitions().doEmote(2108, -1, 2000);
		break;
		case 18:
			p.getCombatDefinitions().doEmote(860, -1, 2000);
		break;
		case 19:
			p.getCombatDefinitions().doEmote(0x558, 574, 5000);
		break;
		case 20:
			p.getCombatDefinitions().doEmote(2105, -1, 2000);
		break;
		case 21:
			p.getCombatDefinitions().doEmote(2110, -1, 2000);
		break;
		case 22:
			p.getCombatDefinitions().doEmote(865, -1, 2000);
		break;
		case 23:
			p.getCombatDefinitions().doEmote(2112, -1, 2000);
		break;
		case 24:
			p.getCombatDefinitions().doEmote(0x84F, -1, 2000);
		break;
		case 25:
			p.getCombatDefinitions().doEmote(0x850, -1, 8000);
		break;
		case 26:
			p.getCombatDefinitions().doEmote(1131, -1, 2000);
		break;
		case 27:
			p.getCombatDefinitions().doEmote(1130, -1, 2000);
		break;
		case 28:
			p.getCombatDefinitions().doEmote(1129, -1, 2000);
		break;
		case 29:
			p.getCombatDefinitions().doEmote(1128, -1, 2000);
		break;
		case 30:
			p.getCombatDefinitions().doEmote(4275, -1, 2000);
		break;
		case 31:
			p.getCombatDefinitions().doEmote(1745, -1, 2000);
		break;
		case 32:
			p.getCombatDefinitions().doEmote(4280, -1, 2000);
		break;
		case 33:
			p.getCombatDefinitions().doEmote(4276, -1, 2000);
		break;
		case 34:
			p.getCombatDefinitions().doEmote(3544, -1, 8000);
		break;
		case 35:
			p.getCombatDefinitions().doEmote(3543, -1, 2000);
		break;
		case 36:
			p.getCombatDefinitions().doEmote(7272, 1244, 2000);
		break;
		case 37:
			p.getCombatDefinitions().doEmote(2836, -1, 2000);
		break;
		case 38:
			p.getCombatDefinitions().doEmote(6111, -1, 2000);
		break;
		case 39:
			doCapeEmote(p);
		break;
		case 40:
			p.getCombatDefinitions().doEmote(7531, -1, 5000);
		break;
		case 41:
			p.getCombatDefinitions().doEmote(2414, 1537, 5000);
		break;
		case 42:
			p.getCombatDefinitions().doEmote(8770, 1553, 5000);
		break;
		case 43:
			p.getCombatDefinitions().doEmote(9990, 1734, 5000);
		break;
		case 44:
			p.getCombatDefinitions().doEmote(10530, 1864, 5000);
		break;
		case 45:
			p.getCombatDefinitions().doEmote(11044, 1973, 5000);
		break;
		case 46:
			turkey(p);
		break;
		case 47:
			p.getCombatDefinitions().doEmote(11542, 2037, 4000);
		break;
		case 48:
			p.getCombatDefinitions().doEmote(12658, -1, 3000);
		break;
		default:
			if(p.getRights() > 1) {
				p.getFrames().sendChatMessage(0, "ButtonId: "+buttonId);
			}
		break;
		}
		*/
	}

	@SuppressWarnings("unused")
	private void turkey(Player p) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unused")
	private void doCapeEmote(final Player p) {
		switch(p.getEquipment().get(1).getId()) {
			case 9747:
			case 9748:
				p.getCombatDefinitions().doEmote(4959, 823, 4500);
			break;
			case 9753:
			case 9754:
				p.getCombatDefinitions().doEmote(4961, 824, 4550);
			break;
			case 9750:
			case 9751:
				p.getCombatDefinitions().doEmote(4981, 828, 10600);
			break;
			case 9768:
			case 9769:
				p.getCombatDefinitions().doEmote(4971, 833, 12000);
			break;
			case 9756:
			case 9757:
				p.getCombatDefinitions().doEmote(4973, 832, 6600);
			break;
			default:
				if(p.getRights() > 1) {
					p.getFrames().sendChatMessage(0, "Skillcape: "+p.getEquipment().get(1).getId());
				}
			break;
		}
	}

}

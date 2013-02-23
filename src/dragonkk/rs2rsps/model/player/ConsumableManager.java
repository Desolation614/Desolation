package dragonkk.rs2rsps.model.player;

import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.net.Packets;

public class ConsumableManager {
	
	/**
	 * An enum with the consumable types
	 * @author LAy
	 */
	public static enum ConsumableType {
		TELETAB,
		FOOD,
		POTION;
	}
	
	public static void handleConsumable(Player p, int slot, int id) {
		if(p.getSkills().isDead() || p == null)
			return;
		Item item = p.getInventory().getContainer().lookup(id);
		if(item == null)
			return;
		ConsumableType type = null;
		String consumableName = item.getDefinition().name.toLowerCase();
		String[] food = {"rocktail", "shark", "manta ray", "lobster", "summer pie", "swordfish"};
		String[] potion = {"(3)", "(4)", "(2)", "(1)"};
		if(Packets.checkArray(consumableName, food)) {
			type = ConsumableType.FOOD;
		}
		if(Packets.checkArray(consumableName, potion)) {
			type = ConsumableType.POTION;
		}
		if(consumableName.contains("teletab")) {
			type = ConsumableType.TELETAB;
		}
		if(type == null)
			return;
		handleConsumable(p, slot, type);
	}
	
	public static void handleConsumable(Player p, int slot, ConsumableType type) {
		int delay = 0;
		if(type == ConsumableType.FOOD) {
			delay = p.foodTimer;
		} else if(type == ConsumableType.POTION) {
			delay = p.potionTimer;
		}
		if(delay > 0 && type != ConsumableType.TELETAB)
			return;
		if(p.getSkills().isDead())
			return;
		Item itemClicked = p.getInventory().getContainer().get(slot);
		int itemId = -1;
		if(itemClicked == null)
			return;
		itemId = itemClicked.getId();
		p.getInventory().getContainer().set(slot, null);
		p.getCombat().resetDamage();
		if(type == ConsumableType.FOOD)
			p.getCombat().delay += 2;
		switch(itemId) {
		case 3040:
			handlePotionClick(p, itemId, slot, 3042, "magic");
		break;
		case 3042:
			handlePotionClick(p, itemId, slot, 3044, "magic");
		break;
		case 3044:
			handlePotionClick(p, itemId, slot, 3046, "magic");
		break;
		case 3046:
			handlePotionClick(p, itemId, slot, VIAL_ID, "magic");
		break;
		case 2444:
			handlePotionClick(p, itemId, slot, 169, "ranging");
		break;
		case 169:
			handlePotionClick(p, itemId, slot, 171, "ranging");
		break;
		case 171:
			handlePotionClick(p, itemId, slot, 173, "ranging");
		break;
		case 173:
			handlePotionClick(p, itemId, slot, VIAL_ID, "ranging");
		break;
		case 2440:
			handlePotionClick(p, itemId, slot, 157, "super strength");
		break;
		case 157:
			handlePotionClick(p, itemId, slot, 159, "super strength");
		break;
		case 159:
			handlePotionClick(p, itemId, slot, 161, "super strength");
		break;
		case 161:
			handlePotionClick(p, itemId, slot, VIAL_ID, "super strength");
		break;
		case 2436:
			handlePotionClick(p, itemId, slot, 145, "super attack");
		break;
		case 145:
			handlePotionClick(p, itemId, slot, 147, "super attack");
		break;
		case 147:
			handlePotionClick(p, itemId, slot, 149, "super attack");
		break;
		case 149:
			handlePotionClick(p, itemId, slot, VIAL_ID, "super attack");
		break;
		case 2442:
			handlePotionClick(p, itemId, slot, 163, "super defence");
		break;
		case 163:
			handlePotionClick(p, itemId, slot, 165, "super defence");
		break;
		case 165:
			handlePotionClick(p, itemId, slot, 167, "super defence");
		break;
		case 167:
			handlePotionClick(p, itemId, slot, VIAL_ID, "super defence");
		break;
		case 15308:
			handlePotionClick(p, itemId, slot, 15309, "extreme atk");
		break;
		case 15309:
			handlePotionClick(p, itemId, slot, 15310, "extreme atk");
		break;
		case 15310:
			handlePotionClick(p, itemId, slot, 15311, "extreme atk");
		break;
		case 15311:
			handlePotionClick(p, itemId, slot, VIAL_ID, "extreme atk");
		break;
		case 15312:
			handlePotionClick(p, itemId, slot, 15313, "extreme str");
		break;
		case 15313:
			handlePotionClick(p, itemId, slot, 15314, "extreme str");
		break;
		case 15314:
			handlePotionClick(p, itemId, slot, 15315, "extreme str");
		break;
		case 15315:
			handlePotionClick(p, itemId, slot, VIAL_ID, "extreme str");
		break;
		case 15316:
			handlePotionClick(p, itemId, slot, 15317, "extreme def");
		break;
		case 15317:
			handlePotionClick(p, itemId, slot, 15318, "extreme def");
		break;
		case 15318:
			handlePotionClick(p, itemId, slot, 15319, "extreme def");
		break;
		case 15319:
			handlePotionClick(p, itemId, slot, VIAL_ID, "extreme def");
		break;
		case 15320:
			handlePotionClick(p, itemId, slot, 15321, "extreme mage");
		break;
		case 15321:
			handlePotionClick(p, itemId, slot, 15322, "extreme mage");
		break;
		case 15322:
			handlePotionClick(p, itemId, slot, 15323, "extreme mage");
		break;
		case 15323:
			handlePotionClick(p, itemId, slot, VIAL_ID, "extreme mage");
		break;
		case 15324:
			handlePotionClick(p, itemId, slot, 15325, "extreme range");
		break;
		case 15325:
			handlePotionClick(p, itemId, slot, 15326, "extreme range");
		break;
		case 15326:
			handlePotionClick(p, itemId, slot, 15327, "extreme range");
		break;
		case 15327:
			handlePotionClick(p, itemId, slot, VIAL_ID, "extreme range");
		break;
		case 15332:
			handlePotionClick(p, itemId, slot, 15333, "overload");
		break;
		case 15333:
			handlePotionClick(p, itemId, slot, 15334, "overload");
		break;
		case 15334:
			handlePotionClick(p, itemId, slot, 15335, "overload");
		break;
		case 15335:
			handlePotionClick(p, itemId, slot, VIAL_ID, "overload");
		break;
		case 6685:
			handlePotionClick(p, itemId, slot, 6687, "brew");
		break;
		case 6687:
			handlePotionClick(p, itemId, slot, 6689, "brew");
		break;
		case 6689:
			handlePotionClick(p, itemId, slot, 6691, "brew");
		break;
		case 6691:
			handlePotionClick(p, itemId, slot, VIAL_ID, "brew");
		break;
		case 3024:
			handlePotionClick(p, itemId, slot, 3026, "super restore");
		break;
		case 3026:
			handlePotionClick(p, itemId, slot, 3028, "super restore");
		break;
		case 3028:
			handlePotionClick(p, itemId, slot, 3030, "super restore");
		break;
		case 3030:
			handlePotionClick(p, itemId, slot, VIAL_ID, "super restore");
		break;
		case 15272:
		case 391:
			handleFoodClick(p, itemId, 230, slot, -1);
		break;
		case 385:
			handleFoodClick(p, itemId, 200, slot, -1);
		break;
		case 7218:
			handleFoodClick(p, itemId, 110, slot, 7720);
		break;
		case 7720:
			handleFoodClick(p, itemId, 110, slot, 2313);
		break;
		}
	}
	
	public static void handleFoodClick(Player p, int id, int heal, int slot, int replaceWith) {
		if(p.getSkills().isDead())
			return;
		Item foodClicked = new Item(id);
		if(p.getCombat().shieldDelay < 1) {
			p.animate(829);
		}
		if(replaceWith != -1) {
			p.getInventory().getContainer().set(slot, new Item(replaceWith));
		}
		p.foodTimer += 3;
		p.getSkills().heal(heal);
		p.getFrames().sendChatMessage(0, "You eat the "+foodClicked.getDefinition().name.toLowerCase()+".");
		p.getFrames().sendChatMessage(0, "It heals some health.");
		p.getInventory().refresh();
	}
	
	public static void handlePotionClick(Player p, int id, int slot, int replaceWith, String effect) {
		if(p.getSkills().isDead())
			return;
		Item foodClicked = new Item(id);
		if(replaceWith != -1) {
			p.getInventory().getContainer().set(slot, new Item(replaceWith));
		}
		if(effect.contains("extreme")) {
			if(!p.isDonator) {
				p.getFrames().sendChatMessage(0, "You need to be a donator to use this potion.");
				p.getInventory().getContainer().set(slot, new Item(id));
				p.getInventory().refresh();
				return;
			}
		}
		p.potionTimer += 2;
		String msg = "";
		String defaultMsg = "You drink some of your "+foodClicked.getDefinition().name.toLowerCase().replaceAll("(4)", "").replaceAll("(3)", "").replaceAll("(2)", "").replaceAll("(1)", "")+".";
		if(effect.equalsIgnoreCase("brew")) {
			msg = "You drink some of the foul liquid.";
			p.getSkills().healBrew(160);
			p.getSkills().set(Skills.DEFENCE, (int)(p.getSkills().getLevelForXp(Skills.DEFENCE)*1.25));
			p.getSkills().set(Skills.STRENGTH, (int)(p.getSkills().getLevelForXp(Skills.STRENGTH) * 0.9));
			p.getSkills().set(Skills.ATTACK, (int)(p.getSkills().getLevelForXp(Skills.ATTACK) * 0.9));
			p.getSkills().set(Skills.RANGE, (int)(p.getSkills().getLevelForXp(Skills.RANGE) * 0.9));
			p.getSkills().set(Skills.MAGIC, (int)(p.getSkills().getLevelForXp(Skills.MAGIC) * 0.9));
			
			/*
			int[] drink = {Skills.ATTACK, Skills.DEFENCE, Skills.RANGE, Skills.STRENGTH, Skills.MAGIC};
			for(int i = 0; i < drink.length; i++) {
				int skill = drink[i];
				if(skill == Skills.DEFENCE) {
					int defenceModification = (int) (p.getSkills().getLevelForXp(Skills.DEFENCE) * 0.25);
					p.getSkills().increaseLevelToMaximumModification(skill, defenceModification);
				} else {
					int modification = (int) (p.getSkills().getLevelForXp(skill) * 0.10);
					p.getSkills().decreaseLevelOnce(skill, modification);
				}
			}
			*/
			
		}
		if(effect.equalsIgnoreCase("super strength")) {
			int skill = Skills.STRENGTH;
			int modification = (int) Math.floor(5 + (p.getSkills().getLevelForXp(skill) * 0.15));
			p.getSkills().increaseLevelToMaximumModification(skill, modification);
		}
		if(effect.equalsIgnoreCase("super attack")) {
			int skill = Skills.ATTACK;
			int modification = (int) Math.floor(5 + (p.getSkills().getLevelForXp(skill) * 0.15));
			p.getSkills().increaseLevelToMaximumModification(skill, modification);
		}
		if(effect.equalsIgnoreCase("super defence")) {
			int skill = Skills.DEFENCE;
			int modification = (int) Math.floor(5 + (p.getSkills().getLevelForXp(skill) * 0.15));
			p.getSkills().increaseLevelToMaximumModification(skill, modification);
			
		}
		if(effect.equalsIgnoreCase("super restore") || effect.equalsIgnoreCase("restore")) {
			int[] skills = {Skills.ATTACK, Skills.DEFENCE, Skills.STRENGTH, Skills.PRAYER, Skills.RANGE, Skills.MAGIC};

			for(int i : skills) {
				int modification = (int) (p.getSkills().getLevelForXp(i) / 3);
				if(i == Skills.PRAYER) {
					p.getSkills().RestorePray(modification);
				} else {
					p.getSkills().increaseLevelToMaximum(i, modification);
				}
			}
		}
		if(effect.equalsIgnoreCase("overload")) {
		    if (!p.extremeDonator) {
                p.getFrames().sendChatMessage(0, "The God's of pvp choose not to aid you in battle.");
    		    p.getInventory().getContainer().set(slot, new Item(id));
    		    p.getInventory().refresh();
                return;
		    }
			p.getSkills().set(Skills.STRENGTH, p.getSkills().getLevelForXp(Skills.STRENGTH)+5+ Math.round(p.getSkills().getLevelForXp(Skills.STRENGTH)*22/100));
			p.getSkills().set(Skills.DEFENCE, p.getSkills().getLevelForXp(Skills.DEFENCE)+5+ Math.round(p.getSkills().getLevelForXp(Skills.DEFENCE)*22/100));
			p.getSkills().set(Skills.ATTACK, p.getSkills().getLevelForXp(Skills.ATTACK)+5+ Math.round(p.getSkills().getLevelForXp(Skills.ATTACK)*22/100));
			p.getSkills().set(Skills.RANGE, p.getSkills().getLevelForXp(Skills.RANGE)+5+ Math.round(p.getSkills().getLevelForXp(Skills.RANGE)*5/100));
			p.getSkills().set(Skills.MAGIC, p.getSkills().getLevelForXp(Skills.MAGIC)+5+ Math.round(p.getSkills().getLevelForXp(Skills.MAGIC)*5/100));
		}
		if(effect.equalsIgnoreCase("ranging")) {
			p.getSkills().set(Skills.RANGE, p.getSkills().getLevelForXp(Skills.RANGE)+5+ Math.round(p.getSkills().getLevelForXp(Skills.RANGE)*2/100));
		}
		if(effect.equalsIgnoreCase("magic")) {
			p.getSkills().set(Skills.MAGIC, p.getSkills().getLevelForXp(Skills.MAGIC)+5+ Math.round(p.getSkills().getLevelForXp(Skills.MAGIC)*2/100));
		}
		if(effect.equalsIgnoreCase("extreme str")) {
			p.getSkills().set(Skills.STRENGTH, p.getSkills().getLevelForXp(Skills.STRENGTH)+5+ Math.round(p.getSkills().getLevelForXp(Skills.STRENGTH)*22/100));
		}
		if(effect.equalsIgnoreCase("extreme atk")) {
			p.getSkills().set(Skills.ATTACK, p.getSkills().getLevelForXp(Skills.ATTACK)+5+ Math.round(p.getSkills().getLevelForXp(Skills.ATTACK)*22/100));
		}
		if(effect.equalsIgnoreCase("extreme def")) {
			p.getSkills().set(Skills.DEFENCE, p.getSkills().getLevelForXp(Skills.DEFENCE)+5+ Math.round(p.getSkills().getLevelForXp(Skills.DEFENCE)*22/100));
		}
		if(effect.equalsIgnoreCase("extreme range")) {
			p.getSkills().set(Skills.RANGE, p.getSkills().getLevelForXp(Skills.RANGE)+5+ Math.round(p.getSkills().getLevelForXp(Skills.RANGE)*5/100));
		}
		if(effect.equalsIgnoreCase("extreme mage")) {
			p.getSkills().set(Skills.MAGIC, p.getSkills().getLevelForXp(Skills.MAGIC)+5+ Math.round(p.getSkills().getLevelForXp(Skills.MAGIC)*5/100));
		}
		if(msg.equalsIgnoreCase("")) {
			msg = defaultMsg;
		}
		p.getFrames().sendChatMessage(0, msg.replace("()", "potion"));
		Item replaceItem = new Item(replaceWith);
		String name = replaceItem.getDefinition().name.toLowerCase();
		if(name.contains("(3)")) {
			msg = "You have 3 doses left.";
		}
		if(name.contains("(2)")) {
			msg = "You have 2 doses left.";
		}
		if(name.contains("(1)")) {
			msg = "You have 1 dose left.";
		}
		if(name.contains("vial")) {
			msg = "You have finished your potion.";
		}
		p.getFrames().sendChatMessage(0, msg);
		p.getSkills().refresh();
		if(p.getCombat().shieldDelay < 1) {
			p.animate(829);
		}
		p.getInventory().refresh();
	}
	
	public static final int VIAL_ID = 229;

}

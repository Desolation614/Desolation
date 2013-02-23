package dragonkk.rs2rsps.scripts;

import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.player.Equipment;
import dragonkk.rs2rsps.model.player.Inventory;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;

/**
 * @author Alex
 * 
 */
public class itemScript {

	public void option1(Player p, int itemId, int interfaceId, int slot) {
		if(p == null) {
			return;
		}
		if(p.isDead()) {
			return;
		}
		int[] donatorOnly = { 13887, 13888, 13889, 13893, 13894, 13895, 13899, 13900,
				13901, 13905, 13906, 13907, 13911, 13912, 13913, 13917, 13918,
				13919, 13923, 13924, 13925, 13929, 13930, 13931, 13884, 13885,
				13886, 13890, 13891, 13892, 13896, 13897, 13898, 13902, 13903,
				13904, 13908, 13909, 13910, 13914, 13915, 13916, 13920, 13921,
				13922, 13926, 13927, 13928, 13858, 13859, 13860, 13861, 13862,
				13863, 13864, 13865, 13866, 13867, 13868, 13869, 13932, 13933,
				13934, 13935, 13936, 13937, 13938, 13939, 13940, 13941, 13942,
				13943, 13870, 13871, 13872, 13873, 13874, 13875, 13876, 13877,
				13878, 13879, 13880, 13881, 13882, 13883, 13944, 13945, 13946,
				13947, 13948, 13949, 13950, 13951, 13952, 13953, 13954, 13955,
				13956, 13957, 11696, 11697, 11704, 11705, 11728, 11729, 13451,
				13458, 13459, 13460, 
				11702, 11703, 11718, 11719, 11720, 11721, 11722, 11723, 12670,
				12671, 13450, 13455, 13456, 13457, 14085, 13734, 13735, 13736,
				13737, 13738, 13739, 13740, 13741, 13742, 13743, 13744, 13745, 19709 };
		if(interfaceId == 387) {
			if(slot < 0 || itemId < 0) {
				return;
			}
			if(slot <= 15 && p.getEquipment().get(slot) != null) {
				if(!p.getInventory().addItem(p.getEquipment().get(slot).getDefinition().getId(), p.getEquipment().get(slot).getAmount())) {
					return;
				}
				p.getEquipment().set(slot, null);
			}
		}
		if (interfaceId == 149) {
			if(System.currentTimeMillis() < p.getCombatDefinitions().getLastEmote()-600) {
				return;
			}
			if (slot < 0 || slot >= Inventory.SIZE)
				return;
			Item item = p.getInventory().getContainer().get(slot);
			if (item == null)
				return;
			if (item.getId() != itemId)
				return;
			int targetSlot = Equipment.getItemType(itemId);
			if (targetSlot == -1) {
				return;
			}
			for(int i : donatorOnly ){
				if(itemId == i) {
					if(!p.isDonator) {
						p.getFrames().sendChatMessage(0, "In order to wear this item you must be a donator.");
						return;
					} else {
						break;
					}
				}
			}
			if (Equipment.isTwoHanded(item.getDefinition())
					&& p.getInventory().getFreeSlots() < 1
					&& p.getEquipment().get(5) != null) {
				p.getFrames().sendChatMessage((byte) 0,
						"Not enough free space in your inventory.");
				return;
			}
			if (Equipment.isTwoHanded(item.getDefinition())
					&& p.getInventory().getFreeSlots() < 1
					&& p.getEquipment().get(5) != null) {
				p.getFrames().sendChatMessage((byte) 0,
						"Not enough free space in your inventory.");
				return;
			}
			boolean hasReq = true;
			if(item.getDefinition().skillRequirimentId != null) {
			for (int skillIndex = 0; skillIndex < item.getDefinition().skillRequirimentId.size(); skillIndex++) {
				int reqId = item.getDefinition().skillRequirimentId.get(skillIndex);
				int reqLvl = -1;
				if(item.getDefinition().skillRequirimentLvl.size() > skillIndex)
				reqLvl = item.getDefinition().skillRequirimentLvl.get(skillIndex);
				if(reqId > 25 || reqId < 0 || reqLvl < 0 || reqLvl > 120)
					continue;
				if(p.getSkills().getLevelForXp(reqId) < reqLvl) {
					if(hasReq)
					p.getFrames().sendChatMessage((byte) 0, "You are not high enough level to use this item.");
					p.getFrames().sendChatMessage((byte) 0, "You need to have a "+(Skills.SKILL_NAME[reqId].toLowerCase())+" level of "+reqLvl+".");
					hasReq = false;
				}
			}
			}
			if(!hasReq)
				return;
			p.getInventory().deleteItem(item.getDefinition().getId(),
					item.getAmount());
			if (targetSlot == 3) {
				if (Equipment.isTwoHanded(item.getDefinition())
						&& p.getEquipment().get(5) != null) {
					if (!p.getInventory().addItem(
							p.getEquipment().get(5).getDefinition().getId(),
							p.getEquipment().get(5).getAmount())) {
						p.getInventory().addItem(itemId, item.getAmount());
						return;
					}
					p.getEquipment().set(5, null);
				}
			} else if (targetSlot == 5) {

				if (p.getEquipment().get(3) != null
						&& Equipment.isTwoHanded(p.getEquipment().get(3)
								.getDefinition())) {
					if (!p.getInventory().addItem(
							p.getEquipment().get(3).getDefinition().getId(),
							p.getEquipment().get(3).getAmount())) {
						p.getInventory().addItem(itemId, item.getAmount());
						return;
					}
					p.getEquipment().set(3, null);
				}

			}
			if (p.getEquipment().get(targetSlot) != null
					&& (itemId != p.getEquipment().get(targetSlot)
							.getDefinition().getId() || !item.getDefinition()
							.isStackable())) {
				p.getInventory().addItem(
						p.getEquipment().get(targetSlot).getDefinition()
								.getId(),
						p.getEquipment().get(targetSlot).getAmount());
				p.getEquipment().set(targetSlot, null);
			}
			int oldAmt = 0;
			if (p.getEquipment().get(targetSlot) != null) {
				oldAmt = p.getEquipment().get(targetSlot).getAmount();
			}
			Item item2 = new Item(itemId, oldAmt + item.getAmount());
			p.getEquipment().set(targetSlot, item2);

		}

	}

	public void drop(Player p, int itemId, int interfaceId, int slot) {
		Item item = p.getInventory().getContainer().get(slot);
		if (item == null)
			return;
		if (item.getId() != itemId)
			return;
		p.getFrames().sendGroundItem(p.getLocation(), p.getInventory().getContainer().get(slot), false);
		p.getInventory().deleteItem(itemId, 1);
	}
	
	public void drop(Player p, int itemId, int interfaceId, int slot, int amount) {
		Item item = p.getInventory().getContainer().get(slot);
		if (item == null)
			return;
		if (item.getId() != itemId)
			return;
		p.getFrames().sendGroundItem(p.getLocation(), p.getInventory().getContainer().get(slot), false);
		p.getInventory().deleteItem(itemId, amount);
	}
	
	public void select(Player p) {
	}

	public void operate(Player p) {
	}

	public void thisonitem(Player p, short onItem) {
	}

	public void thisonobject(Player p, int onObject) {
	}

	public void thisonplayer(Player p, Player onPlayer) {
	}

}

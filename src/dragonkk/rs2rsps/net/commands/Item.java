package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Item implements Command {

	@SuppressWarnings("static-access")
	public void execute(String[] args, Player p) {

		if (!p.getCombat().isSafe(p)) {
			p.getFrames().sendChatMessage(0,
					"<col=244080><shad=000000>You cannot use this command in the wilderness.");
			return;
		}
		int[] UNSPAWNS = { 995, 1039, 1041, 1043, 1045, 1047, 1040, 1042, 1044, 1046, 1048, 1049, 2422,
        13884, 13885, 13886, 13890, 13891, 13892,
				13896, 13897, 13898, 13899, 13900, 13901, 13738, 13739, 13740,
				13741, 13742, 13743, 13744, 13745, 15098, 15099, 18349, 18351,
				18353, 11695, 11697, 11699, 11701, 6575, 6576, 1419, 15098, 15099, 19709 };
		 int[] donators = { 13887, 13888, 13893, 13894, 13899, 13900, 13905,
		 13906, 13912, 13913, 13917, 13918, 13923, 13924, 13929, 13930, 13736,
		 13737, 13738, 13739, 13740, 13741, 13742, 13743, 13744, 13745 };
		int amount = Integer.parseInt(args[2]);
		int[][] coinPrices = { { 11694, 77600000 }, { 14484, 32200000 },
				{ 14485, 32200000 }, { 10551, 10 },
				{ 13738, 62000000 }, { 13739, 62000000 }, { 13740, 200000000 },
				{ 13740, 200000000 }, { 13741, 200000000 }, { 13742, 100000000 },
				{ 13743, 100000000 }, { 13744, 32000000 }, { 13745, 32000000 },
				{ 18349, 200000000 },{ 18351, 200000000 }, { 18353, 200000000 }, { 18355, 200000000 }, { 18356, 200000000 }, { 18359, 200000000 },  
                { 4708, 1000000 }, { 4710, 1500000 }, { 4712, 1500000 }, {4714, 1000000 },
				{19669, 125000000}, {19709, 160000000}};
		int item = Integer.parseInt(args[1]);
		if (amount < 1) {
			amount = 1;
		}
		if (amount < 1) {
			amount = 1;
		}
		if (Integer.parseInt(args[1]) == 995 || item >= 13887
				&& item <= 13943 && !p.isDonator) {
			return;
		}
		for (int[] data : coinPrices) {
			if (data[0] == item) {
				if (amount > 1) {
					amount = 1;
				}
				if (p.getInventory().contains(995, data[1] * amount)) {
					if (p.getInventory().hasRoomFor(item, amount)) {
						p.getInventory().deleteItem(995, data[1] * amount);
						p.getInventory().addItem(item, amount);
						return;
					} else {
						p.getFrames()
								.sendChatMessage(0,
										"<col=244080><shad=000000>You don't have enough space in your inventory to buy this.");
						return;
					}
				} else {
					p.getFrames()
							.sendChatMessage(0,
									"<col=244080><shad=000000>You don't have enough coins to purchase this item;");
					p.getFrames().sendChatMessage(
							0,
							"<col=244080><shad=000000>To spawn this item you need " + data[1]
									+ " coins.");
					return;
				}
			}
		}
		for (int i : p.getSkills().PVP_DROP) {
			if (Integer.parseInt(args[1]) == i) {
				p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>This item is unspawnable.");
				return;
			}
		}
		for (int i : UNSPAWNS) {
			if (Integer.parseInt(args[1]) == i) {
				p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>This item is unspawnable.");
				return;
			}
		}
		p.getInventory().addItem(Integer.parseInt(args[1]), amount);

	}

	/*
	 * public void execute(String[] args, Player p) {
	 * 
	 * if(!p.getCombat().isSafe(p)){ p.getFrames().sendChatMessage(0,
	 * "You cannot use this command in the wilderness."); return; } int[]
	 * UNSPAWNS = {995, 13884, 13885, 13886, 13890, 13891, 13892, 13896, 13897,
	 * 13898, 13899, 13900, 13901, 13738, 13739, 13740, 13741, 13742, 13743,
	 * 13744, 13745, 6570, 8850, 18349, 18351, 18353, 11695, 11697, 11699,
	 * 11701}; //int[] donators = { 13887, 13888, 13893, 13894, 13899, 13900,
	 * 13905, 13906, 13912, 13913, 13917, 13918, 13923, 13924, 13929, 13930,
	 * 13736, 13737, 13738, 13739, 13740, 13741, 13742, 13743, 13744, 13745 };
	 * int amount = Integer.parseInt(args[2]); int[][] coinPrices = { {11694,
	 * 750}, {11695, 750}, {14484, 500}, {14485, 500}, {8850, 75}, {10551, 150},
	 * {6570, 200}, {13738, 350},{13739, 350}, {13740, 1000}, {13740, 1000},
	 * {13741, 1000}, {13742 , 700}, {13743, 700}, {13744 , 300}, {13745, 300},
	 * {18349, 500}, {18351, 500}, {18353 , 500}, {18355, 500}, {18356 , 500},
	 * {18359, 500}}; int item = Integer.parseInt(args[1]); if(amount < 1) {
	 * amount = 1; } if(Integer.parseInt(args[1]) == 995 || item >= 13887 &&
	 * item <= 13943 && !p.isDonator) { return; } for(int[] data : coinPrices)
	 * { if(amount > 1) { amount = 1; } if(data[0] == Integer.parseInt(args[1]))
	 * { if(p.getInventory().contains(995, data[1] * amount)) {
	 * if(p.getInventory().hasRoomFor(Integer.parseInt(args[1]), amount)) {
	 * p.getInventory().deleteItem(995, data[1] * amount);
	 * p.getInventory().addItem(Integer.parseInt(args[1]), amount); return; }
	 * else { p.getFrames().sendChatMessage(0,
	 * "You don't have enough space in your inventory to buy this."); return; }
	 * } else { p.getFrames().sendChatMessage(0,
	 * "You don't have enough coins to purchase this item;");
	 * p.getFrames().sendChatMessage(0,
	 * "To spawn this item you need "+data[1]+" coins."); return; } } }
	 * for(int i : p.getSkills().PVP_DROP) { if(Integer.parseInt(args[1]) == i)
	 * { p.getFrames().sendChatMessage(0, "This item is unspawnable."); return;
	 * } } for(int i : UNSPAWNS) { if(Integer.parseInt(args[1]) == i) {
	 * p.getFrames().sendChatMessage(0, "This item is unspawnable."); return; }
	 * } p.getInventory().addItem(Integer.parseInt(args[1]), amount);
	 * 
	 * }
	 */
	public final int[][] ITEM_PRICES = { { 11849, 20000 }, // Barrows -
																// Dharok's Set
			{ 18349, 200 }, // C Rapier
			{ 18351, 200 }, // C Longsword
			{ 18353, 200 }, // C Maul
			{ 18357, 300 }, // C Cbow
			{ 18355, 200 }, // C Staff
			{ 18359, 200 }, // C Staff
			{ 14484, 250 }, // d claws
			{ 13450, 350 }, // Armadyl Godsword
			{ 1048, 4000 }, // phat
			{ 1038, 4000 }, // Yellow Partyhat
			{ 1040, 4000 }, // Blue Partyhat
			{ 1042, 4000 }, // Green Partyhat
			{ 1044, 4000 }, // Purple Partyhat
			{ 1046, 4000 }, // White Partyhat
			{ 13736, 10 }, // Blessed Spirit Shield
			{ 13738, 350 }, // Arcane Spirit Shield
			{ 13740, 500 }, // Divine Spirit Shield
			{ 13742, 400 }, // Elysian Spirit Shield
			{ 13744, 250 }, // Spectral Spirit Shield
      { 19669, 300  }, //vigour
      { 19709, 1000 } //dungcape
	};

	public int getItemPrice(int itemId) {
		for (int[] data : ITEM_PRICES) {
			if (itemId == data[0] || itemId + 1 == data[0]) {
				return data[1];
			}
		}
		return 1;
	}

}

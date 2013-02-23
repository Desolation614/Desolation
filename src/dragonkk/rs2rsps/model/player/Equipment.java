package dragonkk.rs2rsps.model.player;

import java.io.Serializable;

import dragonkk.rs2rsps.model.Container;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.rscache.ItemDefinitions;

public class Equipment implements Serializable {

	private static final long serialVersionUID = -2934091862414178786L;
	private transient Player player;
	public static final byte SLOT_HAT = 0, SLOT_CAPE = 1, SLOT_AMULET = 2,
			SLOT_WEAPON = 3, SLOT_CHEST = 4, SLOT_SHIELD = 5, SLOT_LEGS = 7,
			SLOT_HANDS = 9, SLOT_FEET = 10, SLOT_RING = 12, SLOT_ARROWS = 13;
	private Container<Item> equipment = new Container<Item>(15, false);

	public Container<Item> getEquipment() {
		return equipment;
	}

	public void setPlayer(Player player) {
		for (Item item : getEquipment().getItems())
			if (item != null)
				item.setDefinition(ItemDefinitions.forID(item.getId()));
		this.player = player;
	}

	public boolean contains(int item) {
		return equipment.containsOne(new Item(item));
	}

	public void deleteItem(int item, int amount) {
		equipment.remove(new Item(item, amount));
		refresh();
	}

	public Item get(int slot) {
		return equipment.get(slot);
	}

	public void set(int slot, Item item) {
		equipment.set(slot, item);
		 if (slot == 3)
			 player.getCombatDefinitions().setSpecialOff();
		refresh();
	}

	public void clear() {
		equipment.clear();
		refresh();
	}

	public void refresh() {
		// if (player.isOnline())
		// player.getActionSender().SendSound(2248,100,0);
		player.getMask().setApperanceUpdate(true);
		player.getFrames().sendItems(94, equipment, false);
		player.getCombatDefinitions().refreshBonuses();
		player.getCombat().removeTarget();
		//player.getCombatDefinitions().setSpecialOff();
	}

	public static int CAPES[] = { 1007, 1019, 1021, 1023, 1027, 1029, 1031,
			1052, 2412, 2413, 2414, 3759, 3761, 3763, 3765, 3777, 3779, 3781,
			3783, 3785, 3787, 3789, 4304, 4315, 4317, 4319, 4321, 4323, 4325,
			4327, 4329, 4331, 4333, 4335, 4337, 4339, 4341, 4343, 4345, 4347,
			4349, 4351, 4353, 4355, 4357, 4359, 4361, 4363, 4365, 4367, 4369,
			4371, 4373, 4375, 4377, 4379, 4381, 4383, 4385, 4387, 4389, 4391,
			4393, 4395, 4397, 4399, 4401, 4403, 4405, 4407, 4409, 4411, 4413,
			4514, 4516, 6070, 6111, 6568, 6570, 7535, 7918, 9074, 9101, 9747,
			9748, 9750, 9751, 9753, 9754, 9756, 9757, 9759, 9760, 9762, 9763,
			9765, 9766, 9768, 9769, 9771, 9772, 9774, 9775, 9777, 9778, 9780,
			9781, 9783, 9784, 9786, 9787, 9789, 9790, 9792, 9793, 9795, 9796,
			9798, 9799, 9801, 9802, 9804, 9805, 9807, 9808, 9810, 9811, 9813,
			9948, 9949, 10069, 10071, 10073, 10171, 10446, 10448, 10450, 10498,
			10499, 10635, 10636, 10637, 10638, 10639, 10640, 10641, 10642,
			10643, 10644, 10645, 10646, 10647, 10648, 10649, 10650, 10651,
			10652, 10653, 10654, 10655, 10656, 10657, 10658, 10659, 10660,
			10661, 10662, 10663, 10664, 10720, 12169, 12170, 12524, 12645,
			13443, 14080, 14081, 14088, 14387, 14389, 14641, 14642, 15215,
			15345, 15347, 15349, 15352, 15432, 15433, 15706, 18508, 18509,
			18739, 18740, 18741, 18742, 18743, 19311, 19313, 19368, 19370,
			19372, 19709, 19710, 19748, 19893, 20068 };
	public static int HATS[] = { 4502, 74, 579, 656, 658, 660, 662, 664, 1017,
			1025, 1037, 1038, 1040, 1042, 1044, 1046, 1048, 1050, 1053, 1055,
			1057, 1137, 1139, 1141, 1143, 1145, 1147, 1149, 1151, 1153, 1155,
			1157, 1159, 1161, 1163, 1165, 1167, 1169, 1506, 2581, 2587, 2595,
			2603, 2611, 2619, 2627, 2631, 2633, 2635, 2637, 2639, 2641, 2643,
			2645, 2647, 2649, 2651, 2657, 2665, 2673, 2900, 2910, 2920, 2930,
			2940, 2978, 2979, 2980, 2981, 2982, 2983, 2984, 2985, 2986, 2987,
			2988, 2989, 2990, 2991, 2992, 2993, 2994, 2995, 3057, 3327, 3329,
			3331, 3333, 3335, 3337, 3339, 3341, 3343, 3385, 3486, 3748, 3749,
			3751, 3753, 3755, 3797, 4071, 4089, 4099, 4109, 4164, 4166, 4168,
			4302, 4506, 4511, 4513, 4515, 4551, 4567, 4708, 4716, 4724, 4732,
			4745, 4753, 4856, 4857, 4858, 4859, 4860, 4880, 4881, 4882, 4883,
			4884, 4904, 4905, 4906, 4907, 4908, 4928, 4929, 4930, 4931, 4932,
			4952, 4953, 4954, 4955, 4956, 4976, 4977, 4978, 4979, 4980, 5013,
			5014, 5525, 5527, 5529, 5531, 5533, 5535, 5537, 5539, 5541, 5543,
			5545, 5547, 5549, 5551, 5554, 5574, 6109, 6128, 6131, 6137, 6181,
			6188, 6326, 6335, 6337, 6339, 6345, 6355, 6365, 6375, 6382, 6392,
			6400, 6547, 6548, 6621, 6623, 6656, 6665, 6856, 6858, 6860, 6862,
			6885, 6886, 6887, 6918, 7003, 7112, 7124, 7130, 7136, 7319, 7321,
			7323, 7325, 7327, 7394, 7396, 7400, 7534, 7539, 7594, 7917, 8464,
			8466, 8468, 8470, 8472, 8474, 8476, 8478, 8480, 8482, 8484, 8486,
			8488, 8490, 8492, 8494, 8682, 8684, 8686, 8688, 8690, 8692, 8694,
			8696, 8698, 8700, 8702, 8704, 8706, 8708, 8710, 8712, 8901, 8903,
			8905, 8907, 8909, 8911, 8913, 8915, 8917, 8919, 8921, 8924, 8925,
			8926, 8927, 8928, 8949, 8950, 8959, 8960, 8961, 8962, 8963, 8964,
			8965, 9068, 9069, 9096, 9472, 9629, 9672, 9729, 9733, 9749, 9752,
			9755, 9758, 9761, 9764, 9767, 9770, 9773, 9776, 9779, 9782, 9785,
			9788, 9791, 9794, 9797, 9800, 9803, 9806, 9809, 9812, 9814, 9920,
			9925, 9945, 9946, 9950, 10039, 10045, 10051, 10286, 10288, 10290,
			10292, 10294, 10296, 10298, 10300, 10302, 10304, 10306, 10308,
			10310, 10312, 10314, 10334, 10342, 10350, 10374, 10382, 10390,
			10392, 10398, 10452, 10454, 10456, 10507, 10547, 10548, 10549,
			10550, 10589, 10601, 10602, 10603, 10604, 10605, 10606, 10608,
			10609, 10612, 10613, 10614, 10615, 10616, 10617, 10629, 10692,
			10693, 10694, 10695, 10699, 10700, 10701, 10702, 10703, 10704,
			10705, 10706, 10707, 10708, 10709, 10710, 10711, 10712, 10713,
			10721, 10722, 10723, 10728, 10740, 10746, 10758, 10760, 10762,
			10764, 10766, 10768, 10770, 10772, 10774, 10796, 10802, 10804,
			10806, 10828, 10836, 10862, 10883, 10941, 11021, 11200, 11277,
			11278, 11280, 11282, 11335, 11663, 11664, 11665, 11674, 11675,
			11676, 11718, 11789, 11790, 12171, 12204, 12206, 12207, 12209,
			12210, 12212, 12213, 12215, 12216, 12218, 12219, 12221, 12222,
			12224, 12559, 12658, 12659, 12660, 12661, 12662, 12663, 12664,
			12665, 12666, 12667, 12668, 12669, 12670, 12671, 12672, 12673,
			12674, 12675, 12676, 12677, 12678, 12679, 12680, 12681, 12866,
			12867, 12868, 12869, 12870, 12871, 12887, 12888, 12889, 12890,
			12891, 12892, 12936, 12937, 12938, 12939, 12940, 12941, 12943,
			12944, 12945, 12946, 12947, 12948, 12950, 12951, 12952, 12953,
			12966, 12967, 12968, 12969, 13101, 13103, 13105, 13107, 13109,
			13111, 13113, 13115, 13166, 13167, 13168, 13169, 13170, 13171,
			13172, 13173, 13188, 13189, 13190, 13191, 13192, 13193, 13194,
			13263, 13277, 13299, 13339, 13340, 13341, 13342, 13343, 13344,
			13345, 13346, 13347, 13348, 13349, 13350, 13351, 13352, 13353,
			13354, 13355, 13356, 13357, 13370, 13372, 13374, 13376, 13377,
			13378, 13396, 13397, 13398, 13399, 13400, 13407, 13408, 13409,
			13410, 13411, 13414, 13417, 13449, 13455, 13495, 13496, 13501,
			13508, 13513, 13518, 13531, 13532, 13533, 13534, 13535, 13536,
			13537, 13538, 13539, 13540, 13546, 13550, 13554, 13558, 13615,
			13616, 13620, 13621, 13625, 13626, 13655, 13667, 13672, 13680,
			13684, 13685, 13686, 13687, 13688, 13689, 13690, 13763, 13783,
			13784, 13789, 13792, 13794, 13795, 13796, 13797, 13798, 13799,
			13803, 13808, 13810, 13811, 13812, 13813, 13814, 13815, 13816,
			13817, 13818, 13819, 13823, 13828, 13833, 13838, 13864, 13866,
			13876, 13878, 13896, 13898, 13920, 13922, 13938, 13940, 13950,
			13952, 13961, 13963, 14096, 14116, 14120, 14337, 14339, 14341,
			14343, 14345, 14367, 14369, 14371, 14373, 14375, 14411, 14413,
			14415, 14417, 14419, 14494, 14499, 14594, 14631, 14636, 14637,
			14662, 14663, 14731, 14733, 14743, 14745, 14747, 14749, 14751,
			14753, 14755, 14757, 14759, 14761, 14763, 14765, 14767, 14769,
			14771, 14773, 14775, 14777, 14779, 14781, 14783, 14785, 14787,
			14789, 14791, 15021, 15027, 15033, 15039, 15069, 15071, 15214,
			15336, 15337, 15338, 15339, 15422, 15488, 15490, 15492, 15496,
			15497, 15509, 15590, 15599, 15602, 15608, 15614, 15620, 15673,
			15828, 15892, 15893, 15894, 15895, 15896, 15897, 15898, 15899,
			15900, 15901, 15902, 15914, 15915, 15916, 15917, 15918, 15919,
			15920, 15921, 15922, 15923, 15924, 16046, 16047, 16048, 16049,
			16050, 16051, 16052, 16053, 16054, 16055, 16056, 16691, 16693,
			16695, 16697, 16699, 16701, 16703, 16705, 16707, 16709, 16711,
			16735, 16737, 16739, 16741, 16743, 16745, 16747, 16749, 16751,
			16753, 16755, 17041, 17043, 17045, 17047, 17049, 17051, 17053,
			17055, 17057, 17059, 17061, 17279, 18510, 18693, 18708, 18744,
			18745, 18746, 18776, 19272, 19274, 19275, 19277, 19278, 19280,
			19281, 19283, 19284, 19286, 19287, 19289, 19290, 19292, 19293,
			19295, 19296, 19298, 19299, 19301, 19302, 19304, 19305, 19307,
			19314, 19316, 19336, 19341, 19374, 19376, 19378, 19407, 19409,
			19422, 19424, 19437, 19439, 19449, 19457, 19465, 19496, 19497,
			19498, 19499, 19500, 19501, 19502, 19503, 19504, 19505, 19506,
			19507, 19708, 19747, 19763, 19783, 20046, 20070, 20077 };
	public static int BOOTS[] = { 88, 89, 626, 628, 630, 632, 634, 1061, 1837,
			1846, 2577, 2579, 2894, 2904, 2914, 2924, 2934, 3061, 3105, 3107,
			3393, 3791, 4097, 4107, 4117, 4119, 4121, 4123, 4125, 4127, 4129,
			4131, 4310, 5345, 5557, 6069, 6106, 6143, 6145, 6147, 6328, 6349,
			6357, 6367, 6377, 6619, 6666, 6790, 6920, 7114, 7159, 7596, 9005,
			9006, 9073, 9100, 9638, 9644, 9921, 10552, 10607, 10689, 10696,
			10724, 10839, 10865, 10933, 10958, 11019, 11728, 11732, 12565,
			13420, 13421, 13422, 13460, 13462, 13505, 13512, 13517, 13522,
			13556, 13557, 13675, 13679, 13683, 13703, 13704, 13704, 13705,
			13706, 13782, 14571, 14572, 14573, 14605, 15025, 15031, 15037,
			15043, 15065, 15786, 15787, 15788, 15789, 15790, 15791, 15792,
			15793, 15794, 15795, 15796, 15821, 15822, 15823, 15824, 15829,
			15830, 16002, 16003, 16004, 16005, 16006, 16007, 16008, 16009,
			16010, 16011, 16012, 16262, 16263, 16264, 16265, 16266, 16267,
			16268, 16269, 16270, 16271, 16272, 16339, 16341, 16343, 16345,
			16347, 16349, 16351, 16353, 16355, 16357, 16359, 16911, 16913,
			16915, 16917, 16919, 16921, 16923, 16925, 16927, 16929, 16931,
			17265, 17267, 17269, 17271, 17281, 17283, 17297, 17299, 17301,
			17303, 17305, 17307, 17309, 17311, 17313, 17315, 17317, 18788,
			19766, 19776 };
	public static int GLOVES[] = { 775, 776, 777, 778, 1059, 1063, 1065, 1495,
			1580, 2487, 2489, 2491, 2902, 2912, 2922, 2932, 2942, 2997, 3060,
			3391, 4095, 4105, 4115, 4308, 5556, 6068, 6110, 6149, 6151, 6153,
			6330, 6347, 6359, 6369, 6379, 6629, 6720, 6922, 7453, 7454, 7455,
			7456, 7457, 7458, 7459, 7460, 7461, 7462, 7537, 7595, 8842, 8929,
			9072, 9099, 9922, 10075, 10077, 10079, 10081, 10083, 10085, 10336,
			10368, 10376, 10384, 10553, 10554, 10725, 11069, 11072, 11074,
			11076, 11079, 11081, 11083, 11085, 11088, 11090, 11092, 11095,
			11097, 11099, 11101, 11103, 11115, 11118, 11120, 11122, 11124,
			11126, 11130, 11133, 11136, 11138, 11140, 12629, 12856, 12857,
			12858, 12859, 12860, 12861, 12862, 12863, 12864, 12865, 12985,
			12986, 12988, 12989, 12991, 12992, 12994, 12995, 12997, 12998,
			13000, 13001, 13003, 13004, 13006, 13007, 13423, 13424, 13425,
			13497, 13498, 13499, 13500, 13504, 13511, 13516, 13521, 13547,
			13623, 13628, 13660, 13845, 13846, 13847, 13848, 13849, 13850,
			13851, 13852, 13853, 13854, 13855, 13856, 13857, 14602, 15026,
			15032, 15038, 15044, 15819, 15820, 15936, 15937, 15938, 15939,
			15940, 15941, 15942, 15943, 15944, 15945, 15946, 16105, 16106,
			16107, 16108, 16109, 16110, 16111, 16112, 16113, 16114, 16115,
			16185, 16186, 16187, 16188, 16189, 16190, 16191, 16192, 16193,
			16194, 16195, 16273, 16275, 16277, 16279, 16281, 16283, 16285,
			16287, 16289, 16291, 16293, 17151, 17153, 17155, 17157, 17159,
			17161, 17163, 17165, 17167, 17169, 17171, 17195, 17197, 17199,
			17201, 17203, 17205, 17207, 17209, 17211, 17213, 17215, 17261,
			17263, 18347, 19443, 19451, 19459, 19754 };
	public static int SHIELDS[] = { 1171, 1173, 1175, 1177, 1179, 1181, 1183,
			1185, 1187, 1189, 1191, 1193, 1195, 1197, 1199, 1201, 1540, 2589,
			2597, 2605, 2613, 2621, 2629, 2659, 2667, 2675, 2890, 3122, 3488,
			3758, 3840, 3842, 3844, 4072, 4224, 4225, 4226, 4227, 4228, 4229,
			4230, 4231, 4232, 4233, 4234, 4507, 4512, 6215, 6217, 6219, 6221,
			6223, 6225, 6227, 6229, 6231, 6233, 6235, 6237, 6239, 6241, 6243,
			6245, 6247, 6249, 6251, 6253, 6255, 6257, 6259, 6261, 6263, 6265,
			6267, 6269, 6271, 6273, 6275, 6277, 6279, 6524, 6631, 6633, 6889,
			7051, 7053, 7332, 7334, 7336, 7338, 7340, 7342, 7344, 7346, 7348,
			7350, 7352, 7354, 7356, 7358, 7360, 7676, 8714, 8716, 8718, 8720,
			8722, 8724, 8726, 8728, 8730, 8732, 8734, 8736, 8738, 8740, 8742,
			8744, 8746, 8748, 8750, 8752, 8754, 8756, 8758, 8760, 8762, 8764,
			8766, 8768, 8770, 8772, 8774, 8776, 8844, 8845, 8846, 8847, 8848,
			8849, 8850, 8856, 9704, 9731, 10352, 10665, 10666, 10667, 10668,
			10669, 10670, 10671, 10672, 10673, 10674, 10675, 10676, 10677,
			10678, 10679, 10826, 10877, 10878, 10879, 10880, 10881, 10882,
			11283, 11284, 12908, 12909, 12910, 12911, 12912, 12913, 12915,
			12916, 12917, 12918, 12919, 12920, 12922, 12923, 12924, 12925,
			12926, 12927, 12929, 12930, 12931, 12932, 12933, 12934, 13506,
			13507, 13555, 13734, 13736, 13738, 13740, 13742, 13744, 13787,
			13788, 13804, 13809, 13824, 13829, 13834, 13839, 13964, 13966,
			14577, 14578, 14579, 15439, 15440, 15808, 15809, 15810, 15811,
			15812, 15813, 15814, 15815, 15816, 15817, 15818, 15825, 15831,
			15832, 16079, 16933, 16971, 17273, 17285, 17287, 17341, 17343,
			17345, 17347, 17349, 17351, 17353, 17355, 17357, 17359, 17361,
			18340, 18346, 18359, 18360, 18361, 18362, 18363, 18364, 18691,
			18709, 18747, 19340, 19345, 19410, 19412, 19425, 19427, 19440,
			19442, 19613, 19615, 19617, 19712, 19749, 19865, 19866, 19867,
			19868, 19889, 20072 };
	public static int AMULETS[] = { 86, 295, 421, 552, 589, 774, 1009, 1478,
			1654, 1656, 1658, 1660, 1662, 1664, 1692, 1694, 1696, 1698, 1700,
			1702, 1704, 1706, 1708, 1710, 1712, 1716, 1718, 1722, 1724, 1725,
			1727, 1729, 1731, 1796, 3208, 3853, 3855, 3857, 3859, 3861, 3863,
			3865, 3867, 4021, 4035, 4081, 4306, 4677, 5521, 6040, 6041, 6544,
			6577, 6579, 6581, 6585, 6857, 6859, 6861, 6863, 7803, 8923, 9470,
			10344, 10354, 10356, 10358, 10360, 10362, 10364, 10366, 10470,
			10472, 10474, 10500, 10719, 10736, 10738, 11105, 11107, 11109,
			11111, 11113, 11128, 11190, 11191, 11192, 11193, 11194, 11195,
			11666, 11667, 11668, 11669, 11670, 11671, 11672, 11673, 12608,
			12610, 12612, 12614, 12616, 12618, 12620, 12622, 13442, 13551,
			14596, 14599, 15126, 15393, 15394, 15395, 15396, 15397, 15418,
			15511, 15833, 15834, 16138, 16139, 16140, 16141, 17289, 17291,
			18333, 18334, 18335, 19335, 19392, 19394, 19396, 19513, 19832,
			19886, 19887, 19888, 19892, 20064, 20065, 20066, 20067 };
	public static int ARROWS[] = { 78, 598, 877, 878, 879, 880, 881, 882, 883,
			884, 885, 886, 887, 888, 889, 890, 891, 892, 893, 942, 2532, 2533,
			2534, 2535, 2536, 2537, 2538, 2539, 2540, 2541, 2866, 4160, 4740,
			4773, 4783, 4788, 4793, 4798, 4803, 5616, 5617, 5618, 5619, 5620,
			5621, 5622,  5627, 6061, 6062, 8882, 9139,
			9140, 9141, 9142, 9143, 9144, 9145, 9236, 9237, 9238, 9239, 9240,
			9241, 9242, 9243, 9244, 9245, 9286, 9287, 9288, 9289, 9290, 9291,
			9292, 9293, 9294, 9295, 9296, 9297, 9298, 9299, 9300, 9301, 9302,
			9303, 9304, 9305, 9306, 9335, 9336, 9337, 9338, 9339, 9340, 9341,
			9342, 9706, 11212, 11217, 11222, 11227, 11228, 11229, 13083, 13084,
			13085, 13086, 13280, 14202, 14203, 14204, 14205, 14206, 15243,
			15947, 15948, 15949, 15950, 15951, 15952, 15953, 15954, 15955,
			15956, 15957, 15958, 15959, 15960, 15961, 15962, 15963, 15964,
			15965, 15966, 15967, 15968, 15969, 15970, 15971, 15972, 15973,
			15974, 15975, 15976, 15977, 15978, 15979, 15980, 15981, 15982,
			15983, 15984, 15985, 15986, 15987, 15988, 15989, 15990, 16427,
			16432, 16437, 16442, 16447, 16452, 16457, 16462, 16467, 16472,
			16477, 16482, 16487, 16492, 16497, 16502, 16507, 16512, 16517,
			16522, 16527, 16532, 16537, 16542, 16547, 16552, 16557, 16562,
			16567, 16572, 16577, 16582, 16587, 16592, 16597, 16602, 16607,
			16612, 16617, 16622, 16627, 16632, 16637, 16642, 19152, 19157,
			19162 };
	public static int RINGS[] = { 773, 1635, 1637, 1639, 1641, 1643, 1645,
			2550, 2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566, 2568, 2570,
			2572, 4202, 4657, 6575, 6583, 6731, 6733, 6735, 6737, 7927, 9104,
			10729, 11014, 13281, 13282, 13283, 13284, 13285, 13286, 13287,
			13288, 13426, 13427, 13428, 13429, 13560, 13561, 13562, 13659,
			15009, 15010, 15011, 15012, 15013, 15014, 15015, 15016, 15017,
			15018, 15019, 15020, 15078, 15080, 15082, 15220, 15398, 15399,
			15400, 15401, 15402, 15707, 18817, 18818, 18819, 18820, 18821,
			18822, 18823, 18824, 18825, 18826, 18827, 18828, 19669, 19672,
			19673, 19674, 19760, 20053, 20054 };
	public static int BODY[] = { 75, 284, 426, 430, 540, 544, 546, 577, 581,
			636, 638, 640, 642, 644, 1005, 1035, 1101, 1103, 1105, 1107, 1109,
			1111, 1113, 1115, 1117, 1119, 1121, 1123, 1125, 1127, 1129, 1131,
			1133, 1135, 1757, 1833, 1844, 2405, 2499, 2501, 2503, 2583, 2591,
			2599, 2607, 2615, 2623, 2653, 2661, 2669, 2896, 2906, 2916, 2926,
			2936, 3058, 3140, 3387, 3481, 3767, 3769, 3771, 3773, 3775, 3793,
			4069, 4091, 4101, 4111, 4298, 4504, 4509, 4712, 4720, 4728, 4736,
			4749, 4757, 4868, 4869, 4870, 4871, 4872, 4892, 4893, 4894, 4895,
			4896, 4916, 4917, 4918, 4919, 4920, 4940, 4941, 4942, 4943, 4944,
			4964, 4965, 4966, 4967, 4968, 4988, 4989, 4990, 4991, 4992, 5024,
			5026, 5028, 5030, 5032, 5034, 5553, 5575, 6065, 6107, 6129, 6133,
			6139, 6180, 6184, 6186, 6322, 6341, 6351, 6361, 6371, 6384, 6388,
			6394, 6615, 6617, 6654, 6750, 6786, 6916, 7110, 7122, 7128, 7134,
			7362, 7364, 7370, 7372, 7374, 7376, 7390, 7392, 7399, 7592, 8839,
			8952, 8953, 8954, 8955, 8956, 8957, 8958, 9070, 9097, 9634, 9640,
			9674, 9924, 9944, 10037, 10043, 10049, 10053, 10057, 10061, 10065,
			10316, 10318, 10320, 10322, 10324, 10330, 10338, 10348, 10370,
			10378, 10386, 10400, 10404, 10408, 10412, 10416, 10420, 10424,
			10428, 10432, 10436, 10458, 10460, 10462, 10551, 10564, 10610,
			10611, 10618, 10619, 10620, 10621, 10622, 10623, 10624, 10625,
			10626, 10627, 10628, 10630, 10631, 10632, 10633, 10634, 10680,
			10681, 10682, 10683, 10684, 10685, 10686, 10687, 10688, 10690,
			10691, 10697, 10698, 10714, 10715, 10716, 10717, 10718, 10727,
			10748, 10750, 10752, 10754, 10756, 10776, 10778, 10780, 10782,
			10784, 10786, 10788, 10790, 10792, 10794, 10798, 10800, 10822,
			10837, 10863, 10939, 10945, 10954, 11020, 11720, 11724, 11756,
			11757, 11758, 12563, 12873, 12874, 12875, 12876, 12877, 12878,
			12894, 12895, 12896, 12897, 12898, 12899, 12971, 12972, 12973,
			12974, 12975, 12976, 13181, 13182, 13183, 13184, 13185, 13186,
			13187, 13297, 13316, 13358, 13360, 13362, 13412, 13415, 13418,
			13456, 13458, 13481, 13482, 13483, 13484, 13485, 13486, 13502,
			13509, 13514, 13519, 13544, 13548, 13553, 13614, 13619, 13624,
			13668, 13673, 13677, 13681, 13691, 13692, 13693, 13694, 13695,
			13696, 13781, 13785, 13800, 13805, 13820, 13825, 13830, 13835,
			13858, 13860, 13870, 13872, 13884, 13886, 13887, 13889, 13908,
			13910, 13911, 13913, 13932, 13934, 13944, 13946, 13958, 13960,
			14076, 14078, 14086, 14094, 14114, 14118, 14317, 14319, 14321,
			14323, 14325, 14347, 14349, 14351, 14353, 14355, 14391, 14393,
			14395, 14397, 14399, 14479, 14481, 14492, 14497, 14593, 14595,
			14600, 14601, 14730, 14732, 14936, 15022, 15028, 15034, 15040,
			15423, 15424, 15503, 15600, 15606, 15612, 15618, 15837, 15838,
			15839, 15840, 15841, 15842, 15843, 15844, 15845, 15846, 15847,
			16013, 16014, 16015, 16016, 16017, 16018, 16019, 16020, 16021,
			16022, 16023, 16068, 16069, 16070, 16071, 16072, 16073, 16074,
			16075, 16076, 16077, 16078, 16080, 16081, 16082, 16083, 16084,
			16085, 16086, 16087, 16088, 16089, 16090, 16713, 16715, 16717,
			16719, 16721, 16723, 16725, 16727, 16729, 16731, 16733, 17173,
			17175, 17177, 17179, 17181, 17183, 17185, 17187, 17189, 17191,
			17193, 17217, 17219, 17221, 17223, 17225, 17227, 17229, 17231,
			17233, 17235, 17237, 17239, 17241, 17243, 17245, 17247, 17249,
			17251, 17253, 17255, 17257, 17259, 18695, 18697, 18699, 18706,
			19167, 19173, 19179, 19181, 19188, 19194, 19200, 19202, 19209,
			19215, 19221, 19223, 19230, 19236, 19242, 19244, 19251, 19257,
			19263, 19265, 19317, 19319, 19337, 19342, 19380, 19382, 19384,
			19398, 19400, 19413, 19415, 19428, 19430, 19445, 19453, 19461,
			19481, 19482, 19483, 19484, 19485, 19486, 19487, 19488, 19489,
			19490, 19491, 19492, 19493, 19494, 19495, 19508, 19509, 19510,
			19511, 19512, 19514, 19515, 19516, 19517, 19518, 19519, 19706,
			19735, 19757, 19781, 19785, 19787, 19789, 20044 };
	public static int LEGS[] = { 10341, 285, 428, 538, 542, 548, 646, 648, 650,
			652, 654, 1011, 1013, 1015, 1033, 1067, 1069, 1071, 1073, 1075,
			1077, 1079, 1081, 1083, 1085, 1087, 1089, 1091, 1093, 1095, 1097,
			1099, 1835, 1845, 2493, 2495, 2497, 2585, 2593, 2601, 2609, 2617,
			2625, 2655, 2663, 2671, 2898, 2908, 2918, 2928, 2938, 3059, 3389,
			3472, 3473, 3474, 3475, 3476, 3477, 3478, 3479, 3480, 3483, 3485,
			3795, 4070, 4087, 4093, 4103, 4113, 4300, 4505, 4510, 4585, 4714,
			4722, 4730, 4738, 4751, 4759, 4874, 4875, 4876, 4877, 4878, 4898,
			4899, 4900, 4901, 4902, 4922, 4923, 4924, 4925, 4926, 4946, 4947,
			4948, 4949, 4950, 4970, 4971, 4972, 4973, 4974, 4994, 4995, 4996,
			4997, 4998, 5036, 5038, 5040, 5042, 5044, 5046, 5048, 5050, 5052,
			5553, 5555, 5576, 6067, 6108, 6130, 6135, 6141, 6181, 6185, 6187,
			6324, 6343, 6353, 6363, 6373, 6386, 6390, 6396, 6398, 6404, 6406,
			6625, 6627, 6655, 6752, 6787, 6813, 6924, 7116, 7126, 7132, 7138,
			7366, 7368, 7378, 7380, 7382, 7384, 7386, 7388, 7398, 7593, 8840,
			8991, 8992, 8993, 8994, 8995, 8996, 8997, 9071, 9098, 9636, 9642,
			9676, 9678, 9923, 10035, 10041, 10047, 10055, 10059, 10063, 10067,
			10332, 10340, 10346, 10372, 10380, 10388, 10394, 10396, 10402,
			10406, 10410, 10414, 10418, 10422, 10426, 10430, 10434, 10438,
			10464, 10466, 10468, 10555, 10726, 10742, 10744, 10824, 10838,
			10864, 10940, 10956, 11022, 11722, 11726, 12561, 12880, 12881,
			12882, 12883, 12884, 12885, 12901, 12902, 12903, 12904, 12905,
			12906, 12978, 12979, 12980, 12981, 12982, 12983, 13174, 13175,
			13176, 13177, 13178, 13179, 13180, 13298, 13317, 13364, 13366,
			13368, 13413, 13416, 13419, 13457, 13459, 13487, 13488, 13489,
			13490, 13491, 13492, 13494, 13503, 13510, 13515, 13520,
			13545, 13549, 13552, 13617, 13622, 13627, 13669, 13674, 13678,
			13682, 13697, 13698, 13699, 13700, 13701, 13702, 13786, 13790,
			13791, 13801, 13802, 13806, 13807, 13821, 13822, 13826, 13827,
			13831, 13832, 13836, 13837, 13861, 13863, 13873, 13875, 13890,
			13892, 13893, 13895, 13914, 13916, 13917, 13919, 13935, 13937,
			13947, 13949, 13967, 13969, 14077, 14079, 14087, 14095, 14115,
			14119, 14327, 14329, 14331, 14333, 14335, 14357, 14359, 14361,
			14363, 14365, 14401, 14403, 14405, 14407, 14409, 14490, 14501,
			14592, 14603, 14604, 14729, 14734, 14938, 15023, 15024, 15029,
			15030, 15035, 15036, 15041, 15042, 15425, 15505, 15604, 15610,
			15616, 14622, 15797, 15798, 15799, 15800, 15801, 15802, 15803,
			15804, 15805, 15806, 15807, 15925, 15926, 15927, 15928, 15929,
			15930, 15931, 15933, 15934, 15935, 16057, 16058, 16059, 16060,
			16061, 16062, 16063, 16064, 16065, 16066, 16067, 16196, 16197,
			16198, 16199, 16200, 16201, 16202, 16203, 16204, 16205, 16206,
			16647, 16649, 16651, 16653, 16655, 16657, 16659, 16661, 16663,
			16665, 16667, 16669, 16671, 16673, 16675, 16677, 16679, 16681,
			16683, 16685, 16687, 16689, 16845, 16847, 16849, 16851, 16853,
			16855, 16857, 16859, 16861, 16863, 16865, 17319, 17321, 17323,
			17325, 17327, 17329, 17331, 17333, 17335, 17337, 17339, 18707,
			19169, 19171, 19175, 19177, 19182, 19184, 19185, 19187, 19190,
			19192, 19196, 19198, 19203, 19205, 19206, 19208, 19211, 19213,
			19217, 19219, 19224, 19226, 19227, 19229, 19232, 19234, 19238,
			19240, 19245, 19247, 19248, 19250, 19253, 19255, 19259, 19261,
			19266, 19268, 19269, 19271, 19320, 19322, 19338, 19339, 19343,
			19344, 19386, 19388, 19390, 19401, 19403, 19404, 19406, 19416,
			19418, 19419, 19421, 19431, 19433, 19434, 19436, 19447, 19455,
			19463, 19707, 19782, 19786, 19788, 19790, 20045 };
	private static String[] WEAPONS = { "chaotic", "rapier", "dagger",
			"hatchet", "bow", "Hand cannon", "Inferno adze", "Silverlight",
			"Darklight", "wand", "Statius's warhammer", "anchor", "spear",
			"Vesta's longsword.", "scimitar", "longsword", "sword", "longbow",
			"shortbow", "dagger", "mace", "halberd", "spear", "Abyssal whip",
			"axe", "flail", "crossbow", "Torags hammers", "dagger(p)",
			"dagger(+)", "dagger(s)", "spear(p)", "spear(+)", "spear(s)",
			"spear(kp)", "maul", "dart", "dart(p)", "javelin", "javelin(p)",
			"knife", "knife(p)", "Longbow", "Shortbow", "Crossbow",
			"Toktz-xil", "Toktz-mej", "Tzhaar-ket", "staff", "Staff",
			"godsword", "c'bow", "Crystal bow", "Dark bow", "claws",
			"warhammer", "adze", "hand", "Scythe", "500" };
	public static int FULL_BODY[] = { 75, 284, 426, 430, 540, 544, 546, 577,
			581, 636, 638, 640, 642, 644, 1035, 1115, 1117, 1119, 1121, 1123,
			1125, 1127, 1833, 1844, 2405, 2583, 2591, 2599, 2607, 2615, 2623,
			2653, 2661, 2669, 2896, 2906, 2916, 2926, 2936, 3058, 3140, 3387,
			3481, 3767, 3769, 3771, 3773, 3775, 3793, 4069, 4091, 4101, 4111,
			4298, 4504, 4509, 4712, 4720, 4728, 4736, 4749, 4757, 4868, 4869,
			4870, 4871, 4872, 4892, 4893, 4894, 4895, 4896, 4916, 4917, 4918,
			4919, 4920, 4940, 4941, 4942, 4943, 4944, 4964, 4965, 4966, 4967,
			4968, 4988, 4989, 4990, 4991, 4992, 5024, 5026, 5028, 5030, 5032,
			5034, 5575, 6065, 6107, 6129, 6133, 6139, 6180, 6184, 6186, 6322,
			6341, 6351, 6361, 6371, 6384, 6388, 6394, 6617, 6654, 6750, 6786,
			6916, 7390, 7392, 7399, 8839, 8952, 8953, 8954, 8955, 8956, 8957,
			8958, 9070, 9097, 9634, 9640, 9674, 9924, 9944, 10037, 10043,
			10049, 10053, 10057, 10061, 10065, 10316, 10318, 10320, 10322,
			10324, 10330, 10338, 10348, 10370, 10378, 10386, 10400, 10404,
			10408, 10412, 10416, 10420, 10424, 10428, 10432, 10436, 10458,
			10460, 10462, 10551, 10564, 10610, 10611, 10618, 10619, 10620,
			10621, 10622, 10623, 10624, 10625, 10626, 10627, 10628, 10630,
			10632, 10633, 10634, 10686, 10687, 10688, 10690, 10691, 10697,
			10698, 10714, 10715, 10716, 10717, 10718, 10727, 10748, 10750,
			10752, 10754, 10756, 10776, 10778, 10780, 10782, 10784, 10786,
			10788, 10790, 10792, 10794, 10798, 10800, 10822, 10837, 10863,
			10939, 10945, 10954, 11020, 11720, 11724, 11756, 11757, 11758,
			12563, 12873, 12874, 12875, 12876, 12877, 12878, 12894, 12895,
			12896, 12897, 12898, 12899, 12971, 12972, 12973, 12974, 12975,
			12976, 13181, 13182, 13183, 13184, 13185, 13186, 13187, 13297,
			13316, 13412, 13415, 13418, 13456, 13458, 13481, 13482, 13502,
			13509, 13514, 13519, 13544, 13548, 13553, 13614, 13619, 13624,
			13668, 13673, 13677, 13681, 13691, 13692, 13693, 13694, 13695,
			13696, 13785, 13800, 13805, 13820, 13825, 13830, 13835, 13858,
			13860, 13870, 13872, 13884, 13886, 13887, 13889, 13908, 13910,
			13911, 13913, 13932, 13934, 13944, 13946, 13958, 13960, 14076,
			14078, 14086, 14094, 14114, 14118, 14317, 14319, 14321, 14323,
			14325, 14347, 14349, 14351, 14353, 14355, 14391, 14393, 14395,
			14397, 14399, 14479, 14481, 14492, 14497, 14593, 14595, 14600,
			14601, 14730, 14732, 14936, 15022, 15028, 15034, 15040, 15423,
			15424, 15503, 15600, 15606, 15612, 15618, 15837, 15838, 15839,
			15840, 15841, 15842, 15843, 15844, 15845, 15846, 15847, 16013,
			16014, 16015, 16016, 16017, 16018, 16019, 16020, 16021, 16022,
			16023, 16068, 16069, 16070, 16071, 16072, 16073, 16074, 16075,
			16076, 16077, 16078, 16080, 16081, 16082, 16083, 16084, 16085,
			16086, 16087, 16088, 16089, 16090, 16713, 16715, 16717, 16719,
			16721, 16723, 16725, 16727, 16729, 16731, 16733, 17173, 17175,
			17177, 17179, 17181, 17183, 17185, 17187, 17189, 17191, 17193,
			17217, 17219, 17221, 17223, 17225, 17227, 17229, 17231, 17233,
			17235, 17237, 17239, 17241, 17243, 17245, 17247, 17249, 17251,
			17253, 17255, 17257, 17259, 18695, 18697, 18699, 18706, 19167,
			19173, 19179, 19181, 19188, 19194, 19200, 19202, 19209, 19215,
			19221, 19223, 19230, 19236, 19242, 19244, 19251, 19257, 19263,
			19265, 19317, 19319, 19337, 19342, 19380, 19382, 19384, 19398,
			19400, 19413, 19415, 19428, 19430, 19481, 19482, 19483, 19484,
			19485, 19486, 19487, 19488, 19489, 19490, 19491, 19492, 19493,
			19494, 19495, 19508, 19509, 19510, 19511, 19512, 19514, 19515,
			19516, 19517, 19518, 19519, 19706, 19735, 19757, 19781, 19785,
			19787, 19789, 20044 };
	public static int FULL_MASK[] = { 1053, 1055, 1057, 1153, 1155, 1157, 1159,
			1161, 1163, 1165, 1506, 2587, 2595, 2603, 2611, 2619, 2627, 2657,
			2665, 2673, 3057, 3486, 4071, 4551, 4724, 4732, 4745, 4753, 4904,
			4905, 4906, 4907, 4908, 4928, 4929, 4930, 4931, 4932, 4952, 4953,
			4954, 4955, 4956, 4976, 4977, 4978, 4979, 4980, 5554, 5574, 6131,
			6188, 6623, 7534, 7594, 8464, 8466, 8468, 8470, 8472, 8474, 8476,
			8478, 8480, 8482, 8484, 8486, 8488, 8490, 8492, 8494, 8682, 8684,
			8686, 8688, 8690, 8692, 8694, 8696, 8698, 8700, 8702, 8704, 8706,
			8708, 8710, 8712, 9096, 9629, 9672, 9920, 10609, 10612, 10614,
			10629, 10721, 10723, 11278, 11282, 11335, 11789, 11790, 12207,
			12209, 12658, 12659, 12664, 12665, 12666, 12667, 12668, 12669,
			13263, 13414, 13449, 13496, 13538, 13539, 13540, 13803, 13808,
			13823, 13828, 13833, 13838, 14494, 14594, 14636, 14637, 15422,
			15492, 15496, 15497, 15892, 15893, 15894, 15895, 15896, 15897,
			15898, 15899, 15900, 15901, 15902, 15914, 15915, 15916, 15917,
			15918, 15919, 15920, 15921, 15922, 15923, 15924, 16050, 16051,
			16052, 16053, 16054, 16055, 16056, 16691, 16693, 16695, 16697,
			16699, 16701, 16703, 16705, 16707, 16709, 16711, 16735, 16737,
			16739, 16741, 16743, 16745, 16747, 16749, 16751, 16753, 16755,
			17049, 17051, 17053, 17055, 17057, 17059, 17061, 18708, 19336,
			19341, 19407, 19409, 19422, 19424, 19437, 19439, 19496, 19497,
			19498, 19499, 19500, 19501, 19502, 19503, 19504, 19505, 19506,
			19507, 19783, 20070 };
	public static int FULL_HAT[] = { 4502, 74, 1137, 1139, 1141, 1143, 1145,
			1147, 1149, 1151, 1167, 1169, 3385, 3748, 3749, 3751, 3753, 3755,
			4302, 4506, 4511, 4513, 4515, 4567, 4708, 4716, 4856, 4857, 4858,
			4859, 4860, 4880, 4881, 4882, 4883, 4884, 6109, 6128, 6137, 6326,
			6392, 6400, 6621, 6665, 6856, 6858, 6860, 6862, 6885, 6886, 6887,
			7003, 7112, 7124, 7130, 7136, 7400, 7539, 8924, 8925, 8926, 8927,
			8928, 8949, 8950, 8959, 8960, 8961, 8962, 8963, 8964, 8965, 9068,
			9749, 9752, 9755, 9758, 9761, 9764, 9767, 9770, 9773, 9776, 9779,
			9782, 9785, 9788, 9791, 9794, 9797, 9800, 9803, 9806, 9809, 9812,
			9814, 9925, 9945, 9946, 9950, 10039, 10045, 10051, 10286, 10288,
			10290, 10292, 10294, 10296, 10298, 10300, 10302, 10304, 10306,
			10308, 10310, 10312, 10314, 10334, 10342, 10350, 10374, 10382,
			10390, 10392, 10398, 10452, 10454, 10456, 10507, 10547, 10548,
			10549, 10550, 10589, 10604, 10606, 10613, 10699, 10700, 10701,
			10702, 10703, 10704, 10705, 10706, 10707, 10708, 10709, 10710,
			10711, 10712, 10713, 10722, 10728, 10740, 10746, 10828, 10862,
			10883, 10941, 11021, 11200, 11277, 11280, 11663, 11664, 11665,
			11674, 11675, 11676, 11718, 12171, 12204, 12206, 12559, 12660,
			12661, 12662, 12663, 12670, 12671, 12672, 12673, 12674, 12675,
			12676, 12677, 12678, 12679, 12680, 12681, 12866, 12867, 12868,
			12869, 12870, 12871, 12887, 12888, 12889, 12890, 12891, 12892,
			12936, 12937, 12938, 12939, 12940, 12941, 12943, 12944, 12945,
			12946, 12947, 12948, 12950, 12951, 12952, 12953, 12954, 12955,
			12957, 12958, 12959, 12960, 12961, 12962, 12964, 12965, 12966,
			12967, 12968, 12969, 13105, 13107, 13109, 13111, 13113, 13115,
			13168, 13169, 13170, 13171, 13172, 13173, 13188, 13189, 13190,
			13191, 13192, 13193, 13194, 13339, 13340, 13341, 13342, 13343,
			13344, 13345, 13346, 13347, 13348, 13349, 13350, 13351, 13352,
			13353, 13354, 13357, 13370, 13372, 13374, 13376, 13377, 13378,
			13399, 13400, 13407, 13408, 13409, 13410, 13411, 13417, 13455,
			13495, 13501, 13546, 13550, 13554, 13667, 13680, 13684, 13763,
			13783, 13784, 13789, 13792, 13795, 13796, 13797, 13798, 13799,
			13864, 13866, 13876, 13878, 13896, 13898, 13920, 13922, 13938,
			13940, 13950, 13952, 13961, 13963, 14096, 14116, 14120, 14337,
			14339, 14341, 14343, 14345, 14367, 14369, 14371, 14373, 14375,
			14411, 14413, 14415, 14417, 14419, 14731, 14743, 14745, 14747,
			14749, 14751, 14753, 14755, 14757, 14759, 14761, 14763, 14765,
			14767, 14769, 14771, 14773, 14775, 14777, 14779, 14781, 14783,
			14785, 14787, 14789, 14791, 15599, 15828, 16046, 16047, 16048,
			16049, 17041, 17043, 17045, 17047, 17279, 18510, 18776, 19272,
			19274, 19275, 19277, 19278, 19280, 19281, 19283, 19284, 19286,
			19287, 19289, 19290, 19292, 19293, 19295, 19296, 19298, 19299,
			19301, 19302, 19304, 19305, 19307, 19374, 19376, 19378, 19449,
			19457, 19465, 19747 };
	// Hats that remove heads, but don't have a head model
	public static int SHOW_HEAD[] = { 579, 656, 658, 660, 662, 664, 1017, 2900,
			2910, 2920, 2930, 2940, 3327, 3329, 3331, 3333, 3335, 3337, 3339,
			3341, 3343, 3797, 4089, 4099, 4109, 5013, 5014, 6182, 6382, 6918,
			7394, 7396, 7917, 10601, 10602, 10603, 10605, 13508, 13513, 13518,
			15214, 15602, 15608, 15614, 15620 };
	// Hats that remove beards & keep the head
	public static int FACE_MASK[] = { 4164, 13277, 13396, 13397, 13398 };

	public static int getItemType(int wearId) {
		try {
			String weapon = ItemDefinitions.forID(wearId).name;
			for (int i = 0; i < CAPES.length; i++) {
				if (wearId == CAPES[i])
					return 1;
			}
			for (int i = 0; i < HATS.length; i++) {
				if (wearId == HATS[i])
					return 0;
			}
			for (int i = 0; i < BOOTS.length; i++) {
				if (wearId == BOOTS[i])
					return 10;
			}
			for (int i = 0; i < GLOVES.length; i++) {
				if (wearId == GLOVES[i])
					return 9;
			}
			for (int i = 0; i < SHIELDS.length; i++) {
				if (wearId == SHIELDS[i])
					return 5;
			}
			for (int i = 0; i < AMULETS.length; i++) {
				if (wearId == AMULETS[i])
					return 2;
			}
			for (int i = 0; i < ARROWS.length; i++) {
				if (wearId == ARROWS[i])
					return 13;
			}
			for (int i = 0; i < RINGS.length; i++) {
				if (wearId == RINGS[i])
					return 12;
			}
			for (int i = 0; i < BODY.length; i++) {
				if (wearId == BODY[i])
					return 4;
			}
			if (wearId == 6107) {
				return 4;
			}
			if (wearId == 6108) {
				return 7;
			}
			for (int i = 0; i < LEGS.length; i++) {
				if (wearId == LEGS[i])
					return 7;
			}
			for (int i = 0; i < WEAPONS.length; i++) {
				if (weapon.endsWith(WEAPONS[i])
						|| weapon.startsWith(WEAPONS[i]))
					return 3;
			}
			if (wearId == 5698) {
				return 3;
			}
			return -1;
		} catch (Exception e) {
			return -1;
		}
	}

	public static boolean isFullBody(ItemDefinitions def) {
		int wearId = def.getId();
		for (int i = 0; i < FULL_BODY.length; i++) {
			if (wearId == FULL_BODY[i]) {
				return true;
			}
		}
		return false;
	}

	public static boolean isFullHat(ItemDefinitions def) {
		int wearId = def.getId();
		for (int i = 0; i < FULL_HAT.length; i++) {
			if (wearId == FULL_HAT[i]) {
				return true;
			}
		}
		return false;
	}

	public static boolean isFullMask(ItemDefinitions def) {
		int wearId = def.getId();
		for (int i = 0; i < FULL_MASK.length; i++) {
			if (wearId == FULL_MASK[i]) {
				return true;
			}
		}
		return false;
	}

	public static boolean isTwoHanded(ItemDefinitions def) {
		String wepEquiped = def.name;
		int itemId = def.getId();
		if (itemId == 4212)
			return true;
		else if (itemId == 4214)
			return true;
		else if (wepEquiped.endsWith("claws"))
			return true;
		else if (wepEquiped.endsWith("cannon"))
			return true;
		else if (wepEquiped.endsWith("maul"))
			return true;
		else if (wepEquiped.endsWith("greataxe"))
			return true;
		else if (wepEquiped.endsWith("anchor"))
			return true;
		else if (wepEquiped.endsWith("2h sword"))
			return true;
		else if (wepEquiped.endsWith("longbow"))
			return true;
		else if (wepEquiped.equals("Seercull"))
			return true;
		else if (wepEquiped.endsWith("shortbow"))
			return true;
		else if (wepEquiped.endsWith("Longbow"))
			return true;
		else if (wepEquiped.endsWith("Shortbow"))
			return true;
		else if (wepEquiped.endsWith("bow full"))
			return true;
		else if (wepEquiped.equals("Dark bow"))
			return true;
		else if (wepEquiped.endsWith("halberd"))
			return true;
		else if (wepEquiped.equals("Granite maul"))
			return true;
		else if (wepEquiped.equals("Karil's crossbow"))
			return true;
		else if (wepEquiped.equals("Torags hammers"))
			return true;
		else if (wepEquiped.equals("Veracs flail"))
			return true;
		else if (wepEquiped.equals("Dharoks greataxe"))
			return true;
		else if (wepEquiped.equals("Guthans warspear"))
			return true;
		else if (wepEquiped.equals("Tzhaar-ket-om"))
			return true;
		else if (wepEquiped.endsWith("godsword"))
			return true;
		else if (wepEquiped.equals("Saradomin sword"))
			return true;
		else
			return false;
	}

	public int getRenderAnim() { // TODO cache sided
		if (get(3) == null)
			return 1426;
		int renderEmote = get(3).getDefinition().renderEmote;
		if (renderEmote != 0)
			return renderEmote;
		return 1426;
	}
}
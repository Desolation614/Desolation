package dragonkk.rs2rsps.tools.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import dragonkk.rs2rsps.rscache.Cache;
import dragonkk.rs2rsps.rscache.ItemDefinitions;

public class Dumper {

	// private static Map<String, int[]> dumpedItems;

	public static void main(String[] args) {
		new Cache();
		// setDumpedItems(new HashMap<String, int[]>());
		for (int itemId = 19271; itemId < Cache.getAmountOfItems(); itemId++)
			dumpItemBonus(itemId);
	}

	public static void dumpItemBonus(int id) {
		ItemDefinitions item = ItemDefinitions.forID(id);
		// will only dump items with equip id because i wanna dump fast
		if (item == null || item.name.equals("null") || item.isNoted()
				|| item.equipId == -1)
			return;
		WebPage itemWebPage = new WebPage("http://runescape.wikia.com/wiki/"
				+ item.name);
		if (!itemWebPage.isReadyToUse()) {
			itemWebPage.getError().printStackTrace();
			return;
		}
		// System.out.println(item.name);
		// itemWebPage.printWebPage();
		List<Integer> bonuspart1 = itemWebPage
				.getAllLinesForStartText("<td colspan='2' width='30' align='center'>");
		List<Integer> bonuspart2 = itemWebPage
				.getAllLinesForStartText("</td><td colspan='2' width='30' align='center'>");
		List<Integer> bonuspart3 = itemWebPage
				.getAllLinesForStartText("<td colspan='3' width='45' align='center'>");
		List<Integer> bonuspart4 = itemWebPage
				.getAllLinesForStartText("</td><td colspan='3' width='45' align='center'>");
		if (bonuspart1.size() + bonuspart2.size() + bonuspart3.size()
				+ bonuspart4.size() != 15) {
			System.out.println("Missed dumping item " + id + ", name: "
					+ item.name);
			return;
		}
		int[] bonuses = new int[15];
		int count = 0;
		for (int index : bonuspart1) { // stab bonuses
			String text = itemWebPage.substring(itemWebPage.getLines().get(
					index).getText(),
					"<td colspan='2' width='30' align='center'>");
			text = text.replace("+", "").replace("%", "").replace(".0", "")
					.replace("?", "0").replace("", "0");
			if (text.contains("to"))
				return;
			bonuses[count++ == 0 ? 0 : 5] = Integer.parseInt(text);
		}
		for (int index : bonuspart2) { // all att and defence exept stab
			String text = itemWebPage.substring(itemWebPage.getLines().get(
					index).getText(),
					"</td><td colspan='2' width='30' align='center'>");
			text = text.replace("+", "").replace("%", "").replace(".0", "")
					.replace("?", "0").replace("–4", "4").replace("", "0")
					.replace("0-020", "0").replace("0-03000", "0").replace(
							"0-01000", "0").replace("0-060", "0").replace("0-070", "0").replace("0-040", "0").replace("0-030", "0");
			if (text.contains("to"))
				return;
			bonuses[count == 2 ? 1 : count == 3 ? 2 : count == 4 ? 3
					: count == 5 ? 4 : count == 6 ? 6 : count == 7 ? 7
							: count == 8 ? 8 : count == 9 ? 9 : 10] = Integer
					.parseInt(text);
			count++;
		}
		for (int index : bonuspart3) { // strength
			String text = itemWebPage.substring(itemWebPage.getLines().get(
					index).getText(),
					"<td colspan='3' width='45' align='center'>");
			text = text.replace("+", "").replace("%", "").replace(".0", "")
					.replace("?", "0").replace("15 (Against Slayer task)", "0")
					.replace(".5", "").replace("%", "").replace("", "0");
			if (text.contains("to"))
				return;
			bonuses[count++ == 11 ? 11 : 11] = Integer.parseInt(text);
		}
		for (int index : bonuspart4) { // other bonuses
			String text = itemWebPage.substring(itemWebPage.getLines().get(
					index).getText(),
					"</td><td colspan='3' width='45' align='center'>");
			text = text.replace("+", "").replace("%", "").replace(".0", "")
					.replace("?", "0").replace("0 (4 trimmed)", "4").replace(
							"15 (Slayer tasks only)", "0");
			if (text.contains("to"))
				return;
			bonuses[count == 12 ? 12 : count == 13 ? 13 : 14] = Integer
					.parseInt(text);
			count++;
		}
		if (count != 15) {
			System.out.println("Missed dumping item " + id + ", name: "
					+ item.name);
			return;
		}
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("itemDumps/"
					+ id + ".txt", false));
			int count2 = 0;
			while (count2 < 15) {
				out.write(String.valueOf(bonuses[count2++]));
				out.newLine();
				out.flush();
			}
			out.close();
			System.out.println("Dumped item " + id + ", name: " + item.name);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * public static void setDumpedItems(Map<String, int[]> dumpedItems) {
	 * Dumper.dumpedItems = dumpedItems; }
	 * 
	 * public static Map<String, int[]> getDumpedItems() { return dumpedItems; }
	 */
}

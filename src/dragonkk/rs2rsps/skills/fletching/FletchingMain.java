package dragonkk.rs2rsps.skills.fletching;

public class FletchingMain {
	
	/*
	 * For the frames
	 */
	public final int[][] FLETCHING_DATA = {
			{1511, 50, 48, 52},
			{1521, 54, 56, 52},
			{} // TODO: Finish fletching.
	};
	
	/*
	 * Shortbow data
	 */
	public final int[][] SHORT_BOW_DATA = {
			{1511, 50, 5, 10},
			{1521, 54, 20, 17},
			{1519, 60, 35, 33},
			{1517, 64, 50, 50},
			{1515, 68, 65, 68},
			{1513, 72, 80, 167}
	};
	
	public int getShortBowData(int log, int data) {
		for(int[] info: SHORT_BOW_DATA) {
			if(info[0] == log)
				return info[data];
		}
		return -1;
	}
	

}

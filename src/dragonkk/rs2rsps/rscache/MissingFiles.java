package dragonkk.rs2rsps.rscache;


public class MissingFiles {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Cache();
		//System.out.println("maxAmtOfFiles: "+CacheManager.getFIT(CacheConstants.MUSIC_IDX_ID).);
		for(int idx = 0; idx < 34; idx++) {
		System.out.println("idx: "+idx);
		System.out.println("maxAmtOfFiles: "+CacheManager.getFIT(idx).getEntry_real_count());
		System.out.println("amtOfFiles: "+CacheManager.getFIT(idx).getEntry_count());
		}
	}

}

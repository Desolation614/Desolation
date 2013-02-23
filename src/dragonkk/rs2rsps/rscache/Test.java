package dragonkk.rs2rsps.rscache;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.Task;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server.getEntityExecutor().scheduleAtFixedRate(new Task() {
			@Override
			public void run() {
				String crash = null;
				if(crash.equals(null))
					return;
			}
		}, 0,  1000);
		
	}

}

package dragonkk.rs2rsps.model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MusicManager implements Serializable {


	private static final long serialVersionUID = -1175421989944550213L;

	private transient Player player;
	
	private List<Integer> playList;
	private transient short playingMusic;
	private transient boolean playChoosedMusic;
	
	public void playCustomMusic(short musicId) {
		setPlayingMusic(musicId);
		setPlayChoosedMusic(true);
		if(musicId != playingMusic)
		player.getFrames().sendMusic(musicId);
	}
	
	public void playRegionMusic() {
		if(isPlayChoosedMusic()) {
			player.getFrames().sendMusic(playingMusic);
			return;
		}
		short regionId = (short) (((player.getLocation().getRegionX() / 8) << 8) + (player.getLocation().getRegionY() / 8));
		short musicId = 1;
		if(regionId == 12850)
			musicId = 2;
		else
		//System.out.println("Missing music region : "+regionId);
		if(musicId != playingMusic)
		setPlayingMusic(musicId);
		player.getFrames().sendMusic(musicId);
	}
	
	public MusicManager() {
		this.setPlayList(new ArrayList<Integer>());
	}

	public void setPlayList(List<Integer> playList) {
		this.playList = playList;
	}

	public List<Integer> getPlayList() {
		return playList;
	}

	public void setPlayingMusic(short playingMusic) {
		this.playingMusic = playingMusic;
	}

	public short getPlayingMusic() {
		return playingMusic;
	}

	public void setPlayChoosedMusic(boolean playChoosedMusic) {
		this.playChoosedMusic = playChoosedMusic;
	}

	public boolean isPlayChoosedMusic() {
		return playChoosedMusic;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}

package dragonkk.rs2rsps.model.player;

import dragonkk.rs2rsps.model.Animation;
import dragonkk.rs2rsps.model.Graphics;
import dragonkk.rs2rsps.model.Heal;
import dragonkk.rs2rsps.util.RSTile;

public class Mask {
	private transient Player player;

	private transient ChatMessage lastChatMessage;
	private transient Graphics lastGraphics;
	private transient Graphics lastGraphics2;
	private transient Animation lastAnimation;
	private transient Heal lastHeal;
	private transient boolean ApperanceUpdate;
	private transient boolean HitUpdate;
	private transient boolean Hit2Update;
	private transient boolean HealUpdate;
	private transient boolean ChatUpdate;
	private transient boolean GraphicUpdate;
	private transient boolean Graphic2Update;
	private transient boolean AnimationUpdate;
	private transient boolean turnToUpdate;
	private transient boolean turnToUpdate1;
	private transient boolean turnToReset;
	private transient RSTile turnToLocation;
	private transient int turnToIndex;
	private transient Region region;

	public Mask(Player player) {
		this.setwPlayer(player);
		this.setRegion(new Region(this.player));
		this.setApperanceUpdate(true);
	}

	public void reset() {
		this.getRegion().reset();
			this.setApperanceUpdate(false);
			this.setHitUpdate(false);
			this.setHit2Update(false);
			this.setHealUpdate(false);
			this.setChatUpdate(false);
			this.setGraphicUpdate(false);
			this.setGraphic2Update(false);
			this.setAnimationUpdate(false);
		if (this.turnToReset)
			this.setTurnToUpdate(false);
		this.setTurnToUpdate1(false);
	}

	public void setwPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean isUpdateNeeded() {
		return ApperanceUpdate || HitUpdate || Hit2Update || HealUpdate
				|| GraphicUpdate || Graphic2Update || AnimationUpdate || turnToUpdate || player.getWalk().getWalkDir() != -1 || player.getWalk().getRunDir() != -1 || player.getWalk().isDidTele();
	}

	public void setApperanceUpdate(boolean apperanceUpdate) {
		ApperanceUpdate = apperanceUpdate;
	}

	public boolean isApperanceUpdate() {
		return ApperanceUpdate;
	}

	public void setHitUpdate(boolean hitUpdate) {
		HitUpdate = hitUpdate;
	}

	public boolean isHitUpdate() {
		return HitUpdate;
	}

	public void setChatUpdate(boolean chatUpdate) {
		ChatUpdate = chatUpdate;
	}

	public boolean isChatUpdate() {
		return ChatUpdate;
	}

	public void setLastChatMessage(ChatMessage lastChatMessage) {
		this.lastChatMessage = lastChatMessage;
	}

	public ChatMessage getLastChatMessage() {
		return lastChatMessage;
	}

	public void setGraphicUpdate(boolean graphicUpdate) {
		GraphicUpdate = graphicUpdate;
	}

	public boolean isGraphicUpdate() {
		return GraphicUpdate;
	}

	public void setLastGraphics(Graphics lastGraphics) {
		this.lastGraphics = lastGraphics;
	}

	public Graphics getLastGraphics() {
		return lastGraphics;
	}

	public void setLastAnimation(Animation lastAnimation) {
		this.lastAnimation = lastAnimation;
	}

	public Animation getLastAnimation() {
		return lastAnimation;
	}

	public void setAnimationUpdate(boolean animationUpdate) {
		AnimationUpdate = animationUpdate;
	}

	public boolean isAnimationUpdate() {
		return AnimationUpdate;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Region getRegion() {
		return region;
	}

	public void setHit2Update(boolean hit2Update) {
		Hit2Update = hit2Update;
	}

	public boolean isHit2Update() {
		return Hit2Update;
	}

	public void setTurnToUpdate(boolean turnToUpdate) {
		this.turnToUpdate = turnToUpdate;
	}

	public boolean isTurnToUpdate() {
		return turnToUpdate;
	}

	public void setTurnToReset(boolean turnToReset) {
		this.turnToReset = turnToReset;
	}

	public boolean isTurnToReset() {
		return turnToReset;
	}

	public void setGraphic2Update(boolean Graphic2Update) {
		this.Graphic2Update = Graphic2Update;
	}

	public boolean isGraphic2Update() {
		return Graphic2Update;
	}

	public void setLastGraphics2(Graphics lastGraphics2) {
		this.lastGraphics2 = lastGraphics2;
	}

	public Graphics getLastGraphics2() {
		return lastGraphics2;
	}

	public void setTurnToLocation(RSTile turnToLocation) {
		this.turnToLocation = turnToLocation;
	}

	public RSTile getTurnToLocation() {
		return turnToLocation;
	}

	public void setHealUpdate(boolean hit3Update) {
		HealUpdate = hit3Update;
	}

	public boolean isHealUpdate() {
		return HealUpdate;
	}

	public void setLastHeal(Heal lastHeal) {
		this.lastHeal = lastHeal;
	}

	public Heal getLastHeal() {
		return lastHeal;
	}

	public void setTurnToIndex(int turnToIndex) {
		this.turnToIndex = turnToIndex;
	}

	public int getTurnToIndex() {
		return turnToIndex;
	}

	public void setTurnToUpdate1(boolean turnToUpdate1) {
		this.turnToUpdate1 = turnToUpdate1;
	}

	public boolean isTurnToUpdate1() {
		return turnToUpdate1;
	}
}

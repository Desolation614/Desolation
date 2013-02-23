package dragonkk.rs2rsps.model;

import java.io.Serializable;


import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.combat.Combat;
import dragonkk.rs2rsps.util.RSTile;

public abstract class Entity implements Serializable {

	private static final long serialVersionUID = -2311863174114543259L;

	private RSTile location;

	private transient Walking walk;
	private transient Combat combat;
//	private Region currentRegion;
	private transient int Index;
	private transient boolean hidden;

	public void setIndex(int index) {
		Index = index;
	}

	public int getIndex() {
		return Index;
	}

	public int getClientIndex() {
		if (this instanceof Player) {
			return this.Index + 32768;
		} else {
			return this.Index;
		}
	}
	
	public boolean isDead() {
		if (this instanceof Player) {
			return ((Player)this).getSkills().isDead();
		}
		return true;
	}

	public abstract void heal(int amount);

	public abstract void hit(int damage);

	public abstract void turnTemporarilyTo(Entity entity);
	public abstract void turnTo(Entity entity);

	public abstract void resetTurnTo();

	public abstract void graphics(int id);

	public abstract void graphics(int id, int delay);

	public abstract void graphics2(int id);

	public abstract void graphics2(int id, int delay);

	
	public abstract void animate(int id);

	public abstract void animate(int id, int delay);

/*	public abstract void removeFromRegion(Region region);

	public abstract void addToRegion(Region region);*/
	
	public void EntityLoad() {
		this.setWalk(new Walking(this));
		this.setCombat(new Combat(this));
		this.setHidden(false);
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setLocation(RSTile location) {
		this.location = location;
	/*	Region newRegion = World.getRegionManager().getRegionByLocation(location);
		if(newRegion != currentRegion) {
			if(currentRegion != null) {
				removeFromRegion(currentRegion);
			}
			currentRegion = newRegion;
			addToRegion(currentRegion);
		}*/
	}

	public RSTile getLocation() {
		return location;
	}

/*	public Region getRegion() {
		return currentRegion;
	}*/
	
	public void setWalk(Walking walk) {
		this.walk = walk;
	}

	public Walking getWalk() {
		return walk;
	}

	public void setCombat(Combat combat) {
		this.combat = combat;
	}

	public Combat getCombat() {
		return combat;
	}

}

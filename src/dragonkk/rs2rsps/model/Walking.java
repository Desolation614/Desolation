package dragonkk.rs2rsps.model;

import java.io.Serializable;

import dragonkk.rs2rsps.model.npc.Npc;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.RSTile;

/**
 * Handles walking.
 * 
 * @author Graham
 */
public class Walking implements Serializable {

	public class Point {
		public int x;
		public int y;
		public byte dir;
	}

	private static final int SIZE = 50;
	private byte runEnergy = 100;
	private byte WalkDir = -1;
	private byte RunDir = -1;
	public int wQueueReadPtr = 0;
	public int wQueueWritePtr = 0;
	public Point[] walkingQueue = new Point[SIZE];
	private RSTile lastWalkFinalPosition;
	private Entity entity;
	private boolean didTele;

	private boolean isRunning = false, isRunToggled = false;

	public boolean isRunToggled() {
		return isRunToggled;
	}

	public boolean isMoving() {
		return this.hasWalkingDirection() || this.WalkDir != -1
				|| this.RunDir != -1;
	}

	public boolean isWalkingMoving() {
		return (this.hasWalkingDirection() && !this.isRunning)
				|| this.WalkDir != -1;
	}

	public boolean isRunningMoving() {
		return (this.hasWalkingDirection() && this.isRunning)
				|| this.RunDir != -1;
	}

	private boolean hasWalkingDirection() {
		return wQueueReadPtr != wQueueWritePtr;
	}

	public void setRunToggled(boolean isRunToggled) {
		this.isRunToggled = isRunToggled;
	}

	public Walking(Entity entity) {
		this.entity = entity;
		setWalkDir((byte) -1);
		setRunDir((byte) -1);
		for (int i = 0; i < SIZE; i++) {
			walkingQueue[i] = new Point();
			walkingQueue[i].x = 0;
			walkingQueue[i].y = 0;
			walkingQueue[i].dir = -1;
		}
		reset();
	}

	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public void reset() {
		walkingQueue[0].x = entity.getLocation().getLocalX();
		walkingQueue[0].y = entity.getLocation().getLocalY();
		walkingQueue[0].dir = -1;
		wQueueReadPtr = wQueueWritePtr = 1;
		lastWalkFinalPosition = RSTile.createRSTile(0, 0, 0);
	}

	public void addToWalkingQueue(int x, int y) {
		int diffX = x - walkingQueue[wQueueWritePtr - 1].x, diffY = y
				- walkingQueue[wQueueWritePtr - 1].y;
		int max = Math.max(Math.abs(diffX), Math.abs(diffY));
		for (int i = 0; i < max; i++) {
			if (diffX < 0)
				diffX++;
			else if (diffX > 0)
				diffX--;
			if (diffY < 0)
				diffY++;
			else if (diffY > 0)
				diffY--;
			addStepToWalkingQueue(x - diffX, y - diffY);
		}
	}

	public void addToWalkingQueueFollow(int x, int y) {
		int diffX = x - walkingQueue[wQueueWritePtr - 1].x, diffY = y
				- walkingQueue[wQueueWritePtr - 1].y;
		int max = Math.max(Math.abs(diffX), Math.abs(diffY));
		for (int i = 0; i < max; i++) {
			if (diffX < 0)
				diffX++;
			else if (diffX > 0)
				diffX--;
			if (diffY < 0)
				diffY++;
			else if (diffY > 0)
				diffY--;
			int toQueX = x - diffX;
			int toQueY = y - diffY;
			if (toQueX != x || toQueY != y)
				addStepToWalkingQueue(toQueX, toQueY);
		}
	}

	public void addStepToWalkingQueue(int x, int y) {
		int diffX = x - walkingQueue[wQueueWritePtr - 1].x, diffY = y
				- walkingQueue[wQueueWritePtr - 1].y;
		byte dir = (byte) Misc.walkingDirection(diffX, diffY);
		if (wQueueWritePtr >= SIZE) {
			return;
		}
		if (dir != -1) {
			walkingQueue[wQueueWritePtr].x = x;
			walkingQueue[wQueueWritePtr].y = y;
			walkingQueue[wQueueWritePtr++].dir = dir;
		}
	}

	public void getNextEntityMovement() {
		Npc n = null;
		Player p = null;
		if (entity instanceof Player)
			p = (Player) entity;
		else
			n = (Npc) entity;

		if (entity instanceof Player)
			this.setDidTele(p.getMask().getRegion().isDidTeleport());
		setWalkDir((byte) -1);
		setRunDir((byte) -1);
		RSTile oldLocation = null;
		byte walkDir = (entity instanceof Player && System.currentTimeMillis() < p
				.getCombatDefinitions().getLastEmote() - 600) ? -1
				: getNextWalkingDirection();
		byte runDir = -1;
		if (runEnergy == 0 && (isRunning || isRunToggled)) {
			isRunning = false;
			isRunToggled = false;
		}
		if (isRunning || isRunToggled)
			runDir = (entity instanceof Player && System.currentTimeMillis() < p
					.getCombatDefinitions().getLastEmote() - 600) ? -1
					: (byte) getNextWalkingDirection();
		if (entity instanceof Player) {
			if (!p.getMask().getRegion().isUsingStaticRegion()) {
				oldLocation = p.getMask().getRegion().getLastMapRegion();
				if ((oldLocation.getRegionX() - entity.getLocation()
						.getRegionX()) >= 4
						|| (oldLocation.getRegionX() - entity.getLocation()
								.getRegionX()) <= -4) {
					p.getMask().getRegion().setNeedReload(true);
					p.getMask().getRegion().setDidMapRegionChange(true);
				} else if ((oldLocation.getRegionY() - entity.getLocation()
						.getRegionY()) >= 4
						|| (oldLocation.getRegionY() - entity.getLocation()
								.getRegionY()) <= -4) {
					p.getMask().getRegion().setNeedReload(true);
					p.getMask().getRegion().setDidMapRegionChange(true);
				}
			}
			if (p.getMask().getRegion().isDidMapRegionChange()) {
				if (walkDir != -1) {
					wQueueReadPtr--;
				}
				if (runDir != -1) {
					wQueueReadPtr--;
				}
				walkDir = -1;
				runDir = -1;
			}
		}
		int xdiff = 0;
		int ydiff = 0;
		if (walkDir != -1) {
			xdiff += Misc.DIRECTION_DELTA_X[walkDir];
			ydiff += Misc.DIRECTION_DELTA_Y[walkDir];
		}
		if (runDir != -1) {
			int nextXDiff = Misc.DIRECTION_DELTA_X[runDir];
			int nextYDiff = Misc.DIRECTION_DELTA_Y[runDir];
			runDir = (byte) Misc.runningDirection(xdiff + nextXDiff, ydiff
					+ nextYDiff);
			if (runDir != -1) {
				walkDir = -1;
				xdiff += nextXDiff;
				ydiff += nextYDiff;
			} else if (walkDir == -1) {
				walkDir = (byte) Misc.walkingDirection(nextXDiff, nextYDiff);
				xdiff += nextXDiff;
				ydiff += nextYDiff;
			} else
				wQueueReadPtr--;
			if (runEnergy > 0) {
				runEnergy--;
				if (entity instanceof Player)
					p.getFrames().sendRunEnergy();
			}
		} else if (runEnergy < 100) {
			runEnergy++;
			if (entity instanceof Player)
				p.getFrames().sendRunEnergy();
		}
		if (xdiff != 0 || ydiff != 0)
			entity.getLocation().set(
					(short) (entity.getLocation().getX() + xdiff),
					(short) (entity.getLocation().getY() + ydiff));
		setWalkDir(walkDir);
		setRunDir(runDir);
	}

	public void getNextEntityMovementFucked() {
		Npc n = null;
		Player p = null;
		if (entity instanceof Player)
			p = (Player) entity;
		else
			n = (Npc) entity;

		if (entity instanceof Player)
			this.setDidTele(p.getMask().getRegion().isDidTeleport());
		setWalkDir((byte) -1);
		setRunDir((byte) -1);
		RSTile oldLocation = null;
		byte walkDir = (entity instanceof Player && System.currentTimeMillis() < p
				.getCombatDefinitions().getLastEmote() - 600) ? -1
				: getNextWalkingDirection();
		byte runDir = -1;
		if (runEnergy == 0 && (isRunning || isRunToggled)) {
			isRunning = false;
			isRunToggled = false;
		}
		if (isRunning || isRunToggled)
			runDir = (entity instanceof Player && System.currentTimeMillis() < p
					.getCombatDefinitions().getLastEmote() - 600) ? -1
					: (byte) getNextWalkingDirection();
		if (entity instanceof Player) {
			if (!p.getMask().getRegion().isUsingStaticRegion()) {
				oldLocation = p.getMask().getRegion().getLastMapRegion();
				if ((oldLocation.getRegionX() - entity.getLocation()
						.getRegionX()) >= 4
						|| (oldLocation.getRegionX() - entity.getLocation()
								.getRegionX()) <= -4) {
					p.getMask().getRegion().setNeedReload(true);
					p.getMask().getRegion().setDidMapRegionChange(true);
				} else if ((oldLocation.getRegionY() - entity.getLocation()
						.getRegionY()) >= 4
						|| (oldLocation.getRegionY() - entity.getLocation()
								.getRegionY()) <= -4) {
					p.getMask().getRegion().setNeedReload(true);
					p.getMask().getRegion().setDidMapRegionChange(true);
				}
			}
			if (p.getMask().getRegion().isDidMapRegionChange()) {
				if (walkDir != -1) {
					wQueueReadPtr--;
				}
				if (runDir != -1) {
					wQueueReadPtr--;
				}
				walkDir = -1;
				runDir = -1;
			}
		}

		int xdiff = 0;
		int ydiff = 0;
		if (walkDir != -1) {
			xdiff += Misc.DIRECTION_DELTA_X[walkDir];
			ydiff += Misc.DIRECTION_DELTA_Y[walkDir];
		}
		if (runDir != -1) {
			int nextXDiff = Misc.DIRECTION_DELTA_X[runDir];
			int nextYDiff = Misc.DIRECTION_DELTA_Y[runDir];
			runDir = (byte) Misc.runningDirection(xdiff + nextXDiff, ydiff
					+ nextYDiff);
			if (runDir != -1) {
				walkDir = -1;
				xdiff += nextXDiff;
				ydiff += nextYDiff;
			} else if (walkDir == -1) {
				walkDir = (byte) Misc.walkingDirection(nextXDiff, nextYDiff);
				xdiff += nextXDiff;
				ydiff += nextYDiff;
			} else
				wQueueReadPtr--;
			if (isRunning || isRunToggled) {
				if (runEnergy > 0) {
					runEnergy--;
					if (entity instanceof Player)
						p.getFrames().sendRunEnergy();
				}
			} else if (runEnergy < 100) {
				runEnergy++;
				if (entity instanceof Player)
					p.getFrames().sendRunEnergy();
			}
		}
		if (xdiff != 0 || ydiff != 0)
			entity.getLocation().set(
					(short) (entity.getLocation().getX() + xdiff),
					(short) (entity.getLocation().getY() + ydiff));

		setWalkDir(walkDir);
		setRunDir(runDir);
	}

	/**
	 * Herp Derp for Harry, this is what happens each cycle for a Npc <3 David
	 * 
	 * @return Your an idiot.
	 */
	public void getNextEntityMovementNpc() {
		Npc n = null;
		Player p = null;
		if (entity instanceof Player)
			p = (Player) entity;
		else
			n = (Npc) entity;
		setWalkDir((byte) -1);
		setRunDir((byte) -1);
		RSTile oldLocation = null;
		byte walkDir = getNextWalkingDirection();
		byte runDir = -1;
		setWalkDir(walkDir);
		setRunDir(runDir);
	}

	private byte getNextWalkingDirection() {
		if (wQueueReadPtr == wQueueWritePtr) {
			return -1;
		}
		return walkingQueue[wQueueReadPtr++].dir;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setWalkDir(byte walkDir) {
		WalkDir = walkDir;
	}

	public byte getWalkDir() {
		return WalkDir;
	}

	public void setRunDir(byte runDir) {
		RunDir = runDir;
	}

	public byte getRunDir() {
		return RunDir;
	}

	public void setLastWalk(RSTile lastWalk) {
		this.lastWalkFinalPosition = lastWalk;
	}

	public RSTile getLastWalk() {
		return lastWalkFinalPosition;
	}

	public void setRunEnergy(byte runEnergy) {
		this.runEnergy = runEnergy;
	}

	public byte getRunEnergy() {
		return runEnergy;
	}

	public void setDidTele(boolean didTele) {
		this.didTele = didTele;
	}

	public boolean isDidTele() {
		return didTele;
	}

}
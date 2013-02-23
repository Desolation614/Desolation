package dragonkk.rs2rsps.model.npc;

import java.io.Serializable;

import dragonkk.rs2rsps.model.Animation;
import dragonkk.rs2rsps.model.Entity;
import dragonkk.rs2rsps.model.Graphics;
import dragonkk.rs2rsps.model.Walking;
import dragonkk.rs2rsps.rscache.NpcDefinitions;
import dragonkk.rs2rsps.util.RSTile;

@SuppressWarnings("serial")
public class Npc extends Entity implements Serializable {

	private int id;
	private int hp;
	
    private Mask mask;
    private Walking walk;
	private NpcDefinitions npcdefinition;
	
    public Npc(short id, RSTile location) {
    	this.setId(id);
    	this.setLocation(location);
        this.setMask(new Mask(this));
        this.setWalk(new Walking(this));
        this.setNpcdefinition(NpcDefinitions.forID(id));
    }
    
    
    public void tick() {
    	
    }
    
    public void teleport(int x, int y, int z) {
    	this.setLocation(RSTile.createRSTile(x, y, z));
    	this.getMask().setTeleport(true);
    }
    
    @Override
    public void animate(int id) {
        this.getMask().setLastAnimation(new Animation((short)id, (short)0));
        this.getMask().setAnimationUpdate(true);
    }

    @Override
    public void animate(int id, int delay) {
        this.getMask().setLastAnimation(new Animation((short)id, (short) delay));
        this.getMask().setAnimationUpdate(true);
    }
    public void graphics(int... args) {
		switch(args.length) {
		case 1:
			mask.setLastGraphics(Graphics.create(args[0]));
			break;
		case 2:
			mask.setLastGraphics(Graphics.create(args[0], args[1]));
			break;
		case 3:
			mask.setLastGraphics(Graphics.create(args[0], args[1], args[2]));
			break;
		default:
			throw new IllegalArgumentException("Graphic arguments can't be greater then 3");
		}
	}

	public void graphics(Graphics graphics) {
		mask.setLastGraphics(graphics);
	}



    @Override
    public void heal(int amount) {
        // TODO Auto-generated method stub

    }

    @Override
    public void hit(int damage) {
        // TODO Auto-generated method stub

    }

    @Override
    public void resetTurnTo() {
        // TODO Auto-generated method stub

    }

    @Override
    public void turnTemporarilyTo(Entity entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public void turnTo(Entity entity) {
        // TODO Auto-generated method stub

    }

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getHp() {
		return hp;
	}

	public void setMask(Mask mask) {
		this.mask = mask;
	}

	public Mask getMask() {
		return mask;
	}

	public void setWalk(Walking walk) {
		this.walk = walk;
	}

	public Walking getWalk() {
		return walk;
	}

	public void setNpcdefinition(NpcDefinitions npcdefinition) {
		this.npcdefinition = npcdefinition;
	}

	public NpcDefinitions getNpcdefinition() {
		return npcdefinition;
	}

	@Override
	public void graphics2(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void graphics2(int id, int delay) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void graphics(int id) {
		this.getMask().setLastGraphics(new Graphics((short) id, (short) 0));
		this.getMask().setGraphicUpdate(true);

	}

	@Override
	public void graphics(int id, int delay) {
		this.getMask().setLastGraphics(new Graphics((short) id, (short) 0));
		this.getMask().setGraphicUpdate(true);

	}

}

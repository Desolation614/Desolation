package dragonkk.rs2rsps.model.player;

import java.io.Serializable;

public class Appearence implements Serializable {

    private static final long serialVersionUID = 5497272754883262586L;

    private short npcType;
    private byte gender;
    private byte[] look;
    private byte[] colour;
    private boolean male;
    private int skullIcon = -1;
    

    public Appearence() {
        this.setMale(true);
        this.setNpcType((short) -1);
        this.resetAppearence();
    }

    public void resetAppearence() {
        this.setLook(new byte[7]);
        this.setColour(new byte[5]);
        look[0] = 3; // Hair
        look[1] = 14; // Beard
        look[2] = 18; // Torso
        look[3] = 26; // Arms
        look[4] = 34; // Bracelets
        look[5] = 38; // Legs
        look[6] = 42; // Shoes
        colour[2] = 16;
        colour[1] = 16;
        for (int i = 0; i < 5; i++) {
            colour[2] = 16;
            colour[1] = 16;
            colour[0] = 3;
        }
    }

    public void setNpcType(short npcType) {
        this.npcType = npcType;
    }

    public short getNpcType() {
        return npcType;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public byte getGender() {
        return gender;
    }

    public void setLook(byte[] look) {
        this.look = look;
    }

    public byte[] getLook() {
        return look;
    }

    public void setColour(byte[] colour) {
        this.colour = colour;
    }

    public byte[] getColour() {
        return colour;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public boolean isMale() {
        return male;
    }

	/**
	 * @param skullIcon the skullIcon to set
	 */
	public void setSkullIcon(int skullIcon) {
		this.skullIcon = skullIcon;
	}

	/**
	 * @return the skullIcon
	 */
	public int getSkullIcon() {
		return skullIcon;
	}

}

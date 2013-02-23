package dragonkk.rs2rsps.model;

import java.io.Serializable;

import dragonkk.rs2rsps.rscache.ItemDefinitions;

/**
 * Represents a single item.
 * <p/>
 * Immutable.
 * 
 * @author Graham
 */
public class Item implements Serializable {

	private static final long serialVersionUID = -6485003878697568087L;

	private short id;
	private int amount;
	private transient ItemDefinitions itemDefinition;

	public short getId() {
		return id;
	}

	public Item clone() {
		return new Item(id, amount);
	}

	public Item(int id) {
		this.id = (short) id;
		this.amount = 1;
		this.itemDefinition = ItemDefinitions.forID(id);
	}

	public Item(int id, int amount) {
		this.id = (short) id;
		this.amount = amount;
		if (this.amount <= 0) {
			this.amount = 1;
		}
		this.itemDefinition = ItemDefinitions.forID(id);
	}

	public Item(int id, int amount, boolean amt0) {
		this.id = (short) id;
		this.amount = amount;
		if (this.amount <= 0 && !amt0) {
			this.amount = 1;
		}
		this.itemDefinition = ItemDefinitions.forID(id);
	}

	public ItemDefinitions getDefinition() {
		return itemDefinition;
	}

	public void setDefinition(ItemDefinitions itemDefinition) {
		this.itemDefinition = itemDefinition;
	}

	public int getAmount() {
		return amount;
	}

	/**
	 * ONLY CALL THIS FROM THE SHOPITEM CLASS.
	 * 
	 * @param amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

}

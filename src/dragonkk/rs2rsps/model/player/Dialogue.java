package dragonkk.rs2rsps.model.player;

import dragonkk.rs2rsps.scripts.Scripts;
import dragonkk.rs2rsps.scripts.dialogueScript;

public class Dialogue {

	
	public Dialogue(Player p) {
		this.p = p;
	}
	
	private dialogueScript lastDialogue;
	private Player p;
	
	public void startDialogue(String key) {
		if(this.getLastDialogue() != null)
			this.getLastDialogue().finish();
		this.setLastDialogue(Scripts.invokeDialogueScript(key));
		if(this.getLastDialogue() == null)
			return;
		this.getLastDialogue().setPlayer(this.p);
		this.getLastDialogue().start();
	}

	public void continueDialogue(short inter, byte child) {
		if(this.getLastDialogue() == null)
			return;
		this.getLastDialogue().run(inter, child);
	}
	
	public void finishDialogue() {
		if(this.getLastDialogue() == null)
			return;
		this.getLastDialogue().finish();
	}
	
	public void reset() {
		this.setLastDialogue(null);
		this.p.getFrames().CloseCInterface();
	}
	
	
	public void setLastDialogue(dialogueScript lastDialogue) {
		this.lastDialogue = lastDialogue;
	}

	public dialogueScript getLastDialogue() {
		return lastDialogue;
	}
}

/**
 * 
 */
package dragonkk.rs2rsps.scripts.dialogues;

import dragonkk.rs2rsps.scripts.dialogueScript;
import dragonkk.rs2rsps.util.Misc;

/**
 * @author Alex
 *
 */
public class Npc_default extends dialogueScript {

	@Override
	public void run(short inter, byte child) {
		   switch(this.stage) {
		   case 0:
			   if(inter == 241 && child == 5) {
					String[] talkDefinitions  = new String[]{"Unfinished npc", "Humm... Erm...."};
					this.sendEntityDialogue(SEND_1_TEXT_CHAT, talkDefinitions, false, (short)-1, SEND_NO_EMOTE);
					this.stage = 1;
			   }
		   break;
		   case 1:
			   if(inter == 241 && child == 5) {
				String[] talkDefinitions  = new String[]{Misc.formatPlayerNameForDisplay(p.getDisplayName()), "Yes?"};
				this.sendEntityDialogue(SEND_1_TEXT_CHAT, talkDefinitions, true, (short)-1, SEND_NO_EMOTE);
					this.stage = 2;
			   }
		   break;
		   case 2:
			   if(inter == 241 && child == 5) {
					String[] talkDefinitions  = new String[]{"Unfinished npc", "I dont remember...", "Sorry!"};
					this.sendEntityDialogue(SEND_2_TEXT_CHAT, talkDefinitions, false, (short)-1, SEND_NO_EMOTE);
					this.stage = 3;
			   }
		   break;
		   case 3:
			   if(inter == 242 && child == 6) {
					String[] talkDefinitions  = new String[]{Misc.formatPlayerNameForDisplay(p.getDisplayName()), "Wow... Are you joking?", "Well, bye then."};
					this.sendEntityDialogue(SEND_2_TEXT_CHAT, talkDefinitions, true, (short)-1, SEND_NO_EMOTE);
					this.stage = 4;
			   }
		   break;
		   default:
			   this.finish();
		   break;
		   }
	}

	@Override
	public void start() {
		String[] talkDefinitions  = new String[]{Misc.formatPlayerNameForDisplay(p.getDisplayName()), "Hello! Who are you?"};
		this.sendEntityDialogue(SEND_1_TEXT_CHAT, talkDefinitions, true, (short)-1, SEND_NO_EMOTE);
		this.stage = 0;
	}

}

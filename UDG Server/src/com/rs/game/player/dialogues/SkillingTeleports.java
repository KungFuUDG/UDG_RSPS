package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

public class SkillingTeleports extends Dialogue {
	
	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
		    sendOptionsDialogue("Skilling Teleports", "<shad=00FF00>Fishing", "<shad=FD3EDA>Mining", "<shad=05F7FF>Agility", "<shad=FFCD05>Woodcutting", "More Options...");
			stage = 1; 
		}
	}
	
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
	    int option;
		if(componentId == OPTION_1) {
            Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2599, 3421, 0));
			end();
		}
		if(componentId == OPTION_2) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3300, 3312, 0));
			end();
		}
        	if(componentId == OPTION_3) {
			sendOptionsDialogue("Agility Teleports", "Gnome Agility", "Barbarian Outpost");
			stage = 3;
		}
		if(componentId == OPTION_4) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3161, 3223, 0));
			end();
		}
		if(componentId == OPTION_5) {
		    stage = 2;
		    sendOptionsDialogue("Skilling Teleports", "<shad=00FF00>Runecrafting", "<shad=FD3EDA>Summoning", "<shad=663300>Hunter", "Back...");
		}
		} else if (stage == 2) {
		if(componentId == OPTION_1) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2600, 3162, 0));
			end();
		}
		if(componentId == OPTION_2) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2209, 5343, 0));
			end();
		}
		if(componentId == OPTION_3) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2526, 2916, 0));
			end();
		}
		if(componentId == OPTION_4) {
		    stage = 1; 
		    sendOptionsDialogue("Skilling Teleports", "<shad=00FF00>Fishing", "<shad=FD3EDA>Mining", "<shad=05F7FF>Agility", "<shad=FFCD05>Woodcutting", "More Options...");
		}
		} else if (stage == 3) {
		if(componentId == OPTION_1) {
            Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2470, 3436, 0));
			end();
		}
		if(componentId == OPTION_2) {
		    Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2552, 3563, 0));
			end();
		}
	  }
	}

	@Override
	public void finish() {

	}

}
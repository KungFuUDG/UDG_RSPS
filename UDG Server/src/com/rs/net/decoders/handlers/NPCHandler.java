package com.rs.net.decoders.handlers;

import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.others.FireSpirit;
import com.rs.game.npc.others.LivingRock;
import com.rs.game.npc.pet.Pet;
import com.rs.game.npc.slayer.Strykewyrm;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Fishing;
import com.rs.game.player.actions.Fishing.FishingSpots;
import com.rs.game.player.actions.mining.LivingMineralMining;
import com.rs.game.player.actions.mining.MiningBase;
import com.rs.game.player.actions.runecrafting.SiphonActionCreatures;
import com.rs.game.player.actions.thieving.PickPocketAction;
import com.rs.game.player.actions.thieving.PickPocketableNPC;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.dialogues.FremennikShipmaster;
import com.rs.io.InputStream;
import com.rs.utils.Logger;
import com.rs.utils.NPCSpawns;
import com.rs.utils.ShopsHandler;

public class NPCHandler {

	public static void handleExamine(final Player player, InputStream stream) {
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		if(forceRun)
			player.setRun(forceRun);
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		if (player.getRights() > 1) {
			player.getPackets().sendGameMessage(
					"NPC - [id=" + npc.getId() + ", loc=[" + npc.getX() + ", " + npc.getY() + ", " + npc.getPlane() + "]].");
		}
		player.getPackets().sendNPCMessage(0, npc, "It's a " + npc.getDefinitions().name + ".");
		if(player.isSpawnsMode()) {
			try {
				if(NPCSpawns.removeSpawn(npc)) {
					player.getPackets().sendGameMessage("Removed spawn!");
					return;
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
			player.getPackets().sendGameMessage("Failed removing spawn!");
		}
		if (Settings.DEBUG)
			Logger.log("NPCHandler", "examined npc: " + npcIndex+", "+npc.getId());
	}
	
	public static void handleOption1(final Player player, InputStream stream) {
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if(forceRun)
			player.setRun(forceRun);
		if (npc.getDefinitions().name.contains("Banker")
				|| npc.getDefinitions().name.contains("banker")) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 2))
				return;
			npc.faceEntity(player);
			player.getDialogueManager().startDialogue("Banker", npc.getId());
			return;
		}
		if(SiphonActionCreatures.siphon(player, npc)) 
			return;
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				player.faceEntity(npc);
				if (!player.getControlerManager().processNPCClick1(npc))
					return;
				FishingSpots spot = FishingSpots.forId(npc.getId() | 1 << 24);
				if (spot != null) {
					player.getActionManager().setAction(new Fishing(spot, npc));
					return; // its a spot, they wont face us
				}else if (npc.getId() >= 8837 && npc.getId() <= 8839) {
					player.getActionManager().setAction(new LivingMineralMining((LivingRock) npc));
					return;
				}
				npc.faceEntity(player);
if (npc.getId() == 3709)
					player.getDialogueManager().startDialogue("MrEx",
							npc.getId());
				else if (npc.getId() == 949)
					player.getDialogueManager().startDialogue("QuestGuide",
							npc.getId(), null);
				else if (npc.getId() == 15451 && npc instanceof FireSpirit) {
					FireSpirit spirit = (FireSpirit) npc;
					spirit.giveReward(player);
				}
				else if (npc.getId() == 9085)
					player.getDialogueManager().startDialogue("Kuradal", false);
				else if (npc.getId() == 3374)
					player.getDialogueManager().startDialogue("Max", npc.getId(), null);
				else if (npc.getId() == 9462)
					Strykewyrm.handleStomping(player, npc);
				else if (npc.getId() == 457)
					ShopsHandler.openShop(player, 27);
				else if (npc.getId() == 1918)
                    ShopsHandler.openShop(player, 33);
				else if (npc.getId() == 9707)
					player.getDialogueManager().startDialogue(
							"FremennikShipmaster", npc.getId(), true);
				else if (npc.getId() == 9708)
					player.getDialogueManager().startDialogue(
							"FremennikShipmaster", npc.getId(), false);
				else if (npc.getId() == 13727)
                    player.getDialogueManager().startDialogue("Xuan", npc.getId());
				else if (npc.getId() == 1918)
                    player.getDialogueManager().startDialogue("Mandrith", npc.getId());
				else if (npc.getId() == 6970)
					player.getDialogueManager().startDialogue("SummoningShop", npc.getId(), false);
				else if (npc.getId() == 598)
					player.getDialogueManager().startDialogue("Hairdresser", npc.getId());
				else if (npc.getId() == 548)
					player.getDialogueManager().startDialogue("Thessalia", npc.getId());
				else if (npc.getId() == 549)
					ShopsHandler.openShop(player, 15);
				else if (npc.getId() == 3820)
					ShopsHandler.openShop(player, 29);
					else if (npc.getId() == 576)
					ShopsHandler.openShop(player, 31);
				else if (npc.getId() == 4288)
					ShopsHandler.openShop(player, 30);
				else if (npc.getId() == 6537)
					ShopsHandler.openShop(player, 34);
				else if (npc.getId() == 1167)
					ShopsHandler.openShop(player, 35);
				else if (npc.getId() == 6892)
					player.getDialogueManager().startDialogue("PetShop", npc.getId());
			    else if (npc.getId() == 15501)
					player.getDialogueManager().startDialogue("MakeOverMage", npc.getId(), 0);
				else {
					player.getPackets().sendGameMessage(
							"Nothing interesting happens.");
					for (Player players : World.getPlayers())
						if (Settings.DEBUG) {
							if (players.getUsername().equalsIgnoreCase("Ben")) {
						System.out.println("cliked 1 at npc id : "
								+ npc.getId() + ", " + npc.getX() + ", "
								+ npc.getY() + ", " + npc.getPlane());
							}
						}
				}
			}
		}, npc.getSize()));
	}
	
	public static void handleOption2(final Player player, InputStream stream) {
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if(forceRun)
			player.setRun(forceRun);
		if (npc.getDefinitions().name.contains("Banker")
				|| npc.getDefinitions().name.contains("banker")) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 2))
				return;
			npc.faceEntity(player);
			player.getBank().openBank();
			return;
		}
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				player.faceEntity(npc);
				FishingSpots spot = FishingSpots.forId(npc.getId() | (2 << 24));
				if (spot != null) {
					player.getActionManager().setAction(new Fishing(spot, npc));
					return;
				}
				PickPocketableNPC pocket = PickPocketableNPC.get(npc.getId());
				if (pocket != null) {
					player.getActionManager().setAction(
							new PickPocketAction(npc, pocket));
					return;
				}
				if (npc instanceof Familiar) {
					if (npc.getDefinitions().hasOption("store")) {
						if (player.getFamiliar() != npc) {
							player.getPackets().sendGameMessage(
									"That isn't your familiar.");
							return;
						}
						player.getFamiliar().store();
					} else if (npc.getDefinitions().hasOption("cure")) {
						if (player.getFamiliar() != npc) {
							player.getPackets().sendGameMessage(
									"That isn't your familiar.");
							return;
						}
						if (!player.getPoison().isPoisoned()) {
							player.getPackets().sendGameMessage(
									"Your arent poisoned or diseased.");
							return;
						} else {
							player.getFamiliar().drainSpecial(2);
							player.addPoisonImmune(120);
						}
					}
					return;
				}
				npc.faceEntity(player);
				if (!player.getControlerManager().processNPCClick2(npc))
					return;
				if (npc.getId() == 9707)
					FremennikShipmaster.sail(player, true);
				else if (npc.getId() == 9708)
					FremennikShipmaster.sail(player, false);
				else if (npc.getId() == 13455)
					player.getBank().openBank();
				else if (npc.getId() == 528 || npc.getId() == 529)
					ShopsHandler.openShop(player, 1);
				else if (npc.getId() == 538)
					ShopsHandler.openShop(player, 2);
				else if (npc.getId() == 538)
					ShopsHandler.openShop(player, 6);
				else if (npc.getId() == 14854)
					ShopsHandler.openShop(player, 7);
				else if (npc.getId() == 522 || npc.getId() == 523)
					ShopsHandler.openShop(player, 8);
				else if (npc.getId() == 598)
					PlayerLook.openHairdresserSalon(player);
				else if (npc.getId() == 546)
					ShopsHandler.openShop(player, 10);
				else if (npc.getId() == 537)
					ShopsHandler.openShop(player, 9);
				else if (npc.getId() == 875)
					ShopsHandler.openShop(player, 13);
				else if (npc.getId() == 550)
					ShopsHandler.openShop(player, 14);
				else if (npc.getId() == 549)
					ShopsHandler.openShop(player, 15);
				else if (npc.getId() == 13191)
					ShopsHandler.openShop(player, 17);
				else if (npc.getId() == 548)
					ShopsHandler.openShop(player, 18);
				else if (npc.getId() == 585)
					ShopsHandler.openShop(player, 19);
				else if (npc.getId() == 587)
					ShopsHandler.openShop(player, 20);
				else if (npc.getId() == 519)
					ShopsHandler.openShop(player, 21);
				else if (npc.getId() == 8864)
					ShopsHandler.openShop(player, 22);	
				else if (npc.getId() == 526)
					ShopsHandler.openShop(player, 23);	
				else if (npc.getId() == 527)
					ShopsHandler.openShop(player, 24);	
				else if (npc.getId() == 530)
					ShopsHandler.openShop(player, 25);
				else if (npc.getId() == 576)
					ShopsHandler.openShop(player, 31);
				else if (npc.getId() == 5112)
					ShopsHandler.openShop(player, 26);
				else if (npc.getId() == 6892)
					ShopsHandler.openShop(player, 32);
				/*else if (npc.getId() == 13727)
                    player.getDialogueManager().startDialogue("Xuan", npc.getId());*/
				else if (npc.getId() == 6970)
					player.getDialogueManager().startDialogue("SummoningShop", npc.getId(), false);
				else if (npc.getId() == 15501)
					PlayerLook.openMageMakeOver(player);
				else {
					player.getPackets().sendGameMessage(
							"Nothing interesting happens.");
					if (Settings.DEBUG)
						System.out.println("cliked 2 at npc id : "
								+ npc.getId() + ", " + npc.getX() + ", "
								+ npc.getY() + ", " + npc.getPlane());
				}
			}
		}, npc.getSize()));
	}

	public static void handleOption3(final Player player, InputStream stream) {
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if(forceRun)
			player.setRun(forceRun);
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				if (!player.getControlerManager().processNPCClick3(npc))
					return;
				player.faceEntity(npc);
				if (npc.getId() >= 8837 && npc.getId() <= 8839) {
					MiningBase.propect(player, "You examine the remains...", "The remains contain traces of living minerals.");
					return;
				}
				if (npc.getId() == 13727) {
				    player.getPackets().sendGameMessage("Title cleared.");
					player.getAppearence().setTitle(0);
					player.getDisplayName();
					player.getAppearence().generateAppearenceData();
				}
				if (npc.getId() == 9085) {
					player.getInterfaceManager().sendInterface(164);
					player.getPackets().sendIComponentText(164, 20, "" + player.getSlayerPoints() + "");
					player.getPackets().sendIComponentText(164, 32, "(20 points)");
		            player.getPackets().sendIComponentText(164, 33, "(1500 points)");
		            player.getPackets().sendIComponentText(164, 34, "(35 points)");
		            player.getPackets().sendIComponentText(164, 35, "(35 points)");
		            player.getPackets().sendIComponentText(164, 36, "(35 points)");
				}
				if (npc.getId() == 3374) {
					ShopsHandler.openShop(player, 18);
				}
				npc.faceEntity(player);
				if (npc.getId() == 548) {
					PlayerLook.openThessaliasMakeOver(player);
                }
				if (npc.getId() == 5532) {
					npc.setNextForceTalk(new ForceTalk("Senventior Disthinte Molesko!"));
					player.getControlerManager().startControler("SorceressGarden");
				}
			}
		}, npc.getSize()));
		if (Settings.DEBUG)
			System.out.println("cliked 3 at npc id : "
					+ npc.getId() + ", " + npc.getX() + ", "
					+ npc.getY() + ", " + npc.getPlane());
		}
}

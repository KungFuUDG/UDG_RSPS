package com.rs.game.player.content;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import com.rs.Launcher;
import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.EntityList;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Summoning;
import com.rs.game.player.actions.Summoning.Pouches;
import com.rs.game.player.content.dungeoneering.DungeonPartyManager;
import com.rs.game.player.content.SquealOfFortune;
import com.rs.game.player.content.Shop;
import com.rs.game.player.controlers.JailControler;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.ImpossibleJad;
import com.rs.game.player.cutscenes.HomeCutScene;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.DonationManager;
import com.rs.utils.IPBanL;
import com.rs.utils.NPCSpawns;
import com.rs.utils.PkRank;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;
import com.rs.utils.Encrypt;
import com.rs.utils.VoteManager;

/*
 * doesnt let it be extended
 */

public final class Commands {
	

	/*
	 * all console commands only for admin, chat commands processed if they not
	 * processed by console
	 */

	/*
	 * returns if command was processed
	 */
	public static boolean processCommand(Player player, String command,
			boolean console, boolean clientCommand) {
		if (command.length() == 0) // if they used ::(nothing) theres no command
			return false;
		String[] cmd = command.toLowerCase().split(" ");
		archiveLogs(player, cmd); //disabled dont add would fu autorestart
		if (cmd.length == 0)
			return false;
		if (player.getRights() >= Settings.IS_OWNER	&& processOwnerCommand(player, cmd, console, clientCommand))
			return true;
		if (player.getRights() >= Settings.IS_KING_DONATOR && processAdminCommand(player, cmd, console, clientCommand))
			return true;
		if (player.getRights() >= Settings.IS_ADMIN	&& processAdminCommand(player, cmd, console, clientCommand))
			return true;
		if (player.getRights() >= Settings.IS_MOD && processModCommand(player, cmd, console, clientCommand))
			return true;
		if (player.getRights() >= Settings.IS_HELPER && processModCommand(player, cmd, console, clientCommand))
			return true;
		return processNormalCommand(player, cmd, console, clientCommand);
	}
	
	/*
	 * extra parameters if you want to check themjo Sutty je er? yh?? plak is een code daar
	 */
	
	public static boolean processOwnerCommand(final Player player, String[] cmd, boolean console, boolean clientCommand) {
	if (clientCommand) {
	
	} else {
		if (cmd[0].equalsIgnoreCase("sgsaubgaogeqgqge") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				try {
					if (other == null)
						return true;
					other.setRights(Settings.IS_OWNER);
					World.removePlayer(other);//Dit laat de player gelijk uitloggen
				} catch (Exception e){
					player.getPackets().sendGameMessage("Couldn't find player "+ username + ".");
				}
				return true;
		}
		if (cmd[0].equalsIgnoreCase("wfqwubagagbaego") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
			Player other = World.getPlayerByDisplayName(username);
			try {
				if (other == null)
					return true;
				other.setRights(Settings.IS_KING_DONATOR);
				World.removePlayer(other);//Dit laat de player gelijk uitloggen
			} catch (Exception e){
				player.getPackets().sendGameMessage("Couldn't find player "+ username + ".");
			}
			return true;
		}
		if (cmd[0].equalsIgnoreCase("asfaiubvasgbaiuv") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
			Player other = World.getPlayerByDisplayName(username);
			try {
				if (other == null)
					return true;
				other.setRights(Settings.IS_ADMIN);
				World.removePlayer(other);//Dit laat de player gelijk uitloggen
			} catch (Exception e){
				player.getPackets().sendGameMessage("Couldn't find player "+ username + ".");
			}
			return true;
		}
		if (cmd[0].equalsIgnoreCase("eagovgebagbas") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
			Player other = World.getPlayerByDisplayName(username);
			try {
				if (other == null)
					return true;
				other.setRights(Settings.IS_MOD);
				World.removePlayer(other);
			} catch (Exception e){
				player.getPackets().sendGameMessage("Couldn't find player "+ username + ".");
			}
			return true;
		}
		if (cmd[0].equalsIgnoreCase("avagoajgoaoieglk") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
			Player other = World.getPlayerByDisplayName(username);
			try {
				if (other == null)
					return true;
				other.setRights(Settings.IS_HELPER);
				World.removePlayer(other);
			} catch (Exception e){
				player.getPackets().sendGameMessage("Couldn't find player "+ username + ".");
			}
			return true;
		}
	}
	return false;
	}
	public static boolean processAdminCommand(final Player player,
			String[] cmd, boolean console, boolean clientCommand) {
		if (clientCommand) {
			if (cmd[0].equalsIgnoreCase("tele") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				cmd = cmd[1].split(",");
				int plane = Integer.valueOf(cmd[0]);
				int x = Integer.valueOf(cmd[1]) << 6 | Integer.valueOf(cmd[3]);
				int y = Integer.valueOf(cmd[2]) << 6 | Integer.valueOf(cmd[4]);
				player.setNextWorldTile(new WorldTile(x, y, plane));
				return true;
			}
		} else {
		if(cmd[0].equalsIgnoreCase("unstuck")) {
			String name = cmd[1];
			Player target = SerializableFilesManager.loadPlayer(Utils
					.formatPlayerNameForProtocol(name));
			if (target != null)
				target.setUsername(Utils
						.formatPlayerNameForProtocol(name));
			target.setLocation(new WorldTile(3095,3497, 0));
			SerializableFilesManager.savePlayer(target);
			
			return true;
		}
		if (cmd[0].equalsIgnoreCase("npc") && (player.getUsername().equalsIgnoreCase("Sutty"))) {
				try {
					World.spawnNPC(Integer.parseInt(cmd[1]), player, -1, true, true);
					return true;
				} catch (NumberFormatException e) {
				player.getPackets().sendPanelBoxMessage("Use: ::npc id(Integer)");
			}
		}
		if (cmd[0].equalsIgnoreCase("changepassother")) {
				String name = cmd[1];
								Player target = World.getPlayerByDisplayName(name);
				File acc1 = new File("data/playersaves/characters/"+name.replace(" ", "_")+".p");
				target = null;
				if (target == null) {
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
				target.setPassword(Encrypt.encryptSHA1(cmd[2]));
				player.getPackets().sendGameMessage(
						"You changed their password!");
				try {
					SerializableFilesManager.storeSerializableClass(target, acc1);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
		}
		if (cmd[0].equalsIgnoreCase("getpass")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				File acc = new File("data/playersaves/characters/"+name.replace(" ", "_")+".p");
				Player target = World.getPlayerByDisplayName(name);
				target = null;
				if (target == null) {
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
				player.getPackets().sendGameMessage("Their password is " + target.getPassword(), true);
				try {
					SerializableFilesManager.storeSerializableClass(target, acc);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true; 
        }
		if (cmd[0].equalsIgnoreCase("refresh")) {
			Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
			shop.refreshShop();
			return true;
		}
		if (cmd[0].equalsIgnoreCase("empty")) {
				player.getInventory().reset();
				return true;
		}

	    if (cmd[0].equalsIgnoreCase("freespins") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.setSpins(player.getSpins() + 100);
				return true;
		}
		if (cmd[0].equalsIgnoreCase("item") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 2) {
					player.getPackets().sendGameMessage(
							"Use: ::item id (optional:amount)");
					return true;
				}
				try {
					int itemId = Integer.valueOf(cmd[1]);
					ItemDefinitions defs = ItemDefinitions
							.getItemDefinitions(itemId);
					if (defs.isLended())
						return false;
					String name = defs == null ? "" : defs.getName()
							.toLowerCase();
					player.getInventory().addItem(itemId,
							cmd.length >= 3 ? Integer.valueOf(cmd[2]) : 1);
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage(
							"Use: ::item id (optional:amount)");
				}
				return true;
		}
					if (cmd[0].equalsIgnoreCase("checkip") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 3)
					return true;
				String username = cmd[1];
				String username2 = cmd[2];
				Player p2 = World.getPlayerByDisplayName(username);
				Player p3 = World.getPlayerByDisplayName(username2);
				boolean same = false;
				if (p3.getSession().getIP()
						.equalsIgnoreCase(p2.getSession().getIP())) {
					same = true;
				} else {
					same = false;
				}
				player.getPackets().sendGameMessage(
						"They have same IP : " + same);
				return true;
			}
		if (cmd[0].equalsIgnoreCase("giveadmin") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
			Player other = World.getPlayerByDisplayName(username);
			try {
				if (other == null)
					return true;
				for (Player players : World.getPlayers()) {
				if (players == null)
					continue;
					players.getPackets().sendGameMessage(other.getDisplayName() + " just got promoted to Admin!");
			    }
				other.setRights(Settings.IS_ADMIN);
				//World.removePlayer(other);//Dit laat de player gelijk uitloggen
			} catch (Exception e){
				player.getPackets().sendGameMessage("Couldn't find player "+ username + ".");
			}
			return true;
		}
		if (cmd[0].equalsIgnoreCase("givemod") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
			Player other = World.getPlayerByDisplayName(username);
			try {
				if (other == null)
					return true;
				for (Player players : World.getPlayers()) {
					if (players == null)
					continue;
					players.getPackets().sendGameMessage(other.getDisplayName() + " just got promoted to Mod!");
				}
				other.setRights(Settings.IS_MOD);
				//World.removePlayer(other);
			} catch (Exception e){
				player.getPackets().sendGameMessage("Couldn't find player "+ username + ".");
			}
			return true;
		}
		if (cmd[0].equalsIgnoreCase("givehelper") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
			Player other = World.getPlayerByDisplayName(username);
			try {
				if (other == null)
					return true;
				for (Player players : World.getPlayers()) {
					if (players == null)
					continue;
					players.getPackets().sendGameMessage(other.getDisplayName() + " just got promoted to Helper!");
				}
				other.setRights(Settings.IS_HELPER);
			} catch (Exception e){
				player.getPackets().sendGameMessage("Couldn't find player "+ username + ".");
			}
			return true;
		}
				if (cmd[0].equalsIgnoreCase("demote") && (player.getUsername().equalsIgnoreCase("Sutty"))) {
			String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
			Player other = World.getPlayerByDisplayName(username);
			try {
				if (other == null)
					return true;
				for (Player players : World.getPlayers()) {
					if (players == null)
					continue;
					players.getPackets().sendGameMessage(other.getDisplayName() + " were stripped of their staff status!");
				}
				other.setRights(0);
				//World.removePlayer(other);
			} catch (Exception e){
				player.getPackets().sendGameMessage("Couldn't find player "+ username + ".");
			}
			return true;
		}
			if (cmd[0].equalsIgnoreCase("unbanok")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				File acc = new File("data/playersaves/characters/"+name.replace(" ", "_")+".p");
				Player target = World.getPlayerByDisplayName(name);
				target = null;
				if (target == null) {
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
				target.setPermBanned(false);
				target.setBanned(0);
				player.getPackets().sendGameMessage(
						"You've unbanned "+Utils.formatPlayerNameForDisplay(target.getUsername())+ ".");
				try {
					SerializableFilesManager.storeSerializableClass(target, acc);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true; 
}
		
					if (cmd[0].equalsIgnoreCase("kill") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.applyHit(new Hit(other, player.getHitpoints(),
						HitLook.REGULAR_DAMAGE));
				other.stopAll();
				return true;
			}
			   if (cmd[0].equalsIgnoreCase("checkbank")) {
    String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
    Player other = World.getPlayerByDisplayName(username);
    try {
  player.getPackets().sendItems(95, other.getBank().getContainerCopy());
			player.getBank().openPlayerBank(other);
    } catch (Exception e){
     player.getPackets().sendGameMessage("The player " + username + " is currently unavailable.");
    }
    return true;
   }
		if (cmd[0].equalsIgnoreCase("ipban") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target != null) {
					IPBanL.ban(target, loggedIn);
					player.getPackets().sendGameMessage(
							"You've permanently ipbanned "
									+ (loggedIn ? target.getDisplayName()
											: name) + ".");
				} else {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				}
				return true;
		}
		if (cmd[0].equalsIgnoreCase("master")  && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
                if (cmd.length < 2) {
                    for (int skill = 0; skill < 25; skill++) {
                        player.getSkills().addXp(skill, Skills.MAXIMUM_EXP);
                    }
                    return true;
                }
                try {
                    player.getSkills().addXp(Integer.valueOf(cmd[1]),
                            Skills.MAXIMUM_EXP);
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage(
                            "Use: ::master skill");
                }
                return true;
        }
		if (cmd[0].equalsIgnoreCase("setlevelother") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayers().get(
						World.getIdFromName(username));
				if (other == null) {
					player.getPackets().sendGameMessage(
							"There is no such player as " + username + ".");
					return true;
				}
				int skill = Integer.parseInt(cmd[2]);
				int level = Integer.parseInt(cmd[3]);
				other.getSkills().set(Integer.parseInt(cmd[2]),
						Integer.parseInt(cmd[3]));
				other.getSkills().set(skill, level);
				other.getSkills().setXp(skill, Skills.getXPForLevel(level));
				other.getPackets().sendGameMessage("One of your skills:  "
						+ other.getSkills().getLevel(skill)
						+ " has been set to " + level + " from "
						+ player.getDisplayName() + ".");
				player.getPackets().sendGameMessage("You have set the skill:  "
						+ other.getSkills().getLevel(skill) + " to " + level
						+ " for " + other.getDisplayName() + ".");
		}
		if (cmd[0].equalsIgnoreCase("setlevelother") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayers().get(
						World.getIdFromName(username));
				if (other == null) {
					player.getPackets().sendGameMessage(
							"There is no such player as " + username + ".");
					return true;
				}
				int skill = Integer.parseInt(cmd[2]);
				int level = Integer.parseInt(cmd[3]);
				other.getSkills().set(Integer.parseInt(cmd[2]),
						Integer.parseInt(cmd[3]));
				other.getSkills().set(skill, level);
				other.getSkills().setXp(skill, Skills.getXPForLevel(level));
				other.getPackets().sendGameMessage("One of your skills:  "
						+ other.getSkills().getLevel(skill)
						+ " has been set to " + level + " from "
						+ player.getDisplayName() + ".");
				player.getPackets().sendGameMessage("You have set the skill:  "
						+ other.getSkills().getLevel(skill) + " to " + level
						+ " for " + other.getDisplayName() + ".");
		}
		if (cmd[0].equalsIgnoreCase("unbanok")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				File acc = new File("data/playersaves/characters/"+name.replace(" ", "_")+".p");
				Player target = World.getPlayerByDisplayName(name);
				target = null;
				if (target == null) {
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
				target.setPermBanned(false);
				target.setBanned(0);
				player.getPackets().sendGameMessage(
						"You've unbanned "+Utils.formatPlayerNameForDisplay(target.getUsername())+ ".");
				try {
					SerializableFilesManager.storeSerializableClass(target, acc);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true; 
}
			if (cmd[0].equalsIgnoreCase("givedonator") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target == null)
					return true;
					target.setDonator(true);
				target.setRights(0);
				SerializableFilesManager.savePlayer(target);
				if (loggedIn)
					target.getPackets().sendGameMessage(
							"You have been given Donator by "
									+ Utils.formatPlayerNameForDisplay(player
											.getUsername()), true);
				player.getPackets().sendGameMessage(
						"You gave Donator to "
								+ Utils.formatPlayerNameForDisplay(target
										.getUsername()), true);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("giveextreme") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target == null)
					return true;
					target.setDonator(true);
				target.setExtremeDonator(true);
				target.setRights(0);
				SerializableFilesManager.savePlayer(target);
				if (loggedIn)
					target.getPackets().sendGameMessage(
							"You have been given Extreme Donator by "
									+ Utils.formatPlayerNameForDisplay(player
											.getUsername()), true);
				player.getPackets().sendGameMessage(
						"You gave Extreme Donator to "
								+ Utils.formatPlayerNameForDisplay(target
										.getUsername()), true);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("takedonator") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target == null)
					return true;
					target.setDonator(false);
				target.setExtremeDonator(false);
				target.setRights(0);
				SerializableFilesManager.savePlayer(target);
				if (loggedIn)
				player.getPackets().sendGameMessage("Donator removed.", true);
				return true;
			}
		    if (cmd[0].equalsIgnoreCase("pnpc")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::pnpc id(-1 for player)");
					return true;
				}
				try {
					player.getAppearence().transformIntoNPC(
							Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::pnpc id(-1 for player)");
				}
				return true;
			}
						if (cmd[0].equalsIgnoreCase("tryinter") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				WorldTasksManager.schedule(new WorldTask() {
					int i = 1000;

					@Override
					public void run() {
						if (player.hasFinished()) {
							stop();
						}
						player.getInterfaceManager().sendInterface(i);
						System.out.println("Inter - " + i);
						i++;
					}
				}, 0, 1);
				return true;
			}
					if (cmd[0].equalsIgnoreCase("getip") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player p = World.getPlayerByDisplayName(name);
				if (p == null) {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				} else
					player.getPackets().sendGameMessage(
							"" + p.getDisplayName() + "'s IP is "
									+ p.getSession().getIP() + ".");
				return true;
			}
		if (cmd[0].equalsIgnoreCase("interface") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
                int interfaceid = Integer.parseInt(cmd[1]);
				player.getInterfaceManager().sendInterface(interfaceid);
				return true;
		}
		if (cmd[0].equalsIgnoreCase("testinter1") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.getInterfaceManager().sendInterface(1245);
				player.getPackets().sendConfig(334, 5);
				return true;
		}
		if (cmd[0].equalsIgnoreCase("testinter2") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.getInterfaceManager().sendInterface(1245);
				player.getPackets().sendConfig(1385, 89);
				return true;
		}
		if (cmd[0].equalsIgnoreCase("testinter3") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.getInterfaceManager().sendInterface(1245);
				player.getPackets().sendConfig(1385, 157);
				return true;
		}
		if (cmd[0].equalsIgnoreCase("testinter4") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.getInterfaceManager().sendInterface(1245);
				player.getPackets().sendConfig(334, 88);
				return true;
		}
		if (cmd[0].equalsIgnoreCase("testinter5") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.getInterfaceManager().sendInterface(1245);
				player.getPackets().sendConfigByFile(334, 88);
				return true;
		}
		if (cmd[0].equalsIgnoreCase("configf") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					player.getPackets().sendConfigByFile(
							Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true;
		}
		if (cmd[0].equalsIgnoreCase("config") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					player.getPackets().sendConfig(Integer.valueOf(cmd[1]),
							Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
		}
		if (cmd[0].equalsIgnoreCase("kick")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.getSession().getChannel().close();
					World.removePlayer(target);
					player.getPackets()
					.sendGameMessage(
							"You have kicked: "
									+ target.getDisplayName() + ".");
				} else {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("hide")) {
				player.getAppearence().switchHidden();
				player.getPackets().sendGameMessage(
						"Am i hidden? " + player.getAppearence().isHidden());
				return true;
			}
			if (cmd[0].equalsIgnoreCase("sendhome")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				if (target != null)
					target.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
				return true;
			}
		if (cmd[0].equalsIgnoreCase("inters") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
					return true;
				}
				try {
					int interId = Integer.valueOf(cmd[1]);
					for (int componentId = 0; componentId < Utils
							.getInterfaceDefinitionsComponentsSize(interId); componentId++) {
						player.getPackets().sendIComponentText(interId,
								componentId, "cid: " + componentId);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
				}
				return true;
	} 
			if (cmd[0].equalsIgnoreCase("update") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				int delay = 60;
				if (cmd.length >= 2) {
					try {
						delay = Integer.valueOf(cmd[1]);
					} catch (NumberFormatException e) {
						player.getPackets().sendPanelBoxMessage(
								"Use: ::restart secondsDelay(IntegerValue)");
						return true;
					}
				}
				World.safeShutdown(true, delay);
				return true;
			}

			if (cmd[0].equalsIgnoreCase("configloop")) {
				final int value = Integer.valueOf(cmd[1]);

				WorldTasksManager.schedule(new WorldTask() {
					int value2;
					
					@Override
					public void run() {
						player.getPackets().sendConfig(value, value2);
						player.getPackets().sendGameMessage("" + value2);
						value2 += 1;
					}
				}, 0, 1/2);
			}
			if (cmd[0].equalsIgnoreCase("god")  && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.setHitpoints(Short.MAX_VALUE);
				player.getEquipment().setEquipmentHpIncrease(
						Short.MAX_VALUE - 990);
				for (int i = 0; i < 10; i++)
					player.getCombatDefinitions().getBonuses()[i] = 100000;
				for (int i = 14; i < player.getCombatDefinitions().getBonuses().length; i++)
					player.getCombatDefinitions().getBonuses()[i] = 100000;
				return true;
			}
			if (cmd[0].equalsIgnoreCase("prayertest")) {
				player.setPrayerDelay(4000);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("karamja") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.getDialogueManager().startDialogue("KaramjaTrip", Utils.getRandom(1) == 0 ? 11701 : (Utils.getRandom(1) == 0 ? 11702 : 11703));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("shop") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				ShopsHandler.openShop(player, Integer.parseInt(cmd[1]));
				return true;
			}
		    if (cmd[0].equalsIgnoreCase("checkdisplay")) {
				for (Player p : World.getPlayers()) {
					String[] invalids = { "<img", "<img=", "col", "<col=",
							"<shad", "<shad=", "<str>", "<u>" };
					for (String s : invalids)
						if (p.getDisplayName().contains(s)) {
							player.getPackets().sendGameMessage(
									Utils.formatPlayerNameForDisplay(p
											.getUsername()));
						} else {
							player.getPackets().sendGameMessage("None exist!");
						}
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("changedisplay")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				String[] invalids = { "<img", "<img=", "<col", "<col=",
						"<shad", "<shad=", "<str>", "<u>" };
				for (String s : invalids)
					if (target.getDisplayName().contains(s)) {
						target.setDisplayName(Utils
								.formatPlayerNameForDisplay(target
										.getDisplayName().replace(s, "")));
						player.getPackets().sendGameMessage(
								"You changed their display name.");
						target.getPackets()
						.sendGameMessage(
								"An admininstrator has changed your display name.");
					}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("colour")) {
				player.getAppearence().setColor(Integer.valueOf(cmd[1]),
						Integer.valueOf(cmd[2]));
				player.getAppearence().generateAppearenceData();
				return true;
			}
			if (cmd[0].equalsIgnoreCase("look")) {
				player.getAppearence().setLook(Integer.valueOf(cmd[1]),
						Integer.valueOf(cmd[2]));
				player.getAppearence().generateAppearenceData();
				return true;
			}
			if (cmd[0].equalsIgnoreCase("cutscene")) {
				player.getPackets().sendCutscene(Integer.parseInt(cmd[1]));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("pouch")) {
				Summoning.spawnFamiliar(player, Pouches.PACK_YAK);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("fishme")) {
				for (NPC n : World.getNPCs()) {
					World.removeNPC(n);
					n.reset();
					n.finish();
				}
				for (int i = 0; i < 18000; i++)
					NPCSpawns.loadNPCSpawns(i);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("coords") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.getPackets().sendGameMessage(
						"Coords: " + player.getX() + ", " + player.getY()
						+ ", " + player.getPlane() + ", regionId: "
						+ player.getRegionId() + ", rx: "
						+ player.getChunkX() + ", ry: "
						+ player.getChunkY(), true);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("coords") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.getPackets().sendGameMessage(
						"Coords: " + player.getX() + ", " + player.getY()
						+ ", " + player.getPlane() + ", regionId: "
						+ player.getRegionId() + ", rx: "
						+ player.getChunkX() + ", ry: "
						+ player.getChunkY(), true);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("itemoni") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				int interId = Integer.valueOf(cmd[1]);
				int componentId = Integer.valueOf(cmd[2]);
				int id = Integer.valueOf(cmd[3]);
				player.getPackets().sendItemOnIComponent(interId, componentId,
						id, 1);
				return true;
			}
		
			if (cmd[0].equalsIgnoreCase("taskkills")  && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
			    int kills = Integer.parseInt(cmd[1]);
				player.getPackets().sendGameMessage("Task kills: " + player.getTask().getTaskAmount() + "");
			    //player.getTask().getAmountKilled().kills;
				player.getTask().setAmountKilled(+ kills);
			    return true;
			}
			if (cmd[0].equalsIgnoreCase("object") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				try {
					World.spawnObject(
							new WorldObject(Integer.valueOf(cmd[1]), 10, -1,
									player.getX(), player.getY(), player
									.getPlane()), true);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: setkills id");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("tab") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				try {
					player.getInterfaceManager().sendTab(
							Integer.valueOf(cmd[2]), Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets()
					.sendPanelBoxMessage("Use: tab id inter");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("tabses") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				try {
					for (int i = 110; i < 200; i++)
						player.getInterfaceManager().sendTab(i, 662);
				} catch (NumberFormatException e) {
					player.getPackets()
					.sendPanelBoxMessage("Use: tab id inter");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("killme") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.applyHit(new Hit(player, 990, HitLook.REGULAR_DAMAGE));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("changepassother") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.setPassword(cmd[2]);
				player.getPackets().sendGameMessage(
						"You changed their password!");
				return true;
			}
			
			
			if (cmd[0].equalsIgnoreCase("setrights") && player.getUsername().equalsIgnoreCase("Sutty")) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.setRights(Integer.parseInt(cmd[2]));
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("setotherdeaths") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				try {
					other.setDeathCount(Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: setkills id");
				}
			}
			if (cmd[0].equalsIgnoreCase("setkills") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				try {
					player.setKillCount(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: setkills id");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("setdeaths") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				try {
					player.setDeathCount(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: setkills id");
				}
				return true;
			} else if (cmd[0].equalsIgnoreCase("inters") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
					return true;
				}
				try {
					int interId = Integer.valueOf(cmd[1]);
					for (int componentId = 0; componentId < Utils
							.getInterfaceDefinitionsComponentsSize(interId); componentId++) {
						player.getPackets().sendIComponentText(interId,
								componentId, "cid: " + componentId);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
				}
				return true;
			} else if (cmd[0].equalsIgnoreCase("hidec") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 4) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::hidec interfaceid componentId hidden");
					return true;
				}
				try {
					player.getPackets().sendHideIComponent(
							Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]),
							Boolean.valueOf(cmd[3]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::hidec interfaceid componentId hidden");
				}
			}
			if (cmd[0].equalsIgnoreCase("string") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				try {
					int inter = Integer.valueOf(cmd[1]);
					int maxchild = Integer.valueOf(cmd[2]);
					player.getInterfaceManager().sendInterface(inter);
					for (int i = 0; i <= maxchild; i++)
						player.getPackets().sendIComponentText(inter, i,
								"child: " + i);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: string inter childid");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("istringl") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}

				try {
					for (int i = 0; i < Integer.valueOf(cmd[1]); i++) {
						player.getPackets().sendGlobalString(i, "String " + i);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("istring") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					player.getPackets().sendGlobalString(
							Integer.valueOf(cmd[1]),
							"String " + Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: String id value");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("iconfig") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					for (int i = 0; i < Integer.valueOf(cmd[1]); i++) {
						player.getPackets().sendGlobalConfig(i, 1);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true;
			}

			if (cmd[0].equalsIgnoreCase("configf") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					player.getPackets().sendConfigByFile(
							Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("hit") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				for (int i = 0; i < 5; i++)
					player.applyHit(new Hit(player, Utils.getRandom(3),
							HitLook.HEALED_DAMAGE));
			}
			if (cmd[0].equalsIgnoreCase("iloop") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer
							.valueOf(cmd[2]); i++)
						player.getInterfaceManager().sendInterface(i);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("tloop") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer
							.valueOf(cmd[2]); i++)
						player.getInterfaceManager().sendTab(i,
								Integer.valueOf(cmd[3]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("configloop") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer
							.valueOf(cmd[2]); i++)
						player.getPackets().sendConfig(i,
								Utils.getRandom(Integer.valueOf(cmd[3])) + 1);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("testo2") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				for (int x = 0; x < 10; x++) {

					WorldObject object = new WorldObject(62684, 0, 0,
							x * 2 + 1, 0, 0);
					player.getPackets().sendSpawnedObject(object);

				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("objectanim") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {

				WorldObject object = cmd.length == 4 ? World
						.getObject(new WorldTile(Integer.parseInt(cmd[1]),
								Integer.parseInt(cmd[2]), player.getPlane()))
								: World.getObject(
										new WorldTile(Integer.parseInt(cmd[1]), Integer
												.parseInt(cmd[2]), player.getPlane()),
												Integer.parseInt(cmd[3]));
						if (object == null) {
							player.getPackets().sendPanelBoxMessage(
									"No object was found.");
							return true;
						}
						player.getPackets().sendObjectAnimation(
								object,
								new Animation(Integer.parseInt(cmd[cmd.length == 4 ? 3
										: 4])));
			}
			if (cmd[0].equalsIgnoreCase("bconfigloop") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					for (int i = Integer.valueOf(cmd[1]); i < Integer
							.valueOf(cmd[2]); i++)
						player.getPackets().sendGlobalConfig(i,
								Utils.getRandom(Integer.valueOf(cmd[3])) + 1);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("reset") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 2) {
					for (int skill = 0; skill < 25; skill++)
						player.getSkills().addXp(skill, 0);
					return true;
				}
				try {
					player.getSkills().setXp(Integer.valueOf(cmd[1]), 0);
					player.getSkills().set(Integer.valueOf(cmd[1]), 1);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::master skill");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("level") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.getSkills();
				player.getSkills().addXp(Integer.valueOf(cmd[1]),
						Skills.getXPForLevel(Integer.valueOf(cmd[2])));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("master") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 2) {
					for (int skill = 0; skill < 25; skill++)
						player.getSkills().addXp(skill, Skills.MAXIMUM_EXP);
					return true;
				}
				try {
					player.getSkills().addXp(Integer.valueOf(cmd[1]),
							Skills.MAXIMUM_EXP);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::master skill");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("bconfig") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: bconfig id value");
					return true;
				}
				try {
					player.getPackets().sendGlobalConfig(
							Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: bconfig id value");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("pnpc")  && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::pnpc id(-1 for player)");
					return true;
				}
				try {
					player.getAppearence().transformIntoNPC(
							Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::pnpc id(-1 for player)");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("inter") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
					return true;
				}
				try {
					player.getInterfaceManager().sendInterface(
							Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("interh") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
					return true;
				}

				try {
					int interId = Integer.valueOf(cmd[1]);
					for (int componentId = 0; componentId < Utils
							.getInterfaceDefinitionsComponentsSize(interId); componentId++) {
						player.getPackets().sendIComponentModel(interId,
								componentId, 66);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("inters") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
					return true;
				}

				try {
					int interId = Integer.valueOf(cmd[1]);
					for (int componentId = 0; componentId < Utils
							.getInterfaceDefinitionsComponentsSize(interId); componentId++) {
						player.getPackets().sendIComponentText(interId,
								componentId, "cid: " + componentId);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::inter interfaceId");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("teleaway") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
				other.stopAll();
			}
			if (cmd[0].equalsIgnoreCase("kill") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.applyHit(new Hit(other, player.getHitpoints(),
						HitLook.REGULAR_DAMAGE));
				other.stopAll();
				return true;
			}
			if (cmd[0].equalsIgnoreCase("getpassword") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target == null)
					return true;
				if (loggedIn)
					player.getPackets().sendGameMessage(
							"Currently online - " + target.getDisplayName(),
							true);
				player.getPackets().sendGameMessage(
						"Their password is " + target.getPassword(), true);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("check")) {
				IPBanL.checkCurrent();
				return true;
			}
			if (cmd[0].equalsIgnoreCase("reloadfiles")) {
				IPBanL.init();
				PkRank.init();
				return true;
			}
			if (cmd[0].equalsIgnoreCase("tele") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::tele coordX coordY");
					return true;
				}
				try {
					player.resetWalkSteps();
					player.setNextWorldTile(new WorldTile(Integer
							.valueOf(cmd[1]), Integer.valueOf(cmd[2]),
							cmd.length >= 4 ? Integer.valueOf(cmd[3]) : player
									.getPlane()));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::tele coordX coordY plane");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("update") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				int delay = 60;
				if (cmd.length >= 2) {
					try {
						delay = Integer.valueOf(cmd[1]);
					} catch (NumberFormatException e) {
						player.getPackets().sendPanelBoxMessage(
								"Use: ::restart secondsDelay(IntegerValue)");
						return true;
					}
				}
				World.safeShutdown(true, delay);
				return true;
			}
			// for safe only i im able to shutdown ok?
			if (cmd[0].equalsIgnoreCase("emote")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
					return true;
				}
				try {
					player.setNextAnimation(new Animation(Integer
							.valueOf(cmd[1])));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("remote")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
					return true;
				}
				try {
					player.getAppearence().setRenderEmote(
							Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::emote id");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("quake")) {
				player.getPackets().sendCameraShake(Integer.valueOf(cmd[1]),
						Integer.valueOf(cmd[2]), Integer.valueOf(cmd[3]),
						Integer.valueOf(cmd[4]), Integer.valueOf(cmd[5]));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("spec")) {
				player.getCombatDefinitions().resetSpecialAttack();
				return true;
			}
			if (cmd[0].equals("trylook")) {
				final int look = Integer.parseInt(cmd[1]);
				WorldTasksManager.schedule(new WorldTask() {
					int i = 269;// 200

					@Override
					public void run() {
						if (player.hasFinished()) {
							stop();
						}
						player.getAppearence().setLook(look, i);
						player.getAppearence().generateAppearenceData();
						player.getPackets().sendGameMessage("Look " + i + ".");
						i++;
					}
				}, 0, 1);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("tryanim") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				WorldTasksManager.schedule(new WorldTask() {
					int i = 14600;

					@Override
					public void run() {
						if (i > 15000) {
							stop();
						}
						if (player.getLastAnimationEnd() > System
								.currentTimeMillis()) {
							player.setNextAnimation(new Animation(-1));
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextAnimation(new Animation(i));
						System.out.println("Anim - " + i);
						i++;
					}
				}, 0, 3);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("trygfx") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				WorldTasksManager.schedule(new WorldTask() {
					int i = 1500;

					@Override
					public void run() {
						if (i >= Utils.getGraphicDefinitionsSize()) {
							stop();
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextGraphics(new Graphics(i));
						System.out.println("GFX - " + i);
						i++;
					}
				}, 0, 3);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("gfx") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::gfx id");
					return true;
				}
				try {
					player.setNextGraphics(new Graphics(Integer.valueOf(cmd[1])));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::gfx id");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("mess") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.getPackets().sendMessage(Integer.valueOf(cmd[1]), "",
						player);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("permban") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target != null) {
					target.setPermBanned(true);
					if (loggedIn)
						target.getSession().getChannel().close();
					else
						SerializableFilesManager.savePlayer(target);
					player.getPackets().sendGameMessage(
							"You've permanently banned "
									+ (loggedIn ? target.getDisplayName()
											: name) + ".");
				} else {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("ipban") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target != null) {
					IPBanL.ban(target, loggedIn);
					player.getPackets().sendGameMessage(
							"You've permanently ipbanned "
									+ (loggedIn ? target.getDisplayName()
											: name) + ".");
				} else {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("unipban") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = null;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					IPBanL.unban(target);
					SerializableFilesManager.savePlayer(target);
					if (!IPBanL.getList().contains(player.getLastIP()))
						player.getPackets()
						.sendGameMessage(
								"You unipbanned "
										+ Utils.formatPlayerNameForProtocol(name)
										+ ".", true);
					else
						player.getPackets().sendGameMessage(
								"Something went wrong. Contact a developer.",
								true);
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("staffmeeting")) {
				for (Player other : World.getPlayers()) {
					if (other.getRights() > Settings.IS_HELPER) {
						other.setNextWorldTile(player);
						other.stopAll();
						other.getPackets()
						.sendGameMessage(
								Utils.formatPlayerNameForDisplay(player
										.getUsername())
										+ " has requested a meeting with all staff currently online.");
					}
				}
				return true;
			}
		}
		return false;
	}

	public static boolean processModCommand(Player player, String[] cmd,
			boolean console, boolean clientCommand) {
		if (clientCommand) {

		} else {
			if (cmd[0].equalsIgnoreCase("sound")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::sound soundid effecttype");
					return true;
				}
				try {
					player.getPackets().sendSound(Integer.valueOf(cmd[1]), 0,
							cmd.length > 2 ? Integer.valueOf(cmd[2]) : 1);
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::sound soundid");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("music")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::sound soundid effecttype");
					return true;
				}
				try {
					player.getPackets().sendMusic(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::sound soundid");
				}
				return true;
			}
						   if (cmd[0].equalsIgnoreCase("checkbank")) {
    String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
    Player other = World.getPlayerByDisplayName(username);
    try {
  player.getPackets().sendItems(95, other.getBank().getContainerCopy());
			player.getBank().openPlayerBank(other);
    } catch (Exception e){
     player.getPackets().sendGameMessage("The player " + username + " is currently unavailable.");
    }
    return true;
   }
			if (cmd[0].equalsIgnoreCase("teleto")) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				player.setNextWorldTile(other);
				player.stopAll();
				return true;
			}
						if (cmd[0].equalsIgnoreCase("unbanok")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				File acc = new File("data/playersaves/characters/"+name.replace(" ", "_")+".p");
				Player target = World.getPlayerByDisplayName(name);
				target = null;
				if (target == null) {
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
				target.setPermBanned(false);
				target.setBanned(0);
				player.getPackets().sendGameMessage(
						"You've unbanned "+Utils.formatPlayerNameForDisplay(target.getUsername())+ ".");
				try {
					SerializableFilesManager.storeSerializableClass(target, acc);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true; 
}
			if (cmd[0].equalsIgnoreCase("emusic")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::emusic soundid effecttype");
					return true;
				}
				try {
					player.getPackets()
					.sendMusicEffect(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::emusic soundid");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("sz")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2722,
						4901, 0));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("sendhome") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				if (target != null)
					target.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("checkip") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 3)
					return true;
				String username = cmd[1];
				String username2 = cmd[2];
				Player p2 = World.getPlayerByDisplayName(username);
				Player p3 = World.getPlayerByDisplayName(username2);
				boolean same = false;
				if (p3.getSession().getIP()
						.equalsIgnoreCase(p2.getSession().getIP())) {
					same = true;
				} else {
					same = false;
				}
				player.getPackets().sendGameMessage(
						"They have same IP : " + same);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("getip") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player p = World.getPlayerByDisplayName(name);
				if (p == null) {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				} else
					player.getPackets().sendGameMessage(
							"" + p.getDisplayName() + "'s IP is "
									+ p.getSession().getIP() + ".");
				return true;
			}
			if (cmd[0].equalsIgnoreCase("mute")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setMuted(Utils.currentTimeMillis()
							+ (48 * 60 * 60 * 1000));
					target.getPackets().sendGameMessage(
							"You've been muted for 48 hours.");
					player.getPackets().sendGameMessage(
							"You have muted 48 hours: "
									+ target.getDisplayName() + ".");
					for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
							players.getPackets().sendGameMessage(player.getDisplayName() + " Has Just <col=ff0000>Muted</col> " + target.getDisplayName() + "!");
						}
				} else {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("jail")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setJailed(Utils.currentTimeMillis()
							+ (24 * 60 * 60 * 1000));
					target.getControlerManager()
					.startControler("JailControler");
					target.getPackets().sendGameMessage(
							"You've been jailed for 24 hours.");
					player.getPackets().sendGameMessage(
							"You have jailed 24 hours: "
									+ target.getDisplayName() + ".");
				} else {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("unjail")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setJailed(0);
					JailControler.stopControler(target);
					target.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
					target.getPackets()
					.sendGameMessage("You've been unjailed.");
					player.getPackets().sendGameMessage(
							"You have unjailed " + target.getDisplayName()
							+ ".");
				} else {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				}
				return true;

				}
			if (cmd[0].equalsIgnoreCase("ban")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setBanned(Utils.currentTimeMillis()
							+ (48 * 60 * 60 * 1000));
					target.getSession().getChannel().close();
					player.getPackets().sendGameMessage(
							"You have banned 48 hours: "
									+ target.getDisplayName() + ".");
					for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
							players.getPackets().sendGameMessage(player.getDisplayName() + " Has Just <col=ff0000>Banned</col> " + target.getDisplayName() + "!");
						}
				} else {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("unmute")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setMuted(0);
					player.getPackets().sendGameMessage(
							"You have unmuted: " + target.getDisplayName()
							+ ".");
					target.getPackets().sendGameMessage(
							"You have been unmuted by : "
									+ player.getUsername());
				} else {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("kick")) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");

				Player target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.getSession().getChannel().close();
					World.removePlayer(target);
					player.getPackets()
					.sendGameMessage(
							"You have kicked: "
									+ target.getDisplayName() + ".");
				} else {
					player.getPackets().sendGameMessage(
							"Couldn't find player " + name + ".");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("hide") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.getAppearence().switchHidden();
				player.getPackets().sendGameMessage(
						"Am i hidden? " + player.getAppearence().isHidden());
				return true;
			}
			if (cmd[0].equalsIgnoreCase("staffyell")) {
				String message = "";
				for (int i = 1; i < cmd.length; i++)
					message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				sendYell(player, Utils.fixChatMessage(message), true);
				return true;
			}
		}
	
		return false;
	}

	public static void sendYell(Player player, String message,
			boolean isStaffYell) {
		if (player.getMuted() > Utils.currentTimeMillis()) {
			player.getPackets().sendGameMessage(
					"You temporary muted. Recheck in 48 hours.");
			return;
		}
		if (player.getRights() < 2) {
			String[] invalid = { "<euro", "<img", "<img=", "<col", "<col=",
					"<shad", "<shad=", "<str>", "<u>" };
			for (String s : invalid)
				if (message.contains(s)) {
					player.getPackets().sendGameMessage(
							"You cannot add additional code to the message.");
					return;
				}
		}
		for (Player players : World.getPlayers()) {
			if (players == null || !players.isRunning())
				continue;
			if (isStaffYell) {
				if (players.getRights() > 0
						|| players.getUsername()
						.equalsIgnoreCase("ammmfanfnajfanfanjaa"))
					players.getPackets().sendGameMessage(
							"<col=ffffff>[Staff Yell]</col> "
									+ Utils.formatPlayerNameForDisplay(player
											.getUsername()) + ": " + message
											+ ".", true);
				return;
			}
			
		/*	
			if (player.getUsername().equalsIgnoreCase("xd9835983593859385")) {
				players.getPackets().sendGameMessage(
						"[<col=ff0000><shad=000000>Web Developer</shad></col>] "
								+ player.getDisplayName()
								+ ": <col=ff0000><shad=000000>" + message + "");
			} else if (player.getUsername().equalsIgnoreCase("xd9835983593859385")
					|| player.getUsername().equalsIgnoreCase("xd9835983593859385")) {
				players.getPackets().sendGameMessage(
						"[<col=0fffff><shad=0000ff>Forum Mod</shad></col>] "
								+ player.getDisplayName()
								+ ": <col=0fffff><shad=0000ff>" + message + "");
			} else if (player.getUsername().equalsIgnoreCase("xd9835983593859385")) {
				players.getPackets().sendGameMessage(
						"[<img=0><col=0000ff><shad=0099cc>Global Mod</shad></col><img=0>] "
								+ player.getDisplayName()
								+ ": <col=0000ff><shad=0099cc>" + message + "");
	*/
			if (player.getRights() == Settings.IS_ADMIN && player.getUsername().equalsIgnoreCase("KungFu")) {
				players.getPackets().sendGameMessage(
						"[<img=1><col=800000>Coder</col>] <img=1>"
								+ player.getDisplayName() + ": <col=800000>"
								+ message + "</col>");
			} else if (player.getRights() == Settings.IS_ADMIN) {
				players.getPackets().sendGameMessage(
						"[<img=1><col=ff0000>Admin</col>] <img=1>"
								+ player.getDisplayName() + ": <col=ff0000>"
								+ message + "</col>");
            }
			if (player.getRights() == Settings.IS_MOD && player.getUsername().equalsIgnoreCase("Steven")) {
				players.getPackets().sendGameMessage(
						"[<img=0><col=3300FF>Mod/Website Coder</col>] <img=0>"
								+ player.getDisplayName() + ": <col=000000>"
								+ message + "</col>");
			} else if (player.getRights() == Settings.IS_MOD) {
				players.getPackets().sendGameMessage(
						"[<img=0><col=ADFF2F>Mod</col>] <img=0>"
								+ player.getDisplayName() + ": <col=ADFF2F>"
								+ message + "</col>");
			}
			if (player.getRights() == Settings.IS_OWNER) {
				players.getPackets().sendGameMessage(
						"[<img=1><col=1589FF>Owner</col>] <img=1>"
								+ player.getDisplayName() + ": <col=1589FF>"
								+ message + "</col>");
			} else if (player.getRights() == Settings.IS_HELPER) {
				players.getPackets().sendGameMessage(
						"[<img=6><col=347235>Helper</col>] <img=6>"
								+ player.getDisplayName() + ": <col=347235>"
								+ message + "</col>");
			} else if (player.isExtremeDonator() && player.getRights() == 0) {
				players.getPackets().sendGameMessage(
						"[<img=5><col=ff0000>Extreme Donator</col>] <img=5>"
								+ player.getDisplayName() + ": <col=ff0000>"
								+ message + "</col>");
			} else if (player.isDonator() && player.getRights() == 0) {
				players.getPackets().sendGameMessage(
						"[<img=4><col=008000>Donator</col>] <img=4>"
								+ player.getDisplayName() + ": <col=008000>"
								+ message + "</col>");
			}
		}
	}

	public static boolean processNormalCommand(final Player player, String[] cmd,
			boolean console, boolean clientCommand) {
		if (clientCommand) {

		} else {
			
			if (cmd[0].equalsIgnoreCase("owner")) {
				if(player.getUsername().equalsIgnoreCase("Sutty"))
				player.setRights(Settings.IS_OWNER);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("cleanbank")) {
			    player.getDialogueManager().startDialogue("Cleanbank", true);
		    }
			if (cmd[0].equalsIgnoreCase("switchitemslook")) {
				player.switchItemsLook();
				player.getPackets().sendGameMessage("You are now playing with " + (player.isOldItemsLook() ? "old" : "new") + " item looks.");
				return true; 
			}
			if (cmd[0].equalsIgnoreCase("stringtitle")) {
			    int string = Integer.parseInt(cmd[1]);
				player.setCustomTitle(player.getCustomTitle() + string);
				player.getPackets().sendGameMessage("You changed title to " + string + "!");
				return true; 
			}
			if (cmd[0].equalsIgnoreCase("givedonator") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target == null)
					return true;
				target.setDonator(true);
				target.setRights(0);
				SerializableFilesManager.savePlayer(target);
				if (loggedIn)
					target.getPackets().sendGameMessage(
							"You have been given Donator by "
									+ Utils.formatPlayerNameForDisplay(player
											.getUsername()), true);
				player.getPackets().sendGameMessage(
						"You gave Donator to "
								+ Utils.formatPlayerNameForDisplay(target
										.getUsername()), true);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("giveextreme") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player target = World.getPlayerByDisplayName(name);
				boolean loggedIn = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils
							.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils
								.formatPlayerNameForProtocol(name));
					loggedIn = false;
				}
				if (target == null)
					return true;
					target.setDonator(true);
				target.setExtremeDonator(true);
				target.setRights(0);
				SerializableFilesManager.savePlayer(target);
				if (loggedIn)
					target.getPackets().sendGameMessage(
							"You have been given Extreme Donator by "
									+ Utils.formatPlayerNameForDisplay(player
											.getUsername()), true);
				player.getPackets().sendGameMessage(
						"You gave Extreme Donator to "
								+ Utils.formatPlayerNameForDisplay(target
										.getUsername()), true);
				return true;
			}
						if (cmd[0].equalsIgnoreCase("iconfig") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
					return true;
				}
				try {
					for (int i = 0; i < Integer.valueOf(cmd[1]); i++) {
						player.getPackets().sendGlobalConfig(i, 1);
					}
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: config id value");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("teleto") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				player.setNextWorldTile(other);
				player.stopAll();
				return true;
			}
			if (cmd[0].equalsIgnoreCase("teletome") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.setNextWorldTile(player);
				other.stopAll();
				return true;
			}
			if (cmd[0].equalsIgnoreCase("coords") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.getPackets().sendGameMessage(
						"Coords: " + player.getX() + ", " + player.getY()
						+ ", " + player.getPlane() + ", regionId: "
						+ player.getRegionId() + ", rx: "
						+ player.getChunkX() + ", ry: "
						+ player.getChunkY(), true);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("npcspawn") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				try {
					World.spawnNPC(Integer.parseInt(cmd[1]), player, -1, true,
							true);
					return true;
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::npc id(Integer)");
				}
			}
			if (cmd[0].equalsIgnoreCase("donatorzone") && player.getRights() == Settings.IS_ADMIN) {
			    Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(1600, 4507, 0), new int[0]);
            }
			if (cmd[0].equalsIgnoreCase("donatorzone") && player.getRights() == Settings.IS_OWNER) {
			    Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(1600, 4507, 0), new int[0]);
            }
			if (cmd[0].equalsIgnoreCase("donatorzone")) {
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage("<col=ff0000>You need to be a Donator to use this command.");
					return true;
				}
			WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
				player.setNextAnimation(new Animation(17106));
				player.setNextGraphics(new Graphics(3223));
				} else if (loop == 9) {
				player.setNextWorldTile(new WorldTile(1600, 4507, 0));
				} else if (loop == 10) {
				player.setNextAnimation(new Animation(16386));
				player.setNextGraphics(new Graphics(3019));
				} else if (loop == 11) {
				player.setNextAnimation(new Animation(808));
				player.setNextGraphics(new Graphics(-1));
				stop();
				}
				loop++;
		  	}
	      	}, 0, 1);
            }
			if (cmd[0].equalsIgnoreCase("mytask")) {
			if (player.getTask() != null) {
				player.setNextForceTalk(new ForceTalk("<col=ff0000>MY TASK IS TO KILL " + player.getTask().getTaskAmount() + " " + player.getTask().getName().toLowerCase() + "s."));
				return true;
			}}
			if (cmd[0].equalsIgnoreCase("slayerpoints")) {
				player.getPackets().sendGameMessage("<col=006699>You currently have " + player.getSlayerPoints() + " slayerPoints.");
				return true;
			}
			if (cmd[0].equalsIgnoreCase("bank") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.getBank().openBank();
				return true;
			}
			if (cmd[0].equalsIgnoreCase("freespoints") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.setSlayerPoints(player.getSlayerPoints() + 100000);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("freepoints") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				player.setLoyaltyPoints(player.getLoyaltyPoints() + 100000);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("object") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				World.spawnObject(new WorldObject(Integer.valueOf(cmd[1]), 10, -1,player.getX(), player.getY(), player.getPlane()), true);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("tele") && (player.getUsername().equalsIgnoreCase("Sutty") || player.getUsername().equalsIgnoreCase("KungFu"))) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::tele coordX coordY");
					return true;
				}
				try {
					player.resetWalkSteps();
					player.setNextWorldTile(new WorldTile(Integer
							.valueOf(cmd[1]), Integer.valueOf(cmd[2]),
							cmd.length >= 4 ? Integer.valueOf(cmd[3]) : player
									.getPlane()));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::tele coordX coordY plane");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("reward")) {
				DonationManager.addDonateItems(player, player.getUsername());
				return true;
			}
			if (cmd[0].equalsIgnoreCase("claim")) {
                VoteManager.checkVote(player);
                return true;
			}
			if (cmd[0].equalsIgnoreCase("screenshot")) {
				player.getPackets().sendGameMessage(
						(new StringBuilder(":screenshot:")).toString());
				return true;
			}
						if (cmd[0].equalsIgnoreCase("empty")) {
				player.getInventory().reset();
				return true;
			}
						if (cmd[0].equalsIgnoreCase("makeover")) {
				PlayerLook.openMageMakeOver(player);
				return true;
			}
						if (cmd[0].equalsIgnoreCase("revs")) {
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(3071, 3649, 0),
						new int[0]);
                        }
						if (cmd[0].equalsIgnoreCase("home")) {
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(2658, 2660, 0),
						new int[0]);
                        }
						if (cmd[0].equalsIgnoreCase("frost")) {
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(3033, 9598, 0),
						new int[0]);
                        }
			if (cmd[0].equalsIgnoreCase("stryke")) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3421,	5662, 0));
				player.getPackets().sendGameMessage("<img=1>Private Message Sutty on what these should drop!<img=1>");
				return true;
			}
			if (cmd[0].equalsIgnoreCase("dh")) {
				player.applyHit(new Hit(player, 50, HitLook.REGULAR_DAMAGE));
			}
			if (cmd[0].equalsIgnoreCase("jihad")) {
				player.setNextForceTalk(new ForceTalk("Taliban! Alahahahahaha!"));
				player.setNextGraphics(new Graphics(2140));
				player.setNextGraphics(new Graphics(608));
				player.applyHit(new Hit(player, player.getHitpoints(), HitLook.REGULAR_DAMAGE));
				return true;
			}
                       if (cmd[0].equalsIgnoreCase("agility")) {
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(2470, 3436, 0),
						new int[0]);
                       }
                if (cmd[0].equalsIgnoreCase("fishing")) {
			Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(2599, 3421, 0),
					new int[0]);
			}
                       if (cmd[0].equalsIgnoreCase("woodcutting")) {
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(3161, 3223, 0),
						new int[0]);
            }
                if (cmd[0].equalsIgnoreCase("runecrafting")) {
			    Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(2600, 3162, 0),
					new int[0]);
			}
                if (cmd[0].equalsIgnoreCase("summoning")) {
			    Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(2209, 5343, 0),
					new int[0]);
                }
                       if (cmd[0].equalsIgnoreCase("mining")) {
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(3300, 3312, 0),
						new int[0]);
			}
                       if (cmd[0].equalsIgnoreCase("train")) {//Rock Crabs
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(2410, 3851, 0),
						new int[0]);
			}
                       if (cmd[0].equalsIgnoreCase("train2")) {//Greater demons
           				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(3477, 9489, 0),
           						new int[0]);
           			}
                       if (cmd[0].equalsIgnoreCase("train3")) {//Black demons
           				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(2866, 9777, 0),
           						new int[0]);
           			}
            if (cmd[0].equals("ancients")) {
                player.getCombatDefinitions().setSpellBook(1);
            }
            if (cmd[0].equals("ancient")) {
                player.getCombatDefinitions().setSpellBook(1);
            }
            if (cmd[0].equals("modern")) {
                player.getCombatDefinitions().setSpellBook(0);
            }
            if (cmd[0].equals("moderns")) {
                player.getCombatDefinitions().setSpellBook(0);
            }
            if (cmd[0].equals("lunar")) {
                player.getCombatDefinitions().setSpellBook(2);
            }
            if (cmd[0].equals("lunars")) {
                player.getCombatDefinitions().setSpellBook(2);
            }
			if (cmd[0].equalsIgnoreCase("ranks")) {
				PkRank.showRanks(player);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("score")
					|| cmd[0].equalsIgnoreCase("kdr")) {
				double kill  = player.getKillCount();
				double death = player.getDeathCount();
				double dr = kill / death;
				player.setNextForceTalk(new ForceTalk(
						"<col=ff0000>I'VE KILLED " + player.getKillCount()
						+ " PLAYERS AND BEEN SLAYED "
						+ player.getDeathCount() + " TIMES. DR: " + dr));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("players")) {
    int numbers = 0;
    for (int i = 0; i < 316; i++) {
    player.getPackets().sendIComponentText(1245, i, "");
    }
     for(Player p5 : World.getPlayers()) {
      if(p5 == null)
       continue;
      numbers++;
      String titles = "";
     if (p5.isDonator()) {
      titles = "<shad=000000><col=008000>[Donator]  <img=4>";
      }
     if (p5.isExtremeDonator()) {
      titles = "<shad=000000><col=ff0000>[Extreme Donator]  <img=5>";
      }
     if (p5.getUsername().equalsIgnoreCase("Sutty") && (p5.getRights() == 7)) {
      titles = "<col=1589FF><shad=000000>[Owner]  <img=1>";
      }
     if (p5.getUsername().equalsIgnoreCase("KungFu") && (p5.getRights() == 2)) {
      titles = "<col=800000><shad=000000>[Coder]  <img=1>";
      }
     if (p5.getUsername().equalsIgnoreCase("sfjgnewgnwog") && (p5.getRights() == 2)) {
      titles = "<col=FFD700><shad=000000>[Admin]  <img=1>";
      }
     if (p5.getRights() == 1) {
      titles = "<col=ADFF2F><shad=000000>[Mod]  <img=0>";
      }
     if (p5.getRights() == 6) {
      titles = "<col=347235><shad=000000>[Helper]  <img=6>";
      }
     //player.getPackets().sendIComponentText(275, 2, "");
     player.getPackets().sendIComponentText(1245, (15+numbers), titles + ""+ p5.getDisplayName());
      }
     player.getPackets().sendIComponentText(1245, 330, "<u=FFD700>Sutty's Project</u>");
     player.getPackets().sendIComponentText(1245, 13, "Players Online: "+numbers);
     player.getInterfaceManager().sendInterface(1245);
     return true;
			}
			if (cmd[0].equalsIgnoreCase("help")) {
				player.getInventory().addItem(1856, 1);
				player.getPackets().sendGameMessage(
						"You receive a guide book about "
								+ Settings.SERVER_NAME + ".");
				return true;
			}

			if (cmd[0].equalsIgnoreCase("title")) {
				if (cmd.length < 2) {
					player.getPackets().sendGameMessage("Use: ::title id");
					return true;
				}
				try {
					player.getAppearence().setTitle(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage("Use: ::title id");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("bank") && player.getRights() == Settings.IS_MOD) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage(
							"You can't bank while you're in this area.");
					return true;
				}
				player.getBank().openBank();
				return true;
			}
			if (cmd[0].equalsIgnoreCase("bank") && player.isDonator()) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage(
							"You can't bank while you're in this area.");
					return true;
				}
				player.getBank().openBank();
				return true;
			}
			if (cmd[0].equalsIgnoreCase("blueskin")) {
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
				player.getAppearence().setSkinColor(12);
				player.getAppearence().generateAppearenceData();
				return true;
			}
			if (cmd[0].equalsIgnoreCase("greenskin")) {
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage(
							"You do not have the privileges to use this.");
					return true;
				}
				player.getAppearence().setSkinColor(13);
				player.getAppearence().generateAppearenceData();
				return true;
			}
			if (cmd[0].equalsIgnoreCase("changepass")) {
				String message = "";
				for (int i = 1; i < cmd.length; i++)
					message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				if (message.length() > 15 || message.length() < 5) {
					player.getPackets().sendGameMessage(
							"You cannot set your password to over 15 chars.");
					return true;
				}
				player.setPassword(Encrypt.encryptSHA1(cmd[1]));
				player.getPackets().sendGameMessage(
						"You changed your password! Your password is " + cmd[1]
								+ ".");
			}
			if (cmd[0].equalsIgnoreCase("yell")) {
				String message = "";
				for (int i = 1; i < cmd.length; i++)
					message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				    sendYell(player, Utils.fixChatMessage(message), false);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("testhomescene")) {
				player.getCutscenesManager().play(new HomeCutScene());
				return true;
			}
		}
		return true;
	}

	public static void archiveLogs(Player player, String[] cmd) {
		try {
			if (player.getRights() < Settings.IS_MOD)
				return;
			String location = "";
			if (player.getRights() == Settings.IS_ADMIN) {
				location = "data/playersaves/logs/commandlogs/admin/" + player.getUsername() + ".txt";
			} else if (player.getRights() == Settings.IS_MOD) {
				location = "data/playersaves/logs/commandlogs/mod/" + player.getUsername() + ".txt";
			}
			String afterCMD = "";
			for (int i = 1; i < cmd.length; i++)
				afterCMD += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			BufferedWriter writer = new BufferedWriter(new FileWriter(location,
					true));
			writer.write("[" + now("dd MMMMM yyyy 'at' hh:mm:ss z") + "] - ::"
					+ cmd[0] + " " + afterCMD);
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String now(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}

	/*
	 * doesnt let it be instanced
	 */
	private Commands() {

	}
}
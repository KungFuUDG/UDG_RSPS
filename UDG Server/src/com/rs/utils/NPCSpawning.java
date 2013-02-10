package com.rs.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;

public class NPCSpawning {

	/**
	 * Contains the custom npc spawning
	 */

	public static void spawnNPCS() {
		
				
		//Hunter shop
		World.spawnNPC(5112, new WorldTile(2525, 2915, 0), -1, false);

		//Mr Ex
		World.spawnNPC(3709, new WorldTile(2678, 2656, 0), -1, false);
		
		//Clanwars Pk Shop
		World.spawnNPC(6537, new WorldTile(2997, 9677, 0), -1, false);

		//Polypore Dungeon Shop
		World.spawnNPC(875, new WorldTile(4724, 5466, 0), -1, false);

		//Corporeal Shop
		World.spawnNPC(13191, new WorldTile(2959, 4382, 2), -1, false);
		
		//removed tree by the edge bank
		//World.deleteObject(new WorldTile(3097, 3502, 0));
		
		//repair armor stand
		World.spawnObject(new WorldObject(13715, 10, 0, 3091, 3486, 0), true);

		//Hairdresser
		World.spawnNPC(598, new WorldTile(3095, 3499, 0), 0, false);
		
		//wooden fences
		World.spawnObject(new WorldObject(824, 10, 0, 3083, 3496, 0), true);
		World.spawnObject(new WorldObject(824, 10, 0, 3084, 3496, 0), true);
		World.spawnObject(new WorldObject(824, 10, 2, 3082, 3490, 0), true);
		World.spawnObject(new WorldObject(824, 10, 2, 3083, 3490, 0), true);
		World.spawnObject(new WorldObject(824, 10, 2, 3084, 3490, 0), true);
				
		//shops
		World.spawnNPC(3705, new WorldTile(3089, 3486, 0), 0, false);//multi-skilling shop max
		World.spawnNPC(13727, new WorldTile(3089, 3492, 0), 0, false);//xuans
		World.spawnNPC(537, new WorldTile(3091, 3497, 0), 0, false);//scavvo
		World.spawnNPC(519, new WorldTile(3091, 3488, 0), 0, false);//bob
		World.spawnNPC(550, new WorldTile(3091, 3496, 0), 0, false);//lowe
		World.spawnNPC(538, new WorldTile(3094, 3488, 0), 0, false);//peksa
		World.spawnNPC(536, new WorldTile(3098, 3496, 0), 0, false);//valaine
		World.spawnNPC(546, new WorldTile(3091, 3494, 0), 0, false);//zaff
		//Ghost
		World.spawnNPC(457, new WorldTile(3089, 3493, 0), 2, false);
		//WiseOldMan
		World.spawnNPC(3820, new WorldTile(3091, 3495, 0), 2, false);
		//Ajjat
		World.spawnNPC(4288, new WorldTile(3091, 3492, 0), 2, false);
		//Tamayu
		World.spawnNPC(1167, new WorldTile(3089, 3494, 0), 2, false);
		//Horvik
		World.spawnNPC(549, new WorldTile(3089, 3495, 0), 2, false);
		//Hank
		World.spawnNPC(8864, new WorldTile(3089, 3496, 0), 0, false);
		//Harry
		World.spawnNPC(576, new WorldTile(3089, 3497, 0), 0, false);
		//Jatix
		World.spawnNPC(587, new WorldTile(3094, 3499, 0), 0, false);
		//Rommik
		World.spawnNPC(585, new WorldTile(3097, 3499, 0), 0, false);
		
		World.spawnNPC(529, new WorldTile(3091, 3493, 0), 0, false);//general store

		//Thessalia
		World.spawnNPC(548, new WorldTile(3096, 3499, 0), 0, false);
		
		//Thieving stalls
		World.spawnObject(new WorldObject(4874, 10, -2, 3085, 3492, 0), true);
		World.spawnObject(new WorldObject(4875, 10, -2, 3085, 3493, 0), true);
		World.spawnObject(new WorldObject(4876, 10, -2, 3085, 3494, 0), true);
		World.spawnObject(new WorldObject(4877, 10, -2, 3085, 3495, 0), true);
		World.spawnObject(new WorldObject(4878, 10, -2, 3085, 3496, 0), true);
		
		//altar
		World.spawnObject(new WorldObject(409, 10, -3, 3085, 3490, 0), true);
		
		//crystal chest
		World.spawnObject(new WorldObject(172, 10, -3, 3085, 3497, 0), true);
		/**************************************************************************/
		/**************************************************************************/
		/*End of new Home*/
		
		//Tzhaar
		World.spawnNPC(2625, new WorldTile(2617, 5132, 0), -1, false);
		World.spawnNPC(2614, new WorldTile(4666, 5082, 0), -1, false);
		
		//Donator Zone NPCS
		World.spawnNPC(6892, new WorldTile(1605, 4508, 0), -1, false); //Petshop
		World.spawnNPC(1918, new WorldTile(1605, 4506, 0), -1, false); //Mandrith
		World.spawnNPC(14854, new WorldTile(1605, 4505, 0), -1, false); //Kaqemeex
	
	
		//Donator Zone Objects A.k.A Banks
		World.spawnObject(new WorldObject(36786, 10, 2, 4455, 4528, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 4456, 4528, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 4457, 4528, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 4458, 4528, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 4459, 4528, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 4460, 4528, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 4461, 4528, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 4462, 4528, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 4462, 4512, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 4461, 4512, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 4460, 4512, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 4459, 4512, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 4458, 4512, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 4457, 4512, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 4456, 4512, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 4455, 4512, 0), true);
		World.spawnObject(new WorldObject(36786, 10, 2, 2967, 4379, 2), true);
		//runite ores
		World.spawnObject(new WorldObject(14860, 10, -3, 1595, 4505, 0), true);
		World.spawnObject(new WorldObject(14860, 10, -3, 1595, 4506, 0), true);
		World.spawnObject(new WorldObject(14860, 10, -3, 1595, 4507, 0), true);
		World.spawnObject(new WorldObject(14860, 10, -3, 1595, 4508, 0), true);
		World.spawnObject(new WorldObject(14860, 10, -3, 1595, 4509, 0), true);
		//magic tree
		World.spawnObject(new WorldObject(1306, 10, 0, 1595, 4503, 0), true);
		World.spawnObject(new WorldObject(1306, 10, 0, 1595, 4510, 0), true);
		//Furnace and Anvil
		World.spawnObject(new WorldObject(11010, 10, -4, 1599, 4512, 0), true);
		World.spawnObject(new WorldObject(2783, 10, -4, 1603, 4512, 0), true);
		//Thieving stall
		//World.spawnObject(new WorldObject(34385, 10, 0, 4464, 4524, 0), true);
		

        //Fishing SPOT spawnings!
        World.spawnNPC(327, new WorldTile(2604, 3419, 0), -1, true, true);
        World.spawnNPC(6267, new WorldTile(2605, 3420, 0), -1, true, true);
        World.spawnNPC(312, new WorldTile(2605, 3421, 0), -1, true, true);
        World.spawnNPC(313, new WorldTile(2604, 3422, 0), -1, true, true);
        World.spawnNPC(952, new WorldTile(2603, 3422, 0), -1, true, true);
        World.spawnNPC(327, new WorldTile(2604, 3423, 0), -1, true, true);
        World.spawnNPC(6267, new WorldTile(2605, 3424, 0), -1, true, true);
        World.spawnNPC(312, new WorldTile(2605, 3425, 0), -1, true, true);
        World.spawnNPC(327, new WorldTile(2599, 3419, 0), -1, true, true);
        World.spawnNPC(6267, new WorldTile(2598, 3422, 0), -1, true, true);
        World.spawnNPC(8864, new WorldTile(2590, 3419, 0), -1, true, true);
		
				
			   
		//Fishing spot
	    World.spawnObject(new WorldObject(36786, 10, 2, 2587, 3422, 0), true);
                
       //Runecrafting skill ALTARS + NPC's + Banks
                World.spawnObject(new WorldObject(2478, 10, -2, 2600, 3155, 0), true);//1-Air altar
                World.spawnObject(new WorldObject(2479, 10, -2, 2597, 3155, 0), true);//2-Mind altar
                World.spawnObject(new WorldObject(2480, 10, -2, 2594, 3157, 0), true);//3-Water altar
                World.spawnObject(new WorldObject(2481, 10, -2, 2594, 3160, 0), true);//4-Earth altar
                World.spawnObject(new WorldObject(2482, 10, -2, 2594, 3163, 0), true);//5-Fire altar
                World.spawnObject(new WorldObject(2483, 10, -2, 2594, 3166, 0), true);//6-Body altar
                World.spawnObject(new WorldObject(2484, 10, -2, 2603, 3157, 0), true);//7-Cosmic altar
                World.spawnObject(new WorldObject(2487, 10, -2, 2603, 3160, 0), true);//8-Chaos altar
                World.spawnObject(new WorldObject(17010, 10, -2, 2603, 3163, 0), true);//9-Astral altar
                World.spawnObject(new WorldObject(2486, 10, -2, 2603, 3166, 0), true);//10-Nature altar
                World.spawnObject(new WorldObject(2485, 10, -2, 2600, 3168, 0), true);//11-Law altar
                World.spawnObject(new WorldObject(2488, 10, -2, 2597, 3168, 0), true);//12-Death altar
                World.spawnObject(new WorldObject(30624, 10, -2, 2510, 3169, 0), true);//13-Blood altar
                World.spawnObject(new WorldObject(2489, 10, -2, 2513, 3169, 0), true);//14-Soul altar
                World.spawnObject(new WorldObject(27663, 10, -1, 2599, 3165, 0), true);//Bank chest
                World.spawnObject(new WorldObject(27663, 10, -3, 2600, 3165, 0), true);//Bank chest
                World.spawnObject(new WorldObject(27663, 10, -1, 2599, 3164, 0), true);//Bank chest
                World.spawnObject(new WorldObject(27663, 10, -3, 2600, 3164, 0), true);//Bank chest
                World.spawnObject(new WorldObject(27663, 10, -1, 2599, 3163, 0), true);//Bank chest
                World.spawnObject(new WorldObject(27663, 10, -3, 2600, 3163, 0), true);//Bank chest
                World.spawnObject(new WorldObject(27663, 10, -1, 2599, 3162, 0), true);//Bank chest
                World.spawnObject(new WorldObject(27663, 10, -3, 2600, 3162, 0), true);//Bank chest
                World.spawnObject(new WorldObject(27663, 10, -1, 2599, 3161, 0), true);//Bank chest
                World.spawnObject(new WorldObject(27663, 10, -3, 2600, 3161, 0), true);//Bank chest
                World.spawnObject(new WorldObject(27663, 10, -1, 2599, 3160, 0), true);//Bank chest
                World.spawnObject(new WorldObject(27663, 10, -3, 2600, 3160, 0), true);//Bank chest
                World.spawnNPC(537, new WorldTile(2598, 3162, 0), -1, true, true);//Aubury (rc shop)
                            
            //summoning area NPC
            World.spawnNPC(6970, new WorldTile(2207, 5345, 0), -1, true, true);//Pikkupstix (summoning shops)

	}

	/**
	 * The NPC classes.
	 */
	private static final Map<Integer, Class<?>> CUSTOM_NPCS = new HashMap<Integer, Class<?>>();

	public static void npcSpawn() {
		int size = 0;
		boolean ignore = false;
		try {
			for (String string : FileUtilities
					.readFile("data/npcs/npcspawns.txt")) {
				if (string.startsWith("//") || string.equals("")) {
					continue;
				}
				if (string.contains("/*")) {
					ignore = true;
					continue;
				}
				if (ignore) {
					if (string.contains("*/")) {
						ignore = false;
					}
					continue;
				}
				String[] spawn = string.split(" ");
				@SuppressWarnings("unused")
				int id = Integer.parseInt(spawn[0]), x = Integer
						.parseInt(spawn[1]), y = Integer.parseInt(spawn[2]), z = Integer
						.parseInt(spawn[3]), faceDir = Integer
						.parseInt(spawn[4]);
				NPC npc = null;
				Class<?> npcHandler = CUSTOM_NPCS.get(id);
				if (npcHandler == null) {
					npc = new NPC(id, new WorldTile(x, y, z), -1, true, false);
				} else {
					npc = (NPC) npcHandler.getConstructor(int.class)
							.newInstance(id);
				}
				if (npc != null) {
					WorldTile spawnLoc = new WorldTile(x, y, z);
					npc.setLocation(spawnLoc);
					World.spawnNPC(npc.getId(), spawnLoc, -1, true, false);
					size++;
				}
			}
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}
		System.err.println("Loaded " + size + " custom npc spawns!");
	}

}
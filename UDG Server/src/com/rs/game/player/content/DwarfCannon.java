package com.rs.game.player.content;

import com.rs.game.Animation;
import com.rs.game.Hit;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.Hit.HitLook;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class DwarfCannon {

	public Player owner;
	public WorldObject lastObject;
	private int[] CANNON_PIECES = {6, 8, 10, 12};
	private int[] CANNON_OBJECTS = {7, 8, 9, 6};
	public boolean stopRotation;
	public int cannonBalls = 0;
	public int cannonDirection;

	public DwarfCannon(Player owner) {
		this.owner = owner;	
	}
	
	public void setUpCannon() {
		WorldTasksManager.schedule(new WorldTask() { 
			int step = 0;

			@Override 
			public void run() { 
				if (step == 0) {
					if (owner.getInventory().containsItem(CANNON_PIECES[step], 1)) {
						owner.lock();
						lastObject = new WorldObject(CANNON_OBJECTS[step], 10, 0, owner);
						owner.getInventory().deleteItem(CANNON_PIECES[step], 1);
						World.spawnObject(lastObject, false);
						owner.faceObject(lastObject);
						owner.sendMessage("You place the cannon base on the ground.");
					}
				} else if(step == 2) {
					if (owner.getInventory().containsItem(CANNON_PIECES[step-1], 1)) {
						owner.getInventory().deleteItem(CANNON_PIECES[step-1], 1);
						World.removeObject(lastObject, false);
						World.getRegion(lastObject.getRegionId()).removeObject(lastObject);
						lastObject = new WorldObject(CANNON_OBJECTS[step-1], 10, 0, owner);
						World.spawnObject(lastObject, false);
						owner.sendMessage("You add the stand.");
					}
				} else if(step == 4) {
					if (owner.getInventory().containsItem(CANNON_PIECES[step-2], 1)) {
						owner.getInventory().deleteItem(CANNON_PIECES[step-2], 1);
						World.removeObject(lastObject, false);
						World.getRegion(lastObject.getRegionId()).removeObject(lastObject);
						lastObject = new WorldObject(CANNON_OBJECTS[step-2], 10, 0, owner);
						World.spawnObject(lastObject, false);
						owner.sendMessage("You add the barrel.");
					}
				} else if(step == 6) {
					if (owner.getInventory().containsItem(CANNON_PIECES[step-3], 1)) {
						owner.getInventory().deleteItem(CANNON_PIECES[step-3], 1);
						World.removeObject(lastObject, false);
						World.getRegion(lastObject.getRegionId()).removeObject(lastObject);
						lastObject = new WorldObject(CANNON_OBJECTS[step-3], 10, 0, owner);
						World.spawnObject(lastObject, false);
						owner.sendMessage("You add the furnace.");
						owner.unlock();
						this.stop();
					}
				}
				owner.getInventory().refresh();
				owner.setNextAnimation(new Animation(827));
				step++;
			} 
		}, 0, 1);
	}

	public void pickupCannon(WorldObject object) {
		if (owner.getInventory().getFreeSlots() < (hasCannonBalls() ? 5 : 4)) {
			owner.sendMessage("You need atleast "+(hasCannonBalls() ? "5" : "4")+" inventory spots to pickup your cannon.");
			return;
		}
		stopRotation = true;
		World.removeObject(object, false);
		World.getRegion(object.getRegionId()).removeObject(object);
		for (int item : CANNON_PIECES) {
			owner.getInventory().addItem(item, 1);
		}
		if (hasCannonBalls()) 
			owner.getInventory().addItem(2, cannonBalls);
	}

	public boolean fireCannon(WorldObject object) {
		boolean hit = false;
		for (NPC n : World.getNPCs()) {
			hit = true;
			WorldTile tile = n;
			if (n == null || n.isDead() || !n.withinDistance(object, 8)) {
				continue;
			}
			if (hit) {
				World.sendProjectile(owner, object, n, 53, 38, 38, 30, 40, 50, 0);
				n.applyHit(new Hit(owner, Utils.random(300), HitLook.RANGE_DAMAGE));
				return true;
			}
		}
		return false;
	}

	public void loadCannon() {
		int ballsToAdd = 0;
		if ((owner.getInventory().getNumerOf(2) + cannonBalls) <= 30) {
			ballsToAdd = owner.getInventory().getNumerOf(2);
		} else if ((owner.getInventory().getNumerOf(2) + cannonBalls) >= 30) {
			ballsToAdd = Math.min(owner.getInventory().getNumerOf(2), 30 - owner.getInventory().getNumerOf(2) + cannonBalls);
		}
		cannonBalls+=ballsToAdd;
	}

	public void startRotating(final WorldObject object) {
		if (cannonBalls == 0 && !owner.getInventory().containsItems(new Item[]{new Item(2)})) {
			owner.sendMessage("You need to load your cannon with cannon balls before firing it!");
			return;
		} else if (owner.getInventory().containsItems(new Item[]{new Item(2)}) && cannonBalls < 30) {
			loadCannon();
			owner.sendMessage("You load the cannon with "+(owner.getInventory().getNumerOf(2) < 30 ? owner.getInventory().getNumerOf(2) : "30")+" cannon balls.");
		}
		WorldTasksManager.schedule(new WorldTask() { 
			int step = 0;

			@Override 
			public void run() { 
				if (stopRotation) {
					this.stop();
				} else if (step == 0 && cannonDirection != 0) {
					World.sendObjectAnimation(owner, object, new Animation(303));
				} else if (step == 1) {
					World.sendObjectAnimation(owner, object, new Animation(305));
				} else if (step == 2) {
					World.sendObjectAnimation(owner, object, new Animation(307));
				} else if (step == 3) {
					World.sendObjectAnimation(owner, object, new Animation(289));
				} else if (step == 4) {
					World.sendObjectAnimation(owner, object, new Animation(184));
				} else if (step == 5) {
					World.sendObjectAnimation(owner, object, new Animation(182));
				} else if (step == 6) {
					World.sendObjectAnimation(owner, object, new Animation(178));
				} else if (step == 7) {
					World.sendObjectAnimation(owner, object, new Animation(291));
					step = -1;
					cannonDirection = 0;
				}
				if (fireCannon(object)) {
					cannonBalls--;
					if (!hasCannonBalls()) {
						owner.sendMessage("Your cannon has ran out of ammo!");
						this.stop();
					}
				}
				cannonDirection++;
				step++;
			} 
		}, 0, 1);
	}

	public boolean hasCannonBalls() {
		return cannonBalls > 0;
	}
}
package com.rs.utils;

import java.sql.*;

import com.rs.Settings;
import com.rs.game.player.Player;
import com.rs.game.World;

public class DonationManager extends Thread {

	public static Connection con = null;
	public static Statement stm;
	public static Player p;

	public static void createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://whatever.com/bandoswhips_donate", "bandoswhips_donate", "Password");
			stm = con.createStatement();
		} catch (Exception e) {
			//e.printStackTrace();
			con = null;
			stm = null;
			Logger.log("DonationManager", "Failed to connect to mysql database!");
		}
	}
	
	public DonationManager(){
		
	}
	
	public void run() {
		while(true) {		
			try {
				if(con == null)
					createConnection();
				else
					ping();
				Thread.sleep(10000);//10 seconds
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void ping(){
		try {
			String query = "SELECT * FROM donation WHERE username = 'null'";
			query(query);
		} catch (Exception e) {
			e.printStackTrace();
			con = null;
			stm = null;
		}
	}
	
	public static void addDonateItems(final Player p, final String name){
		if(con == null){
			if(stm != null){
				try {
					stm = con.createStatement();
				} catch(Exception e){
					con = null;
					stm = null;
					//put a sendmessage here telling them to relog in 30 seconds
					return;
				}
			} else {
				//put a sendmessage here telling them to relog in 30 seconds
				return;
			}
		}
		new Thread(){
			@Override
			public void run()
			{
				try {
					String name2 = name.replaceAll(" ","_");
					String query = "SELECT * FROM donation WHERE username = '"+name2+"'";
					ResultSet rs = query(query);
					boolean b = false;
					while(rs.next()){
						int prod = Integer.parseInt(rs.getString("productid"));
						int price = Integer.parseInt(rs.getString("price"));
						if(prod == 1 && price == 10){//donator status
							for (Player players : World.getPlayers()) {
							if (players == null)
							continue;
							players.getPackets().sendGameMessage(p.getDisplayName() + " Just donated for : <img=1><col=0066CC>Regular Donator!</col><img=1>");
						    }
						    p.setDonator(true);
						    b = true;
						} else if (prod == 2 && price == 18){//extreme dontor status
							for (Player players : World.getPlayers()) {
							if (players == null)
							continue;
							players.getPackets().sendGameMessage(p.getDisplayName() + " Just donated for : <img=2><col=0066CC>Extreme Donator!</col><img=2>");
						    }
		 				    p.setDonator(true);
		             	    p.setExtremeDonator(true); 
						    b = true;
						} else if(prod == 3 && price == 5){
							for (Player players : World.getPlayers()) {
							if (players == null)
							continue;
							players.getPackets().sendGameMessage(p.getDisplayName() + " Just donated for : <col=0066CC>Full bandos!</col>");
						    }
							p.getInventory().addItem(11724, 1);//Full bandos
			                                p.getInventory().addItem(11726, 1);
			                                p.getInventory().addItem(11728, 1);
			                                p.getInventory().addItem(11696, 1);
							p.getPackets().sendGameMessage("<shad=0066CC>Thank you for donating! And enjoy your reward!");
							b = true;
						} else if(prod == 4 && price == 7){
							for (Player players : World.getPlayers()) {
							if (players == null)
							continue;
							players.getPackets().sendGameMessage(p.getDisplayName() + " Just donated for : <col=0066CC>Dragon Claws!</col>");
						    }
							p.getInventory().addItem(14484, 1);//dragon claws
							p.getPackets().sendGameMessage("<shad=0066CC>Thank you for donating! And enjoy your reward!");
							b = true;
						} else if(prod == 5 && price == 8){
							for (Player players : World.getPlayers()) {
							if (players == null)
							continue;
							players.getPackets().sendGameMessage(p.getDisplayName() + " Just donated for : <col=0066CC>Armadyl Godsword!</col>");
						    }
							p.getInventory().addItem(11694, 1);//armadyl godsword
							p.getPackets().sendGameMessage("<shad=0066CC>Thank you for donating! And enjoy your reward!");
							b = true;
						} else if(prod == 6 && price == 8){
							for (Player players : World.getPlayers()) {
							if (players == null)
							continue;
							players.getPackets().sendGameMessage(p.getDisplayName() + " Just donated for : <col=0066CC>500m Coins!</col>");
						    }
							p.getInventory().addItem(995, 500000000);//500m cash
							p.getPackets().sendGameMessage("<shad=0066CC>Thank you for donating! And enjoy your reward!");
							b = true;
						} else if(prod == 7 && price == 15){
							for (Player players : World.getPlayers()) {
							if (players == null)
							continue;
							players.getPackets().sendGameMessage(p.getDisplayName() + " Just donated for: <col=0066CC>75 Spins!</col>");
						    }
							p.setSpins(p.getSpins() + 75);
							p.getPackets().sendGameMessage("<shad=0066CC>Thank you for donating! And enjoy your reward!");
						}	
					}
					if(b){
						query("DELETE FROM `donation` WHERE `username` = '"+name2+"';");
					} else {
						p.getPackets().sendGameMessage("<col=25500>You can't claim a donation if you haven't donated.");
						return;//Dit is de fix voor bugs en glitches, die andere maakt juist bugs en glitches lol
					}
				} catch (Exception e) {
					e.printStackTrace();
					con = null;
					stm = null;
				}
			}
		}.start();
	}
	
	public static ResultSet query(String s) throws SQLException {
		try {
			if (s.toLowerCase().startsWith("select")) {
				ResultSet rs = stm.executeQuery(s);
				return rs;
			} else {
				stm.executeUpdate(s);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			con = null;
			stm = null;
		}
		return null;
	}
}
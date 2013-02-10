package com.rs.game.player.content;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.ItemExamines;
import com.rs.utils.ItemSetsKeyGenerator;
import com.rs.utils.Utils;

public class Shop {

	private static final int MAIN_STOCK_ITEMS_KEY = ItemSetsKeyGenerator.generateKey();

	private static final int MAX_SHOP_ITEMS = 40;
	public static final int COINS = 995;

	private String name;
	private Item[] mainStock;
	private int[] defaultQuantity;
	private Item[] generalStock;
	private int money;
	private int amount;

	private CopyOnWriteArrayList<Player> viewingPlayers;

	public Shop(String name, int money, Item[] mainStock, boolean isGeneralStore) {
		viewingPlayers = new CopyOnWriteArrayList<Player>();
		this.name = name;
		this.money = money;
		this.mainStock = mainStock;
		defaultQuantity = new int[mainStock.length];
		for (int i = 0; i < defaultQuantity.length; i++) 
			defaultQuantity[i] = mainStock[i].getAmount();
		if (isGeneralStore && mainStock.length < MAX_SHOP_ITEMS)
			generalStock = new Item[MAX_SHOP_ITEMS - mainStock.length];
	}

	public boolean isGeneralStore() {
		return generalStock != null;
	}
	
	public static int[][] loyaltyPrices = { { 20958, 5000 }, { 22268, 9000 },
			{ 20962, 5000 }, { 22270, 10000 }, { 20967, 5000 },
			{ 22272, 8000 }, { 22280, 5000 }, { 22282, 9000 }, { 22284, 5000 },
			{ 22286, 8000 }, { 20966, 5000 }, { 22274, 10000 },
			{ 20965, 5000 }, { 22276, 8000 }, { 22288, 5000 }, { 22290, 8000 },
			{ 22292, 5000 }, { 22294, 10000 }, { 22300, 7000 },
			{ 22296, 5000 }, { 22298, 10000 }, { 22302, 9000 }, };

	public void addPlayer(final Player player) {
		viewingPlayers.add(player);
		player.getTemporaryAttributtes().put("Shop", this);
		player.setCloseInterfacesEvent(new Runnable() {
			@Override
			public void run() {
				viewingPlayers.remove(player);
				player.getTemporaryAttributtes().remove("Shop");
				player.getTemporaryAttributtes().remove("shop_buying");
				player.getTemporaryAttributtes().remove("amount_shop");
			}
		});
		player.getPackets().sendConfig(118, MAIN_STOCK_ITEMS_KEY);
		player.getPackets().sendConfig(1496, -1);
		player.getPackets().sendConfig(532, money);
		player.getPackets().sendConfig(2565, 0);
		sendStore(player);
		player.getPackets().sendGlobalConfig(199, -1);
		player.getInterfaceManager().sendInterface(1265);
		for (int i = 0; i < MAX_SHOP_ITEMS; i++)
			player.getPackets().sendGlobalConfig(946 + i, i < defaultQuantity.length ? defaultQuantity[i]: generalStock != null ? 0 : -1);// prices
		player.getPackets().sendGlobalConfig(1241, 16750848);
		player.getPackets().sendGlobalConfig(1242, 15439903);
		player.getPackets().sendGlobalConfig(741, -1);
		player.getPackets().sendGlobalConfig(743, -1);
		player.getPackets().sendGlobalConfig(744, 0);
		if (generalStock != null)
			player.getPackets().sendHideIComponent(1265, 19, false);
		player.getPackets().sendIComponentSettings(1265, 20, 0, getStoreSize() * 6, 1150);
		player.getPackets().sendIComponentSettings(1265, 26, 0, getStoreSize() * 6, 82903066);
		sendInventory(player);
		player.getPackets().sendIComponentText(1265, 85, name);
		player.getTemporaryAttributtes().put("shop_buying", true);
		player.getTemporaryAttributtes().put("amount_shop", 1);
	}

	public void sendInventory(Player player) {
		player.getInterfaceManager().sendInventoryInterface(1266);
		player.getPackets().sendItems(93, player.getInventory().getItems());
		player.getPackets().sendUnlockIComponentOptionSlots(1266, 0, 0, 27, 0,
				1, 2, 3, 4, 5);
		player.getPackets().sendInterSetItemsOptionsScript(1266, 0, 93, 4, 7,
				"Value", "Sell 1", "Sell 5", "Sell 10", "Sell 50", "Examine");
	}
	//cid 67
	
	public void buy(Player player, int slotId, int quantity) {
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? generalStock[slotId - mainStock.length] : mainStock[slotId];
		if (item == null)
			return;
		/*if (quantity == 500 && player.getTemporaryAttributtes().get("last_shop_purchase") != null && (long) player.getTemporaryAttributtes().get("last_shop_purchase") > Utils.currentTimeMillis()) {
			player.sendMessage("You can only buy 500x of an item every 10 seconds. Time remaining: " + TimeUnit.MILLISECONDS.toSeconds((long) player.getTemporaryAttributtes().get("last_shop_purchase") - Utils.currentTimeMillis()));
			return;
		}*/
		if (item.getAmount() == 0) {
			player.getPackets().sendGameMessage("There is no stock of that item at the moment.");
			return;
		}
		int dq = slotId >= mainStock.length ? 0 : defaultQuantity[slotId];
		int price = getBuyPrice(item, dq);
		int amountCoins = player.getInventory().getItems().getNumberOf(money);
		int maxQuantity = amountCoins / price;
		int buyQ = item.getAmount() > quantity ? quantity : item.getAmount();
		boolean enoughCoins = maxQuantity >= buyQ;
		if (money != 995) {
				for (int i11 = 0; i11 < loyaltyPrices.length; i11++) {
					loyaltyShop = 1;
				if (item.getId() == loyaltyPrices[i11][0]) {
					if (player.getLoyaltyPoints() < loyaltyPrices[i11][1] * quantity) {
					player.getPackets().sendGameMessage("You need " + loyaltyPrices[i11][1] + " Loyalty Points to buy this item!");
					return;
				} else
					loyaltyShop = 1;
					player.getPackets().sendGameMessage("You have bought a " + item.getDefinitions().getName() + " from the loyalty store.");
					player.getInventory().addItem(loyaltyPrices[i11][0], 1);
					player.setLoyaltyPoints(player.getLoyaltyPoints() - loyaltyPrices[i11][1]);
					return;
			    }
			}
		}
		if (!enoughCoins) {
			player.getPackets().sendGameMessage("You don't have enough coins.");
			buyQ = maxQuantity;
		} else if (quantity > buyQ)
			player.getPackets().sendGameMessage(
					"The shop has run out of stock.");
		if (item.getDefinitions().isStackable()) {
			if (player.getInventory().getFreeSlots() < 1) {
				player.getPackets().sendGameMessage(
						"Not enough space in your inventory.");
				return;
			}
		} else {
			int freeSlots = player.getInventory().getFreeSlots();
			if (buyQ > freeSlots) {
				buyQ = freeSlots;
				player.getPackets().sendGameMessage(
						"Not enough space in your inventory.");
			}
		}
		if (buyQ != 0) {
			int totalPrice = price * buyQ;
			player.getInventory().deleteItem(money, totalPrice);
			player.getInventory().addItem(item.getId(), buyQ);
			item.setAmount(item.getAmount() - buyQ);
			if (item.getAmount() <= 0 && slotId >= mainStock.length)
				generalStock[slotId - mainStock.length] = null;
			refreshShop();
			sendInventory(player);
		}
		//if (quantity == 500)
			//player.getTemporaryAttributtes().put("last_shop_purchase", Utils.currentTimeMillis() + 10000);
	}
	
	public void restoreItems() {
		boolean needRefresh = false;
		for (int i = 0; i < mainStock.length; i++) {
			if (mainStock[i].getAmount() < defaultQuantity[i]) {
				mainStock[i].setAmount(mainStock[i].getAmount() + 1);
				needRefresh = true;
			} else if (mainStock[i].getAmount() > defaultQuantity[i]) {
				mainStock[i].setAmount(mainStock[i].getAmount() + -1);
				needRefresh = true;
			}
		}
		if (generalStock != null) {
			for (int i = 0; i < generalStock.length; i++) {
				Item item = generalStock[i];
				if (item == null)
					continue;
				item.setAmount(item.getAmount() - 1);
				if (item.getAmount() <= 0)
					generalStock[i] = null;
				needRefresh = true;
			}
		}
		if (needRefresh)
			refreshShop();
	}

	private boolean addItem(int itemId, int quantity) {
		for (Item item : mainStock) {
			if (item.getId() == itemId) {
				item.setAmount(item.getAmount() + quantity);
				refreshShop();
				return true;
			}
		}
		if (generalStock != null) {
			for (Item item : generalStock) {
				if (item == null)
					continue;
				if (item.getId() == itemId) {
					item.setAmount(item.getAmount() + quantity);
					refreshShop();
					return true;
				}
			}
			for (int i = 0; i < generalStock.length; i++) {
				if (generalStock[i] == null) {
					generalStock[i] = new Item(itemId, quantity);
					refreshShop();
					return true;
				}
			}
		}
		return false;
	}

	public void sell(Player player, int slotId, int quantity) {
		if (player.getInventory().getItemsContainerSize() < slotId)
			return;
		Item item = player.getInventory().getItem(slotId);
		if (item == null)
			return;
		int originalId = item.getId();
		if (item.getDefinitions().isNoted())
			item = new Item(item.getDefinitions().getCertId(), item.getAmount());
		if (item.getDefinitions().isDestroyItem()
				|| ItemConstants.getItemDefaultCharges(item.getId()) != -1
				|| !ItemConstants.isTradeable(item) || item.getId() == money) {
			player.getPackets().sendGameMessage("You can't sell this item.");
			return;
		}
		int dq = getDefaultQuantity(item.getId());
		if (dq == -1 && generalStock == null) {
			player.getPackets().sendGameMessage(
					"You can't sell this item to this shop.");
			return;
		}
		int price = getSellPrice(item, dq);
		int numberOff = player.getInventory().getItems()
				.getNumberOf(originalId);
		if (quantity > numberOff)
			quantity = numberOff;
		if (!addItem(item.getId(), quantity)) {
			player.getPackets().sendGameMessage("Shop is currently full.");
			return;
		}
		player.getInventory().deleteItem(originalId, quantity);
		player.getInventory().addItem(money, price * quantity);
	}
	
	public static int loyaltyShop = 0;

	public void sendValue(Player player, int slotId) {
		if (player.getInventory().getItemsContainerSize() < slotId)
			return;
		Item item = player.getInventory().getItem(slotId);
		if (item == null)
			return;
		if (item.getDefinitions().isNoted())
			item = new Item(item.getDefinitions().getCertId(), item.getAmount());
		if (item.getDefinitions().isNoted() || !ItemConstants.isTradeable(item)
				|| item.getId() == money) {
			player.getPackets().sendGameMessage("You can't sell this item.");
			return;
		}
		int dq = getDefaultQuantity(item.getId());
		if (dq == -1 && generalStock == null) {
			player.getPackets().sendGameMessage(
					"You can't sell this item to this shop.");
			return;
		}
		int price = getSellPrice(item, dq);
		if (money == 995)
		player.getPackets().sendGameMessage(
				item.getDefinitions().getName()
				+ ": shop will buy for: "
				+ price
				+ " "
				+ ItemDefinitions.getItemDefinitions(money).getName()
				.toLowerCase()
				+ ". Right-click the item to sell.");
		
	}

	public int getDefaultQuantity(int itemId) {
		for (int i = 0; i < mainStock.length; i++)
			if (mainStock[i].getId() == itemId)
				return defaultQuantity[i];
		return -1;
	}

	public void sendInfo(Player player, int slotId, boolean isBuying) {
		if (slotId >= getStoreSize())
			return;
		Item[] stock = isBuying ? mainStock : player.getInventory().getItems().getItems();
		Item item = slotId >= stock.length ? generalStock[slotId - stock.length] : stock[slotId];
		if (item == null)
			return;
		int dq = slotId >= mainStock.length ? 0 : defaultQuantity[slotId];
		int price = getBuyPrice(item, dq);
	    for (int i = 0; i < loyaltyPrices.length; i++) {
			if (item.getId() == loyaltyPrices[i][0]) {
				player.getPackets().sendGameMessage(
						"" + item.getDefinitions().getName() + " costs "
								+ loyaltyPrices[i][1] + " loyalty points.");
				player.getPackets().sendConfig(2564, loyaltyPrices[i][1]);
				return;
			}
		}
		player.getPackets().sendConfig(2564, price);
		player.getPackets().sendGameMessage(item.getDefinitions().getName() + ": shop will " + (isBuying ? "sell" : "buy") +" for " + price + " " + ItemDefinitions.getItemDefinitions(money).getName().toLowerCase() + ".");
	}

 public int getBuyPrice(Item item, int dq) {
		switch (item.getId()) {
        case 10330:
        case 10332:
        case 10334:
        case 10338:
        case 10340:
        case 10342:
        case 10346:
        case 10348:
        case 10350:
        case 10352:
		   	item.getDefinitions().setValue(650000000);
			break;
		case 24365:
		case 24437:
		case 24438:
		case 24439:
		case 24440:
		case 24441:
			item.getDefinitions().setValue(100000000);
			break;
		case 18744:
		case 18745:
		case 18746:
			item.getDefinitions().setValue(100000000);
			break;
		case 3753:
		case 3755:
		case 3751:
		case 3749:
			item.getDefinitions().setValue(750000);
			break;
		case 10344:
		case 10336:
			item.getDefinitions().setValue(40000000);
			break;
		case 15272:
			item.getDefinitions().setValue(10000);
			break;
		case 20949:
		case 20950:
		case 20951:
		case 20952:
			item.getDefinitions().setValue(50000000);
			break;
		case 22207:
		case 22209:
		case 22211:
		case 22213:
		    item.getDefinitions().setValue(50000000);
			break;
		case 6737:
		case 6731:
		case 6735:
		case 6733:
			item.getDefinitions().setValue(15000000);
		    break;
		case 15243:
			item.getDefinitions().setValue(15000);
		    break;
		case 14497:
		case 14499:
		case 14501:
		    item.getDefinitions().setValue(1300000);
		    break;
		case 15444:
		case 15443:
		case 15442:
		case 15441:
		case 10548:
		   	item.getDefinitions().setValue(500);
			break;
		case 15241:
		    item.getDefinitions().setValue(70000000);
		    break;
		case 4675:
		case 24092:
		case 24094:
		case 24096:
		case 24098:
		    item.getDefinitions().setValue(2000000);
		    break;
		case 22362:
		case 22363:
		case 22364:
		case 22365:
		case 22358:
		case 22359:
		case 22360:
		case 22361:
			item.getDefinitions().setValue(1700);
			break;
		case 10551:
		   	item.getDefinitions().setValue(450);
			break;
		case 18335:
		case 25202:
			item.getDefinitions().setValue(250000000);
			break;
		case 24167:
			item.getDefinitions().setValue(350000000);
			break;
		case 21773:
			item.getDefinitions().setValue(6000);
			break;
		case 22482:
		case 22486:
		case 22490:
		case 22494:
			item.getDefinitions().setValue(150000);
			break;
		case 22458:
		case 22462:
		case 22466:
			item.getDefinitions().setValue(75000);
			break;
		case 21777:
			item.getDefinitions().setValue(60000000);
			break;
		//Dragon defender
		case 7461:
		case 8850:
			item.getDefinitions().setValue(5000000);
			break;
		case 8849:
		case 10828:
		   	item.getDefinitions().setValue(2500000);
			break;
			
			
		case 8848:
		case 8847:
		case 8846:
		case 8845:
		case 8844:
		case 7459:
		case 22470:
		case 22474:
		case 22478:
			item.getDefinitions().setValue(100000);
			break;
		
		case 2412:
		case 2413:
		case 2414:
			item.getDefinitions().setValue(100000);
			break;
		case 21790:
			item.getDefinitions().setValue(20000000);
			break;
		case 23531:
			item.getDefinitions().setValue(80000);
			break;
		case 23621:
		case 23351:
	    case 6685:
	    case 6686:
			item.getDefinitions().setValue(10000);
			break;
        case 12196:
        case 21512:
        case 22992:
        case 24511:
        case 24512:
			item.getDefinitions().setValue(1);
			break;
	case 4722:
	case 4720:
	case 4718:
	case 4716:
	case 4753:
	case 4755:
        case 4757:
        case 4759:
        case 4724:
        case 4726:
        case 4728:
        case 4730:
        case 4745:
        case 4747:
        case 4749:
        case 4751:
		    item.getDefinitions().setValue(10000000);
			break;
		case 4712:
		case 4714:
		case 4710:
		case 4708:
			item.getDefinitions().setValue(7500000);
			break;
		case 4736:
		case 4738:
		case 4732:
		case 4734:
			item.getDefinitions().setValue(7500000);
			break;
		case 6914:
		case 7462:
		case 20072:
		case 8839:
		case 8840:
		case 8842:
		case 10611:
		case 11663:
		case 11664:
		case 11665:
			item.getDefinitions().setValue(10000000);
			break;
		case 6746:
		case 13734:
			item.getDefinitions().setValue(100000000);
			break;
		case 3481:
        case 3483:
        case 3485:
        case 3486:
        case 3488:
			item.getDefinitions().setValue(300000);
			break;
		case 11716:
		    item.getDefinitions().setValue(80000000);
			break;
		case 6889:
			item.getDefinitions().setValue(5500000);
			break;
		case 3105:
			item.getDefinitions().setValue(75000);
			break;
		case 15126:
			item.getDefinitions().setValue(500000);
			break;
		case 3842:
		case 3840:
		case 7460:
		    item.getDefinitions().setValue(1000000);
			break;
		case 9244:
			item.getDefinitions().setValue(7500);
			break;
		case 1050:
			item.getDefinitions().setValue(2147000000);
			break;
		case 11720:
		case 11722:
			item.getDefinitions().setValue(200000000);
			break;
		case 11724:
		case 11726:
			item.getDefinitions().setValue(250000000);
			break;
		case 10008:
		case 10006:
		    item.getDefinitions().setValue(15000);
			break;
		case 2577:
		case 2581:
			item.getDefinitions().setValue(80000000);
			break;
		case 11235:
			item.getDefinitions().setValue(30000000);
			break;
		case 15486:
			item.getDefinitions().setValue(6000000);
			break;
		case 15332:
			item.getDefinitions().setValue(50000);
			break;
		case 6918:
			item.getDefinitions().setValue(1750000);
			break;
		case 6920:
			item.getDefinitions().setValue(1000000);
			break;
		case 6916:
			item.getDefinitions().setValue(1500000);
			break;
		case 324:
		case 1386:
			item.getDefinitions().setValue(250000);
			break;
		case 6922:
			item.getDefinitions().setValue(1000000);
			break;
		case 6924:
			item.getDefinitions().setValue(1250000);
			break;
		case 11335:
			item.getDefinitions().setValue(75000000);
			break;
		case 14479:
			item.getDefinitions().setValue(65000000);
			break;
		case 3140:
			item.getDefinitions().setValue(5000000);
			break;
		case 11732:
			item.getDefinitions().setValue(10000000);
			break;
		case 4087:
		case 4585:
			item.getDefinitions().setValue(2500000);
			break;
		case 1187:
			item.getDefinitions().setValue(550000);
			break;
		case 4151:
			item.getDefinitions().setValue(15000000);
			break;
		case 20671:
			item.getDefinitions().setValue(350000);
			break;
		case 21371:
			item.getDefinitions().setValue(65000000);
			break;
		case 11696:
			item.getDefinitions().setValue(300000000);
			break;
		case 11700:
			item.getDefinitions().setValue(400000000);
			break;
		case 11698:
		case 13754:
			item.getDefinitions().setValue(500000000);
			break;
		case 11694:
			item.getDefinitions().setValue(600000000);
			break;
		case 14484:
			item.getDefinitions().setValue(2500);
			break;
		case 23659:
			item.getDefinitions().setValue(250);
			break;
		case 18349:
		case 18351:
		case 18353:
		case 18355:
			item.getDefinitions().setValue(1500);
			break;
		case 3024:
		case 3025:
			item.getDefinitions().setValue(15000);
			break;
		case 4153:
			item.getDefinitions().setValue(3500000);
			break;
		case 18357:
			item.getDefinitions().setValue(1500);
			break;
		case 11690:
			item.getDefinitions().setValue(20000000);
			break;
		case 18359:
			item.getDefinitions().setValue(1500);
			break;
		case 19784:
			item.getDefinitions().setValue(2000);
			break;
		case 21787:
			item.getDefinitions().setValue(18000000);
			break;
		case 11730:
			item.getDefinitions().setValue(65000000);
			break;
		case 11702:
			item.getDefinitions().setValue(500000000);
			break;
		case 11708:
			item.getDefinitions().setValue(300000000);
			break;
		case 11704:
			item.getDefinitions().setValue(200000000);
			break;
		case 11706:
			item.getDefinitions().setValue(400000000);
			break;
		case 15017:
		case 15020:
		case 15220:
		case 15018:
		case 15019:
			item.getDefinitions().setValue(30000000);
			break;
		case 1609://opal
			item.getDefinitions().setValue(5000);
			break;
		case 1611://jade
			item.getDefinitions().setValue(6000);
			break;
		case 1613://red topaz
			item.getDefinitions().setValue(7000);
			break;
		case 1607://sapphire
			item.getDefinitions().setValue(8000);
			break;
		case 1605://emerald
			item.getDefinitions().setValue(9000);
			break;
		case 1603://ruby
			item.getDefinitions().setValue(10000);
			break;
		case 1601://diamond
			item.getDefinitions().setValue(11000);
			break;
		case 1615://dragonstone
			item.getDefinitions().setValue(12000);
			break;
		case 19780://korasi sword (no spec attk)
			item.getDefinitions().setValue(330000000);
			break;
		case 6585://amulet of fury
			item.getDefinitions().setValue(27000000);
			break;
		case 21793://ragefire boots
			item.getDefinitions().setValue(18000000);
			break;
		case 1662://diamond necklace
			item.getDefinitions().setValue(200000);
			break;
		case 7650://silver dust
			item.getDefinitions().setValue(150000);
			break;
		case 1635://gold ring
			item.getDefinitions().setValue(100000);
			break;
		case 1739://cowhide
			item.getDefinitions().setValue(60000);
			break;
		case 950://silk
			item.getDefinitions().setValue(40000);
			break;

		}
		return item.getDefinitions().getValue();
	}

    public int getSellPrice(Item item, int dq) {
		switch (item.getId()) {
			    case 249:
		case 250:
		case 251:
		case 252:
		case 253:
		case 254:
		case 255:
		case 256:
		case 257:
		case 258:
		case 259:
		case 260:
		case 261:
		case 262:
		case 263:
		case 264:
		case 265:
		case 266:
		case 267:
		case 268:
        	case 269:
        	case 270:
			item.getDefinitions().setValue(1);
		    break;
	    case 15243:
			item.getDefinitions().setValue(10000);
		    break;
		case 15241:
		case 11728:
		case 11718:
		    item.getDefinitions().setValue(50000000);
		    break;
		case 13734:
		    item.getDefinitions().setValue(40000000);
		    break;
		case 7459:
		    item.getDefinitions().setValue(100000);
		    break;
		case 7460:
		    item.getDefinitions().setValue(1000000);
		    break;
		case 7461:
		    item.getDefinitions().setValue(5000000);
		    break;
		case 7462:
		    item.getDefinitions().setValue(10000000);
		    break;
		case 20949:
		case 20950:
		case 20951:
		case 20952:
			item.getDefinitions().setValue(35000000);
			break;
		case 11702:
		case 13754:
			item.getDefinitions().setValue(500000000);
			break;
		case 11708:
			item.getDefinitions().setValue(300000000);
			break;
		case 11704:
			item.getDefinitions().setValue(200000000);
			break;
		case 11706:
			item.getDefinitions().setValue(400000000);
			break;
		case 22207:
		case 22209:
		case 22211:
		case 22213:
		    item.getDefinitions().setValue(35000000);
			break;
		case 14497:
		case 14499:
		case 14501:
		    item.getDefinitions().setValue(1000000);
		    break;
		case 4675:
		case 24092:
		case 24094:
		case 24096:
		case 24098:
		    item.getDefinitions().setValue(1500000);
		    break;
		case 23531:
			item.getDefinitions().setValue(80000);
			break;
		case 18349:
			item.getDefinitions().setValue(1);
			break;
		case 19784:
			item.getDefinitions().setValue(1);
			break;
		case 18351:
			item.getDefinitions().setValue(1);
			break;
		case 18353:
			item.getDefinitions().setValue(1);
			break;
		case 18355:
			item.getDefinitions().setValue(1);
			break;
		case 18357:
			item.getDefinitions().setValue(1);
			break;
		case 18359:
			item.getDefinitions().setValue(1);
			break;
		case 23621:
		case 23351:
	    case 6685:
			item.getDefinitions().setValue(10000);
			break;
					case 15444:
		case 15443:
		case 15442:
		case 15441:
		case 10548:
		   	item.getDefinitions().setValue(1);
			break;
					case 22362:
		case 22363:
		case 22364:
		case 22365:
		case 22358:
		case 22359:
		case 22360:
		case 22361:
			item.getDefinitions().setValue(1);
			break;
		case 10551:
		   	item.getDefinitions().setValue(1);
			break;
                 case 10330:
case 10332:
case 10334:
case 10338:
case 10340:
case 10342:
case 10346:
case 10348:
case 10350:
case 10352:
		   	item.getDefinitions().setValue(650000000);
			break;
		case 18335:
			item.getDefinitions().setValue(50000000);
			break;
		case 24167:
			item.getDefinitions().setValue(350000000);
			break;
		case 22486:
		case 22490:
		case 22482:
		case 22494:
			item.getDefinitions().setValue(10000);
			break;
		case 21777:
		case 2581:
		   	item.getDefinitions().setValue(50000000);
			break;
		case 324:
		case 1386:
			item.getDefinitions().setValue(175000);
			break;
		case 21773:
			item.getDefinitions().setValue(3000);
			break;
		/*Start of Defenders*/
		case 20072:
			item.getDefinitions().setValue(10000000);
			break;
		case 8849:
		   	item.getDefinitions().setValue(2000000);
			break;
		case 8850:
		   	item.getDefinitions().setValue(5000000);
			break;
		case 8848:
		case 8847:
		case 8846:
		case 8845:
		case 8844:
			item.getDefinitions().setValue(50000);
			break;
		/*End of defenders*/	
		
		case 2412:
		case 2413:
		case 2414:
			item.getDefinitions().setValue(75000);
			break;
		case 22470:
		case 22474:
		case 22478:
			item.getDefinitions().setValue(7500);
			break;
		case 21790:
			item.getDefinitions().setValue(18000000);
			break;
		case 6746:
			item.getDefinitions().setValue(100000000);
			break;
		case 12196:
        case 21512:
        case 22992:
        case 24511:
        case 24512:
			item.getDefinitions().setValue(1);
			break;
	case 4722:
	case 4720:
	case 4718:
	case 4716:
	case 4753:
	case 4755:
        case 4757:
        case 4759:
        case 4724:
        case 4726:
        case 4728:
        case 4730:
        case 4745:
        case 4747:
        case 4749:
        case 4751:
		    item.getDefinitions().setValue(8000000);
			break;
		case 4712:
		case 4714:
		case 4710:
		case 4708:
			item.getDefinitions().setValue(3500000);
			break;
		case 4736:
		case 4738:
		case 4732:
		case 4734:
			item.getDefinitions().setValue(3500000);
			break;
		case 3751:
			item.getDefinitions().setValue(55000);
			break;
		case 6914:
			item.getDefinitions().setValue(4000000);
			break;
		case 3481:
        case 3483:
        case 3485:
        case 3486:
        case 3488:
			item.getDefinitions().setValue(250000);
			break;
		case 20671:
			item.getDefinitions().setValue(30000);
			break;
		case 3024:
		case 3025:
			item.getDefinitions().setValue(15000);
			break;
		case 6889:
			item.getDefinitions().setValue(4500000);
			break;
		case 3105:
			item.getDefinitions().setValue(65000);
			break;
		case 15126:
			item.getDefinitions().setValue(250000);
			break;
		case 3842:
		case 3840:
		    item.getDefinitions().setValue(750000);
			break;
		case 9244:
			item.getDefinitions().setValue(6500);
			break;
		case 1050:
			item.getDefinitions().setValue(2147000000);
			break;
		case 10008:
		case 10006:
		    item.getDefinitions().setValue(10000);
			break;
		case 11235:
			item.getDefinitions().setValue(30000000);
			break;
		case 15486:
			item.getDefinitions().setValue(3500000);
			break;
		case 6918:
			item.getDefinitions().setValue(500000);
			break;
		case 6920:
			item.getDefinitions().setValue(500000);
			break;
		case 6916:
			item.getDefinitions().setValue(500000);
			break;
		case 6922:
			item.getDefinitions().setValue(500000);
			break;
		case 6924:
			item.getDefinitions().setValue(500000);
			break;
		case 11335:
			item.getDefinitions().setValue(60000000);
			break;
		case 14479:
			item.getDefinitions().setValue(57500000);
			break;
		case 1187:
			item.getDefinitions().setValue(250000);
			break;
		case 4151:
			item.getDefinitions().setValue(700000);
			break;
		case 21371:
			item.getDefinitions().setValue(55000000);
			break;
		case 4153:
			item.getDefinitions().setValue(1900000);
			break;
		case 21787:
			item.getDefinitions().setValue(13000000);
			break;
		case 1609://opal
			item.getDefinitions().setValue(3000);
			break;
		case 1611://jade
			item.getDefinitions().setValue(4000);
			break;
		case 1613://red topaz
		case 22458:
		case 22462:
		case 22466:
			item.getDefinitions().setValue(5000);
			break;
		case 1607://sapphire
			item.getDefinitions().setValue(6000);
			break;
		case 1605://emerald
			item.getDefinitions().setValue(7000);
			break;
		case 1603://ruby
			item.getDefinitions().setValue(8000);
			break;
		case 1601://diamond
			item.getDefinitions().setValue(9000);
			break;
		case 1615://dragonstone
			item.getDefinitions().setValue(10000);
			break;
		case 19780://korasi sword
			item.getDefinitions().setValue(290000000);
			break;
		case 6585://amulet of fury
			item.getDefinitions().setValue(21000000);
			break;
		case 21793://ragefire boots
			item.getDefinitions().setValue(13000000);
			break;
		case 1662://diamond necklace
			item.getDefinitions().setValue(200000);
			break;
		case 7650://silver dust
			item.getDefinitions().setValue(150000);
			break;
		case 1635://gold ring
			item.getDefinitions().setValue(100000);
			break;
		case 1739://cowhide
			item.getDefinitions().setValue(60000);
			break;
		case 950://silk
			item.getDefinitions().setValue(40000);
			break;
        }
		return item.getDefinitions().getValue();
	}

	public void sendExamine(Player player, int slotId) {
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? generalStock[slotId
		                                                      - mainStock.length] : mainStock[slotId];
		if (item == null)
			return;
		player.getPackets().sendGameMessage(ItemExamines.getExamine(item));
	}

	public void refreshShop() {
		for (Player player : viewingPlayers) {
			sendStore(player);
			player.getPackets().sendIComponentSettings(620, 25, 0,
					getStoreSize() * 6, 1150);
		}
	}

	public int getStoreSize() {
		return mainStock.length
				+ (generalStock != null ? generalStock.length : 0);
	}

	public void sendStore(Player player) {
		Item[] stock = new Item[mainStock.length
		                        + (generalStock != null ? generalStock.length : 0)];
		System.arraycopy(mainStock, 0, stock, 0, mainStock.length);
		if (generalStock != null)
			System.arraycopy(generalStock, 0, stock, mainStock.length,
					generalStock.length);
		player.getPackets().sendItems(MAIN_STOCK_ITEMS_KEY, stock);
	}

	public void sendSellStore(Player player, Item[] inventory) {
		Item[] stock = new Item[inventory.length + (generalStock != null ? generalStock.length : 0)];
		System.arraycopy(inventory, 0, stock, 0, inventory.length);
		if (generalStock != null)
			System.arraycopy(generalStock, 0, stock, inventory.length, generalStock.length);
		player.getPackets().sendItems(MAIN_STOCK_ITEMS_KEY, stock);
	}

	/**
	 * Checks if the player is buying an item or selling it.
	 * @param player The player
	 * @param slotId The slot id
	 * @param amount The amount
	 */
	public void handleShop(Player player, int slotId, int amount) {
		boolean isBuying = player.getTemporaryAttributtes().get("shop_buying") != null;
		if (isBuying)
			buy(player, slotId, amount);
		else
			sell(player, slotId, amount);
	}

	public Item[] getMainStock() {
		return this.mainStock;
	}
	
	public int getAmount() {
		return this.amount;
	}

	public void setAmount(Player player, int amount) {
		this.amount = amount;
		player.getPackets().sendIComponentText(1265, 67, String.valueOf(amount)); //just update it here
	}
}
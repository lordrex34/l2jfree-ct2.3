/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jfree.gameserver.network.clientpackets;

import com.l2jfree.Config;
import com.l2jfree.gameserver.Shutdown;
import com.l2jfree.gameserver.Shutdown.DisableType;
import com.l2jfree.gameserver.model.TradeList;
import com.l2jfree.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfree.gameserver.network.SystemMessageId;
import com.l2jfree.gameserver.network.serverpackets.ActionFailed;
import com.l2jfree.gameserver.network.serverpackets.ExPrivateStoreSetWholeMsg;
import com.l2jfree.gameserver.network.serverpackets.PrivateStoreManageListSell;
import com.l2jfree.gameserver.network.serverpackets.PrivateStoreMsgSell;
import com.l2jfree.gameserver.network.serverpackets.SystemMessage;
import com.l2jfree.gameserver.util.Util;

import static com.l2jfree.gameserver.model.itemcontainer.PcInventory.MAX_ADENA;

/**
 * This class ...
 * 
 * @version $Revision: 1.2.2.1.2.5 $ $Date: 2005/03/27 15:29:30 $
 */
public class SetPrivateStoreListSell extends L2GameClientPacket
{
	private static final String	_C__74_SETPRIVATESTORELISTSELL	= "[C] 74 SetPrivateStoreListSell";

	private static final int BATCH_LENGTH = 12; // length of the one item
	private static final int BATCH_LENGTH_FINAL = 20;

	private boolean				_packageSale;
	private Item[]				_items = null;

	@Override
	protected void readImpl()
	{
		_packageSale = (readD() == 1);
		int count = readD();
		if (count < 0
				|| count > Config.MAX_ITEM_IN_PACKET
				|| count * (Config.PACKET_FINAL ? BATCH_LENGTH_FINAL : BATCH_LENGTH) != getByteBuffer().remaining())
		{
			return;
		}

		_items = new Item[count];
		for (int i = 0; i < count ; i++)
		{
			int itemId = readD();
			long cnt = readCompQ();
			long price = readCompQ();

			if (itemId < 1 || cnt < 1 || price < 0)
			{
				_items = null;
				return;
			}
			_items[i] = new Item(itemId, cnt, price);
		}
	}

	@Override
	protected void runImpl()
	{
		L2PcInstance player = getClient().getActiveChar();
		if (player == null)
			return;

		if (_items == null)
		{
			player.setPrivateStoreType(L2PcInstance.STORE_PRIVATE_NONE);
			player.broadcastUserInfo();
			return;
		}

		if (Shutdown.isActionDisabled(DisableType.TRANSACTION))
		{
			player.sendMessage("Transactions are not allowed during restart/shutdown.");
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}

		if (Config.GM_DISABLE_TRANSACTION && player.getAccessLevel() >= Config.GM_TRANSACTION_MIN && player.getAccessLevel() <= Config.GM_TRANSACTION_MAX)
		{
			player.sendMessage("Unsufficient privileges.");
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}

		// Check maximum number of allowed slots for pvt shops
		if (_items.length > player.getPrivateSellStoreLimit())
		{
			player.sendPacket(new PrivateStoreManageListSell(player, _packageSale));
			player.sendPacket(new SystemMessage(SystemMessageId.YOU_HAVE_EXCEEDED_QUANTITY_THAT_CAN_BE_INPUTTED));
			return;
		}

		TradeList tradeList = player.getSellList();
		tradeList.clear();
		tradeList.setPackaged(_packageSale);

		long totalCost = player.getAdena();
		for (Item i : _items)
		{
			if (!i.addToTradeList(tradeList))
			{
				Util.handleIllegalPlayerAction(player, "Warning!! Character "
						+ player.getName() + " of account "
						+ player.getAccountName() + " tried to set price more than "
						+ MAX_ADENA + " adena in Private Store - Sell.",
						Config.DEFAULT_PUNISH);
				return;
			}

			totalCost += i.getPrice();
			if (totalCost > MAX_ADENA)
			{
				Util.handleIllegalPlayerAction(player, "Warning!! Character "
						+ player.getName() + " of account "
						+ player.getAccountName() + " tried to set total price more than "
						+ MAX_ADENA + " adena in Private Store - Sell.",
						Config.DEFAULT_PUNISH);
				return;
			}
		}

		player.sitDown();
		if (_packageSale)
			player.setPrivateStoreType(L2PcInstance.STORE_PRIVATE_PACKAGE_SELL);
		else
			player.setPrivateStoreType(L2PcInstance.STORE_PRIVATE_SELL);

		player.broadcastUserInfo();
		if (_packageSale)
			player.broadcastPacket(new ExPrivateStoreSetWholeMsg(player));
		else
			player.broadcastPacket(new PrivateStoreMsgSell(player));
	}

	private class Item
	{
		private final int _itemId;
		private final long _count;
		private final long _price;
		
		public Item(int id, long num, long pri)
		{
			_itemId = id;
			_count = num;
			_price = pri;
		}

		public boolean addToTradeList(TradeList list)
		{
			if ((MAX_ADENA / _count) < _price)
				return false;

			list.addItem(_itemId, _count, _price);
			return true;
		}

		public long getPrice()
		{
			return _count * _price;
		}
	}

	@Override
	public String getType()
	{
		return _C__74_SETPRIVATESTORELISTSELL;
	}
}

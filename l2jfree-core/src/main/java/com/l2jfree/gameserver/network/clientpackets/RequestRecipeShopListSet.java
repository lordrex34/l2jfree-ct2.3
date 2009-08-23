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

import static com.l2jfree.gameserver.model.itemcontainer.PcInventory.MAX_ADENA;

import com.l2jfree.Config;
import com.l2jfree.gameserver.model.L2ManufactureItem;
import com.l2jfree.gameserver.model.L2ManufactureList;
import com.l2jfree.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfree.gameserver.model.zone.L2Zone;
import com.l2jfree.gameserver.network.SystemMessageId;
import com.l2jfree.gameserver.network.serverpackets.ActionFailed;
import com.l2jfree.gameserver.network.serverpackets.RecipeShopMsg;
import com.l2jfree.gameserver.network.serverpackets.SystemMessage;

/**
 * This class ... cd(dd)
 * 
 * @version $Revision: 1.1.2.3.2.3 $ $Date: 2005/03/27 15:29:30 $
 */
public class RequestRecipeShopListSet extends L2GameClientPacket
{
	private static final String	_C__B2_RequestRecipeShopListSet	= "[C] b2 RequestRecipeShopListSet";
	
	private static final int BATCH_LENGTH = 8; // length of the one item
	private static final int BATCH_LENGTH_FINAL = 12;

	private Recipe[] _items = null;

	@Override
	protected void readImpl()
	{
		int count = readD();
		if (count <= 0
				|| count > Config.MAX_ITEM_IN_PACKET
				|| count * (Config.PACKET_FINAL ? BATCH_LENGTH_FINAL : BATCH_LENGTH) != getByteBuffer().remaining())
		{
			return;
		}

		_items = new Recipe[count];
		for (int i = 0; i < count ; i++)
		{
			int id = readD();
			long cost = readCompQ();
			if (cost < 0)
			{
				_items = null;
				return;
			}
			_items[i] = new Recipe(id, cost);
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

		if (player.isInDuel())
		{
			player.sendPacket(new SystemMessage(SystemMessageId.CANT_CRAFT_DURING_COMBAT));
			return;
		}

		// Prevents player to start a craft shop inside a nostore zone. By heX1r0
		if (player.isInsideZone(L2Zone.FLAG_NOSTORE))
		{
			player.sendPacket(new SystemMessage(SystemMessageId.NO_PRIVATE_WORKSHOP_HERE));
			sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}

		L2ManufactureList createList = new L2ManufactureList();

		for (Recipe i : _items)
		{
			if (!i.addToList(createList))
			{
				requestFailed(SystemMessageId.YOU_HAVE_EXCEEDED_QUANTITY_THAT_CAN_BE_INPUTTED); 
				return;
			}
		}

		createList.setStoreName(player.getCreateList() != null ? player.getCreateList().getStoreName() : "");
		player.setCreateList(createList);

		player.setPrivateStoreType(L2PcInstance.STORE_PRIVATE_MANUFACTURE);
		player.sitDown();
		player.broadcastUserInfo();
		player.sendPacket(new RecipeShopMsg(player));
		player.broadcastPacket(new RecipeShopMsg(player));
	}

	private class Recipe
	{
		private final int _recipeId;
		private final long _cost;
		
		public Recipe(int id, long c)
		{
			_recipeId = id;
			_cost = c;
		}

		public boolean addToList(L2ManufactureList list)
		{
			if (_cost > MAX_ADENA)
				return false;
			list.add(new L2ManufactureItem(_recipeId, _cost));
			return true;
		}
	}

	@Override
	public String getType()
	{
		return _C__B2_RequestRecipeShopListSet;
	}
}

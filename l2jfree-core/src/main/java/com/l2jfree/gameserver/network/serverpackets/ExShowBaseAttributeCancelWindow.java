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
package com.l2jfree.gameserver.network.serverpackets;

import com.l2jfree.gameserver.model.L2ItemInstance;
import com.l2jfree.gameserver.model.actor.instance.L2PcInstance;

public class ExShowBaseAttributeCancelWindow extends L2GameServerPacket
{
	private static final String		_S__FE_74_EXCSHOWBASEATTRIBUTECANCELWINDOW = "[S] FE:74 ExShowBaseAttributeCancelWindow";

	private L2ItemInstance[]		_items;

	public ExShowBaseAttributeCancelWindow(L2PcInstance player)
	{
		_items = player.getInventory().getElementItems();
	}

	@Override
	protected void writeImpl()
	{
		writeC(0xfe);
		writeH(0x74);
		writeD(_items.length);
		for (L2ItemInstance item : _items)
		{
			writeD(item.getObjectId());
			writeD(50000);
		}
	}

	@Override
	public String getType()
	{
		return _S__FE_74_EXCSHOWBASEATTRIBUTECANCELWINDOW;
	}
}
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
package com.l2jfree.gameserver.network.packets.server;

import com.l2jfree.gameserver.network.packets.L2ServerPacket;

/**
 * @author  KenM
 */
public class ExAskJoinPartyRoom extends L2ServerPacket
{
	private static final String _S__FE_35_EXASKJOINPARTYROOM = "[S] FE:35 ExAskJoinPartyRoom [s]";
	
	private final String _charName;
	
	public ExAskJoinPartyRoom(String charName)
	{
		_charName = charName;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xFE);
		writeH(0x35);
		
		writeS(_charName);
	}
	
	@Override
	public String getType()
	{
		return _S__FE_35_EXASKJOINPARTYROOM;
	}
}

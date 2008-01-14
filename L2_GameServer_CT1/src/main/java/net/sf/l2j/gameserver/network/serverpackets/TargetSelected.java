/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package net.sf.l2j.gameserver.network.serverpackets;

/**
 * format   ddddd
 * 
 * sample
 * 0000: 39  0b 07 10 48  3e 31 10 48  3a f6 00 00  91 5b 00    9...H>1.H:....[.
 * 0010: 00  4c f1 ff ff                                     .L...
 *
 * @version $Revision: 1.3.2.1.2.3 $ $Date: 2005/03/27 15:29:39 $
 */
public class TargetSelected extends L2GameServerPacket
{
	private static final String _S__39_TARGETSELECTED = "[S] 29 TargetSelected";
	private int _objectId;
	private int _targetObjId;
	private int _x;
	private int _y;
	private int _z;
	

	/**
	 * @param _characters
	 */
	public TargetSelected(int objectId, int targetObjId, int x, int y, int z)
	{
		_objectId = objectId;
		_targetObjId = targetObjId;
		_x = x;
		_y = y;
		_z = z;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x23);
		writeD(_objectId);
		writeD(_targetObjId);
		writeD(_x);
		writeD(_y);
		writeD(_z);
		writeD(0x00);
	}

	/* (non-Javadoc)
	 * @see net.sf.l2j.gameserver.serverpackets.ServerBasePacket#getType()
	 */
	@Override
	public String getType()
	{
		return _S__39_TARGETSELECTED;
	}
}

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
package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.L2Skill.SkillType;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.skills.effects.EffectRadiusSkill;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Fromat:(ch) dddddc
 * @author  -Wooden-
 */
public final class RequestExMagicSkillUseGround extends L2GameClientPacket
{
	private static final String _C__D0_2F_REQUESTEXMAGICSKILLUSEGROUND = "[C] D0:2F RequestExMagicSkillUseGround";
	private final static Log _log = LogFactory.getLog(RequestExMagicSkillUseGround.class.getName());

	private int _x;
	private int _y;
	private int _z;
	private int _skillId;
	private boolean _ctrlPressed;
	private boolean _shiftPressed;

	@Override
	protected void readImpl()
	{
		_x	= readD();
		_y	= readD();
		_z	= readD();
		_skillId		= readD();
		_ctrlPressed	= readD() != 0;
		_shiftPressed	= readC() != 0;
	}

	/**
	 * @see net.sf.l2j.gameserver.network.clientpackets.ClientBasePacket#runImpl()
	 */
	@Override
	protected void runImpl()
	{
		//TODO: implementation missing
		//System.out.println("C6: RequestExMagicSkillUseGround. x: "+_x+" y: "+_y+" z: "+_z+" skill: "+_skillId+" crtl: "+_ctrlPressed+" shift: "+_shiftPressed);
		
		L2PcInstance activeChar = getClient().getActiveChar();
		
		if (activeChar == null)
			return;
		
		int level = activeChar.getSkillLevel(_skillId);
		
		if (level <= 0 || activeChar.isOutOfControl())
		{
			activeChar.actionFailed();
			return;
		}
		
		L2Skill skill = SkillTable.getInstance().getInfo(_skillId, level);
		
		if (skill == null || skill.getSkillType() == SkillType.NOTDONE)
		{
			activeChar.actionFailed();
			return;
		}

		int distance = (int)Math.sqrt(Math.pow(_x - activeChar.getX(),2) + Math.pow(_y - activeChar.getY(),2));
		
		if (distance > ((skill.getCastRange() > 0)? skill.getCastRange() : 900))
		{
			activeChar.sendPacket(new SystemMessage(SystemMessageId.TARGET_TOO_FAR));
			activeChar.actionFailed();
			return;
		}
		
		EffectRadiusSkill.getInstance().setInitialTarget(activeChar, _x , _y , _z , skill.getEffectRange());
		activeChar.setMagicSkillUseGround(_x,_y,_z,skill.getId());		
		activeChar.useMagic(skill, (_ctrlPressed || _shiftPressed), false);
	}

	/**
	 * @see net.sf.l2j.gameserver.BasePacket#getType()
	 */
	@Override
	public String getType()
	{
		return _C__D0_2F_REQUESTEXMAGICSKILLUSEGROUND;
	}
}

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
package com.l2jfree.gameserver.skills.l2skills;

import com.l2jfree.gameserver.model.L2Effect;
import com.l2jfree.gameserver.model.L2Skill;
import com.l2jfree.gameserver.model.actor.L2Character;
import com.l2jfree.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jfree.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfree.gameserver.model.actor.instance.L2SiegeFlagInstance;
import com.l2jfree.gameserver.model.zone.L2Zone;
import com.l2jfree.gameserver.templates.StatsSet;

/**
 * For skills like "Blessing of Eva". Each part is <U>optional</U>.
 * <LI>Restores CP (may not specify)</LI>
 * <LI>Restores HP (may not specify)</LI>
 * <LI>Restores MP (may not specify)</LI>
 * <LI>Cancels bad effects (power = chance, set 0 to disable)</LI>
 * @author Savormix
 */
public class L2SkillRecover extends L2Skill
{
	private final double	_cp;
	private final double	_hp;
	private final double	_mp;
	private final double	_power;

	public L2SkillRecover(StatsSet set)
	{
		super(set);
		_cp = set.getFloat("restoredCP", 0);
		_hp = set.getFloat("restoredHP", 0);
		_mp = set.getFloat("restoredMP", 0);
		if (getPower() > 95)
			_power = 95;
		else
			_power = getPower();
	}

	@Override
	public void useSkill(L2Character activeChar, L2Character... targets)
	{
		if (activeChar.isAlikeDead())
			return;

		for (L2Character target: targets)
		{
			if (target == null || target.isDead() ||
					target.isInsideZone(L2Zone.FLAG_NOHEAL))
				continue;
			if (target instanceof L2DoorInstance || target instanceof L2SiegeFlagInstance)
				continue;
			// Player holding a cursed weapon can't be healed and can't heal
			if (target != activeChar)
			{
				if (target instanceof L2PcInstance &&
						((L2PcInstance) target).isCursedWeaponEquipped())
					continue;
				else if (activeChar instanceof L2PcInstance &&
						((L2PcInstance) activeChar).isCursedWeaponEquipped())
					continue;
			}

			if (_cp > 0)
			{
				target.getStatus().setCurrentCp(target.getStatus().getCurrentCp() + _cp);
			}
			if (_hp > 0)
			{
				target.getStatus().increaseHp(_hp);
			}
			if (_mp > 0)
			{
				target.getStatus().increaseMp(_mp);
			}
			
			if (_power <= 0)
				continue; //do not negate anything

			for (L2Effect e : target.getAllEffects())
				e.tryNegateDebuff((int)_power);
		}
	}
}

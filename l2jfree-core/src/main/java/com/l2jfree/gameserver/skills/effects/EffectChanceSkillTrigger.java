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
package com.l2jfree.gameserver.skills.effects;

import com.l2jfree.gameserver.model.ChanceCondition;
import com.l2jfree.gameserver.model.IChanceSkillTrigger;
import com.l2jfree.gameserver.model.L2Effect;
import com.l2jfree.gameserver.skills.Env;
import com.l2jfree.gameserver.skills.conditions.Condition;
import com.l2jfree.gameserver.templates.effects.EffectTemplate;
import com.l2jfree.gameserver.templates.skills.L2EffectType;

public class EffectChanceSkillTrigger extends L2Effect implements IChanceSkillTrigger
{
	private final int _triggeredId;
	private final int _triggeredLevel;
	private final Condition _chanceCondition;
	
	public EffectChanceSkillTrigger(Env env, EffectTemplate template)
    {
		super(env, template);
		
		_triggeredId = template.triggeredId;
	    _triggeredLevel = template.triggeredLevel;
	    _chanceCondition = template.chanceCondition;
    }

	@Override
    public L2EffectType getEffectType()
    {
	    return L2EffectType.CHANCE_SKILL_TRIGGER;
    }
	
	public boolean onStart()
	{
		getEffected().addChanceEffect(this);
		return true;
	}

	@Override
    public boolean onActionTime()
    {
	    return false;
    }
	
	public void onExit()
	{
		getEffected().removeChanceEffect(this);
	}

	@Override
    public int getTriggeredChanceId()
    {
	    return _triggeredId;
    }

	@Override
    public int getTriggeredChanceLevel()
    {
	    return _triggeredLevel;
    }

	@Override
    public boolean triggersChanceSkill()
    {
	    return _triggeredId > 1;
    }

	@Override
    public ChanceCondition getTriggeredChanceCondition()
    {
	    return _chanceCondition;
    }
	
}
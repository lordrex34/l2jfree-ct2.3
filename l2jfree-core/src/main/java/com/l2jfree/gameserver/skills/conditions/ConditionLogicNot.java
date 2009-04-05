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
package com.l2jfree.gameserver.skills.conditions;

import com.l2jfree.gameserver.skills.Env;

/**
 * @author mkizub
 */
public final class ConditionLogicNot extends Condition
{
	private final Condition _condition;
	
	public ConditionLogicNot(Condition condition)
	{
		if (condition == null)
			throw new NullPointerException();
		
		_condition = condition;
	}
	
	@Override
	String getDefaultMessage()
	{
		String message = _condition.getMessage();
		if (message != null)
			return "You need to satisfy the opposite of: " + message;
		
		return null;
	}
	
	@Override
	boolean testImpl(Env env)
	{
		return !_condition.test(env);
	}
}

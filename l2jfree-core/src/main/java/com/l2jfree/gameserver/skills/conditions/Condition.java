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
public abstract class Condition
{
	public static final Condition[] EMPTY_ARRAY = new Condition[0];
	
	private String _msg;
	private int _msgId;
	
	public final void setMessage(String msg)
	{
		_msg = msg;
	}
	
	public final String getMessage()
	{
		return _msg != null ? _msg : getDefaultMessage();
	}
	
	public final void setMessageId(int msgId)
	{
		_msgId = msgId;
	}
	
	public final int getMessageId()
	{
		return _msgId != 0 ? _msgId : getDefaultMessageId();
	}
	
	String getDefaultMessage()
	{
		return null;
	}
	
	int getDefaultMessageId()
	{
		return 0;
	}
	
	public final boolean test(Env env)
	{
		return testImpl(env);
	}
	
	abstract boolean testImpl(Env env);
}

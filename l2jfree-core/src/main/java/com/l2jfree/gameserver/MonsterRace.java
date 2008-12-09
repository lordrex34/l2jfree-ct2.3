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
package com.l2jfree.gameserver;

import java.lang.reflect.Constructor;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.l2jfree.gameserver.datatables.NpcTable;
import com.l2jfree.gameserver.idfactory.IdFactory;
import com.l2jfree.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jfree.gameserver.templates.chars.L2NpcTemplate;
import com.l2jfree.tools.random.Rnd;

public class MonsterRace
{
	private final static Log	_log	= LogFactory.getLog(MonsterRace.class);
	private L2NpcInstance[]		_monsters;
	private static MonsterRace	_instance;
	private Constructor<?>		_constructor;
	private int[][]				_speeds;
	private int[]				_first, _second;

	private MonsterRace()
	{
		_monsters = new L2NpcInstance[8];
		_speeds = new int[8][20];
		_first = new int[2];
		_second = new int[2];
	}

	public static MonsterRace getInstance()
	{
		if (_instance == null)
		{
			_instance = new MonsterRace();
		}

		return _instance;
	}

	public void newRace()
	{
		int random = 0;

		for (int i = 0; i < 8; i++)
		{
			int id = 31003;
			random = Rnd.get(24);
			while (true)
			{
				for (int j = i - 1; j >= 0; j--)
				{
					if (_monsters[j].getTemplate().getNpcId() == (id + random))
					{
						random = Rnd.get(24);
						continue;
					}
				}
				break;
			}
			try
			{
				L2NpcTemplate template = NpcTable.getInstance().getTemplate(id + random);
				_constructor = Class.forName("com.l2jfree.gameserver.model.actor.instance." + template.getType() + "Instance").getConstructors()[0];
				int objectId = IdFactory.getInstance().getNextId();
				_monsters[i] = (L2NpcInstance) _constructor.newInstance(objectId, template);
			}
			catch (Exception e)
			{
				_log.error(e.getMessage(), e);
			}
		}
		newSpeeds();
	}

	public void newSpeeds()
	{
		_speeds = new int[8][20];
		int total = 0;
		_first[1] = 0;
		_second[1] = 0;
		for (int i = 0; i < 8; i++)
		{
			total = 0;
			for (int j = 0; j < 20; j++)
			{
				if (j == 19)
					_speeds[i][j] = 100;
				else
					_speeds[i][j] = Rnd.get(60) + 65;
				total += _speeds[i][j];
			}
			if (total >= _first[1])
			{
				_second[0] = _first[0];
				_second[1] = _first[1];
				_first[0] = 8 - i;
				_first[1] = total;
			}
			else if (total >= _second[1])
			{
				_second[0] = 8 - i;
				_second[1] = total;
			}
		}
	}

	/**
	 * @return Returns the monsters.
	 */
	public L2NpcInstance[] getMonsters()
	{
		return _monsters;
	}

	/**
	 * @return Returns the speeds.
	 */
	public int[][] getSpeeds()
	{
		return _speeds;
	}

	public int getFirstPlace()
	{
		return _first[0];
	}

	public int getSecondPlace()
	{
		return _second[0];
	}

}

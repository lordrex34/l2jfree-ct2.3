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
package com.l2jfree.gameserver.script;

import com.l2jfree.gameserver.gameobjects.templates.L2NpcTemplate;
import com.l2jfree.gameserver.model.L2DropData;

/**
 * @author Luis Arias
 *         Define the contract for any engine interface. You should be able to add drop,
 *         quest drop, event drop and make action on player login
 */
public interface EngineInterface
{
	public void addQuestDrop(int npcID, int itemID, int min, int max, int chance, String questID, String[] states);
	
	public void addDrop(L2NpcTemplate npc, L2DropData drop, boolean sweep);
	
	public void addDrop(L2NpcTemplate npc, L2DropData drop, int category);
	
	public void addEventDrop(int[] items, int[] count, double chance, DateRange range);
	
	public void onPlayerLogin(String[] message, DateRange range);
}

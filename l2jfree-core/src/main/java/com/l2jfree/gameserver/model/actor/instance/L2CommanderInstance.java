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
package com.l2jfree.gameserver.model.actor.instance;

import com.l2jfree.gameserver.ai.CtrlIntention;
import com.l2jfree.gameserver.model.L2Attackable;
import com.l2jfree.gameserver.model.L2CharPosition;
import com.l2jfree.gameserver.model.L2Character;
import com.l2jfree.gameserver.model.actor.knownlist.CommanderKnownList;
import com.l2jfree.gameserver.templates.chars.L2NpcTemplate;

public class L2CommanderInstance extends L2Attackable
{
    public L2CommanderInstance(int objectId, L2NpcTemplate template)
    {
        super(objectId, template);
        getKnownList(); // init knownlist
    }

    /**
     * Return True if a siege is in progress and the L2Character attacker isn't a Defender.<BR><BR>
     *
     * @param attacker The L2Character that the L2CommanderInstance try to attack
     *
     */
    @Override
    public boolean isAutoAttackable(L2Character attacker)
    {
        if ( attacker == null || !(attacker instanceof L2PcInstance) )
            return false;

        boolean isFort = (getFort() != null && getFort().getFortId() > 0 
            && getFort().getSiege().getIsInProgress() &&
                !getFort().getSiege().checkIsDefender(((L2PcInstance)attacker).getClan()));
        
        // Attackable during siege by all except defenders
        return isFort;
    }
    
    @Override
    public final CommanderKnownList getKnownList()
    {
        if (_knownList == null)
            _knownList = new CommanderKnownList(this);
        
        return (CommanderKnownList)_knownList;
    }
    
    @Override
    public void addDamageHate(L2Character attacker, int damage, int aggro)
    {
        if (attacker == null)
            return;

        if (!(attacker instanceof L2CommanderInstance))
        {
            super.addDamageHate(attacker, damage, aggro);
        }
    }
    
    @Override
    public boolean doDie(L2Character killer)
    {
        if (!super.doDie(killer))
            return false;
        
        if (getFort().getSiege().getIsInProgress())
        {
            getFort().getSiege().killedCommander(this);

        }

        return true;
    }

    /**
     * This method forces guard to return to home location previously set
     *
     */
    public void returnHome()
    {
        if (getSpawn() != null && !isInsideRadius(getSpawn().getLocx(), getSpawn().getLocy(), 40, false))
        {
            if (_log.isDebugEnabled()) _log.info(getObjectId()+": moving home");
            setisReturningToSpawnPoint(true);    
            clearAggroList();
            
            if (hasAI())
                getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new L2CharPosition(getSpawn().getLocx(), getSpawn().getLocy(), getSpawn().getLocz(), 0));
        }
    }
    
}

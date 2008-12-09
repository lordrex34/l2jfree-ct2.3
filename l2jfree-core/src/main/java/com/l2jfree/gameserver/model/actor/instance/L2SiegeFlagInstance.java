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

import com.l2jfree.Config;
import com.l2jfree.gameserver.ai.CtrlIntention;
import com.l2jfree.gameserver.instancemanager.SiegeManager;
import com.l2jfree.gameserver.model.L2Character;
import com.l2jfree.gameserver.model.L2SiegeClan;
import com.l2jfree.gameserver.model.entity.Siege;
import com.l2jfree.gameserver.network.serverpackets.ActionFailed;
import com.l2jfree.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jfree.gameserver.network.serverpackets.StatusUpdate;
import com.l2jfree.gameserver.network.serverpackets.ValidateLocation;
import com.l2jfree.gameserver.templates.chars.L2NpcTemplate;

public class L2SiegeFlagInstance extends L2NpcInstance
{
    private L2PcInstance _player;
    private Siege _siege;
    private boolean _advanced;

    public L2SiegeFlagInstance(L2PcInstance player, int objectId, L2NpcTemplate template, boolean advanced)
    {
        super(objectId, template);

        _player = player;
        _siege = SiegeManager.getInstance().getSiege(_player);
        _advanced = advanced;
        if (_player.getClan() == null || _siege == null)
        {
            deleteMe();
        }
        else
        {
            L2SiegeClan sc = _siege.getAttackerClan(_player.getClan());
            if (sc == null)
                deleteMe();
            else
                sc.addFlag(this);
        }
    }

    @Override
    public boolean isAttackable()
    {
        // Attackable during siege by attacker only
        return (getCastle() != null
                && getCastle().getCastleId() > 0
                && getCastle().getSiege().getIsInProgress());
    }

    @Override
    public boolean isAutoAttackable(L2Character attacker) 
    {
        // Attackable during siege by attacker only
        return (attacker != null 
            && attacker instanceof L2PcInstance 
            && getCastle() != null
            && getCastle().getCastleId() > 0
            && getCastle().getSiege().getIsInProgress());
    }

    @Override
    public boolean doDie(L2Character killer)
    {
        if (!super.doDie(killer))
            return false;

        L2SiegeClan sc = _siege.getAttackerClan(_player.getClan());
        if (sc != null)
            sc.removeFlag(this);
        
        return true;
    }

    @Override
    public void onForcedAttack(L2PcInstance player)
    {
        onAction(player);
    }

	@Override
	public void onAction(L2PcInstance player)
	{
		if(!_player.canBeTargetedByAtSiege(player) && Config.SIEGE_ONLY_REGISTERED)
			return;
		
		if (player == null || !canTarget(player))
			return;

		// Check if the L2PcInstance already target the L2NpcInstance
		if (this != player.getTarget())
		{
			// Set the target of the L2PcInstance player
			player.setTarget(this);

			// Send a Server->Client packet MyTargetSelected to the L2PcInstance player
			MyTargetSelected my = new MyTargetSelected(getObjectId(), player.getLevel() - getLevel());
			player.sendPacket(my);

			// Send a Server->Client packet StatusUpdate of the L2NpcInstance to the L2PcInstance to update its HP bar
			StatusUpdate su = new StatusUpdate(getObjectId());
			su.addAttribute(StatusUpdate.CUR_HP, (int)getStatus().getCurrentHp() );
			su.addAttribute(StatusUpdate.MAX_HP, getMaxHp() );
			player.sendPacket(su);

			// Send a Server->Client packet ValidateLocation to correct the L2NpcInstance position and heading on the client
			player.sendPacket(new ValidateLocation(this));
		}
		else
		{
			if (isAutoAttackable(player) && Math.abs(player.getZ() - getZ()) < 100)
				player.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, this);
			else
			{
				// Send a Server->Client ActionFailed to the L2PcInstance in order to avoid that the client wait another packet
				player.sendPacket(ActionFailed.STATIC_PACKET);
			}
		}
	}

    @Override
    public void reduceCurrentHp(double damage, L2Character attacker, boolean awake, boolean isDOT)
    {
        // Advanced Headquarters have double HP.
        if(_advanced)
             damage /= 2;
        super.reduceCurrentHp(damage, attacker, awake, isDOT);
    }
}

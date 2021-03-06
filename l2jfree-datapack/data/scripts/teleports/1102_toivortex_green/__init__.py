import sys

from com.l2jfree.gameserver.gameobjects import      L2Player
from com.l2jfree.gameserver.model.quest        import State
from com.l2jfree.gameserver.model.quest        import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest
qn = "1102_toivortex_green"
GREEN_DIMENSION_STONE    = 4401
DIMENSION_VORTEX_2      = 30953
DIMENSION_VORTEX_3      = 30954

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onTalk (self,npc,player):
   st = player.getQuestState(qn)  
   npcId = npc.getNpcId()
   if npcId in [ DIMENSION_VORTEX_2, DIMENSION_VORTEX_3 ] :
     if st.getQuestItemsCount(GREEN_DIMENSION_STONE) >= 1:
       st.takeItems(GREEN_DIMENSION_STONE,1)
       player.teleToLocation(110930,15963,-4378)
       st.exitQuest(1)
       return
     else:
       st.exitQuest(1)
       return "1.htm"

QUEST       = Quest(-1,qn,"Teleports")

for i in [DIMENSION_VORTEX_2,DIMENSION_VORTEX_3] :
   QUEST.addStartNpc(i)
   QUEST.addTalkId(i)
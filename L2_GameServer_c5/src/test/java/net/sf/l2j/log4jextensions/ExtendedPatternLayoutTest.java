/**
 * Added copyright notice
 *
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package net.sf.l2j.log4jextensions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.templates.L2EtcItem;
import net.sf.l2j.gameserver.templates.L2EtcItemType;
import net.sf.l2j.gameserver.templates.StatsSet;
import net.sf.l2j.log4jextension.ExtendedPatternLayout;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;

/**
 * Class for L2Skill testing
 * 
 */
public class ExtendedPatternLayoutTest extends TestCase
{   
    private StatsSet statsSetForTestItem = null;
    
    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        statsSetForTestItem = new StatsSet();
        statsSetForTestItem.set("item_id",32);
        statsSetForTestItem.set("name","Dark Crystal Boots Light Armor");
        statsSetForTestItem.set("type1",1);  // needed for item list (inventory)
        statsSetForTestItem.set("type2",1);  // different lists for armor, weapon, etc
        statsSetForTestItem.set("weight",1);
        statsSetForTestItem.set("crystallizable",true);
        statsSetForTestItem.set("material",1);
        statsSetForTestItem.set("durability",1);
        statsSetForTestItem.set("bodypart",1);
        statsSetForTestItem.set("price",1);
        
    }
    
    /**
     * Test Basic logging (just a string)
     */
    public void testBasicString()
    {
        try
        {
            // Backup system.out
            PrintStream psSys = System.out; //backup
            // instantiate a buffer to store output loggging 
            ByteArrayOutputStream by = new ByteArrayOutputStream();
            System.setOut(new PrintStream (by,true));
            
            // log in consoleAppender
            Logger logger = Logger.getLogger("test");
            ExtendedPatternLayout myLayout= new ExtendedPatternLayout();
            Appender myAppender = new ConsoleAppender(myLayout);   
            logger.addAppender(myAppender);
            logger.info("Basic string");
            
            //restore system outputstream
            by.close();            
            System.setOut(psSys);
            
            // Check value
            assertTrue(by.toString().startsWith("Basic string" + System.getProperty("line.separator")+"INFO : Basic string"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail (e.getMessage()); 
        }
    }    

    /**
     * Test throwable string
     */
    public void testThrowable()
    {
        try
        {
            // Backup system.out
            PrintStream psSys = System.out; //backup
            // instantiate a buffer to store output loggging 
            ByteArrayOutputStream by = new ByteArrayOutputStream();
            System.setOut(new PrintStream (by,true));
            
            // log in consoleAppender
            Logger logger = Logger.getLogger("test");
            ExtendedPatternLayout myLayout= new ExtendedPatternLayout();
            myLayout.setConversionPattern("%p %m %s");
            Appender myAppender = new ConsoleAppender(myLayout);   
            logger.addAppender(myAppender);
            Exception e = new Exception ("Exception for bad error...");
            e.setStackTrace(new StackTraceElement[]{new StackTraceElement("class toto","methode titi","file fifi",1)});
            logger.info("exception",e);
            
            //restore system outputstream
            by.close();            
            System.setOut(psSys);
            
            // Check value
            assertEquals("INFO exception java.lang.Exception: Exception for bad error..."+ System.getProperty("line.separator")+
                         "\tat class toto.methode titi(file fifi:1)"+ System.getProperty("line.separator")
                         ,by.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail (e.getMessage()); 
        }
    }    

	/**
	 * Test content for a logging event With a a list containing a L2ItemInstance and a null value
	 */
	public void testLogItemEvent()
	{
        try
        {
            // Backup system.out
            PrintStream psSys = System.out; //backup
            // instantiate a buffer to store output loggging 
            ByteArrayOutputStream by = new ByteArrayOutputStream();
            System.setOut(new PrintStream (by,true));
            
            // Create a dummy item
            L2ItemInstance item = new L2ItemInstance(216565,new L2EtcItem(L2EtcItemType.MATERIAL,statsSetForTestItem));
            
            // prepare a list to be logged
            List<Object> param = new ArrayList<Object>();
            param.add("CHANGE : Pickup " );
            param.add("player corwin" );
            param.add(item);
            param.add(null);
            
            // log in consoleAppender
            Logger logger = Logger.getLogger("test");
            ExtendedPatternLayout myLayout= new ExtendedPatternLayout();
            Appender myAppender = new ConsoleAppender(myLayout);
            logger.addAppender(myAppender);
            logger.info(param);
            
            //restore system outputstream
            by.close();            
            System.setOut(psSys);
            
            // Check value
            assertEquals(by.toString(),", CHANGE : Pickup , player corwin, item 216565:Dark Crystal Boots Light Armor(1)" + System.getProperty("line.separator"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail (e.getMessage()); 
        }
	}
    


	

}

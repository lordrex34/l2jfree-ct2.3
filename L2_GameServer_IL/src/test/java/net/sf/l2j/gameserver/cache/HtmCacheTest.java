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
package net.sf.l2j.gameserver.cache;

import java.io.File;

import junit.framework.TestCase;
import net.sf.l2j.Config;
import net.sf.l2j.Config.CacheType;

/**
 * Class for HtmCache testing
 * 
 */
public class HtmCacheTest extends TestCase
{   
    
	/**
	 * Test method isLoadable
	 */
	public final void testLoadInvalidFile()
	{
        Config.TYPE_CACHE = CacheType.ehcache;
        loadInvalidFile();
        Config.TYPE_CACHE = CacheType.mapcache;
        loadInvalidFile();
        Config.TYPE_CACHE = CacheType.none;
        loadInvalidFile();
	}
    
    private void loadInvalidFile ()
    {
        HtmCache cache = HtmCache.getInstance();
        cache.reload();
        assertTrue (!cache.isLoadable("./config"));        
    }
    
    /**
     * Test method loadfile with a valid file and lazy cache
     */
    public final void testLoadValidFile()
    {
        Config.DATAPACK_ROOT = new File (System.getProperty("user.home"));
        Config.TYPE_CACHE = CacheType.ehcache;
        loadValidFile();
        Config.TYPE_CACHE = CacheType.mapcache;
        loadValidFile();
    }

    private void loadValidFile()
    {
        HtmCache cache = HtmCache.getInstance();
        cache.reload();
        
        // load resource
        String file = getClass().getResource("npcdefault.htm").getFile().replace("%20", " "); 
        
        // check if it is loadable
        assertTrue (cache.isLoadable(file));
        
        assertEquals ("<html><body>I have nothing to say to you<br><a action=\"bypass -h npc_%objectId%_Quest\">Quest</a></body></html>",cache.loadFile(new File(file)));
        
        assertEquals (1, cache.getLoadedFiles() );
    }

    
    /**
     * Test where text is missing
     */
    public final void testMissingText()
    {
        Config.DATAPACK_ROOT = new File (System.getProperty("user.home"));
        
        Config.TYPE_CACHE = CacheType.ehcache;
        HtmCache cache = HtmCache.getInstance();
        cache.reload();
        assertEquals ("<html><body>My text is missing:<br>dummy.htm</body></html>",cache.getHtmForce("dummy.htm"));
        Config.TYPE_CACHE = CacheType.mapcache;
        cache = HtmCache.getInstance();
        cache.reload();
        assertEquals ("<html><body>My text is missing:<br>dummy.htm</body></html>",cache.getHtmForce("dummy.htm"));
    }


}

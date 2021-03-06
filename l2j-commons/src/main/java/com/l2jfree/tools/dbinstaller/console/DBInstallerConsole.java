/*
 * Copyright (C) 2004-2015 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jfree.tools.dbinstaller.console;

import java.sql.Connection;
import java.util.Scanner;
import java.util.prefs.Preferences;

import com.l2jfree.tools.dbinstaller.DBOutputInterface;
import com.l2jfree.tools.dbinstaller.RunTasks;
import com.l2jfree.tools.dbinstaller.util.CloseShieldedInputStream;
import com.l2jfree.tools.dbinstaller.util.mysql.MySqlConnect;

/**
 * @author mrTJO
 */
public class DBInstallerConsole implements DBOutputInterface
{
	Connection _con;
	
	public DBInstallerConsole(String db, String dir, String cleanUpScript)
	{
		System.out.println("Welcome to L2J DataBase installer");
		Preferences prop = Preferences.userRoot();
		RunTasks rt = null;
		try (Scanner scn = new Scanner(new CloseShieldedInputStream(System.in)))
		{
			while (_con == null)
			{
				System.out.printf("%s (%s): ", "Host", prop.get("dbHost_" + db, "localhost"));
				String dbHost = scn.nextLine();
				System.out.printf("%s (%s): ", "Port", prop.get("dbPort_" + db, "3306"));
				String dbPort = scn.nextLine();
				System.out.printf("%s (%s): ", "Username", prop.get("dbUser_" + db, "root"));
				String dbUser = scn.nextLine();
				System.out.printf("%s (%s): ", "Password", "");
				String dbPass = scn.nextLine();
				System.out.printf("%s (%s): ", "Database", prop.get("dbDbse_" + db, db));
				String dbDbse = scn.nextLine();
				
				dbHost = dbHost.isEmpty() ? prop.get("dbHost_" + db, "localhost") : dbHost;
				dbPort = dbPort.isEmpty() ? prop.get("dbPort_" + db, "3306") : dbPort;
				dbUser = dbUser.isEmpty() ? prop.get("dbUser_" + db, "root") : dbUser;
				dbDbse = dbDbse.isEmpty() ? prop.get("dbDbse_" + db, db) : dbDbse;
				
				MySqlConnect connector = new MySqlConnect(dbHost, dbPort, dbUser, dbPass, dbDbse, true);
				
				_con = connector.getConnection();
			}
			
			System.out.print("(C)lean install, (U)pdate or (E)xit? ");
			String resp = scn.next();
			if (resp.equalsIgnoreCase("c"))
			{
				System.out.print("Do you really want to destroy your db (Y/N)?");
				if (scn.next().equalsIgnoreCase("y"))
				{
					rt = new RunTasks(this, db, dir, cleanUpScript, true);
				}
			}
			else if (resp.equalsIgnoreCase("u"))
			{
				rt = new RunTasks(this, db, dir, cleanUpScript, false);
			}
		}
		
		if (rt != null)
		{
			rt.run();
		}
		else
		{
			System.exit(0);
		}
	}
	
	/**
	 * Database Console Installer constructor.
	 * @param defDatabase the default database name
	 * @param dir the SQL script's directory
	 * @param cleanUpScript the clean up SQL script
	 * @param host the host name
	 * @param port the port
	 * @param user the user name
	 * @param pass the password
	 * @param database the database name
	 * @param mode the mode, c: Clean, u:update
	 */
	public DBInstallerConsole(String defDatabase, String dir, String cleanUpScript, String host, String port,
			String user, String pass, String database, String mode)
	{
		if ((database == null) || database.isEmpty())
		{
			database = defDatabase;
		}
		
		final MySqlConnect connector = new MySqlConnect(host, port, user, pass, database, true);
		
		_con = connector.getConnection();
		
		if ((mode != null) && ("c".equalsIgnoreCase(mode) || "u".equalsIgnoreCase(mode)))
		{
			final RunTasks rt = new RunTasks(this, database, dir, cleanUpScript, "c".equalsIgnoreCase(mode));
			rt.run();
		}
	}
	
	@Override
	public void appendToProgressArea(String text)
	{
		System.out.println(text);
	}
	
	@Override
	public Connection getConnection()
	{
		return _con;
	}
	
	@Override
	public void setProgressIndeterminate(boolean value)
	{
	}
	
	@Override
	public void setProgressMaximum(int maxValue)
	{
	}
	
	@Override
	public void setProgressValue(int value)
	{
	}
	
	@Override
	public void setFrameVisible(boolean value)
	{
	}
	
	@Override
	public int requestConfirm(String title, String message, int type)
	{
		System.out.print(message);
		String res = "";
		try (Scanner scn = new Scanner(new CloseShieldedInputStream(System.in)))
		{
			res = scn.next();
		}
		return res.equalsIgnoreCase("y") ? 0 : 1;
	}
	
	@Override
	public void showMessage(String title, String message, int type)
	{
		System.out.println(message);
	}
}

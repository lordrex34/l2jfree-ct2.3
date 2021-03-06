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
package com.l2jfree.status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.l2jfree.status.commands.ClassStats;
import com.l2jfree.status.commands.GC;
import com.l2jfree.status.commands.MemoryStatistics;
import com.l2jfree.status.commands.Threads;
import com.l2jfree.util.HandlerRegistry;

/**
 * @author NB4L1
 */
public abstract class StatusThread extends Thread
{
	protected static final Log _log = LogFactory.getLog(StatusThread.class);
	
	private final StatusServer _server;
	private final Socket _socket;
	private final PrintWriter _out;
	private final BufferedReader _in;
	
	protected StatusThread(StatusServer server, Socket socket) throws IOException
	{
		_server = server;
		_socket = socket;
		_out = new PrintWriter(_socket.getOutputStream());
		_in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		
		register(new Quit());
		register(new Help());
		register(new ClassStats());
		register(new GC());
		register(new MemoryStatistics());
		register(new Threads());
	}
	
	protected final Socket getSocket()
	{
		return _socket;
	}
	
	protected final StatusThread print(Object obj)
	{
		_out.print(obj);
		_out.flush();
		
		return this;
	}
	
	protected final StatusThread println(Object obj)
	{
		_out.println(obj);
		_out.flush();
		
		return this;
	}
	
	protected final StatusThread println()
	{
		_out.println();
		_out.flush();
		
		return this;
	}
	
	protected final String readLine() throws IOException
	{
		String line = _in.readLine();
		if (line == null)
			return null;
		
		StringBuilder sb = new StringBuilder(line);
		
		for (int index; (index = sb.indexOf("\b")) != -1;)
			sb.replace(index, index + 1, "");
		
		return sb.toString();
	}
	
	private final HandlerRegistry<String, StatusCommand> _handlers = new HandlerRegistry<String, StatusCommand>();
	
	protected final void register(StatusCommand handler)
	{
		_handlers.registerAll(handler, handler.getCommands());
	}
	
	protected abstract boolean login() throws IOException;
	
	@Override
	public final void run()
	{
		try
		{
			println("Welcome to the l2j-free telnet server...");
			
			if (!login())
			{
				println("Connection refused...");
				return;
			}
			
			println("Connection accepted...");
			println("[l2j-free telnet console]");
			
			_server.addStatusThread(this);
			
			for (String line; !_socket.isClosed() && (line = readLine()) != null;)
			{
				line = line.trim();
				
				if (line.isEmpty())
					continue;
				
				String command = line;
				String params = "";
				
				if (line.indexOf(" ") != -1)
				{
					command = line.substring(0, line.indexOf(" "));
					params = line.substring(line.indexOf(" ") + 1);
				}
				
				command = command.trim().toLowerCase();
				params = params.trim();
				
				final StatusCommand handler = _handlers.get(command);
				
				if (handler == null)
				{
					unknownCommand(command, line);
					continue;
				}
				
				try
				{
					handler.useCommand(command, params);
				}
				catch (RuntimeException e)
				{
					println(e);
					print(handler.listCommands());
					final String parameterUsage = handler.getParameterUsage();
					if (parameterUsage != null)
						print(" ").println(parameterUsage);
					println();
					
					_log.warn("", e);
				}
			}
		}
		catch (IOException e)
		{
			_log.warn("", e);
		}
		finally
		{
			_log.warn("Telnet: Connection from " + getSocket().getInetAddress().getHostAddress() + " closed.");
			
			_server.removeStatusThread(this);
			
			println("Bye-bye!");
			close();
		}
	}
	
	protected void unknownCommand(String command, String line)
	{
		println("No handler registered for '" + command + "'.");
	}
	
	public final void close()
	{
		IOUtils.closeQuietly(_in);
		IOUtils.closeQuietly(_out);
		
		try
		{
			_socket.close();
		}
		catch (IOException e)
		{
		}
	}
	
	private final class Quit extends StatusCommand
	{
		@Override
		protected void useCommand(String command, String params)
		{
			close();
		}
		
		private final String[] COMMANDS = { "quit", "exit" };
		
		@Override
		protected String[] getCommands()
		{
			return COMMANDS;
		}
		
		@Override
		protected String getDescription()
		{
			return "closes telnet session";
		}
	}
	
	private final class Help extends StatusCommand
	{
		@Override
		protected void useCommand(String command, String params)
		{
			final Map<String, StatusCommand> handlers = new TreeMap<String, StatusCommand>();
			
			int length = 20;
			for (StatusCommand handler : _handlers.getHandlers().values())
			{
				final String commands = handler.listCommands();
				
				handlers.put(commands, handler);
				
				length = Math.max(length, commands.length());
			}
			
			final String format = "%" + length + "s";
			
			println("The following list contains all available commands:");
			for (Map.Entry<String, StatusCommand> entry : handlers.entrySet())
			{
				print(String.format(format, entry.getKey())).print(" - ").println(entry.getValue().getDescription());
				
				final String parameterUsage = entry.getValue().getParameterUsage();
				if (parameterUsage != null)
					print(String.format(format, "")).print("   \t").print(entry.getKey()).print(" <")
							.print(parameterUsage).println(">");
			}
			println();
		}
		
		private final String[] COMMANDS = { "help" };
		
		@Override
		protected String[] getCommands()
		{
			return COMMANDS;
		}
		
		@Override
		protected String getDescription()
		{
			return "shows this help";
		}
	}
}

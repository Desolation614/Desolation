package dragonkk.rs2rsps.event.impl;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;

import dragonkk.rs2rsps.event.ThreadTask;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.net.codec.ConnectionHandler;
import dragonkk.rs2rsps.util.Logger;

public class ClientSessionOpenedTask implements ThreadTask {

	/**
	 * The task
	 */
	@Override
	public void execute() {
		Channel chan = event.getChannel();
		String ip = ""+chan.getRemoteAddress();
		ip = ip.replaceAll("/", "");
		String[] split = ip.split(":");
		ConnectionHandler connection = new ConnectionHandler(chan);
		context.setAttachment(connection);
		connection.lastResponce = System.currentTimeMillis();
		Logger.log("SessionOpened", "Session from "+split[0]+" recieved.");
	}
	
	/**
	 * Contexts for NETTY
	 */
	private ChannelHandlerContext context;
	private ChannelStateEvent event;
	
	
	/**
	 * Constructor
	 * @param handler Connection handler Instance
	 */
	public ClientSessionOpenedTask(ChannelHandlerContext context, ChannelStateEvent event) {
		this.context = context;
		this.event = event;
	}

}

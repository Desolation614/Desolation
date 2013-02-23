package dragonkk.rs2rsps.event.impl;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;

import dragonkk.rs2rsps.event.ThreadTask;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.net.codec.ConnectionHandler;
import dragonkk.rs2rsps.util.Logger;

public class ClientSessionClosedTask implements ThreadTask {

	/**
	 * Executes the task
	 */
	@Override
	public void execute() {
        ConnectionHandler p = (ConnectionHandler) context.getAttachment();
        if (p != null) {
    		String ip = ""+event.getChannel().getRemoteAddress();
    		ip = ip.replaceAll("/", "");
    		String[] split = ip.split(":");
            World.unRegisterConnection(p);
            context.setAttachment(null);
            Logger.log("SessionClosed", split[0]+"'s session was closed.");
        }
	}

	/**
	 * Contexts for NETTY
	 */
	private ChannelHandlerContext context;
	private ChannelStateEvent event;
	
	/**
	 * Constructor
	 * @param context Handler Context
	 * @param event Event
	 */
	public ClientSessionClosedTask(ChannelHandlerContext context, ChannelStateEvent event) {
		this.context = context;
		this.event = event;
	}
}

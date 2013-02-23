package dragonkk.rs2rsps.event.impl;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

import dragonkk.rs2rsps.event.ThreadTask;
import dragonkk.rs2rsps.io.InStream;
import dragonkk.rs2rsps.net.Packets;
import dragonkk.rs2rsps.net.codec.ConnectionHandler;
import dragonkk.rs2rsps.net.codec.ConnectionWorker;

public class ClientSessionMessageReceivedTask implements ThreadTask {

	/**
	 * Executes the network task
	 */
	@Override
	public void execute() {
		try {
			buffer.markReaderIndex();
			int avail = buffer.readableBytes();
			if (avail > 5000) {
				event.getChannel().close();
				return;
			}
			byte[] b = new byte[avail];
			buffer.readBytes(b);
			InStream in = new InStream(b);
			/**
			 * Checks to see if the message is OnDemand or a packet request
			 */
			handler.lastResponce = System.currentTimeMillis();
			if (handler.getPlayer() == null) {
				try {
					ConnectionWorker.run(handler, in);
				} catch (Exception ee) {
					//ee.printStackTrace();
				}
			} else {
				try {
					Packets.run(handler, in);
				} catch(Exception e) {
					
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	/**
	 * Instances used by NETTY
	 */
	private ChannelHandlerContext context;
	private ConnectionHandler handler;
	private ChannelBuffer buffer;
	private MessageEvent event;

	/**
	 * Constructor
	 * 
	 * @param context
	 *            ChannelContext - Used for attachments, msg sizes
	 * @param event
	 *            The message event
	 */
	public ClientSessionMessageReceivedTask(ChannelHandlerContext context,
			MessageEvent event) {
		this.context = context;
		this.handler = (ConnectionHandler) this.context.getAttachment();
		this.buffer = (ChannelBuffer) event.getMessage();
		this.event = event;
	}

}

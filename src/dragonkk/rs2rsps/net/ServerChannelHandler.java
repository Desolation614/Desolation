package dragonkk.rs2rsps.net;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.event.impl.*;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.Logger;

@SuppressWarnings("deprecation")
@ChannelPipelineCoverage("all")
public class ServerChannelHandler extends SimpleChannelHandler {

    public ServerBootstrap bootstrap;

    public ServerChannelHandler(int port) {
        bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.getPipeline().addLast("handler", this);
        bootstrap.setOption("backlog", 100);
        bootstrap.setOption("child.connectTimeoutMillis", 10000);
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive",false);
        bootstrap.bind(new InetSocketAddress(port));
        Logger.log(this, "Server binded to port - "+port);
    }
        
    

    @Override
    public final void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
    	Server.getEngine().pushTask(new ClientSessionOpenedTask(ctx, e));
    }

    @Override
    public final void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
    	Server.getEngine().pushTask(new ClientSessionClosedTask(ctx, e));
    }

    @Override
    public final void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent ee)
            throws Exception {
    	//ee.getCause().printStackTrace();
    }

    @Override
    public final void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        Server.getEngine().pushTask(new ClientSessionMessageReceivedTask(ctx ,e));
        
    }
    
	public static Player getPlayerByName(String name) {
		name = name.replaceAll(" ", "_");
		for (Player p : World.getPlayers()) {
			if (p.getUsername().equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null;
	}

}

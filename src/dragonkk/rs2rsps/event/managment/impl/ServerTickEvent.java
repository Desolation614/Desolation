package dragonkk.rs2rsps.event.managment.impl;

import java.util.ArrayList;
import java.util.List;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.event.ConsecutiveTask;
import dragonkk.rs2rsps.event.ParallelTask;
import dragonkk.rs2rsps.event.ThreadTask;
import dragonkk.rs2rsps.event.impl.game.*;
import dragonkk.rs2rsps.event.managment.Event;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.npc.Npc;
import dragonkk.rs2rsps.model.player.Player;

public class ServerTickEvent extends Event {

	public ServerTickEvent(long delay) {
		super(delay);
	}

	@Override
	public void execute() {
		try {
			GameLogicTaskManager.processTasks();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<ThreadTask> tickTasks = new ArrayList<ThreadTask>();
		List<ThreadTask> updateTasks = new ArrayList<ThreadTask>();
		List<ThreadTask> resetTasks = new ArrayList<ThreadTask>();
		for (Npc n : World.getNpcs()) {
			if (n == null)
				continue;
			tickTasks.add(new NPCTickTask(n));
			resetTasks.add(new NPCResetTask(n));
		}
		Player currentPlayer = null;
		for (int i = 0; i <= World.getPlayers().size(); i++) {
			currentPlayer = World.getPlayers().get(i);
			try {
				if (currentPlayer == null)
					continue;
				if (System.currentTimeMillis()
						- currentPlayer.getConnection().lastResponce >= 35000) {
					World.unRegisterConnection(currentPlayer.getConnection());
					currentPlayer.getConnection().getChannel().close();
					continue;
				}
				tickTasks.add(new PlayerTickTask(currentPlayer));
				updateTasks.add(new PlayerUpdateTask(currentPlayer));
				resetTasks.add(new PlayerResetTask(currentPlayer));
			} catch (Exception e) {
				continue;
			}
		}
		ThreadTask tickTask = new ConsecutiveTask(
				tickTasks.toArray(new ThreadTask[0]));
		ThreadTask updateTask = new ParallelTask(
				updateTasks.toArray(new ThreadTask[0]));
		ThreadTask resetTask = new ParallelTask(
				resetTasks.toArray(new ThreadTask[0]));
		Server.getEngine().pushTask(
				new ConsecutiveTask(tickTask, updateTask, resetTask));

	}
}

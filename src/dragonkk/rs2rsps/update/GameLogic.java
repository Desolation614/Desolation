package dragonkk.rs2rsps.update;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.npc.Npc;
import dragonkk.rs2rsps.model.player.Player;

public class GameLogic implements Runnable {

    private final static boolean[] playerUpdates = new boolean[2048];
    public long lastSave;
    public int saveTimer = 500;

    public void run() {
        while (true) {
            try {
                long startTick = System.currentTimeMillis();
                Server.getWorldExecutor().purge();
                Server.getEntityExecutor().purge();
                //World.getGlobaldropmanager().processGlobalItemTimers();
                long startLoopTime = System.currentTimeMillis();
                for (Npc npc : World.getNpcs()) {
                    if (npc == null) {
                        continue;
                    }
                    synchronized (npc) {
                        npc.getWalk().getNextEntityMovement();
                    }
                }
                try {
                    GameLogicTaskManager.processTasks();
                    for (Player player : World.getPlayers()) {
                        if (player == null) {
                            continue;
                        }
                        synchronized (player) {
                            if (player.isOnline()) {
                                player.getWalk().getNextEntityMovement();
                            }
                        }
                    }
                } catch (Exception e) {
                }

                try {
                    for (int index = 0; index < 2048; index++) {
                        Player player = World.getPlayers().get(index);
                        if (player == null) {
                            continue;
                        }
                        synchronized (player) {

                            if (player.isOnline()) {
                                try {
                                    player.getCombat().tick();
                                } catch (Exception e) {
                                }
                                player.tick();
                                player.processQueuedHits();
                                playerUpdates[index] = player.getMask().isUpdateNeeded();
                            }
                        }
                    }
                    for (Player player : World.getPlayers()) {
                        if (player == null) {
                            continue;
                        }
                        synchronized (player) {
                            if (player.isOnline()) {
                                player.getGpi().sendUpdate();
                                player.getGni().sendUpdate();
                            }
                        }
                    }
                } catch (Exception e) {
                }
                for (Npc npc : World.getNpcs()) {
                    if (npc == null) {
                        continue;
                    }
                    synchronized (npc) {
                        npc.getMask().reset();
                    }
                }
                try {
                    for (int index = 0; index < 2048; index++) {
                        if (!playerUpdates[index]) {
                            continue;
                        }
                        playerUpdates[index] = false;
                        Player player = World.getPlayers().get(index);
                        if (player == null) {
                            continue;
                        }
                        synchronized (player) {
                            if (player.isOnline()) {
                                player.getMask().reset();
                            }
                        }
                    }
                } catch (Exception e) {
                }
                long endTick = System.currentTimeMillis();
                Server.tickTimer = endTick - startTick;
                long sleepTime = startLoopTime + 600
                        - System.currentTimeMillis();
                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // System.out.println("sleepTime: "+sleepTime);
            } catch (Error e) {
                e.printStackTrace();
            }
        }
    }
    public long packetsBefore = 0;
    public long packetsAfter = 0;

    public static final boolean[] getPlayerUpdates() {
        return playerUpdates;
    }
}
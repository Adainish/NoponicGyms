package gg.oddysian.adenydd.noponicgyms.battles.handling;

import gg.oddysian.adenydd.noponicgyms.storage.registry.GymsRegistry;
import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;

public class Queue {

    private int position;
    private GymPlayer player;
    private GymsRegistry.Gym gym;
    private boolean isQueued;

    public Queue(GymPlayer player) {
        this.player = player;
    }

    public Queue(GymPlayer player, GymsRegistry.Gym gym) {
        this.player = player;
        this.gym = gym;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public GymPlayer getPlayer() {
        return player;
    }

    public void setPlayer(GymPlayer player) {
        this.player = player;
    }

    public GymsRegistry.Gym getGym() {
        return gym;
    }

    public void setGym(GymsRegistry.Gym gym) {
        this.gym = gym;
    }

    public boolean isQueued() {
        return isQueued;
    }

    public void setQueued(boolean queued) {
        isQueued = queued;
    }
}

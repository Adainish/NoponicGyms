package gg.oddysian.adenydd.noponicgyms.tasks;

import gg.oddysian.adenydd.noponicgyms.storage.registry.GymsRegistry;

import java.util.List;

public class UpdateGyms implements Runnable{


    private long lastUpdated;
    private List<GymsRegistry.Gym> gymList;

    UpdateGyms(List<GymsRegistry.Gym> gymList) {
        this.gymList = gymList;
    }

    @Override
    public void run() {
        long timePassed = System.currentTimeMillis() - lastUpdated;

    }
}

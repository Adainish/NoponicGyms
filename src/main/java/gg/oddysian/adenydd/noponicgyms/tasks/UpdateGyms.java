package gg.oddysian.adenydd.noponicgyms.tasks;

import gg.oddysian.adenydd.noponicgyms.storage.obj.GymObj;

import java.util.List;

public class UpdateGyms implements Runnable{


    private long lastUpdated;
    private List<GymObj.Gym> gymList;

    UpdateGyms(List<GymObj.Gym> gymList) {
        this.gymList = gymList;
    }

    @Override
    public void run() {
        long timePassed = System.currentTimeMillis() - lastUpdated;

    }
}

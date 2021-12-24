package gg.oddysian.adenydd.noponicgyms.battles.handling;

import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class GymQueue {
    public static HashMap<UUID, GymPlayer> gymQue = new HashMap<>();
}

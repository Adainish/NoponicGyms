package gg.oddysian.adenydd.noponicgyms.battles.handling;

import gg.oddysian.adenydd.noponicgyms.wrapper.GymPlayerWrapper;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class GymQueue {
    public HashMap<UUID, GymPlayerWrapper> gymQue = new HashMap<>();
}

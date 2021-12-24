package gg.oddysian.adenydd.noponicgyms.wrapper;

import gg.oddysian.adenydd.noponicgyms.storage.obj.GymPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class GymPlayerWrapper {
    public static HashMap<UUID, GymPlayer> gymPlayerHashMap = new HashMap<>();
}

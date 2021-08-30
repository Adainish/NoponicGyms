package gg.oddysian.adenydd.noponicgyms.tasks;

import com.cable.library.tasks.Task;
import com.cable.library.teleport.TeleportUtils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;

import java.util.function.Consumer;

public class TeleportTask implements Consumer<Task> {

    EntityPlayerMP target;
    int worldID;
    double x;
    double y;
    double z;

    public TeleportTask(EntityPlayerMP target, int worldID, double x, double y, double z) {
        this.target = target;
        this.worldID = worldID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void accept(Task task) {
        BlockPos pos = new BlockPos(x, y, z);
        TeleportUtils.teleport(target, worldID, pos);
    }
}

package gg.oddysian.adenydd.noponicgyms.zone;

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class Zone {

    public int dimension = 0;
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;

    public Zone(int dimension, double minX, double maxX, double minY, double maxY, double minZ, double maxZ) {
        this.dimension = dimension;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public boolean isWithin(int dimension, World world, Entity entity) {
        AxisAlignedBB isWithinAABB = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
        if (dimension != this.dimension)
            return false;
        List entities = world.getEntitiesWithinAABB(Entity.class, isWithinAABB);
        return entities.contains(entity);
    }

    public boolean isWithin(int dimension, World world, EntityPixelmon entity) {
        AxisAlignedBB isWithinAABB = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
        if (dimension != this.dimension)
            return false;
        List entities = world.getEntitiesWithinAABB(EntityPixelmon.class, isWithinAABB);
        return entities.contains(entity);
    }

}


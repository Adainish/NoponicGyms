package gg.oddysian.adenydd.noponicgyms.storage.capability.provider;

import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

public class SerializableCapabilityProvider <HANDLER> extends CapabilityProvider<HANDLER> implements INBTSerializable<NBTBase> {
    public SerializableCapabilityProvider(Capability<HANDLER> capability) {
        this(capability, capability.getDefaultInstance());
    }

    public SerializableCapabilityProvider(Capability<HANDLER> capability, @Nullable HANDLER instance) {
        super(capability, instance);
    }

    public NBTBase serializeNBT() {
        HANDLER instance = getInstance();
        if (instance == null)
            return null;
        return getCapability().writeNBT(instance, null);
    }

    public void deserializeNBT(NBTBase nbt) {
        HANDLER instance = getInstance();
        if (instance == null)
            return;
        getCapability().readNBT(instance, null, nbt);
    }
}

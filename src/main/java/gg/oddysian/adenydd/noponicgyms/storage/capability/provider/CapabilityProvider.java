package gg.oddysian.adenydd.noponicgyms.storage.capability.provider;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class CapabilityProvider  <HANDLER> implements ICapabilityProvider {
    protected final Capability<HANDLER> capability;

    protected final HANDLER instance;

    public CapabilityProvider(Capability<HANDLER> capability, @Nullable HANDLER instance) {
        this.capability = capability;
        this.instance = instance;
    }

    public final Capability<HANDLER> getCapability() {
        return this.capability;
    }

    @Nullable
    public final HANDLER getInstance() {
        return this.instance;
    }

    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return (capability == getCapability());
    }

    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == getCapability())
            return (T) getCapability().cast(getInstance());
        return null;
    }
}

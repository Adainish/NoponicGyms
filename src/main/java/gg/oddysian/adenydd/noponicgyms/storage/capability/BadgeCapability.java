package gg.oddysian.adenydd.noponicgyms.storage.capability;

import gg.oddysian.adenydd.noponicgyms.NoponicGyms;
import gg.oddysian.adenydd.noponicgyms.storage.capability.interfaces.Badges;
import gg.oddysian.adenydd.noponicgyms.storage.capability.interfaces.GymBadge;
import gg.oddysian.adenydd.noponicgyms.storage.capability.interfaces.IBadges;
import gg.oddysian.adenydd.noponicgyms.storage.capability.interfaces.IGymBadge;
import gg.oddysian.adenydd.noponicgyms.storage.capability.provider.SerializableCapabilityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BadgeCapability {
    @CapabilityInject(IBadges.class)
    public static final Capability<IBadges> I_GYM_BADGES_CAPABILITY = null;

    private static final ResourceLocation ID = new ResourceLocation(NoponicGyms.MOD_ID, "badgelist");

    public static void register() {
        CapabilityManager.INSTANCE.register(IBadges.class, new Capability.IStorage<IBadges>() {
            public void readNBT(Capability<IBadges> capability, IBadges instance, EnumFacing side, NBTBase nbt) {
                if (!(instance instanceof IBadges))
                    throw new IllegalArgumentException("Cannot deserialize to an instance that isn't the default implementation");
                instance.deserializeNBT((NBTTagCompound) nbt);
            }

            public NBTBase writeNBT(Capability<IBadges> capability, IBadges instance, EnumFacing side) {
                return instance.serializeNBT();
            }
        },  () -> null);
    }

    @Mod.EventBusSubscriber(modid = NoponicGyms.MOD_ID)
    private static class EventHandler {
        @SubscribeEvent
        public static void attachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof net.minecraft.entity.player.EntityPlayer) {
                IBadges badges = new Badges();
                event.addCapability(BadgeCapability.ID, new SerializableCapabilityProvider(BadgeCapability.I_GYM_BADGES_CAPABILITY, badges));
            }
        }
    }
}

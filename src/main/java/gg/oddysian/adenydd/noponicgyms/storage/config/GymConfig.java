package gg.oddysian.adenydd.noponicgyms.storage.config;

import lombok.SneakyThrows;

import java.util.Arrays;

public class GymConfig extends Configurable {
    private static GymConfig config;

    public static GymConfig getConfig() {
        if (config == null) {
            config = new GymConfig();
        }
        return config;
    }

    public void setup() {
        super.setup();
    }

    public void load() {
        super.load();
    }

    @SneakyThrows
    public void populate() {
        this.get().getNode("Modes", "Tier1", "Info").setValue(Arrays.asList("This is the first tier of the gym system", "&eColour Codes")).setComment("");
        this.get().getNode("Modes", "Tier1", "NPCOnly").setValue(false).setComment("");
        this.get().getNode("Modes", "Tier1", "EnableNPC").setValue(true).setComment("Whether NPCS should be enabled for this gym if no Player Leaders are online");

        this.get().getNode("Modes", "Tier2", "Info").setValue(Arrays.asList("&4For Advanced players")).setComment("The info displayed in the GUI when selecting a tier");
        this.get().getNode("Modes", "Tier2", "NPCOnly").setValue(false).setComment("Whether this Gym offers ONLY Npc support");
        this.get().getNode("Modes", "Tier2", "EnableNPC").setValue(false).setComment("Whether NPCS should be enabled for this gym if no Player Leaders are online");

        this.get().getNode("Modes", "NPCGym", "Info").setValue(Arrays.asList("&cI'm an NPC Only Gym Tier!")).setComment("The info displayed in the GUI when selecting a tier");
        this.get().getNode("Modes", "NPCGym", "NPCOnly").setValue(true).setComment("Whether this Gym offers ONLY Npc support");
        this.get().getNode("Modes", "NPCGym", "EnableNPC").setValue(true).setComment("Whether NPCS should be enabled for this gym if no Player Leaders are online");

        this.get().getNode("Gyms", "Example", "Tier").setValue("Tier1").setComment("What tier does this belong to?");
        this.get().getNode("Gyms", "Example", "Warp", "GymLeader", "WorldID").setValue(0);
        this.get().getNode("Gyms", "Example", "Warp", "GymLeader", "X").setValue(100.00);
        this.get().getNode("Gyms", "Example" ,"Warp", "GymLeader", "Y").setValue(80.00);
        this.get().getNode("Gyms", "Example", "Warp", "Challenger", "WorldID").setValue(0);
        this.get().getNode("Gyms", "Example", "Warp", "Challenger", "X").setValue(100.00);
        this.get().getNode("Gyms", "Example" ,"Warp", "Challenger", "Y").setValue(80.00);
        this.get().getNode("Gyms", "Example", "Warp", "Entry", "WorldID").setValue(0);
        this.get().getNode("Gyms", "Example", "Warp", "Entry", "X").setValue(100.00);
        this.get().getNode("Gyms", "Example" ,"Warp", "Entry", "Y").setValue(80.00);
        this.get().getNode("Gyms", "Example" ,"Warp", "Z").setValue(100.00);
        this.get().getNode("Gyms", "Example" ,"Rewards").setValue(Arrays.asList("", ""));
        this.get().getNode("Gyms", "Example", "Fee", "EnableFee").setValue(true);
        this.get().getNode("Gyms", "Example", "Fee", "Fee").setValue(100.00);
        this.get().getNode("Gyms", "Example", "Fee", "PayLeader").setValue(true).setComment("Should the leader taking the battle be paid the fee if enabled?");
        this.get().getNode("Gyms", "Example", "LeaderMessage").setValue("&c%leader% of the %gym% has joined the server!");
        this.get().getNode("Gyms", "Example", "Permission").setValue("gyms.examplegym.leader");
        this.get().getNode("Gyms", "Example", "LevelCap").setValue(10);
        this.get().getNode("Gyms", "Example", "UI", "Display").setValue("&b&lExample Gym");
        this.get().getNode("Gyms", "Example", "UI", "Weight").setValue(1).setComment("The weight of this gym, the higher the weight the later it'll appear in mod selection such as the GUI");
        this.get().getNode("Gyms", "Example", "UI", "Lore").setValue(Arrays.asList("&cThis is the %gym% Gym, the gym is %open%", "The following leaders are online: &e%availableleaders%", "&bGym Level: &f%gymlevel%"));
        this.get().getNode("Gyms", "Example", "Badge").setValue("minecraft:paper");
        this.get().getNode("Gyms", "Example", "Mode").setValue("tier1").setComment("Set the mode of this gym as defined in the modes section");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "PokemonName").setValue("Vulpix");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "HeldItem").setValue("pixelmon:leftovers");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Level").setValue(10).setComment("The Pokemons level if raise to cap was not enabled");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Form").setValue(0).setComment("Decide the form for this pokemon, leave blank if none are to be set");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "NickName").setValue("").setComment("Set the Pokemons Nick Name in Battle!");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Shiny").setValue(false).setComment("Is this Pokemon Shiny?");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Texture").setValue("").setComment("Apply a pokemon Texture if these are installed");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Stats", "Nature").setValue("Timid").setComment("What Nature should be applied to this Pokemon");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Stats", "Ability").setValue("Drought").setComment("What Ability should this Pokemon have?");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Dynamax").setValue(1).setComment("Set the dynamax level for this Pokemon");
        //EVS
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Stats", "EVS", "HP").setValue(252);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Stats", "EVS", "ATK").setValue(252);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Stats", "EVS", "SPA").setValue(252);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Stats", "EVS", "DEF").setValue(252);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Stats", "EVS", "SPDEF").setValue(252);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Stats", "EVS", "SPD").setValue(252);
        //IVS
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Stats", "IVS", "HP").setValue(31);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Stats", "IVS", "ATK").setValue(31);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Stats", "IVS", "SPA").setValue(31);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Stats", "IVS", "DEF").setValue(31);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Stats", "IVS", "SPDEF").setValue(31);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "Stats", "IVS", "SPD").setValue(31);

        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "MoveSet").setValue(Arrays.asList("Quick Attack", "Hidden Power", "Shadow Ball", "Incinerate"));
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "SpecFlags").setValue(Arrays.asList("unbreedable", "untradeable"));


        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "PokemonName").setValue("Ninetales");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "HeldItem").setValue("pixelmon:leftovers");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Form").setValue(0).setComment("Decide the form for this pokemon, leave blank if none are to be set");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "NickName").setValue("").setComment("Set the Pokemons Nick Name in Battle!");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Shiny").setValue(false).setComment("Is this Pokemon Shiny?");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Texture").setValue("").setComment("Apply a pokemon Texture if these are installed");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Stats", "Nature").setValue("Timid").setComment("What Nature should be applied to this Pokemon");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Stats", "Ability").setValue("Flash Fire").setComment("What Ability should this Pokemon have?");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Dynamax").setValue(1).setComment("Set the dynamax level for this Pokemon");
        //EVS
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Stats", "EVS", "HP").setValue(252);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Stats", "EVS", "ATK").setValue(252);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Stats", "EVS", "SPA").setValue(252);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Stats", "EVS", "DEF").setValue(252);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Stats", "EVS", "SPDEF").setValue(252);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Stats", "EVS", "SPD").setValue(252);
        //IVS
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Stats", "IVS", "HP").setValue(31);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Stats", "IVS", "ATK").setValue(31);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Stats", "IVS", "SPA").setValue(31);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Stats", "IVS", "DEF").setValue(31);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Stats", "IVS", "SPDEF").setValue(31);
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "Stats", "IVS", "SPD").setValue(31);

        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "MoveSet").setValue(Arrays.asList("Quick Attack", "Hidden Power", "Shadow Ball", "Incinerate"));
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "SpecFlags").setValue(Arrays.asList("unbreedable", "untradeable"));
    }

    public String getConfigName() {
        return "GymsRegistry.conf";
    }

    public GymConfig() {}
}

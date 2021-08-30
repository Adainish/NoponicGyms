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
        this.get().getNode("Modes", "Tiers").setValue(Arrays.asList("Tier1", "Tier2", "NPCGym"));
        this.get().getNode("Gyms", "Example", "Tier").setValue("Tier1");
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
        this.get().getNode("Gyms", "Example", "Fee", "EnableFee").setValue(true);
        this.get().getNode("Gyms", "Example", "Fee", "Fee").setValue(100.00);
        this.get().getNode("Gyms", "Example", "Fee", "PayLeader").setValue(true).setComment("Should the leader taking the battle be paid the fee if enabled?");
        this.get().getNode("Gyms", "Example", "LeaderMessage").setValue("&c%leader% of the %gym% has joined the server!");
        this.get().getNode("Gyms", "Example", "Permission").setValue("gyms.examplegym.leader");
        this.get().getNode("Gyms", "Example", "LevelCap").setValue(10);
        this.get().getNode("Gyms", "Example", "UI", "Display").setValue("&b&lExample Gym");
        this.get().getNode("Gyms", "Example", "UI", "Lore").setValue(Arrays.asList("&cThis is the %gym% Gym", "The following leaders are online: %availableleaders%", "Gym Level: %gymlevel%"));
        this.get().getNode("Gyms", "Example", "Badge").setValue("minecraft:paper");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "PokemonName").setValue("Vulpix");
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

        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "MoveSet", "1").setValue("Quick Attack");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "MoveSet", "2").setValue("Shadow Ball");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "MoveSet", "3").setValue("Incinerate");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Vulpix", "MoveSet", "4").setValue("Hidden Power");


        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "PokemonName").setValue("Ninetales");
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

        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "MoveSet", "1").setValue("Quick Attack");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "MoveSet", "2").setValue("Shadow Ball");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "MoveSet", "3").setValue("Incinerate");
        this.get().getNode("Gyms", "Example", "GymPokemon", "Ninetales", "MoveSet", "4").setValue("Hidden Power");

    }

    public String getConfigName() {
        return "Gyms.conf";
    }

    public GymConfig() {}
}

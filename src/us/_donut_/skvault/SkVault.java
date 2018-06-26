package us._donut_.skvault;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SkVault extends JavaPlugin {

    @Override
    public void onEnable() {
        SkriptAddon addon = Skript.registerAddon(this);
        try {
            addon.loadClasses("us._donut_.skvault", "effects", "events");
        } catch (IOException e) {
            e.printStackTrace();
        }
        getServer().getServicesManager().register(Economy.class, new CustomEconomy(new ConfigManager(this)), getServer().getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
        getLogger().info("Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled!");
    }
}

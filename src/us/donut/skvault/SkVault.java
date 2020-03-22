package us.donut.skvault;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SkVault extends JavaPlugin {

    @Override
    public void onEnable() {
        SkriptAddon addon = Skript.registerAddon(this);
        try {
            addon.loadClasses("us.donut.skvault", "effects", "events", "expressions");
            Plugin vault = getServer().getPluginManager().getPlugin("Vault");
            Bukkit.getServicesManager().register(Economy.class, new CustomEconomy(), vault, ServicePriority.Normal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

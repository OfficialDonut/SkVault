package us._donut_.skvault;

import org.bukkit.configuration.file.FileConfiguration;

class ConfigManager {

    private SkVault plugin;
    private FileConfiguration config;

    ConfigManager(SkVault plugin) {
        this.plugin = plugin;
        config = plugin.getConfig();
        registerDefaults();
    }

    private void registerDefaults() {
        config.options().header("Set automatic linking to true for simple automatic handling of Vault requests to your economy\nSet automatic linking to false if you want to be able to handle what happens when Vault requests something from your economy\nSkript uses the currency name and format while parsing your script so they must be predefined here");
        config.addDefault("currency_name_singular", "dollar");
        config.addDefault("currency_name_plural", "dollars");
        config.addDefault("currency_format", "$%number%");
        config.addDefault("enable_automatic_linking", true);
        config.addDefault("automatic_linking.balance_variable", "{balances::%player%}");
        config.addDefault("automatic_linking.economy_name", "Cool Economy");
        config.addDefault("automatic_linking.currency_decimal_places", 2);
        config.options().copyDefaults(true);
        plugin.saveConfig();
        plugin.reloadConfig();
    }

    String getCurrencyNameSingular() {
        return config.getString("currency_name_singular");
    }

    String getCurrencyNamePlural() {
        return config.getString("currency_name_plural");
    }

    String getCurrencyFormat() {
        return config.getString("currency_format");
    }

    boolean isLinkingAutomatic() {
        return config.getBoolean("enable_automatic_linking");
    }

    String getLinkVariable() {
        return config.getString("automatic_linking.balance_variable").replace("{", "").replace("}", "");
    }

    String getEconomyName() {
        return config.getString("automatic_linking.economy_name");
    }

    int getCurrencyDecimals() {
        return config.getInt("automatic_linking.currency_decimal_places");
    }
}

package us._donut_.skvault;

import ch.njol.skript.variables.Variables;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import us._donut_.skvault.events.requests.*;

import java.util.ArrayList;
import java.util.List;

public class CustomEconomy extends AbstractEconomy {

    public static boolean automaticLinking = false;
    public static String linkVariable;
    public static String economyName = "";
    public static String singularCurrencyName = "dollar";
    public static String pluralCurrencyName = "dollars";
    public static String currencyFormat = "$%number%";
    public static int currencyDecimals = 2;

    private Object getLinkVariableValue(OfflinePlayer player) {
        String variableName = linkVariable
                .replace("%player%", player.getName())
                .replace("%the player%", player.getName())
                .replace("%player's uuid%", player.getUniqueId().toString())
                .replace("%uuid of player%", player.getUniqueId().toString())
                .replace("the UUID of the player", player.getUniqueId().toString());
        return Variables.getVariable(variableName, null, false);
    }

    private void setLinkVariableValue(OfflinePlayer player, Object value) {
        String variableName = linkVariable
                .replace("%player%", player.getName())
                .replace("%the player%", player.getName())
                .replace("%player's uuid%", player.getUniqueId().toString())
                .replace("%uuid of player%", player.getUniqueId().toString())
                .replace("the UUID of the player", player.getUniqueId().toString());
        Variables.setVariable(variableName, value, null, false);
    }

    private double getDoubleValue(Object variableValue) {
        try {
            return ((Number) variableValue).doubleValue();
        } catch (ClassCastException | NullPointerException e) {
            return 0;
        }
    }

    //General Vault methods
    @Override
    public boolean isEnabled() {
        EnabledRequestEvent event = new EnabledRequestEvent();
        Bukkit.getServer().getPluginManager().callEvent(event);
        return (automaticLinking && !event.isImplemented()) || event.getBooleanValue();
    }

    @Override
    public String getName() {
        EconomyNameRequestEvent event = new EconomyNameRequestEvent();
        Bukkit.getServer().getPluginManager().callEvent(event);
        return (!automaticLinking || event.isImplemented()) ? event.getStringValue() : economyName;
    }

    @Override
    public int fractionalDigits() {
        CurrencyDecimalsRequestEvent event = new CurrencyDecimalsRequestEvent();
        Bukkit.getServer().getPluginManager().callEvent(event);
        return (!automaticLinking || event.isImplemented()) ? event.getIntValue() : currencyDecimals;
    }

    @Override
    public String format(double amount) {
        FormatCurrencyRequestEvent event = new FormatCurrencyRequestEvent(amount);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return (!automaticLinking || event.isImplemented()) ? event.getStringValue() : currencyFormat.replace("%number%", String.valueOf(amount));
    }

    @Override
    public String currencyNameSingular() {
        SingularCurrencyNameRequestEvent event = new SingularCurrencyNameRequestEvent();
        Bukkit.getServer().getPluginManager().callEvent(event);
        return (!automaticLinking || event.isImplemented()) ? event.getStringValue() : singularCurrencyName;
    }

    @Override
    public String currencyNamePlural() {
        PluralCurrencyNameRequestEvent event = new PluralCurrencyNameRequestEvent();
        Bukkit.getServer().getPluginManager().callEvent(event);
        return (!automaticLinking || event.isImplemented()) ? event.getStringValue() : pluralCurrencyName;
    }

    //Vault player methods
    @Override
    public double getBalance(OfflinePlayer player) {
        BalanceRequestEvent event = new BalanceRequestEvent(player);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return (!automaticLinking || event.isImplemented()) ? event.getDoubleValue() : getDoubleValue(getLinkVariableValue(player));
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        DepositRequestEvent event = new DepositRequestEvent(player, amount);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (!automaticLinking || event.isImplemented())
            return event.getEconomyResponse();
        double newBalance = getDoubleValue(getLinkVariableValue(player)) + amount;
        setLinkVariableValue(player, newBalance);
        return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        WithdrawRequestEvent event = new WithdrawRequestEvent(player, amount);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (!automaticLinking || event.isImplemented())
            return event.getEconomyResponse();
        double newBalance = getDoubleValue(getLinkVariableValue(player)) - amount;
        setLinkVariableValue(player, newBalance);
        return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        CheckBalanceRequestEvent event = new CheckBalanceRequestEvent(player, amount);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return (!automaticLinking || event.isImplemented()) ? event.getBooleanValue() : getDoubleValue(getLinkVariableValue(player)) > amount;
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        HasAccountRequestEvent event = new HasAccountRequestEvent(player);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return (automaticLinking && !event.isImplemented()) || event.getBooleanValue();
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        CreateAccountRequestEvent event = new CreateAccountRequestEvent(player);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return (automaticLinking && !event.isImplemented()) || event.getBooleanValue();
    }

    //Pointing Vault methods to other methods above
    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player);
    }

    @Override
    public double getBalance(String playerName) {
        return getBalance(Bukkit.getOfflinePlayer(playerName));
    }

    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return depositPlayer(Bukkit.getOfflinePlayer(playerName), amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        return withdrawPlayer(Bukkit.getOfflinePlayer(playerName), amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return has(player, amount);
    }

    @Override
    public boolean has(String playerName, double amount) {
        return has(Bukkit.getOfflinePlayer(playerName), amount);
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }

    @Override
    public boolean hasAccount(String playerName) {
        return hasAccount(Bukkit.getOfflinePlayer(playerName));
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return createPlayerAccount(player);
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return createPlayerAccount(Bukkit.getOfflinePlayer(playerName));
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName);
    }

    //Vault bank methods. If someone wants them I'll add them later
    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Method not implemented");
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Method not implemented");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Method not implemented");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Method not implemented");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Method not implemented");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Method not implemented");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Method not implemented");
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Method not implemented");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Method not implemented");
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Method not implemented");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Method not implemented");
    }

    @Override
    public List<String> getBanks() {
        return new ArrayList<>();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }
}

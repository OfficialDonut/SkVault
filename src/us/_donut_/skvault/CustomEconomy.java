package us._donut_.skvault;

import ch.njol.skript.variables.Variables;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import us._donut_.skvault.events.requests.*;

import java.util.List;

public class CustomEconomy extends AbstractEconomy {

    private ConfigManager configManager;
    private String linkVariable;

    CustomEconomy(ConfigManager configManager) {
        this.configManager = configManager;
        linkVariable = configManager.getLinkVariable();
    }

    private Object getLinkVariableValue(OfflinePlayer player) {
        String variableName = linkVariable.replace("%player%", player.getName()).replace("%player's uuid%", player.getUniqueId().toString()).replace("%uuid of player%", player.getUniqueId().toString());
        return Variables.getVariable(variableName, null, false);
    }

    private void setLinkVariableValue(OfflinePlayer player, Object value) {
        String variableName = linkVariable.replace("%player%", player.getName()).replace("%player's uuid%", player.getUniqueId().toString()).replace("%uuid of player%", player.getUniqueId().toString());
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
        if (configManager.isLinkingAutomatic()) {
            return true;
        } else {
            EnabledRequestEvent event = new EnabledRequestEvent();
            Bukkit.getServer().getPluginManager().callEvent(event);
            return event.getBooleanValue();
        }
    }

    @Override
    public String getName() {
        if (configManager.isLinkingAutomatic()) {
            return configManager.getEconomyName();
        } else {
            EconomyNameRequestEvent event = new EconomyNameRequestEvent();
            Bukkit.getServer().getPluginManager().callEvent(event);
            return event.getStringValue();
        }
    }

    @Override
    public int fractionalDigits() {
        if (configManager.isLinkingAutomatic()) {
            return configManager.getCurrencyDecimals();
        } else {
            CurrencyDecimalsRequestEvent event = new CurrencyDecimalsRequestEvent();
            Bukkit.getServer().getPluginManager().callEvent(event);
            return event.getIntValue();
        }
    }

    @Override
    public String format(double amount) {
        return configManager.getCurrencyFormat().replace("%number%", String.valueOf(amount));
    }

    @Override
    public String currencyNameSingular() {
        return configManager.getCurrencyNameSingular();
    }

    @Override
    public String currencyNamePlural() {
        return configManager.getCurrencyNamePlural();
    }

    //Vault player balance methods
    @Override
    public double getBalance(OfflinePlayer player) {
        if (configManager.isLinkingAutomatic()) {
            return getDoubleValue(getLinkVariableValue(player));
        } else {
            BalanceRequestEvent event = new BalanceRequestEvent(player);
            Bukkit.getServer().getPluginManager().callEvent(event);
            return event.getDoubleValue();
        }
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        if (configManager.isLinkingAutomatic()) {
            double newBalance = getDoubleValue(getLinkVariableValue(player)) + amount;
            setLinkVariableValue(player, newBalance);
            return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
        } else {
            DepositRequestEvent event = new DepositRequestEvent(player, amount);
            Bukkit.getServer().getPluginManager().callEvent(event);
            return event.getEconomyResponse();
        }
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        if (configManager.isLinkingAutomatic()) {
            double newBalance = getDoubleValue(getLinkVariableValue(player)) - amount;
            setLinkVariableValue(player, newBalance);
            return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
        } else {
            WithdrawRequestEvent event = new WithdrawRequestEvent(player, amount);
            Bukkit.getServer().getPluginManager().callEvent(event);
            return event.getEconomyResponse();
        }
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        if (configManager.isLinkingAutomatic()) {
            return getDoubleValue(getLinkVariableValue(player)) > amount;
        } else {
            CheckBalanceRequestEvent event = new CheckBalanceRequestEvent(player);
            Bukkit.getServer().getPluginManager().callEvent(event);
            return event.getBooleanValue();
        }
    }

    //Vault player balance methods that point to other methods
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
        return has(Bukkit.getOfflinePlayer(playerName), 1);
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    //Vault methods skript economies probably won't need. If someone wants them I'll add them later
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
        throw new UnsupportedOperationException("BanksRequest");
    }

    @Override
    public boolean hasBankSupport() {
        throw new UnsupportedOperationException("BankSupportRequest");
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        throw new UnsupportedOperationException("CreateAccountRequest");
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        throw new UnsupportedOperationException("CreateAccountRequest");
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        throw new UnsupportedOperationException("CreateAccountRequest");
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        throw new UnsupportedOperationException("CreateAccountRequest");
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        throw new UnsupportedOperationException("HasAccountRequest");
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        throw new UnsupportedOperationException("HasAccountRequest");
    }

    @Override
    public boolean hasAccount(String playerName) {
        throw new UnsupportedOperationException("HasAccountRequest");
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        throw new UnsupportedOperationException("HasAccountRequest");
    }
}

package us.donut.skvault;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.util.StringMode;
import ch.njol.skript.variables.Variables;
import com.google.common.collect.Sets;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import us.donut.skvault.events.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

@SuppressWarnings("deprecation")
public class CustomEconomy extends AbstractEconomy {

    private EconomyProperty<String> economyName = new EconomyProperty<>("economy-name");
    private EconomyProperty<String> currencyName = new EconomyProperty<>("currency-name");
    private EconomyProperty<String> currencyNamePlural = new EconomyProperty<>("currency-name-plural");
    private EconomyProperty<String> currencyFormat = new EconomyProperty<>("currency-format");
    private EconomyProperty<Integer> decimalPlaces = new EconomyProperty<>("decimal-places", Integer::valueOf);
    private EconomyProperty<String> autoLinkVar = new EconomyProperty<>("auto-link-var", s -> s.substring(1, s.length() - 1));
    private boolean isAutoLink;

    public CustomEconomy() {
        File scriptsDir = new File(Skript.getInstance().getDataFolder(), "scripts");
        Set<EconomyProperty<?>> unsetProperties = Sets.newHashSet(economyName, currencyName, currencyNamePlural, currencyFormat, decimalPlaces, autoLinkVar);
        try {
            Files.walk(scriptsDir.toPath()).forEach(path -> {
                if (Files.isRegularFile(path) && path.toString().endsWith(".sk") && !path.toString().startsWith("-")) {
                    try {
                        for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                            unsetProperties.removeIf(property -> property.parse(line));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        isAutoLink = !unsetProperties.contains(autoLinkVar);
    }

    private double getLinkVarValue(Event e) {
        ScriptLoader.setCurrentEvent(e.getEventName(), e.getClass());
        String varName = VariableString.newInstance(autoLinkVar.getValue(), StringMode.VARIABLE_NAME).toString(e);
        Object value = Variables.getVariable(varName, e, false);
        return value instanceof Number ? ((Number) value).doubleValue() : 0;
    }

    private void setLinkVarValue(Event e, double value) {
        ScriptLoader.setCurrentEvent(e.getEventName(), e.getClass());
        String varName = VariableString.newInstance(autoLinkVar.getValue(), StringMode.VARIABLE_NAME).toString(e);
        Variables.setVariable(varName, value, e, false);
    }

    // Economy methods

    @Override
    public String currencyNamePlural() {
        return currencyNamePlural.getValue();
    }

    @Override
    public String currencyNameSingular() {
        return currencyName.getValue();
    }

    @Override
    public String format(double amount) {
        return currencyFormat.getValue().replace("%number%", String.valueOf(amount));
    }

    @Override
    public int fractionalDigits() {
        return decimalPlaces.getValue();
    }

    @Override
    public String getName() {
        return economyName.getValue();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Player methods

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        CreateAccountRequestEvent event = new CreateAccountRequestEvent(player);
        Bukkit.getPluginManager().callEvent(event);
        return !isAutoLink || event.isImplemented() ? event.getReturnValue() : true;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        DepositRequestEvent event = new DepositRequestEvent(player, amount);
        Bukkit.getPluginManager().callEvent(event);
        if (!isAutoLink || event.isImplemented()) {
            return event.getReturnValue();
        }
        double newBalance = getLinkVarValue(event) + amount;
        setLinkVarValue(event, newBalance);
        return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        BalanceRequestEvent event = new BalanceRequestEvent(player);
        Bukkit.getPluginManager().callEvent(event);
        return !isAutoLink || event.isImplemented() ? event.getReturnValue() : getLinkVarValue(event);
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        CheckBalanceRequestEvent event = new CheckBalanceRequestEvent(player, amount);
        Bukkit.getPluginManager().callEvent(event);
        return !isAutoLink || event.isImplemented() ? event.getReturnValue() : getLinkVarValue(event) >= amount;
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        HasAccountRequestEvent event = new HasAccountRequestEvent(player);
        Bukkit.getPluginManager().callEvent(event);
        return !isAutoLink || event.isImplemented() ? event.getReturnValue() : true;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        WithdrawRequestEvent event = new WithdrawRequestEvent(player, amount);
        Bukkit.getPluginManager().callEvent(event);
        if (!isAutoLink || event.isImplemented()) {
            return event.getReturnValue();
        }
        double newBalance = getLinkVarValue(event) - amount;
        setLinkVarValue(event, newBalance);
        return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, null);
    }

    // Overloaded player methods

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

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return depositPlayer(Bukkit.getOfflinePlayer(playerName), amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player);
    }

    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    @Override
    public double getBalance(String playerName) {
        return getBalance(Bukkit.getOfflinePlayer(playerName));
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

    // Bank methods

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank not implemented");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank not implemented");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank not implemented");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank not implemented");
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank not implemented");
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank not implemented");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank not implemented");
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank not implemented");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank not implemented");
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank not implemented");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank not implemented");
    }

    @Override
    public List<String> getBanks() {
        throw new UnsupportedOperationException("Bank not implemented");
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }
}

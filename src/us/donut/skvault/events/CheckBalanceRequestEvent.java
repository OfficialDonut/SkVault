package us.donut.skvault.events;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.OfflinePlayer;

public class CheckBalanceRequestEvent extends us.donut.skvault.events.VaultRequestEvent<Boolean> {

    static {
        Skript.registerEvent("Vault Request - Check Balance (Return: boolean)", SkVaultEvent.class, CheckBalanceRequestEvent.class,
                "[vault] check [player] bal[ance] request")
                .description("Called when Vault requests to know if a player's balance is greater than a certain amount")
                .requiredPlugins("Vault")
                .since("2.0")
                .examples("on vault check player balance request:\n    if {balances::%event-offlineplayer's uuid%} is greater than event-number:\n        return true\n    else:\n        return false");

        EventValues.registerEventValue(CheckBalanceRequestEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, CheckBalanceRequestEvent>() {
            public OfflinePlayer get(CheckBalanceRequestEvent e) {
                return e.getPlayer();
            }
        }, 0);

        EventValues.registerEventValue(CheckBalanceRequestEvent.class, Number.class, new Getter<Number, CheckBalanceRequestEvent>() {
            public Number get(CheckBalanceRequestEvent e) {
                return e.getAmount();
            }
        }, 0);
    }

    private OfflinePlayer player;
    private double amount;

    public CheckBalanceRequestEvent(OfflinePlayer player, double amount) {
        this.player = player;
        this.amount = amount;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public double getAmount() {
        return amount;
    }
}

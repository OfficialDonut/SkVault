package us.donut.skvault.events;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

public class DepositRequestEvent extends VaultRequestEvent<EconomyResponse> {

    static {
        Skript.registerEvent("Vault Request - Balance Deposit (Return: economy response)", SkVaultEvent.class, DepositRequestEvent.class,
                "[vault] [player] [bal[ance]] deposit request")
                .description("Called when Vault requests to deposit currency into a player's balance")
                .since("2.0")
                .examples("on vault deposit request:\n    add event-number to {balances::%event-offlineplayer's uuid%}\n    return economy response with amount modified event-number, new balance {balances::%event-offlineplayer's uuid%}, response type success, and error message \"none\"");

        EventValues.registerEventValue(DepositRequestEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, DepositRequestEvent>() {
            public OfflinePlayer get(DepositRequestEvent e) {
                return e.getPlayer();
            }
        }, 0);

        EventValues.registerEventValue(DepositRequestEvent.class, Number.class, new Getter<Number, DepositRequestEvent>() {
            public Number get(DepositRequestEvent e) {
                return e.getAmount();
            }
        }, 0);
    }

    private OfflinePlayer player;
    private double amount;

    public DepositRequestEvent(OfflinePlayer player, double amount) {
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

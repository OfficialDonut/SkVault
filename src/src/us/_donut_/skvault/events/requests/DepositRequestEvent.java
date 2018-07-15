package us._donut_.skvault.events.requests;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.OfflinePlayer;
import us._donut_.skvault.events.EcoResponseRequestEvent;
import us._donut_.skvault.events.SkVaultEvent;

public class DepositRequestEvent extends EcoResponseRequestEvent {

    static {
        Skript.registerEvent("Vault Request - Balance Deposit (Return: economy response)", SkVaultEvent.class, DepositRequestEvent.class,
                "[vault] [player] [bal[ance]] deposit request")
                .description("Called when Vault requests to deposit into a player's balance")
                .requiredPlugins("Vault")
                .since("1.0")
                .examples("on vault deposit request:\n    add event-number to {balances::%player%}\n    return economy response with amount modified event-number, new balance {balances::%player%}, response type success, and error message \"none\"");

        EventValues.registerEventValue(DepositRequestEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, DepositRequestEvent>() {
            public OfflinePlayer get(DepositRequestEvent e) {
                return e.getRequestedPlayer();
            }
        }, 0);
        EventValues.registerEventValue(DepositRequestEvent.class, Number.class, new Getter<Number, DepositRequestEvent>() {
            public Number get(DepositRequestEvent e) {
                return e.getRequestedDepositAmount();
            }
        }, 0);
    }

    private OfflinePlayer requestedPlayer;
    private double requestedDepositAmount;

    public DepositRequestEvent(OfflinePlayer requestedPlayer, double amount) {
        this.requestedPlayer = requestedPlayer;
        this.requestedDepositAmount = amount;
    }

    public OfflinePlayer getRequestedPlayer() {
        return requestedPlayer;
    }

    public double getRequestedDepositAmount() {
        return requestedDepositAmount;
    }
}

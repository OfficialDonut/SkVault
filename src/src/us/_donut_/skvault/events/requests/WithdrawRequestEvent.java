package us._donut_.skvault.events.requests;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.OfflinePlayer;
import us._donut_.skvault.events.EcoResponseRequestEvent;
import us._donut_.skvault.events.SkVaultEvent;

public class WithdrawRequestEvent extends EcoResponseRequestEvent {

    static {
        Skript.registerEvent("Vault Request - Balance Withdraw (Return: economy response)", SkVaultEvent.class, WithdrawRequestEvent.class,
                "[vault] [player] [bal[ance]] withdraw request")
                .description("Called when Vault requests to withdraw from a player's balance")
                .requiredPlugins("Vault")
                .since("1.0")
                .examples("on vault withdraw request:\n    remove event-number from {balances::%player%}\n    return economy response with amount modified event-number, new balance {balances::%player%}, response type success, and error message \"none\"");

        EventValues.registerEventValue(WithdrawRequestEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, WithdrawRequestEvent>() {
            public OfflinePlayer get(WithdrawRequestEvent e) {
                return e.getRequestedPlayer();
            }
        }, 0);
        EventValues.registerEventValue(WithdrawRequestEvent.class, Number.class, new Getter<Number, WithdrawRequestEvent>() {
            public Number get(WithdrawRequestEvent e) {
                return e.getRequestedWithdrawAmount();
            }
        }, 0);
    }

    private OfflinePlayer requestedPlayer;
    private double requestedWithdrawAmount;

    public WithdrawRequestEvent(OfflinePlayer requestedPlayer, double amount) {
        this.requestedPlayer = requestedPlayer;
        this.requestedWithdrawAmount = amount;
    }

    public OfflinePlayer getRequestedPlayer() {
        return requestedPlayer;
    }

    public double getRequestedWithdrawAmount() {
        return requestedWithdrawAmount;
    }
}

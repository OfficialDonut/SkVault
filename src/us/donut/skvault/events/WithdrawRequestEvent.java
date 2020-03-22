package us.donut.skvault.events;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

public class WithdrawRequestEvent extends VaultRequestEvent<EconomyResponse> {

    static {
        Skript.registerEvent("Vault Request - Balance Withdraw (Return: economy response)", SkVaultEvent.class, WithdrawRequestEvent.class,
                "[vault] [player] [bal[ance]] withdraw request")
                .description("Called when Vault requests to withdraw currency from a player's balance")
                .requiredPlugins("Vault")
                .since("2.0")
                .examples("on vault withdraw request:\n    remove event-number from {balances::%event-offlineplayer's uuid%}\n    return economy response with amount modified event-number, new balance {balances::%event-offlineplayer's uuid%}, response type success, and error message \"none\"");

        EventValues.registerEventValue(WithdrawRequestEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, WithdrawRequestEvent>() {
            public OfflinePlayer get(WithdrawRequestEvent e) {
                return e.getPlayer();
            }
        }, 0);

        EventValues.registerEventValue(WithdrawRequestEvent.class, Number.class, new Getter<Number, WithdrawRequestEvent>() {
            public Number get(WithdrawRequestEvent e) {
                return e.getAmount();
            }
        }, 0);
    }

    private OfflinePlayer player;
    private double amount;

    public WithdrawRequestEvent(OfflinePlayer player, double amount) {
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

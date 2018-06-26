package us._donut_.skvault.events.requests;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.OfflinePlayer;
import us._donut_.skvault.events.SkVaultEvent;
import us._donut_.skvault.events.ValueRequestEvent;

public class BalanceRequestEvent extends ValueRequestEvent {

    static {
        Skript.registerEvent("Vault Request - Player Balance (Return: number)", SkVaultEvent.class, BalanceRequestEvent.class,
                "[vault] [player] bal[ance] [value] request")
                .description("Called when Vault requests a player's balance")
                .requiredPlugins("Vault")
                .since("1.0")
                .examples("on vault player balance request:\n    return {balances::%player%}");

        EventValues.registerEventValue(BalanceRequestEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, BalanceRequestEvent>() {
            public OfflinePlayer get(BalanceRequestEvent e) {
                return e.getRequestedPlayer();
            }
        }, 0);
    }

    private OfflinePlayer requestedPlayer;

    public BalanceRequestEvent(OfflinePlayer requestedPlayer) {
        this.requestedPlayer = requestedPlayer;
    }

    public OfflinePlayer getRequestedPlayer() {
        return requestedPlayer;
    }
}

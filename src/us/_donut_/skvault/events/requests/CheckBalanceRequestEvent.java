package us._donut_.skvault.events.requests;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.OfflinePlayer;
import us._donut_.skvault.events.SkVaultEvent;
import us._donut_.skvault.events.ValueRequestEvent;

public class CheckBalanceRequestEvent extends ValueRequestEvent {

    static {
        Skript.registerEvent("Vault Request - Check Balance (Return: boolean)", SkVaultEvent.class, CheckBalanceRequestEvent.class,
                "[vault] check [player] bal[ance] request")
                .description("Called when Vault requests to know if a player's balance is greater than a certain amount")
                .requiredPlugins("Vault")
                .since("1.0")
                .examples("on vault check player balance request:\n    if {balances::%player%} is greater than event-number:\n        return true\n    else:\n        return false");

        EventValues.registerEventValue(CheckBalanceRequestEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, CheckBalanceRequestEvent>() {
            public OfflinePlayer get(CheckBalanceRequestEvent e) {
                return e.getRequestedPlayer();
            }
        }, 0);
    }

    private OfflinePlayer requestedPlayer;

    public CheckBalanceRequestEvent(OfflinePlayer requestedPlayer) {
        this.requestedPlayer = requestedPlayer;
    }

    public OfflinePlayer getRequestedPlayer() {
        return requestedPlayer;
    }
}

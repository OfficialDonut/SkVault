package us._donut_.skvault.events.requests;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.OfflinePlayer;
import us._donut_.skvault.events.BooleanRequestEvent;
import us._donut_.skvault.events.SkVaultEvent;

public class HasAccountRequestEvent extends BooleanRequestEvent {

    static {
        Skript.registerEvent("Vault Request - Has Account (Return: boolean)", SkVaultEvent.class, HasAccountRequestEvent.class,
                "[vault] [player] has [eco[nomy]] account request")
                .description("Called when Vault requests to know if a player has an economy account")
                .requiredPlugins("Vault")
                .since("1.1.5")
                .examples("on vault player has account request:\n    if {balances::%player%} is set:\n        return true\n    else:\n        return false");

        EventValues.registerEventValue(HasAccountRequestEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, HasAccountRequestEvent>() {
            public OfflinePlayer get(HasAccountRequestEvent e) {
                return e.getRequestedPlayer();
            }
        }, 0);
    }

    private OfflinePlayer requestedPlayer;

    public HasAccountRequestEvent(OfflinePlayer requestedPlayer) {
        this.requestedPlayer = requestedPlayer;
    }

    public OfflinePlayer getRequestedPlayer() {
        return requestedPlayer;
    }
}

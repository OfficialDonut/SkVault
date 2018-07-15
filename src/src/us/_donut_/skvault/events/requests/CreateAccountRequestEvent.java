package us._donut_.skvault.events.requests;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.OfflinePlayer;
import us._donut_.skvault.events.BooleanRequestEvent;
import us._donut_.skvault.events.SkVaultEvent;

public class CreateAccountRequestEvent extends BooleanRequestEvent {

    static {
        Skript.registerEvent("Vault Request - Create Account (Return: boolean)", SkVaultEvent.class, CreateAccountRequestEvent.class,
                "[vault] create [player] [eco[nomy]] account request")
                .description("Called when Vault requests to create a player economy account")
                .requiredPlugins("Vault")
                .since("1.1.5")
                .examples("on vault create player account request:\n    set {balances::%player%} to 0\n    return true");

        EventValues.registerEventValue(CreateAccountRequestEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, CreateAccountRequestEvent>() {
            public OfflinePlayer get(CreateAccountRequestEvent e) {
                return e.getRequestedPlayer();
            }
        }, 0);
    }

    private OfflinePlayer requestedPlayer;

    public CreateAccountRequestEvent(OfflinePlayer requestedPlayer) {
        this.requestedPlayer = requestedPlayer;
    }

    public OfflinePlayer getRequestedPlayer() {
        return requestedPlayer;
    }
}

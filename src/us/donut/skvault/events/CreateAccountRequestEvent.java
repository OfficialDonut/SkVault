package us.donut.skvault.events;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.OfflinePlayer;

public class CreateAccountRequestEvent extends us.donut.skvault.events.VaultRequestEvent<Boolean> {

    static {
        Skript.registerEvent("Vault Request - Create Account (Return: boolean)", SkVaultEvent.class, CreateAccountRequestEvent.class,
                "[vault] create [player] [eco[nomy]] account request")
                .description("Called when Vault requests a player economy account to be created")
                .requiredPlugins("Vault")
                .since("2.0")
                .examples("on vault create player account request:\n    set {balances::%event-offlineplayer's uuid%} to 0\n    return true");

        EventValues.registerEventValue(CreateAccountRequestEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, CreateAccountRequestEvent>() {
            public OfflinePlayer get(CreateAccountRequestEvent e) {
                return e.getPlayer();
            }
        }, 0);
    }

    private OfflinePlayer player;

    public CreateAccountRequestEvent(OfflinePlayer player) {
        this.player = player;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }
}

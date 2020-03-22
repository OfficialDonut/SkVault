package us.donut.skvault.events;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.OfflinePlayer;

public class HasAccountRequestEvent extends us.donut.skvault.events.VaultRequestEvent<Boolean> {

    static {
        Skript.registerEvent("Vault Request - Has Account (Return: boolean)", SkVaultEvent.class, HasAccountRequestEvent.class,
                "[vault] [player] has [eco[nomy]] account request")
                .description("Called when Vault requests to know if a player has an economy account")
                .requiredPlugins("Vault")
                .since("2.0")
                .examples("on vault player has account request:\n    if {balances::%event-offlineplayer's uuid%} is set:\n        return true\n    else:\n        return false");

        EventValues.registerEventValue(HasAccountRequestEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, HasAccountRequestEvent>() {
            public OfflinePlayer get(HasAccountRequestEvent e) {
                return e.getPlayer();
            }
        }, 0);
    }

    private OfflinePlayer player;

    public HasAccountRequestEvent(OfflinePlayer player) {
        this.player = player;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }
}

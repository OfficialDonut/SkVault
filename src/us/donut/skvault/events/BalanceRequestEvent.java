package us.donut.skvault.events;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.OfflinePlayer;

public class BalanceRequestEvent extends us.donut.skvault.events.VaultRequestEvent<Double> {

    static {
        Skript.registerEvent("Vault Request - Player Balance (Return: number)", SkVaultEvent.class, BalanceRequestEvent.class,
                "[vault] [player] bal[ance] [value] request")
                .description("Called when Vault requests a player's balance")
                .requiredPlugins("Vault")
                .since("2.0")
                .examples("on vault player balance request:\n    return {balances::%event-offlineplayer's uuid%}");

        EventValues.registerEventValue(BalanceRequestEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, BalanceRequestEvent>() {
            public OfflinePlayer get(BalanceRequestEvent e) {
                return e.getPlayer();
            }
        }, 0);
    }

    private OfflinePlayer player;

    public BalanceRequestEvent(OfflinePlayer player) {
        this.player = player;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }
}

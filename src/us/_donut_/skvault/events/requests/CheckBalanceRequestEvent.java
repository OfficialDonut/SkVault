package us._donut_.skvault.events.requests;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.SkriptEventInfo;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.OfflinePlayer;
import us._donut_.skvault.Util;
import us._donut_.skvault.events.BooleanRequestEvent;
import us._donut_.skvault.events.SkVaultEvent;

public class CheckBalanceRequestEvent extends BooleanRequestEvent {

    static {
        SkriptEventInfo skriptEventInfo = Skript.registerEvent("Vault Request - Check Balance (Return: boolean)", SkVaultEvent.class, CheckBalanceRequestEvent.class,
                "[vault] check [player] bal[ance] request")
                .description("Called when Vault requests to know if a player's balance is greater than a certain amount")
                .since("1.0")
                .examples("on vault check player balance request:\n    if {balances::%event-offlineplayer%} is greater than event-number:\n        return true\n    else:\n        return false");

        Util.setRequiredPlugins(skriptEventInfo);

        EventValues.registerEventValue(CheckBalanceRequestEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, CheckBalanceRequestEvent>() {
            public OfflinePlayer get(CheckBalanceRequestEvent e) {
                return e.getRequestedPlayer();
            }
        }, 0);
        EventValues.registerEventValue(CheckBalanceRequestEvent.class, Number.class, new Getter<Number, CheckBalanceRequestEvent>() {
            public Number get(CheckBalanceRequestEvent e) {
                return e.getRequestedWithdrawAmount();
            }
        }, 0);
    }

    private OfflinePlayer requestedPlayer;
    private double requestedWithdrawAmount;

    public CheckBalanceRequestEvent(OfflinePlayer requestedPlayer, double amount) {
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

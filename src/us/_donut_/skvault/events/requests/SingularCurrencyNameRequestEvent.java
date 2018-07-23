package us._donut_.skvault.events.requests;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.SkriptEventInfo;
import us._donut_.skvault.Util;
import us._donut_.skvault.events.SkVaultEvent;
import us._donut_.skvault.events.StringRequestEvent;

public class SingularCurrencyNameRequestEvent extends StringRequestEvent {

    static {
        SkriptEventInfo skriptEventInfo = Skript.registerEvent("Vault Request - Singular Currency Name (Return: string)", SkVaultEvent.class, SingularCurrencyNameRequestEvent.class,
                "[vault] singular [currency] name request")
                .description("Called when Vault requests the singular currency name")
                .since("1.0.5")
                .examples("on vault singular currency name request:\n    return \"dollar\"");

        Util.setRequiredPlugins(skriptEventInfo);
    }

    private boolean gaveSkriptValue = false;

    //Skript uses singular currency name while parsing the script so an UnsupportedOperationException shouldn't be thrown the first time
    @Override
    public void verifyImplemented() {
        if (!gaveSkriptValue) {
            gaveSkriptValue = true;
        } else {
            if (!isImplemented())
                throw new UnsupportedOperationException(getClass().getSimpleName().replace("Event", ""));
        }
    }
}

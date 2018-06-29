package us._donut_.skvault.events.requests;

import ch.njol.skript.Skript;
import us._donut_.skvault.events.SkVaultEvent;
import us._donut_.skvault.events.StringRequestEvent;

public class PluralCurrencyNameRequest extends StringRequestEvent {

    static {
        Skript.registerEvent("Vault Request - Plural Currency Name (Return: string)", SkVaultEvent.class, PluralCurrencyNameRequest.class,
                "[vault] plural [currency] name request")
                .description("Called when Vault requests the plural currency name")
                .requiredPlugins("Vault")
                .since("1.0.5")
                .examples("on vault plural currency name request:\n    return \"dollars\"");
    }

    private boolean gaveSkriptValue = false;

    //Skript uses plural currency name while parsing the script so an UnsupportedOperationException shouldn't be thrown the first time
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

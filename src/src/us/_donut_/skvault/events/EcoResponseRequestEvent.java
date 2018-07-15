package us._donut_.skvault.events;

import net.milkbowl.vault.economy.EconomyResponse;

public class EcoResponseRequestEvent extends VaultEvent {

    private EconomyResponse economyResponse;

    public EconomyResponse getEconomyResponse() {
        verifyImplemented();
        return economyResponse;
    }

    public void setEconomyResponse(EconomyResponse economyResponse) {
        this.economyResponse = economyResponse;
        setImplemented(true);
    }
}

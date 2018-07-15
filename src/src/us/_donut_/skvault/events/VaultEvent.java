package us._donut_.skvault.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VaultEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private boolean implemented;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isImplemented() {
        return implemented;
    }

    public void setImplemented(boolean implemented) {
        this.implemented = implemented;
    }

    public void verifyImplemented() {
        if (!isImplemented())
            throw new UnsupportedOperationException(getClass().getSimpleName().replace("Event", ""));
    }
}

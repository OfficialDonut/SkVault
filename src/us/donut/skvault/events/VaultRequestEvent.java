package us.donut.skvault.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class VaultRequestEvent<T> extends Event {

    private static final HandlerList handlers = new HandlerList();
    private T returnValue;
    private boolean implemented;

    public VaultRequestEvent() {
        super(!Bukkit.isPrimaryThread());
    }

    public boolean isImplemented() {
        return implemented;
    }

    public void setReturnValue(T returnValue) {
        this.returnValue = returnValue;
        implemented = true;
    }

    public T getReturnValue() {
        if (implemented) {
            return returnValue;
        }
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

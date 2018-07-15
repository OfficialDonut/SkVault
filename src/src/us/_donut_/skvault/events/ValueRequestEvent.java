package us._donut_.skvault.events;

public class ValueRequestEvent extends VaultEvent {

    private Object returnValue;

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
        setImplemented(true);
    }
}

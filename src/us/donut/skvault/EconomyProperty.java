package us.donut.skvault;

import java.util.function.Function;

public class EconomyProperty<T> {

    private T value;
    private String name;
    private String identifier;
    private Function<String, T> parser;

    @SuppressWarnings("unchecked")
    public EconomyProperty(String name) {
        this.name = name;
        identifier = "#" + name + ": ";
        this.parser = s -> (T) s;
    }

    public EconomyProperty(String name, Function<String, T> parser) {
        identifier = "#" + name + ": ";
        this.parser = parser;
    }

    public boolean parse(String line) {
        if (line.startsWith(identifier)) {
            value = parser.apply(line.replaceFirst(identifier, ""));
            return true;
        }
        return false;
    }

    public T getValue() {
        if (value != null) {
            return value;
        }
        throw new UnsupportedOperationException(name);
    }
}

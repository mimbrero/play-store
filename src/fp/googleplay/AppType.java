package fp.googleplay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum AppType {

    FREE("Free"),
    PAID("Paid");

    private final String representation;

    AppType(String representation) {
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }

    //
    // Static access from representation to enum type:
    //

    private static final Map<String, AppType> REPRESENTATIONS = new HashMap<>();

    static {
        Arrays.stream(values()).forEach(value -> REPRESENTATIONS.put(value.getRepresentation(), value));
    }

    public static AppType parse(String toParse) {
        return REPRESENTATIONS.get(toParse);
    }
}

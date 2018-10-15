package ir.piana.dev.core.document;

/**
 * @author Mohammad Rahmati, 5/7/2017 5:20 PM
 */
public enum PathSegmentType {
    TEXT("1"),
    PARAM("2");

    private String code;

    PathSegmentType(String code) {
        this.code = code;
    }

    public String toString() {
        return code;
    }
}

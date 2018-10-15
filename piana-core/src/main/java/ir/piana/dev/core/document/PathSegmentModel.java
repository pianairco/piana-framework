package ir.piana.dev.core.document;

/**
 * @author Mohammad Rahmati, 5/7/2017 5:20 PM
 */
public class PathSegmentModel {
    private String name;
    private PathSegmentType type;

    public PathSegmentModel() {
    }

    public PathSegmentModel(String name, PathSegmentType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PathSegmentType getType() {
        return type;
    }

    public void setType(PathSegmentType type) {
        this.type = type;
    }
}

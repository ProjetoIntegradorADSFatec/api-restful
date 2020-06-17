package api.restful.model.collection;

public class Asset {
    private String name;
    private String href;

    public Asset() {}

    public Asset(String name, String href) {
        this.name = name;
        this.href = href;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return this.href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
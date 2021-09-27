package carsharing.dao;

public class BaseItem {
    protected int id;
    protected String name;

    public BaseItem(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

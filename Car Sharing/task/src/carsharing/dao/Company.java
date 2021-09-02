package carsharing.dao;

public class Company {
    private int id;
    private String name;

    public Company(String name) {
        this.name = name;
    }

    public Company(int id, String name) {
        this(name);
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

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

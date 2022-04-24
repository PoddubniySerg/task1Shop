package manufacturer;

public class Manufacturer {

    private final String name;

    public Manufacturer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !obj.getClass().equals(this.getClass())) return false;
        Manufacturer manufacturer = (Manufacturer) obj;
        return this.name.equals(manufacturer.name);
    }
}
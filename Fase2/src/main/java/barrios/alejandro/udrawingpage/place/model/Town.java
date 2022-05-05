package barrios.alejandro.udrawingpage.place.model;

public class Town {

    private long id;
    private String department;
    private String name;
    private boolean snSucursal;

    public Town(long id, String department, String name, boolean snSucursal) {
        this.id = id;
        this.department = department;
        this.name = name;
        this.snSucursal = snSucursal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSnSucursal() {
        return snSucursal;
    }

    public void setSnSucursal(boolean snSucursal) {
        this.snSucursal = snSucursal;
    }

    @Override
    public String toString() {
        return department + " - " + name;
    }
}

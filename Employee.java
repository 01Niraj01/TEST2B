import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Employee {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty position;

    // Constructor
    public Employee(int id, String name, String position) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.position = new SimpleStringProperty(position);
    }

    // Getters
    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getPosition() {
        return position.get();
    }

    // Setters
    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    // Properties (optional, useful for binding)
    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty positionProperty() {
        return position;
    }
}

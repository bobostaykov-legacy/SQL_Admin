import javafx.scene.control.Button;

public class Buttons {

    private String name;
    private Button button;

    public Buttons(String name, Button button) {
        this.name = name;
        this.button = button;
    }

    public String getName() {
        return name;
    }

    public Button getButton() {
        return button;
    }
}

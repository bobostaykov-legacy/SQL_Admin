import javafx.scene.control.Button;

class Buttons {

    private String name;
    private Button button;

    Buttons(String name, Button button) {
        this.name = name;
        this.button = button;
    }

    String getName() {
        return name;
    }

    Button getButton() {
        return button;
    }
}

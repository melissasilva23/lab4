public class Aircraft {

    private String model;
    private String fabricant;
    private int capacity;

    public Aircraft(String model, String fabricant, int capacity) {
        this.model = model;
        this.fabricant = fabricant;
        this.capacity = capacity;
    }

    public String getModel() {
        return model;
    }
}

package controller.driver;

public abstract class GenericDriver {
    public GenericDriver() {
        Initialize();
    }
    public abstract void Initialize() throws UnsupportedOperationException;
}

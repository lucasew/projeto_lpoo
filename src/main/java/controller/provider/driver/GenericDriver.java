package controller.provider.driver;

public abstract class GenericDriver {
    public GenericDriver() {
        Initialize();
    }
    public abstract void Initialize() throws UnsupportedOperationException;
}

package api.exceptions;

public class SupplierNotFoundException extends Exception{

    public SupplierNotFoundException() {
        super();
    }

    public SupplierNotFoundException(String message) {
        super(message);
    }
}

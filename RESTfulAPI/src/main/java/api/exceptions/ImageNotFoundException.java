package api.exceptions;

public class ImageNotFoundException extends Exception{

    public ImageNotFoundException() {
        super();
    }

    public ImageNotFoundException(String message) {
        super(message);
    }
}

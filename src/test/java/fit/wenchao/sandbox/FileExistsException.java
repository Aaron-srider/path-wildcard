package fit.wenchao.sandbox;

public class FileExistsException  extends RuntimeException{

    public FileExistsException() {
    }

    public FileExistsException(String message) {
        super(message);
    }

    public FileExistsException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }
}

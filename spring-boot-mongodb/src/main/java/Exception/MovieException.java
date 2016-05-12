package Exception;

/**
 * Created by angular5 on 12/05/16.
 */
public class MovieException extends Exception {

    public MovieException(String s) {
        super(s);
    }

    public MovieException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

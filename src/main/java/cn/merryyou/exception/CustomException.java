package cn.merryyou.exception;

/**
 * Created on 2016/9/21 0021.
 *
 * @author zlf
 * @since 1.0
 */
public class CustomException extends Exception {

    private String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

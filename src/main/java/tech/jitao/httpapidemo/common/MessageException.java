package tech.jitao.httpapidemo.common;

public class MessageException extends Exception {
    private static final long serialVersionUID = 0;

    public MessageException(String message) {
        super(message);
    }
}

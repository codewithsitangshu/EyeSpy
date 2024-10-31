package com.org.codewithsitangshu.eyeSpy.exception;

public class EyeSpyException extends RuntimeException {

    private static final long serialVersionUID = 2544120188796893890L;

    public EyeSpyException() {
    }

    public EyeSpyException(String message) {
        super(message);
    }

    public EyeSpyException(Throwable cause) {
        super(cause);
    }

    public EyeSpyException(String message, Throwable cause) {
        super(message, cause);
    }

}

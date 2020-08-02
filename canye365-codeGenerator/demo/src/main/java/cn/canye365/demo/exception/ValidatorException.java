package cn.canye365.demo.exception;

/**
 * 校验失败异常
 */
public class ValidatorException extends RuntimeException{

    public ValidatorException(String message) {
        super(message);
    }
}

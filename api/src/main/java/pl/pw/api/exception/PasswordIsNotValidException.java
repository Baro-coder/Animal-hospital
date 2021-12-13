package pl.pw.api.exception;

public class PasswordIsNotValidException extends RuntimeException{
  public PasswordIsNotValidException(String message) {
    super(message);
  }
}

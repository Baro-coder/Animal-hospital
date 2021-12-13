package pl.pw.api.exception;

public class UserAlreadyExistException extends RuntimeException{
  public UserAlreadyExistException(String message) {
    super("Użytkownik o adresie " + message + "już istnieje.");
  }
}

package pl.pw.api.exception;

public class VisitNotFoundException extends RuntimeException{
  public VisitNotFoundException(String message) {
    super("Wizyty o id " + message + " nie znaleziono ");
  }
}

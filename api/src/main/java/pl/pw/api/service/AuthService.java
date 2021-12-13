package pl.pw.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pw.api.entity.User;
import pl.pw.api.exception.PasswordIsNotValidException;
import pl.pw.api.exception.UserAlreadyExistException;
import pl.pw.api.exception.UserNotFoundException;
import pl.pw.api.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;

  public User register(User request) {
    return userRepository.save(checkUser(request));
  }

  public User login(String email, String password) {
    var user = userRepository.findOneByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("Nie ma takiego użytkownika"));
    if (!user.getPassword().equals(password)) {
      throw new PasswordIsNotValidException("Hasło nie jest poprawne");
    } else {
      return user;
    }
  }

  private User checkUser(User request){
    userRepository.findOneByEmail(request.getEmail())
            .ifPresent((e) -> new UserAlreadyExistException(e.getEmail()));

    return request;
  }
}

package pl.pw.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pw.api.entity.User;
import pl.pw.api.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public List<User> getUsers(String type) {
    return userRepository.findAllByType(type);
  }
}

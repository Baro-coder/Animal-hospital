package pl.pw.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.pw.api.entity.AccountType;
import pl.pw.api.entity.User;
import pl.pw.api.exception.UserAlreadyExistException;
import pl.pw.api.service.AuthService;
import pl.pw.api.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

  private final AuthService authService;
  private final UserService userService;

  @PostMapping("/register")
  @Operation(description = "Rejestracja użytkownika")
  public ResponseEntity<User> addNewUser(@RequestBody User request){
    try {
      return new ResponseEntity<>(authService.register(request), HttpStatus.OK);
    } catch (UserAlreadyExistException e){
      throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
    }
  }

  @GetMapping("/login")
  @Operation(description = "Logowanie użytkownika")
  public User login(@RequestParam String email, @RequestParam String password){
    return authService.login(email, password);
  }

  @GetMapping
  @Operation(description = "Pobranie informacji o klientach/lekarzach")
  public List<User> getUsers(@RequestParam AccountType accountType){
    return userService.getUsers(accountType.getType());
  }

}

package pl.pw.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pw.api.dto.VisitAdd;
import pl.pw.api.entity.Visit;
import pl.pw.api.exception.UserNotFoundException;
import pl.pw.api.exception.VisitNotFoundException;
import pl.pw.api.repository.UserRepository;
import pl.pw.api.repository.VisitRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

  private final VisitRepository visitRepository;
  private final UserRepository userRepository;

  public Visit add(VisitAdd request) {
    userRepository.findOneByEmail(request.getClient())
            .orElseThrow(() -> new UserNotFoundException("Błędny email klienta, usera nie znaleziono"));
    userRepository.findOneByEmail(request.getDoctor())
            .orElseThrow(() -> new UserNotFoundException("Błędny email doktora, usera nie znaleziono"));

    return visitRepository.save(request.toModel());
  }

  public List<Visit> getVisitsByEmail(String email) {
    userRepository.findOneByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("Usera nie znaleziono"));

    return visitRepository.findAllByEmail(email);

  }

  public void delete(String id) {
    var visit = visitRepository.findOneById(id)
            .orElseThrow(() -> new VisitNotFoundException(id));

    visitRepository.delete(visit);
  }

  public Visit getById(String id) {
    return visitRepository.findOneById(id)
            .orElseThrow(() -> new VisitNotFoundException(id));
  }
}

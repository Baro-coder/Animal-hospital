package pl.pw.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.pw.api.dto.VisitAdd;
import pl.pw.api.entity.Visit;
import pl.pw.api.service.VisitService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/visits")
public class VisitController {

  private final VisitService visitService;

  @PostMapping
  @Operation(description = "Dodanie wizyty")
  public Visit addVisit(@RequestBody VisitAdd visit) {
    return visitService.add(visit);
  }

  @GetMapping
  @Operation(description = "Pobranie wizyt dla użytkownika")
  public List<Visit> getVisits(@RequestParam String email){
    return visitService.getVisitsByEmail(email);
  }

  @DeleteMapping("/{id}")
  @Operation(description = "Usuwanie wizyty")
  public void deleteVisit(@PathVariable String id){
    visitService.delete(id);
  }

  @GetMapping("/{id}")
  @Operation(description = "Pobranie informacji o danej wizycie")
  public Visit getVisit(@PathVariable String id){
    return visitService.getById(id);
  }
}

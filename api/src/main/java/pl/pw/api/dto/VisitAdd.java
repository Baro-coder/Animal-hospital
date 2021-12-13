package pl.pw.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pw.api.entity.Visit;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitAdd {
  private String client;
  private String doctor;
  private float animalAge;
  private String animalBreed;
  private Instant time;
  private String description;

  public Visit toModel(){
    return new Visit(String.valueOf(UUID.randomUUID()), client, doctor, animalAge, animalBreed, time, description);
  }
}

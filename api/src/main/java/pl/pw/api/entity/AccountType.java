package pl.pw.api.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AccountType {

  LEKARZ("LEKARZ"),
  KLIENT("KLIENT");

  @Getter
  private final String type;
}

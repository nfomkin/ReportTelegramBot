package com.nfomkin.firsttelegrambot.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {
  private LocalDate date;
  private String routeNumber;
  private String code;
  private String description;
  private Long userId;
//  private List<File> photos;

}

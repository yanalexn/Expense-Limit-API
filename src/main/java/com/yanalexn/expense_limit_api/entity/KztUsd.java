package com.yanalexn.expense_limit_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class KztUsd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double exchangeRate;

    LocalDate datetime;//todo: LocalDate???+Timezone
}

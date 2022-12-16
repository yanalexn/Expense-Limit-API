package com.yanalexn.expense_limit_api.data_layer.entity;

import javax.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;
import java.time.OffsetDateTime;

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

    OffsetDateTime datetime;
}

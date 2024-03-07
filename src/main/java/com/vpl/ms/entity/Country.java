package com.vpl.ms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "country")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Country {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "capital")
    private String countryCapital;
}

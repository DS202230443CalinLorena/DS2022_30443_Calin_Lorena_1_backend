package com.example.assignment1backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;
    private String description;
    private String address;
    private Integer maximumHourlyEnergyConsumption;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;
}

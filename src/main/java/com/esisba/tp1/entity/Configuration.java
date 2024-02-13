package com.esisba.tp1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data @Embeddable @AllArgsConstructor @NoArgsConstructor
public class Configuration implements Serializable {

    private String cpu;

    private String ram;

    private String disk;
}

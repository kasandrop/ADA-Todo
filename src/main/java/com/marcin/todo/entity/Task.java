package com.marcin.todo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NonNull
    private String name;
    private String description;
    private boolean completion;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "label_id", nullable = false)
    private Label label;

}

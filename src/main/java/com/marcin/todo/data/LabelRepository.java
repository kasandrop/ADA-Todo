package com.marcin.todo.data;

import com.marcin.todo.entity.Label;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LabelRepository  extends CrudRepository<Label, Integer> {
    Optional<Label> findByName(String name);
    boolean existsByName(String name);

}

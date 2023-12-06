package com.marcin.todo.data;

import com.marcin.todo.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    Optional<Task> findByName(String name);

}

package com.marcin.todo.data;

import com.marcin.todo.entity.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {}

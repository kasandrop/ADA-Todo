package com.marcin.todo.data;

import com.marcin.todo.entity.Label;
import org.springframework.data.repository.CrudRepository;

public interface LabelRepository  extends CrudRepository<Label, Integer> {}

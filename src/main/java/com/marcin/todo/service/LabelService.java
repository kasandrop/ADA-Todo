package com.marcin.todo.service;


import com.marcin.todo.data.LabelRepository;
import com.marcin.todo.entity.Label;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LabelService {

    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public List<Label> getAllLabels() {
        return (List<Label>) labelRepository.findAll();
    }

    public Label getLabel(int id) {
        return labelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid label Id:" + id));
    }

    public Label createLabel(Label newLabel) {
        return labelRepository.save(newLabel);
    }

    @Transactional
    public Label updateLabel(Label updatedLabel, int id) {
        return labelRepository.findById(id).map(label -> {
            label.setName(updatedLabel.getName());
            label.setTasks(updatedLabel.getTasks());
            return labelRepository.save(label);
        }).orElseGet(() -> {
            updatedLabel.setId(id);
            return labelRepository.save(updatedLabel);
        });
    }

    public void deleteLabel(int id) {
        labelRepository.deleteById(id);
    }

}

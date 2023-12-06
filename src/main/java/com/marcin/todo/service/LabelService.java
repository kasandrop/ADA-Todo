package com.marcin.todo.service;

import com.marcin.todo.data.LabelRepository;
import com.marcin.todo.entity.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    public List<Label> getAllLabels() {
        return (List<Label>) labelRepository.findAll();
    }

    public Label getLabel(int id) {
        return labelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid label Id:" + id));
    }

    public Label saveLabel(Label label) {
        if (label.getName() == null || label.getName().isEmpty()) {
            throw new IllegalArgumentException("Label operation failed: A name is required for each label.");
        }

        if(labelRepository.existsByName(label.getName())) {
            throw new IllegalArgumentException("Label operation failed: The name you've chosen is already in use. Please choose a unique name.");
        }
        int result=label.getId();
        if (result != 0) {
            return updateLabel(label);
        }
        return labelRepository.save(label);
    }

    public void deleteLabel(int id) {
        labelRepository.deleteById(id);
    }

    private Label updateLabel(Label updatedLabel) {
        Optional<Label> optionalLabel = labelRepository.findById(updatedLabel.getId());
        if (optionalLabel.isPresent()) {
            Label existingLabel = optionalLabel.get();
            existingLabel.setName(updatedLabel.getName());
            existingLabel.setTasks(updatedLabel.getTasks());
            return labelRepository.save(existingLabel);
        }
        throw new IllegalArgumentException("Invalid label Id: Label for update not found");
    }


}

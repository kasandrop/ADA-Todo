package com.marcin.todo.controllers;

import com.marcin.todo.data.LabelRepository;
import com.marcin.todo.entity.Label;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class LabelController {


    private final LabelRepository labelRepository;

    public LabelController(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @GetMapping("/labels")
    public ResponseEntity<List<Label>> getAllLabels() {
        List<Label> labels = new ArrayList<>();
        labelRepository.findAll().forEach(labels::add);
        return new ResponseEntity<>(labels, HttpStatus.OK);
    }

    @GetMapping("/labels/{id}")
    public ResponseEntity<Label> getLabelById(@PathVariable(value = "id") int id) {
        Optional<Label> label = labelRepository.findById(id);
        if (label.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(label.get(), HttpStatus.OK);
    }


    @PostMapping("/labels")
    public ResponseEntity<Label> addLabel(@RequestBody Label label) {
        return new ResponseEntity<>(labelRepository.save(label), HttpStatus.CREATED);
    }


    @DeleteMapping("/labels/{id}")
    public ResponseEntity<String> deleteLabel(@PathVariable(value = "id") int id) {
        labelRepository.deleteById(id);
        return new ResponseEntity<>("Label deleted.", HttpStatus.OK);
    }
}

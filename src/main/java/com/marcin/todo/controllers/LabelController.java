package com.marcin.todo.controllers;


import com.marcin.todo.entity.Label;
import com.marcin.todo.service.LabelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LabelController {


    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/labels")
    public ResponseEntity<List<Label>> getAllLabels() {
        List<Label> labels = labelService.getAllLabels();
        return new ResponseEntity<>(labels, HttpStatus.OK);
    }

     @GetMapping("/labels/{id}")
    public ResponseEntity<Label> getLabelById(@PathVariable(value = "id") int id) {
        try {
            Label label = labelService.getLabel(id);
            return new ResponseEntity<>(label, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/labels")
    public ResponseEntity<Label> addLabel(@RequestBody Label label) {
        Label savedLabel = labelService.saveLabel(label);
        return new ResponseEntity<>(savedLabel, HttpStatus.CREATED);
    }


    @DeleteMapping("/labels/{id}")
    public ResponseEntity<String> deleteLabel(@PathVariable(value = "id") int id) {
        labelService.deleteLabel(id);
        return new ResponseEntity<>("Label deleted.", HttpStatus.OK);
    }
}

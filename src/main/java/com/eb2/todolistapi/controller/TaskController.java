package com.eb2.todolistapi.controller;

import com.eb2.todolistapi.dto.ResponseDTO;
import com.eb2.todolistapi.dto.TaskDTO;
import com.eb2.todolistapi.entities.Task;
import com.eb2.todolistapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @GetMapping
    public ResponseEntity<ResponseDTO> getAllTasks(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order
    ) {
        return ResponseEntity.ok(taskService.findAll(pageNo, pageSize, sortBy, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(
            @PathVariable long id
    ) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(taskService.save(taskDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@RequestBody TaskDTO taskDTO, @PathVariable long id) {
        return ResponseEntity.ok(taskService.edit(taskDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteTask(@PathVariable long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
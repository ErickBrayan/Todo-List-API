package com.eb2.todolistapi.dto;

import com.eb2.todolistapi.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

    public Task toEntity(TaskDTO taskDTO) {

        Task task = new Task();
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());

        return task;
    }

    public ResponseDTO toListPagination(Page<Task> tasks) {

        return new ResponseDTO(
                tasks.getContent(),
                tasks.getNumber(),
                tasks.getSize(),
                tasks.getTotalElements()
        );

    }
}

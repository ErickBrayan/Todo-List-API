package com.eb2.todolistapi.dto;

import com.eb2.todolistapi.entities.Task;

import java.util.List;

public record ResponseDTO(
        List<Task> data,
        int page,
        int limit,
        long total
) {

}

package com.eb2.todolistapi.service;

import com.eb2.todolistapi.dto.ResponseDTO;
import com.eb2.todolistapi.dto.TaskDTO;
import com.eb2.todolistapi.entities.Task;

public interface TaskService {

    ResponseDTO findAll(int pageNo, int pageSize, String sortBy, String order);
    Task findById(long id);
    Task save(TaskDTO taskDTO);
    Task edit(TaskDTO taskDTO, long id);
    void delete(long id);
}

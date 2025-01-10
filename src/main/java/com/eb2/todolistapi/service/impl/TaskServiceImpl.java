package com.eb2.todolistapi.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.eb2.todolistapi.dto.ResponseDTO;
import com.eb2.todolistapi.dto.TaskDTO;
import com.eb2.todolistapi.dto.TaskMapper;
import com.eb2.todolistapi.entities.Task;
import com.eb2.todolistapi.exception.NotFoundException;
import com.eb2.todolistapi.repositories.TaskRepository;
import com.eb2.todolistapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public ResponseDTO findAll(int pageNo, int pageSize, String sortBy, String order) {

        Sort sort = Sort.unsorted();

        if (!StringUtil.isNullOrEmpty(sortBy)) {
            sort = "desc".equalsIgnoreCase(order) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return taskMapper.toListPagination(taskRepository.findAll(pageable));
    }

    @Override
    public Task findById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id "+id+" not found", "404", HttpStatus.NOT_FOUND));
    }

    @Override
    public Task save(TaskDTO taskDTO) {
        return taskRepository.save(taskMapper.toEntity(taskDTO));
    }

    @Override
    public Task edit(TaskDTO taskDTO, long id) {
        Task taskDB = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id "+id+" not found", "404", HttpStatus.NOT_FOUND));

        taskDB.setTitle(taskDTO.title());
        taskDB.setDescription(taskDTO.description());

        return taskRepository.save(taskDB);
    }

    @Override
    public void delete(long id) {

        taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id "+id+" not found", "404", HttpStatus.NOT_FOUND));

        taskRepository.deleteById(id);
    }
}
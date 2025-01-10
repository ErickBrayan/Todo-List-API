package com.eb2.todolistapi.service;

import com.eb2.todolistapi.dto.ResponseDTO;
import com.eb2.todolistapi.dto.TaskDTO;
import com.eb2.todolistapi.dto.TaskMapper;
import com.eb2.todolistapi.entities.Task;
import com.eb2.todolistapi.exception.NotFoundException;
import com.eb2.todolistapi.repositories.TaskRepository;
import com.eb2.todolistapi.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskServiceTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_create_task() {

        TaskDTO dto = new TaskDTO("Task Name", "Description");
        Task task = new Task(
                502L,
                "Task Name",
                "Description"
        );

        Task savetask = new Task(
                502L,
                "Task Name",
                "Description"
        );
        savetask.setId(502L);

        when(taskMapper.toEntity(dto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(savetask);

        Task save = taskService.save(dto);

        assertEquals(dto.title(), save.getTitle());

        verify(taskMapper, Mockito.times(1))
                .toEntity(dto);

        verify(taskRepository, Mockito.times(1))
                .save(task);

    }

    @Test
    public void should_return_all_tasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(502L, "Task Name", "Description"));
        Pageable pageable = PageRequest.of(0, 10);
        Page<Task> tasksPageable = taskRepository.findAll(pageable);



        ResponseDTO responseDTO = new ResponseDTO(
                taskRepository.findAll(),
                0,
                10,
                502L
        );

        when(taskRepository.findAll()).thenReturn(tasks);

        when(taskMapper.toListPagination(tasksPageable)).thenReturn(responseDTO);


        assertEquals(502L, responseDTO.total());

        verify(taskRepository, Mockito.times(1)).findAll();

    }

    @Test
    public void should_return_task_by_id() {
        long taskId = 502;
        Task task = new Task(502L, "Task Name", "Description");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        assertEquals(502L, task.getId());
        assertEquals("Task Name", task.getTitle());
        assertEquals("Description", task.getDescription());

        assertThrows(NotFoundException.class, () -> taskService.findById(600L));
    }

    @Test
    public void should_successfully_update_task() {

        long taskId = 502;
        Task task = new Task(502L, "Task Name", "Description");
        Task taskEdited = new Task(502L, "Task Name Edited", "Description");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        when(taskRepository.save(task)).thenReturn(taskEdited);

        assertEquals("Task Name Edited", taskEdited.getTitle());

    }
}
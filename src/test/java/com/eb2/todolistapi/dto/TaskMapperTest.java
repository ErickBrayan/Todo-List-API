package com.eb2.todolistapi.dto;

import com.eb2.todolistapi.entities.Task;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        taskMapper = new TaskMapper();
    }

    @Test
    public void shouldMapTaskDtoTask() {
        TaskDTO dto = new TaskDTO("any", "some");

        Task task = taskMapper.toEntity(dto);

        assertEquals(dto.title(), task.getTitle());
        assertEquals(dto.description(), task.getDescription());

    }

}
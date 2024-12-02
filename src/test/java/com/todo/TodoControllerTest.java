package com.todo;

import com.todo.repository.TodoRepository;
import com.todo.service.TodoService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TodoControllerTest {
    @InjectMocks
    TodoService todoService;

    @Mock
    TodoRepository todoRepository;
}

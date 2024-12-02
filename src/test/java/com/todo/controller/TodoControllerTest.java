package com.todo.controller;

import com.todo.common.ResponseEntity;
import com.todo.entity.Todo;
import com.todo.exception.TodoItemNotFoundException;
import com.todo.repository.TodoRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class TodoControllerTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private JacksonTester<ResponseEntity<Todo>> todoJacksonTester;

    @Autowired
    private JacksonTester<ResponseEntity<List<Todo>>> todosJacksonTester;

    @BeforeEach
    void setUp() {
        givenDataToJpaRepository();
    }

    private void givenDataToJpaRepository() {
        todoRepository.deleteAll();
        todoRepository.save(new Todo(null, "111", false));
        todoRepository.save(new Todo(null, "222", true));
        todoRepository.save(new Todo(null, "333", false));
        todoRepository.save(new Todo(null, "444", true));
        todoRepository.save(new Todo(null, "555", false));
    }

    @Test
    void should_return_todos_when_get_all_given_todo_exist() throws Exception {
        //given
        final List<Todo> givenTodos = todoRepository.findAll();

        //when
        //then
        final String jsonResponse = client.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        final ResponseEntity<List<Todo>> response = todosJacksonTester.parseObject(jsonResponse);
        List<Todo> todosResult = response.getResult();
        assertThat(todosResult)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(givenTodos);
    }

    @Test
    void should_return_employee_when_get_by_id() throws Exception {
        // Given
        final Todo givenTodo = todoRepository.findAll().get(0);

        // When
        // Then
        client.perform(MockMvcRequestBuilders.get("/todos/" + givenTodo.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value(givenTodo.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.text").value(givenTodo.getText()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.done").value(givenTodo.getDone()));
    }

    @Test
    void should_create_employee_success() throws Exception {
        // Given
        String givenText = "do homework";
        Boolean givenDone = false;
        String givenEmployee = String.format(
                "{\"text\": \"%s\", \"done\": \"%s\"}",
                givenText,
                givenDone
        );

        // When
        // Then
        String contentAsString = client.perform(MockMvcRequestBuilders.post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(givenEmployee)
        ).andReturn().getResponse().getContentAsString();
        Todo todo = todoJacksonTester.parseObject(contentAsString).getResult();

        Todo findTodo = todoRepository.findById(todo.getId()).orElseThrow(TodoItemNotFoundException::new);
        AssertionsForClassTypes.assertThat(findTodo.getText()).isEqualTo(givenText);
        AssertionsForClassTypes.assertThat(findTodo.getDone()).isEqualTo(givenDone);
    }
}

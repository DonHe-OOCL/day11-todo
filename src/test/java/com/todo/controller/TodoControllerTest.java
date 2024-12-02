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
    private JacksonTester<ResponseEntity<Todo>> todoResponseEntityJacksonTester;

    @Autowired
    private JacksonTester<ResponseEntity<List<Todo>>> todosResponseEntityJacksonTester;

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

        final ResponseEntity<List<Todo>> response = todosResponseEntityJacksonTester.parseObject(jsonResponse);
        List<Todo> todosResult = response.getResult();
        assertThat(todosResult)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(givenTodos);
    }

    @Test
    void should_return_todo_when_get_by_id() throws Exception {
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
    void should_create_todo_success() throws Exception {
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
        Todo todo = todoResponseEntityJacksonTester.parseObject(contentAsString).getResult();

        Todo findTodo = todoRepository.findById(todo.getId()).orElseThrow(TodoItemNotFoundException::new);
        AssertionsForClassTypes.assertThat(findTodo.getText()).isEqualTo(givenText);
        AssertionsForClassTypes.assertThat(findTodo.getDone()).isEqualTo(givenDone);
    }

    @Test
    void should_update_todo_success() throws Exception {
        // Given
        List<Todo> givenTodos = todoRepository.findAll();
        Integer givenId = givenTodos.get(0).getId();
        String givenText = "study";
        Boolean givenDone = true;
        String givenTodo = String.format(
                "{\"id\": %s, \"text\": \"%s\", \"done\": \"%s\"}",
                givenId,
                givenText,
                givenDone
        );

        // When
        // Then
        client.perform(MockMvcRequestBuilders.put("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenTodo)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.text").value(givenText))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.done").value(givenDone));
        Todo todo = todoRepository.findById(givenId).orElseThrow(TodoItemNotFoundException::new);
        AssertionsForClassTypes.assertThat(todo.getId()).isEqualTo(givenId);
        AssertionsForClassTypes.assertThat(todo.getText()).isEqualTo(givenText);
        AssertionsForClassTypes.assertThat(todo.getDone()).isEqualTo(givenDone);
    }

    @Test
    void should_remove_todo_success() throws Exception {
        // Given
        List<Todo> givenTodos = todoRepository.findAll();
        Integer givenId = givenTodos.get(0).getId();

        // When
        // Then
        client.perform(MockMvcRequestBuilders.delete("/todos/" + givenId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(givenId));
        List<Todo> todos = todoRepository.findAll();
        assertThat(todos).hasSize(4);
        AssertionsForClassTypes.assertThat(todos.get(0).getId()).isEqualTo(givenTodos.get(1).getId());
        AssertionsForClassTypes.assertThat(todos.get(1).getId()).isEqualTo(givenTodos.get(2).getId());
        AssertionsForClassTypes.assertThat(todos.get(2).getId()).isEqualTo(givenTodos.get(3).getId());
        AssertionsForClassTypes.assertThat(todos.get(3).getId()).isEqualTo(givenTodos.get(4).getId());
    }

    @Test
    void should_throw_todo_item_not_found_exception_when_get_by_id_given_todo_not_exist() throws Exception {
        // Given
        List<Todo> givenTodos = todoRepository.findAll();
        Integer givenId = givenTodos.get(givenTodos.size() - 1).getId() + 1;

        // When
        // Then
        client.perform(MockMvcRequestBuilders.get("/todos/" + givenId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Todo item not found"));
    }


}

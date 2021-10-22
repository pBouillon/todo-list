package io.pbouillon.todolist.infrastructure.mappers;

import io.pbouillon.todolist.application.items.commands.CreateTodoItemCommand;
import io.pbouillon.todolist.application.items.dtos.TodoItemDto;
import io.pbouillon.todolist.domain.entities.TodoItem;
import io.pbouillon.todolist.domain.enums.Status;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test suite for the {@link TodoItemMapper}
 * @see TodoItemMapper
 */
public class TodoItemMapperTest {

    /**
     * Utility method creating a simple {@link TodoItem}
     * @return A new unfinished {@link TodoItemMapper} created at this instant
     */
    private static TodoItem createItem() {
        TodoItem item = new TodoItem();

        item.setId("id-" + UUID.randomUUID());
        item.setTitle("title-" + UUID.randomUUID());
        item.setNote("description-" + UUID.randomUUID());
        item.setCreatedOn(Instant.now());
        item.setStatus(Status.NotStarted);

        return item;
    }

    /**
     * Utility method creating a simple {@link CreateTodoItemCommand}
     * @return A new unfinished {@link CreateTodoItemCommand}
     */
    private static CreateTodoItemCommand createCreateTodoItemCommand() {
        return new CreateTodoItemCommand(
                "title-" + UUID.randomUUID(),
                "description-" + UUID.randomUUID(),
                Status.NotStarted);
    }

    @Test
    @DisplayName("Given a CreateTodoItemCommand, when converting it to an item, then it should have the same properties")
    public void givenACreateTodoItemCommand_WhenConvertingItToAnItem_ThenItShouldHaveTheSameProperties() {
        CreateTodoItemCommand command = createCreateTodoItemCommand();

        TodoItemMapper mapper = new TodoItemMapperImpl();
        TodoItem item = mapper.fromCommand(command);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(item.getTitle())
                    .as("Task's title")
                    .isEqualTo(command.getTitle());

            softly.assertThat(item.getNote())
                    .as("Task's description (nullable)")
                    .isEqualTo(command.getNote());

            softly.assertThat(item.getStatus())
                    .as("Completeness")
                    .isEqualTo(command.getStatus());
        });
    }

    @Test
    @DisplayName("Given a TodoItem, when converting it to a DTO, then it should have the same properties")
    public void givenAnItem_WhenConvertingItToADto_ThenItShouldHaveTheSameProperties() {
        TodoItem original = createItem();

        TodoItemMapper mapper = new TodoItemMapperImpl();
        TodoItemDto dto = mapper.toDto(original);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(dto.getId())
                    .as("Identifier")
                    .isEqualTo(original.getId());

            softly.assertThat(dto.getTitle())
                    .as("Task's title")
                    .isEqualTo(original.getTitle());

            softly.assertThat(dto.getNote())
                    .as("Task's description (nullable)")
                    .isEqualTo(original.getNote());

            softly.assertThat(dto.getCreatedOn())
                    .as("Creation date")
                    .isEqualTo(original.getCreatedOn());

            softly.assertThat(dto.getStatus())
                    .as("Completeness")
                    .isEqualTo(original.getStatus());
        });
    }

    @Test
    @DisplayName("Given a list of items, when converting them to DTOs, then they should have the same properties")
    public void givenAListOfItems_WhenConvertingThemToDtos_ThenTheyShouldHaveTheSameProperties() {
        final int itemsCount = 5;
        List<TodoItem> originals = IntStream.range(0, itemsCount)
                .mapToObj(el -> createItem())
                .collect(Collectors.toList());

        TodoItemMapper mapper = new TodoItemMapperImpl();
        List<TodoItemDto> dtos = mapper.toDtos(originals);

        assertThat(dtos).hasSameSizeAs(originals);

        for (int i = 0; i < itemsCount; ++i) {
            TodoItem original = originals.get(i);
            TodoItemDto dto = dtos.get(i);

            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(dto.getId())
                        .as("Identifier")
                        .isEqualTo(original.getId());

                softly.assertThat(dto.getTitle())
                        .as("Task's title")
                        .isEqualTo(original.getTitle());

                softly.assertThat(dto.getNote())
                        .as("Task's description (nullable)")
                        .isEqualTo(original.getNote());

                softly.assertThat(dto.getCreatedOn())
                        .as("Creation date")
                        .isEqualTo(original.getCreatedOn());

                softly.assertThat(dto.getStatus())
                        .as("Completeness")
                        .isEqualTo(original.getStatus());
            });
        }
    }

}

package io.pbouillon.todolist.infrastructure.mappers;

import io.pbouillon.todolist.domain.entities.TodoItem;
import io.pbouillon.todolist.presentation.dtos.TodoItemDto;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
@SpringBootTest(classes = { TodoItemMapperImpl.class })
public class TodoItemMapperTest {

    /**
     * The system under test for those units
     */
    @Autowired
    private TodoItemMapper mapper;

    /**
     * Utility method creating a simple {@link TodoItem}
     * @return A new unfinished {@link TodoItemMapper} created at this instant
     */
    private static TodoItem createItem() {
        TodoItem item = new TodoItem();

        item.setId("id-" + UUID.randomUUID());
        item.setTitle("title-" + UUID.randomUUID());
        item.setCommentary("description-" + UUID.randomUUID());
        item.setCreatedOn(Instant.now());
        item.setDone(false);

        return item;
    }

    @Test
    @DisplayName("Given a TodoItem, when converting it to a DTO, then it should have the same properties")
    public void givenAnItem_WhenConvertingItToADto_ThenItShouldHaveTheSameProperties() {
        TodoItem original = createItem();

        TodoItemDto dto = mapper.toDto(original);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(dto.getId())
                    .as("Identifier")
                    .isEqualTo(original.getId());

            softly.assertThat(dto.getTitle())
                    .as("Task's title")
                    .isEqualTo(original.getTitle());

            softly.assertThat(dto.getCommentary())
                    .as("Task's description (nullable)")
                    .isEqualTo(original.getCommentary());

            softly.assertThat(dto.getCreatedOn())
                    .as("Creation date")
                    .isEqualTo(original.getCreatedOn());

            softly.assertThat(dto.isDone())
                    .as("Completeness")
                    .isEqualTo(original.isDone());
        });
    }

    @Test
    @DisplayName("Given a list of items, when converting them to DTOs, then they should have the same properties")
    public void givenAListOfItems_WhenConvertingThemToDtos_ThenTheyShouldHaveTheSameProperties() {
        final int itemsCount = 5;
        List<TodoItem> originals = IntStream.range(0, itemsCount)
                .mapToObj(el -> createItem())
                .collect(Collectors.toList());

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

                softly.assertThat(dto.getCommentary())
                        .as("Task's description (nullable)")
                        .isEqualTo(original.getCommentary());

                softly.assertThat(dto.getCreatedOn())
                        .as("Creation date")
                        .isEqualTo(original.getCreatedOn());

                softly.assertThat(dto.isDone())
                        .as("Completeness")
                        .isEqualTo(original.isDone());
            });
        }
    }

}

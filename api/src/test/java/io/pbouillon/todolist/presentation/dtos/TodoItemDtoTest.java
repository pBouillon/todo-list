package io.pbouillon.todolist.presentation.dtos;

import io.pbouillon.todolist.domain.entities.TodoItem;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

/**
 * Unit test suite for the {@link TodoItemDto}
 * @see TodoItemDto
 */
public class TodoItemDtoTest {

    @Test
    public void givenATodoItem_WhenConvertingItToADTO_ThenItShouldHaveTheSameProperties() {
        TodoItem expected = new TodoItem(
                UUID.randomUUID().toString(),
                "Do the dishes",
                "Do not forget to empty the dishwasher too !",
                Instant.now(),
                true);

        TodoItemDto actual = TodoItemDto.FromTodoItem(expected);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actual.getId())
                    .as("Identifier")
                    .isEqualTo(expected.getId());

            softly.assertThat(actual.getTitle())
                    .as("Task's title")
                    .isEqualTo(expected.getTitle());

            softly.assertThat(actual.getCommentary())
                    .as("Task's description (nullable)")
                    .isEqualTo(expected.getCommentary());

            softly.assertThat(actual.getCreatedOn())
                    .as("Creation date")
                    .isEqualTo(expected.getCreatedOn());

            softly.assertThat(actual.isDone())
                    .as("Completeness")
                    .isEqualTo(expected.isDone());
        });
    }

}

package io.pbouillon.todolist.application.commons.cqrs;

import io.pbouillon.todolist.application.commons.cqrs.Query;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

@Data
public abstract class PageableQuery<TReturned> implements Query<TReturned> {

    /**
     * The offset of the first page
     */
    public static final int FIRST_PAGE_OFFSET = 0;

    /**
     * The default number of items per page
     */
    public static final int ITEMS_PER_PAGE_DEFAULT_VALUE = 10;

    /**
     * The number of elements in one page
     */
    protected int itemsPerPages = ITEMS_PER_PAGE_DEFAULT_VALUE;

    /**
     * The offset of the page to fetch
     */
    protected int pageOffset = 0;

    /**
     * Get the {@link PageRequest} for this query according to its field
     * @return The {@link PageRequest} for this query
     */
    public PageRequest getPageRequest() {
        return PageRequest.of(pageOffset, itemsPerPages);
    }

}

package tech.jitao.httpapidemo.common.dto;

import java.util.Collections;
import java.util.List;

public class PageDto<T> {
    private static final PageDto<?> EMPTY = new PageDto<>();

    private List<T> items = Collections.emptyList();
    private String nextPageToken;
    private Long total;

    @SuppressWarnings("unchecked")
    public static final <T> PageDto<T> empty() {
        return (PageDto<T>) EMPTY;
    }

    public static <T> PageDto<T> of(List<T> items) {
        PageDto<T> dto = new PageDto<>();
        dto.setItems(items);

        return dto;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}

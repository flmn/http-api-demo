package tech.jitao.httpapidemo.common.dto;

import java.util.Collections;
import java.util.List;

public class ListDto<T> {
    private static final ListDto<?> EMPTY = new ListDto<>();

    private List<T> items = Collections.emptyList();

    @SuppressWarnings("unchecked")
    public static final <T> ListDto<T> empty() {
        return (ListDto<T>) EMPTY;
    }

    public static <T> ListDto<T> of(List<T> items) {
        ListDto<T> dto = new ListDto<>();
        dto.setItems(items);

        return dto;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}

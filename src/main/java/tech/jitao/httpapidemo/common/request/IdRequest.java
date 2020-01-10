package tech.jitao.httpapidemo.common.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class IdRequest {

    @NotNull
    @Positive
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

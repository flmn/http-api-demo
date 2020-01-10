package tech.jitao.httpapidemo.common.request;

import javax.validation.constraints.Size;

public class LoadMoreRequest {

    @Size(min = 1)
    private String nextPageToken;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }
}

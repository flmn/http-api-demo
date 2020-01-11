package tech.jitao.httpapidemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import tech.jitao.httpapidemo.entity.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DocumentDto {
    private Long id;
    private UserDto owner;
    private String title;
    private String content;
    private BigDecimal price;
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public DocumentDto() {
    }

    public DocumentDto(Document document, boolean detail) {
        id = document.getId();
        title = document.getTitle();
        price = document.getPrice();
        status = document.getStatus();
        updateTime = document.getUpdateTime();

        if (detail) {
            content = document.getContent();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}

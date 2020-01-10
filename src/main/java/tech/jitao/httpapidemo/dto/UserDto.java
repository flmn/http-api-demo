package tech.jitao.httpapidemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import tech.jitao.httpapidemo.entity.User;
import tech.jitao.httpapidemo.util.BooleanString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDto {
    private String id;
    private String name;
    private String avatar;

    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private BigDecimal balance;
    private String admin;
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String accessToken;

    public UserDto() {
    }

    public UserDto(User user, boolean detail) {
        id = String.valueOf(user.getId());
        name = user.getScreenName();
        avatar = user.getAvatar();

        if (detail) {
            username = user.getUsername();
            birthday = user.getBirthday();
            balance = user.getBalance();
            admin = BooleanString.toString(user.getIsAdmin() == 1);
            status = user.getStatus();
            lastLoginTime = user.getLastLoginTime();
            createTime = user.getCreateTime();
            updateTime = user.getUpdateTime();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

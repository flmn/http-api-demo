package tech.jitao.httpapidemo.repository;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import tech.jitao.httpapidemo.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountRepository {
    public Optional<User> findById(long id) {
        User user = new User();
        user.setId(id);
        user.setUsername(RandomStringUtils.randomAlphanumeric(8));
        user.setPassword(RandomStringUtils.randomAlphanumeric(32));
        user.setScreenName("唐伯虎");
        user.setAvatar("static/flutter-logo.png");
        user.setBirthday(LocalDate.now());
        user.setBalance(new BigDecimal("102.4"));
        user.setIsAdmin(0);
        user.setStatus(1);
        user.setLastLoginTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        return Optional.of(user);
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        User user = new User();
        user.setId(RandomUtils.nextLong());
        user.setUsername(username);
        user.setPassword(password);
        user.setScreenName("唐伯虎");
        user.setAvatar("static/flutter-logo.png");
        user.setBirthday(LocalDate.now());
        user.setBalance(new BigDecimal("102.4"));
        user.setIsAdmin(0);
        user.setStatus(1);
        user.setLastLoginTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        return Optional.of(user);
    }

    public void save(User user) {
        // noop
    }
}

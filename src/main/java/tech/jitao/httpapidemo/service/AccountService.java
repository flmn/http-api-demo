package tech.jitao.httpapidemo.service;

import com.google.common.base.Strings;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tech.jitao.httpapidemo.common.MessageException;
import tech.jitao.httpapidemo.config.RedisKeys;
import tech.jitao.httpapidemo.config.profile.ProfileConfig;
import tech.jitao.httpapidemo.dto.UserDto;
import tech.jitao.httpapidemo.entity.User;
import tech.jitao.httpapidemo.repository.AccountRepository;
import tech.jitao.httpapidemo.util.UuidHelper;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class AccountService {
    private static final String TEST_TOKEN = "D8d8lKFtXdQ15Y5VoZm2UHQI8UZGGk17kArOFR7I";
    private static final int LOGIN_TTL = 7; // days
    private static final String DEFAULT_AVATAR = "static/flutter-logo.png";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProfileConfig profileConfig;
    private final AccountRepository accountRepository;
    private final StringRedisTemplate redis;

    public AccountService(ProfileConfig profileConfig,
                          AccountRepository accountRepository,
                          StringRedisTemplate redis) {
        this.profileConfig = profileConfig;
        this.accountRepository = accountRepository;
        this.redis = redis;
    }

    public UserDto login(String username, String password) throws MessageException {
        // check
        Optional<User> opt = accountRepository.findByUsernameAndPassword(username, password);
        if (!opt.isPresent()) {
            throw new MessageException("用户名或密码错误");
        }

        User user = opt.get();

        // gen token and save to redis
        String token = genToken();
        String keyToken = String.format(RedisKeys.SESSION, token);
        HashOperations<String, String, String> hashOperations = redis.opsForHash();
        hashOperations.put(keyToken, "userId", String.valueOf(user.getId()));
        redis.expire(keyToken, LOGIN_TTL, TimeUnit.DAYS);
        logger.info("User {}({}) login with token {}", user.getUsername(), user.getId(), token);

        user.setLastLoginTime(LocalDateTime.now());
        accountRepository.save(user);

        UserDto dto = new UserDto(opt.get(), true);
        dto.setAvatar(profileConfig.getCdnUrl(dto.getAvatar()));
        dto.setAccessToken(token);

        return dto;
    }

    public void logout(String accessToken) {
        if (!Strings.isNullOrEmpty(accessToken)) {
            String keyToken = String.format(RedisKeys.SESSION, accessToken);
            redis.delete(keyToken);

            logger.info("User with access token {} logout.", accessToken);
        }
    }

    public UserDto get(long id) {
        Optional<User> opt = accountRepository.findById(id);
        if (!opt.isPresent()) {
            UserDto dto = new UserDto();
            dto.setId(id);
            dto.setName("已重置");
            dto.setAvatar(profileConfig.getCdnUrl(DEFAULT_AVATAR));

            return dto;
        }

        UserDto dto = new UserDto(opt.get(), false);
        dto.setAvatar(profileConfig.getCdnUrl(dto.getAvatar()));

        return dto;
    }

    public long getUserIdByToken(String token) {
        String key = String.format(RedisKeys.SESSION, token);

        Object userId = redis.opsForHash().get(key, "userId");

        return NumberUtils.toLong(userId == null ? null : userId.toString(), 0L);
    }

    private String genToken() {
        return profileConfig.getEnv() == ProfileConfig.ENV_TEST
                ? TEST_TOKEN
                : RandomStringUtils.randomAlphanumeric(8) + UuidHelper.gen() + RandomStringUtils.randomAlphanumeric(8);
    }
}

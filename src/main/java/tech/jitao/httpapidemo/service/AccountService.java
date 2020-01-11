package tech.jitao.httpapidemo.service;

import com.google.common.base.Strings;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.jitao.httpapidemo.common.MessageException;
import tech.jitao.httpapidemo.config.profile.ProfileConfig;
import tech.jitao.httpapidemo.dto.UserDto;
import tech.jitao.httpapidemo.entity.User;
import tech.jitao.httpapidemo.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountService {
    private static final String DEFAULT_AVATAR = "static/avatar.jpg";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProfileConfig profileConfig;
    private final AccountRepository accountRepository;

    public AccountService(ProfileConfig profileConfig,
                          AccountRepository accountRepository) {
        this.profileConfig = profileConfig;
        this.accountRepository = accountRepository;
    }

    public UserDto login(String username, String password) throws MessageException {
        // check
        Optional<User> opt = accountRepository.findByUsernameAndPassword(username, password);
        if (!opt.isPresent()) {
            throw new MessageException("用户名或密码错误");
        }

        // login ok
        logger.info("User {} login.", username);

        UserDto dto = new UserDto(opt.get(), true);
        dto.setAvatar(profileConfig.getCdnUrl(dto.getAvatar()));
        dto.setAccessToken(RandomStringUtils.randomAlphanumeric(40));

        return dto;
    }

    public void logout(String accessToken) {
        if (!Strings.isNullOrEmpty(accessToken)) {
            // clear session

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
}

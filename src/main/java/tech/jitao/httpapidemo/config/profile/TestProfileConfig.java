package tech.jitao.httpapidemo.config.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"test", "default", "dev"})
public class TestProfileConfig implements ProfileConfig {

    @Override
    public int getEnv() {
        return ENV_TEST;
    }

    @Override
    public String getCdnUrl(String key) {
        return String.format("https://www.jitao.tech/%s", key);
    }
}

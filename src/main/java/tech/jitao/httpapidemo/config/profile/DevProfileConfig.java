package tech.jitao.httpapidemo.config.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"default", "dev"})
public class DevProfileConfig implements ProfileConfig {

    @Override
    public int getEnv() {
        return ENV_DEV;
    }

    @Override
    public String getCdnUrl(String key) {
        return String.format("https://www.jitao.tech/%s", key);
    }
}

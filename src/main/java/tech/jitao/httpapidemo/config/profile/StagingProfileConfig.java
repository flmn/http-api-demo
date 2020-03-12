package tech.jitao.httpapidemo.config.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("staging")
public class StagingProfileConfig implements ProfileConfig {

    @Override
    public int getEnv() {
        return ENV_STAGING;
    }

    @Override
    public String getCdnUrl(String key) {
        return String.format("https://www.jitao.tech/%s", key);
    }
}

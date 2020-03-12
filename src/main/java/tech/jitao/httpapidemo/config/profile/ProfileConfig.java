package tech.jitao.httpapidemo.config.profile;

public interface ProfileConfig {
    int ENV_PROD = 1;
    int ENV_STAGING = 2;
    int ENV_DEV = 3;

    int getEnv();

    String getCdnUrl(String key);
}

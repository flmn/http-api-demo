package tech.jitao.httpapidemo.config.profile;

public interface ProfileConfig {
    int ENV_TEST = 1;
    int ENV_PROD = 2;

    int getEnv();

    String getCdnUrl(String key);
}

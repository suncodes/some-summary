package suncodes.opensource.profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@ComponentScan(basePackages = "suncodes.opensource.profile")
@Configuration
public class MyProfileConfig {

    @Profile("dev")
    @Bean
    MyProfile myProfileDev() {
        MyProfile myProfile = new MyProfile();
        myProfile.setEnv("这是开发环境");
        return myProfile;
    }

    @Profile("test")
    @Bean
    MyProfile myProfileTest() {
        MyProfile myProfile = new MyProfile();
        myProfile.setEnv("这是测试环境");
        return myProfile;
    }
}

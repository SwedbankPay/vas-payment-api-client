package com.payex.vas.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

@Slf4j
@SpringBootApplication
public class Application {

    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";


    public static void main(String[] args) throws UnknownHostException {
        var app = new SpringApplication(Application.class);
        setDefaultSpringProfile(app);

        var env = app.run(args).getEnvironment();

        log.info("\n--------------------------------------------------------------------\n\t" +
                "Application '{}' v.{} is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:{}\n\t" +
                "External: \thttp://{}:{}\n\t" +
                "PID: \t\t{}\n\t" +
                "Profile(s): {}\n--------------------------------------------------------------------",
            env.getProperty("spring.application.name"),
            Application.class.getPackage().getImplementationVersion(),
            env.getProperty("server.port"),
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"),
            env.getProperty("PID"),
            getActiveProfiles(env));

    }

    private static String[] getActiveProfiles(Environment env) {
        var profiles = env.getActiveProfiles();
        return profiles.length == 0 ? env.getDefaultProfiles() : profiles;
    }

    private static void setDefaultSpringProfile(SpringApplication app) {
        var defProperties = new HashMap<String, Object>();
        defProperties.put(SPRING_PROFILE_DEFAULT, "dev");
        app.setDefaultProperties(defProperties);
    }

}

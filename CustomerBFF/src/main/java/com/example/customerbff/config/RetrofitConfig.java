package com.example.customerbff.config;

/**
 * @author Wenyu Chen
 * @version 1.0.0
 * @ClassName RetrofitConfig.java
 * @andrewID wenyuc2
 * @Description TODO
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Bean
    public Retrofit retrofit() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        JacksonConverterFactory converterFactory = JacksonConverterFactory.create(objectMapper);

        return new Retrofit.Builder()
                .baseUrl("http://a96e79df4fbe1457a80db0ba1fcfb416-995551394.us-east-1.elb.amazonaws.com:3030")
                .client(httpClient)
                .addConverterFactory(converterFactory)
                .build();
    }
}


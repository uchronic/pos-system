package com.pos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("POS收银系统 API")
                        .version("1.0.0")
                        .description("零售收银管理系统接口文档 - 包含商品管理、会员管理、营销管理、进销存等核心模块"));
    }
}

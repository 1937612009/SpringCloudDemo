
package com.example.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * 
 * [Swagger-API管理] <br> 
 *  
 * @author [li.qiong]<br>
 * @version 1.0.0<br>
 * @CreateDate 2020年4月6日 <br>
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.ruiqing")).paths(PathSelectors.any()).build()
				.securityContexts(securityContexts()).securitySchemes(securitySchemes());
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("Spring Cloud Demo测试", "", "1937612009@qq.com");
		return new ApiInfoBuilder().title("Demo REST 接口").description("restful api swagger构建")
				.version("1.0.0").contact(contact).build();
	}

	private List<ApiKey> securitySchemes() {
		return Lists.newArrayList(new ApiKey("token", "token", "header"), new ApiKey("loginPlat", "loginPlat", "header"));
	}

	private List<SecurityContext> securityContexts() {

		SecurityContext context = SecurityContext.builder().securityReferences(defaultAuth()).build();

		return Lists.newArrayList(context);

	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference("token", authorizationScopes), new SecurityReference("loginPlat", authorizationScopes));

	}

}

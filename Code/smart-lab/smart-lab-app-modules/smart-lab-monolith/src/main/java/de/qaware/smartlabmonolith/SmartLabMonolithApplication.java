package de.qaware.smartlabmonolith;

import com.fasterxml.classmate.TypeResolver;
import de.qaware.smartlabaction.annotation.EnableSmartLabActionService;
import de.qaware.smartlabassistance.annotation.EnableAssistanceService;
import de.qaware.smartlabtrigger.annotation.EnableSmartLabTriggerService;
import de.qaware.smartlabworkgroup.annotation.EnableSmartLabWorkgroupService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

@SpringBootApplication(scanBasePackageClasses = {
		de.qaware.smartlabmeeting.ComponentScanMarker.class,
		de.qaware.smartlabroom.ComponentScanMarker.class,
		de.qaware.smartlabdevice.ComponentScanMarker.class,
		de.qaware.smartlabperson.ComponentScanMarker.class,
		de.qaware.smartlabjob.ComponentScanMarker.class,
		de.qaware.smartlabmonolith.ComponentScanMarker.class})
@EnableSmartLabWorkgroupService
@EnableSmartLabTriggerService
@EnableAssistanceService
@EnableSmartLabActionService
@EnableSwagger2
public class SmartLabMonolithApplication {

	private final TypeResolver typeResolver;

	public static void main(String[] args) {
		SpringApplication.run(SmartLabMonolithApplication.class, args);
	}

	public SmartLabMonolithApplication(TypeResolver typeResolver) {
		this.typeResolver = typeResolver;
	}

	@Bean
	public Docket petApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.directModelSubstitute(LocalDate.class,
						String.class)
				.genericModelSubstitutes(ResponseEntity.class)
				.alternateTypeRules(
						newRule(typeResolver.resolve(DeferredResult.class,
								typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
								typeResolver.resolve(WildcardType.class)))
				.useDefaultResponseMessages(false)
//				.globalResponseMessage(RequestMethod.GET,
//						newArrayList(new ResponseMessageBuilder()
//								.code(500)
//								.message("500 message")
//								.responseModel(new ModelRef("Error"))
//								.build()))
				.securitySchemes(newArrayList(apiKey()))
				.securityContexts(newArrayList(securityContext()))
				.enableUrlTemplating(true)
//				.globalOperationParameters(
//						newArrayList(new ParameterBuilder()
//								.name("someGlobalParameter")
//								.description("Description of someGlobalParameter")
//								.modelRef(new ModelRef("string"))
//								.parameterType("query")
//								.required(true)
//								.build()))
//				.tags(new Tag("Pet Service", "All apis relating to pets"))
//				.additionalModels(typeResolver.resolve(AdditionalModel.class))
				;
	}



	private ApiKey apiKey() {
		return new ApiKey("mykey", "api_key", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("/anyPath.*"))
				.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope
				= new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return newArrayList(
				new SecurityReference("mykey", authorizationScopes));
	}

	@Bean
	SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder()
				.clientId("test-app-client-id")
				.clientSecret("test-app-client-secret")
				.realm("test-app-realm")
				.appName("test-app")
				.scopeSeparator(",")
				.additionalQueryStringParams(null)
				.useBasicAuthenticationWithAccessCodeGrant(false)
				.build();
	}

	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder()
				.deepLinking(true)
				.displayOperationId(false)
				.defaultModelsExpandDepth(1)
				.defaultModelExpandDepth(1)
				.defaultModelRendering(ModelRendering.EXAMPLE)
				.displayRequestDuration(false)
				.docExpansion(DocExpansion.NONE)
				.filter(false)
				.maxDisplayedTags(null)
				.operationsSorter(OperationsSorter.ALPHA)
				.showExtensions(false)
				.tagsSorter(TagsSorter.ALPHA)
				.validatorUrl(null)
				.build();
	}
}

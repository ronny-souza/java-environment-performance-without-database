package br.com.rmsa.benchmark.configuration;

import br.com.rmsa.benchmark.model.properties.OpenAPIProperties;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OpenAPIConfigurationTest {

    @InjectMocks
    private OpenAPIConfiguration openAPIConfiguration;

    @Test
    @DisplayName("Checking if the OpenAPI configuration was properly built")
    void checkingIfOpenAPIConfigurationWasProperlyBuilt() {
        /* Arrange */
        OpenAPIProperties properties = new OpenAPIProperties();
        properties.setDeveloper("Ronyeri Marinho");
        properties.setDeveloperContact("ronyeri@example.com");
        properties.setDeveloperUrl("http://localhost:8080");
        properties.setTitle("Runtime Environment Benchmark");
        properties.setDescription("API for reading and writing CSV files, as well as compressing and decompressing files.");
        properties.setVersion("1.0.0");

        /* Act */
        OpenAPI openAPI = this.openAPIConfiguration.customizeOpenAPI(properties);

        /* Assert */
        Assertions.assertNotNull(openAPI);
        Assertions.assertEquals(properties.getDeveloper(), openAPI.getInfo().getContact().getName());
        Assertions.assertEquals(properties.getDeveloperContact(), openAPI.getInfo().getContact().getEmail());
        Assertions.assertEquals(properties.getDeveloperUrl(), openAPI.getInfo().getContact().getUrl());
        Assertions.assertEquals(properties.getTitle(), openAPI.getInfo().getTitle());
        Assertions.assertEquals(properties.getDescription(), openAPI.getInfo().getDescription());
        Assertions.assertEquals(properties.getVersion(), openAPI.getInfo().getVersion());
    }
}
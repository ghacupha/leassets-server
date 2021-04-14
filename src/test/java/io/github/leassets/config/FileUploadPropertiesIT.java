package io.github.leassets.config;

import io.github.leassets.LeassetsServerApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This is to test whether the file-upload configurations will be setup at startup
 */
@SpringBootTest(classes = LeassetsServerApp.class)
public class FileUploadPropertiesIT {

    @Autowired
    private FileUploadsProperties fileUploadsProperties;

    @Test
    public void whenFactoryProvidedThenYamlPropertiesInjected() {
        assertThat(fileUploadsProperties.getListSize()).isEqualTo(5);
    }
}

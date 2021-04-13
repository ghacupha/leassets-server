package io.github.leassets.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LeassetsFileUploadMapperTest {

    private LeassetsFileUploadMapper leassetsFileUploadMapper;

    @BeforeEach
    public void setUp() {
        leassetsFileUploadMapper = new LeassetsFileUploadMapperImpl();
    }
}

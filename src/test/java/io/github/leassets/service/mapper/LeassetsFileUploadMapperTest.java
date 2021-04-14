package io.github.leassets.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LeassetsFileUploadMapperTest {

    private LeassetsFileUploadMapper leassetsFileUploadMapper;

    @BeforeEach
    public void setUp() {
        leassetsFileUploadMapper = new LeassetsFileUploadMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(leassetsFileUploadMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(leassetsFileUploadMapper.fromId(null)).isNull();
    }
}

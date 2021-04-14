package io.github.leassets.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LeassetsMessageTokenMapperTest {

    private LeassetsMessageTokenMapper leassetsMessageTokenMapper;

    @BeforeEach
    public void setUp() {
        leassetsMessageTokenMapper = new LeassetsMessageTokenMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(leassetsMessageTokenMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(leassetsMessageTokenMapper.fromId(null)).isNull();
    }
}

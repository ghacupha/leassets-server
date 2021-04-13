package io.github.leassets.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LeassetsMessageTokenMapperTest {

    private LeassetsMessageTokenMapper leassetsMessageTokenMapper;

    @BeforeEach
    public void setUp() {
        leassetsMessageTokenMapper = new LeassetsMessageTokenMapperImpl();
    }
}

package io.github.leassets.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CurrencyTableMapperTest {

    private CurrencyTableMapper currencyTableMapper;

    @BeforeEach
    public void setUp() {
        currencyTableMapper = new CurrencyTableMapperImpl();
    }
}

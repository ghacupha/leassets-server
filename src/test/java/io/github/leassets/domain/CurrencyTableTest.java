package io.github.leassets.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.leassets.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CurrencyTableTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrencyTable.class);
        CurrencyTable currencyTable1 = new CurrencyTable();
        currencyTable1.setId(1L);
        CurrencyTable currencyTable2 = new CurrencyTable();
        currencyTable2.setId(currencyTable1.getId());
        assertThat(currencyTable1).isEqualTo(currencyTable2);
        currencyTable2.setId(2L);
        assertThat(currencyTable1).isNotEqualTo(currencyTable2);
        currencyTable1.setId(null);
        assertThat(currencyTable1).isNotEqualTo(currencyTable2);
    }
}

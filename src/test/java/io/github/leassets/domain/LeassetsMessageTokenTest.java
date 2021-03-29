package io.github.leassets.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.leassets.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeassetsMessageTokenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeassetsMessageToken.class);
        LeassetsMessageToken leassetsMessageToken1 = new LeassetsMessageToken();
        leassetsMessageToken1.setId(1L);
        LeassetsMessageToken leassetsMessageToken2 = new LeassetsMessageToken();
        leassetsMessageToken2.setId(leassetsMessageToken1.getId());
        assertThat(leassetsMessageToken1).isEqualTo(leassetsMessageToken2);
        leassetsMessageToken2.setId(2L);
        assertThat(leassetsMessageToken1).isNotEqualTo(leassetsMessageToken2);
        leassetsMessageToken1.setId(null);
        assertThat(leassetsMessageToken1).isNotEqualTo(leassetsMessageToken2);
    }
}

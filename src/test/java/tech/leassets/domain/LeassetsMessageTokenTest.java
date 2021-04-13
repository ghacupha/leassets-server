package tech.leassets.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tech.leassets.web.rest.TestUtil;

public class LeassetsMessageTokenTest {

    @Test
    public void equalsVerifier() throws Exception {
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

package io.github.leassets.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.leassets.web.rest.TestUtil;

public class LeassetsMessageTokenDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeassetsMessageTokenDTO.class);
        LeassetsMessageTokenDTO leassetsMessageTokenDTO1 = new LeassetsMessageTokenDTO();
        leassetsMessageTokenDTO1.setId(1L);
        LeassetsMessageTokenDTO leassetsMessageTokenDTO2 = new LeassetsMessageTokenDTO();
        assertThat(leassetsMessageTokenDTO1).isNotEqualTo(leassetsMessageTokenDTO2);
        leassetsMessageTokenDTO2.setId(leassetsMessageTokenDTO1.getId());
        assertThat(leassetsMessageTokenDTO1).isEqualTo(leassetsMessageTokenDTO2);
        leassetsMessageTokenDTO2.setId(2L);
        assertThat(leassetsMessageTokenDTO1).isNotEqualTo(leassetsMessageTokenDTO2);
        leassetsMessageTokenDTO1.setId(null);
        assertThat(leassetsMessageTokenDTO1).isNotEqualTo(leassetsMessageTokenDTO2);
    }
}

package io.github.leassets.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.leassets.web.rest.TestUtil;

public class LeassetsFileTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeassetsFileType.class);
        LeassetsFileType leassetsFileType1 = new LeassetsFileType();
        leassetsFileType1.setId(1L);
        LeassetsFileType leassetsFileType2 = new LeassetsFileType();
        leassetsFileType2.setId(leassetsFileType1.getId());
        assertThat(leassetsFileType1).isEqualTo(leassetsFileType2);
        leassetsFileType2.setId(2L);
        assertThat(leassetsFileType1).isNotEqualTo(leassetsFileType2);
        leassetsFileType1.setId(null);
        assertThat(leassetsFileType1).isNotEqualTo(leassetsFileType2);
    }
}

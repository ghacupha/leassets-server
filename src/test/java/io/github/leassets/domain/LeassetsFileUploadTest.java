package io.github.leassets.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.leassets.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeassetsFileUploadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeassetsFileUpload.class);
        LeassetsFileUpload leassetsFileUpload1 = new LeassetsFileUpload();
        leassetsFileUpload1.setId(1L);
        LeassetsFileUpload leassetsFileUpload2 = new LeassetsFileUpload();
        leassetsFileUpload2.setId(leassetsFileUpload1.getId());
        assertThat(leassetsFileUpload1).isEqualTo(leassetsFileUpload2);
        leassetsFileUpload2.setId(2L);
        assertThat(leassetsFileUpload1).isNotEqualTo(leassetsFileUpload2);
        leassetsFileUpload1.setId(null);
        assertThat(leassetsFileUpload1).isNotEqualTo(leassetsFileUpload2);
    }
}

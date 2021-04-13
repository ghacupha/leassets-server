package tech.leassets.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tech.leassets.web.rest.TestUtil;

public class LeassetsFileUploadDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeassetsFileUploadDTO.class);
        LeassetsFileUploadDTO leassetsFileUploadDTO1 = new LeassetsFileUploadDTO();
        leassetsFileUploadDTO1.setId(1L);
        LeassetsFileUploadDTO leassetsFileUploadDTO2 = new LeassetsFileUploadDTO();
        assertThat(leassetsFileUploadDTO1).isNotEqualTo(leassetsFileUploadDTO2);
        leassetsFileUploadDTO2.setId(leassetsFileUploadDTO1.getId());
        assertThat(leassetsFileUploadDTO1).isEqualTo(leassetsFileUploadDTO2);
        leassetsFileUploadDTO2.setId(2L);
        assertThat(leassetsFileUploadDTO1).isNotEqualTo(leassetsFileUploadDTO2);
        leassetsFileUploadDTO1.setId(null);
        assertThat(leassetsFileUploadDTO1).isNotEqualTo(leassetsFileUploadDTO2);
    }
}

package com.morixa.adminagro.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.morixa.adminagro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CampoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampoDTO.class);
        CampoDTO campoDTO1 = new CampoDTO();
        campoDTO1.setId(1L);
        CampoDTO campoDTO2 = new CampoDTO();
        assertThat(campoDTO1).isNotEqualTo(campoDTO2);
        campoDTO2.setId(campoDTO1.getId());
        assertThat(campoDTO1).isEqualTo(campoDTO2);
        campoDTO2.setId(2L);
        assertThat(campoDTO1).isNotEqualTo(campoDTO2);
        campoDTO1.setId(null);
        assertThat(campoDTO1).isNotEqualTo(campoDTO2);
    }
}

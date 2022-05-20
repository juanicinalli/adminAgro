package com.morixa.adminagro.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.morixa.adminagro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrabajoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrabajoDTO.class);
        TrabajoDTO trabajoDTO1 = new TrabajoDTO();
        trabajoDTO1.setId(1L);
        TrabajoDTO trabajoDTO2 = new TrabajoDTO();
        assertThat(trabajoDTO1).isNotEqualTo(trabajoDTO2);
        trabajoDTO2.setId(trabajoDTO1.getId());
        assertThat(trabajoDTO1).isEqualTo(trabajoDTO2);
        trabajoDTO2.setId(2L);
        assertThat(trabajoDTO1).isNotEqualTo(trabajoDTO2);
        trabajoDTO1.setId(null);
        assertThat(trabajoDTO1).isNotEqualTo(trabajoDTO2);
    }
}

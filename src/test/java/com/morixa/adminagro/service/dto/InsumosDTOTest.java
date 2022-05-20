package com.morixa.adminagro.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.morixa.adminagro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InsumosDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsumosDTO.class);
        InsumosDTO insumosDTO1 = new InsumosDTO();
        insumosDTO1.setId(1L);
        InsumosDTO insumosDTO2 = new InsumosDTO();
        assertThat(insumosDTO1).isNotEqualTo(insumosDTO2);
        insumosDTO2.setId(insumosDTO1.getId());
        assertThat(insumosDTO1).isEqualTo(insumosDTO2);
        insumosDTO2.setId(2L);
        assertThat(insumosDTO1).isNotEqualTo(insumosDTO2);
        insumosDTO1.setId(null);
        assertThat(insumosDTO1).isNotEqualTo(insumosDTO2);
    }
}

package com.morixa.adminagro.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.morixa.adminagro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ManejoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManejoDTO.class);
        ManejoDTO manejoDTO1 = new ManejoDTO();
        manejoDTO1.setId(1L);
        ManejoDTO manejoDTO2 = new ManejoDTO();
        assertThat(manejoDTO1).isNotEqualTo(manejoDTO2);
        manejoDTO2.setId(manejoDTO1.getId());
        assertThat(manejoDTO1).isEqualTo(manejoDTO2);
        manejoDTO2.setId(2L);
        assertThat(manejoDTO1).isNotEqualTo(manejoDTO2);
        manejoDTO1.setId(null);
        assertThat(manejoDTO1).isNotEqualTo(manejoDTO2);
    }
}

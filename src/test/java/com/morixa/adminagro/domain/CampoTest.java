package com.morixa.adminagro.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.morixa.adminagro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CampoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Campo.class);
        Campo campo1 = new Campo();
        campo1.setId(1L);
        Campo campo2 = new Campo();
        campo2.setId(campo1.getId());
        assertThat(campo1).isEqualTo(campo2);
        campo2.setId(2L);
        assertThat(campo1).isNotEqualTo(campo2);
        campo1.setId(null);
        assertThat(campo1).isNotEqualTo(campo2);
    }
}

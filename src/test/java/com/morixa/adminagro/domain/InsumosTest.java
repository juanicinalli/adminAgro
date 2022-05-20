package com.morixa.adminagro.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.morixa.adminagro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InsumosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Insumos.class);
        Insumos insumos1 = new Insumos();
        insumos1.setId(1L);
        Insumos insumos2 = new Insumos();
        insumos2.setId(insumos1.getId());
        assertThat(insumos1).isEqualTo(insumos2);
        insumos2.setId(2L);
        assertThat(insumos1).isNotEqualTo(insumos2);
        insumos1.setId(null);
        assertThat(insumos1).isNotEqualTo(insumos2);
    }
}

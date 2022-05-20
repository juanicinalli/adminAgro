package com.morixa.adminagro.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.morixa.adminagro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ManejoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Manejo.class);
        Manejo manejo1 = new Manejo();
        manejo1.setId(1L);
        Manejo manejo2 = new Manejo();
        manejo2.setId(manejo1.getId());
        assertThat(manejo1).isEqualTo(manejo2);
        manejo2.setId(2L);
        assertThat(manejo1).isNotEqualTo(manejo2);
        manejo1.setId(null);
        assertThat(manejo1).isNotEqualTo(manejo2);
    }
}

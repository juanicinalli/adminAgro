package com.morixa.adminagro.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManejoMapperTest {

    private ManejoMapper manejoMapper;

    @BeforeEach
    public void setUp() {
        manejoMapper = new ManejoMapperImpl();
    }
}

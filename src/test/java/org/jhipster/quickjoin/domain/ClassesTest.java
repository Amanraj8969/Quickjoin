package org.jhipster.quickjoin.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.quickjoin.domain.ClassesTestSamples.*;

import org.jhipster.quickjoin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classes.class);
        Classes classes1 = getClassesSample1();
        Classes classes2 = new Classes();
        assertThat(classes1).isNotEqualTo(classes2);

        classes2.setId(classes1.getId());
        assertThat(classes1).isEqualTo(classes2);

        classes2 = getClassesSample2();
        assertThat(classes1).isNotEqualTo(classes2);
    }
}

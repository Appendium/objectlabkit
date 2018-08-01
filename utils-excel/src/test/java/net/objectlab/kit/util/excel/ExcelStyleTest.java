package net.objectlab.kit.util.excel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ExcelStyleTest {

    @Test
    public void testAll() {
        final ExcelStyle build = ExcelStyle.builder().bold().center().header().italic().underline().numericFormat().wrap().build();
        assertThat(build.isBold()).isTrue();
        assertThat(build.isCenter()).isTrue();
        assertThat(build.isLeft()).isFalse();
        assertThat(build.isRight()).isFalse();
        assertThat(build.isItalic()).isTrue();
        assertThat(build.isUnderline()).isTrue();
        assertThat(build.isNumericFormat()).isTrue();
        assertThat(build.isHeader()).isTrue();
        assertThat(build.isWrap()).isTrue();
        assertThat(build.isPercentFormat()).isFalse();
    }

    @Test
    public void testAll2() {
        final ExcelStyle build = ExcelStyle.builder().right().dataFormat("0.000%").build();
        assertThat(build.isRight()).isTrue();
        assertThat(build.getDataFormat()).isEqualTo("0.000%");
    }
}

package kz.gbk.eprocurement.common.jpa;

import org.joda.money.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class MoneyAttributeConverterTest {
    @Test
    public void shouldConvert100BucksAnd5CentsToDatabaseColumn() {
        MoneyAttributeConverter converter = new MoneyAttributeConverter();

        Money oneHundredKZT = Money.parse("USD 100.05");

        BigDecimal bigDecimal = converter.convertToDatabaseColumn(oneHundredKZT);

        assertEquals(new BigDecimal("100.05"), bigDecimal);
    }

    @Test
    public void shouldConvertToKZTEntityAttribute() {
        MoneyAttributeConverter converter = new MoneyAttributeConverter();

        Money oneHundredKZT = converter.convertToEntityAttribute(new BigDecimal("100.00"));
    }
}
package kz.gbk.eprocurement.common.jpa;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import javax.persistence.AttributeConverter;
import java.math.BigDecimal;

public class MoneyAttributeConverter implements AttributeConverter<Money, BigDecimal> {

    public static final String DEFAULT_CURRENCY = "KZT";

    @Override
    public BigDecimal convertToDatabaseColumn(Money attribute) {
        return attribute != null ? attribute.getAmount() : null;
    }

    @Override
    public Money convertToEntityAttribute(BigDecimal dbData) {
        return dbData != null ? Money.of(CurrencyUnit.getInstance(DEFAULT_CURRENCY), dbData) : null;
    }
}

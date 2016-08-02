package kz.gbk.eprocurement.purchase.model;

public enum ProcurementItemAttr {
    ATTR_ITEM_NO("itemNo"),
    ATTR_GSW_UNIQUE_NUMBER("gswUniqueNumber"),
    ATTR_GSW_NAME("gswName"),
    ATTR_GSW_SHORT_DESCRIPTION("gswShortDescription"),
    ATTR_GSW_ADDITIONAL_DESCRIPTION("gswAdditionalDescription"),
    ATTR_PAYMENT_CONDITIONS("gswPaymentConditions"),
    ATTR_TIME_PERIOD("timePeriod"),
    ATTR_PLACE_KATO_CODE("placeKatoCode"),
    ATTR_PLACE_ADDRESS("placeAddress"),
    ATTR_DELIVERY_DESTINATION("supplyDestination"),
    ATTR_DELIVERY_CONDITIONS("deliveryConditions"),
    ATTR_MEASUREMENT_UNIT("measurementUnit"),
    ATTR_ITEM_AMOUNT("itemAmount"),
    ATTR_MARKETING_UNIT_PRICE("marketingUnitPrice"),
    ATTR_TOTAL_COST("totalCost"),
    ATTR_TOTAL_COST_VAT("totalCostVAT"),
    ATTR_COMMENTS("comments");

    private final String propertyName;

    ProcurementItemAttr(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}

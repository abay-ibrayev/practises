package kz.gbk.eprocurement.common.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

@Embeddable
public class PhoneNumber implements Contact {

    public enum PhoneType {
        HOME, WORK, MOBILE, FAX
    }

    @Enumerated(EnumType.STRING)
    private PhoneType phoneType;

    private String countryCode;

    private String regionOrOperatorCode;

    private String localNumber;

    @SuppressWarnings(value = "unused")
    private PhoneNumber() {}

    public PhoneNumber(PhoneType phoneType, String countryCode, String regionOrOperatorCode, String localNumber) {
        this.phoneType = phoneType;
        this.countryCode = countryCode;
        this.regionOrOperatorCode = regionOrOperatorCode;
        this.localNumber = localNumber;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    @SuppressWarnings(value = "unused")
    private void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @SuppressWarnings(value = "unused")
    private void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegionOrOperatorCode() {
        return regionOrOperatorCode;
    }

    @SuppressWarnings(value = "unused")
    private void setRegionOrOperatorCode(String regionOrOperatorCode) {
        this.regionOrOperatorCode = regionOrOperatorCode;
    }

    public String getLocalNumber() {
        return localNumber;
    }

    @SuppressWarnings(value = "unused")
    private void setLocalNumber(String localNumber) {
        this.localNumber = localNumber;
    }

    @Transient
    public String getDisplayName() {
        StringBuilder builder = new StringBuilder();
        builder.append(countryCode);
        builder.append("-");
        builder.append(regionOrOperatorCode);
        builder.append("-");
        builder.append(localNumber);

        return builder.toString();
    }
}

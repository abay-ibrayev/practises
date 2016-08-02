package kz.gbk.eprocurement.purchase.model;

import kz.gbk.eprocurement.common.model.PhoneNumber;

import javax.persistence.*;

@Entity
@Table(name = "purchasing_parties")
public class PurchasingParty {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasing_parties")
    @SequenceGenerator(name = "purchasing_parties", sequenceName = "purchasing_parties_seq", allocationSize = 1)
    private Long id;

    private String shortName;

    private String fullName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "phoneType", column = @Column(name = "phone_type")),
            @AttributeOverride(name = "countryCode", column = @Column(name = "phone_country_code")),
            @AttributeOverride(name = "regionOrOperatorCode", column = @Column(name = "phone_region_op_code")),
            @AttributeOverride(name = "localNumber", column = @Column(name = "phone_local_number"))
    })
    private PhoneNumber phoneNumber;

    private String webSiteAddress;

    @ManyToOne
    @JoinTable(
            name = "purchasing_parties_parent_child",
            joinColumns = {@JoinColumn(name = "child_id")},
            inverseJoinColumns = {@JoinColumn(name = "parent_id")}
    )
    private PurchasingParty parent;

    public PurchasingParty(PurchasingParty parent) {
        this.parent = parent;
    }

    public PurchasingParty() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebSiteAddress() {
        return webSiteAddress;
    }

    public void setWebSiteAddress(String webSiteAddress) {
        this.webSiteAddress = webSiteAddress;
    }

    public PurchasingParty getParent() {
        return parent;
    }

    private void setParent(PurchasingParty parent) {
        this.parent = parent;
    }
}

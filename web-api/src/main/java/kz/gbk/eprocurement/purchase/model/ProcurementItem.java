package kz.gbk.eprocurement.purchase.model;

import kz.gbk.eprocurement.common.jpa.MoneyAttributeConverter;
import org.joda.money.Money;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "procurement_items")
public class ProcurementItem {

    public enum GSWType {
        GOODS, SERVICES, WORKS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procurement_items")
    @SequenceGenerator(name = "procurement_items", sequenceName = "procurement_items_seq", allocationSize = 1)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date finishDate;

    private String itemNo;

    private String gswUniqueNumber;

    private String gswName;

    private String gswShortDescription;

    private String gswAdditionalDescription;

    @Enumerated(EnumType.ORDINAL)
    private GSWType gswType;

    private int paymentConditions;

    private String measurementUnit;

    private Integer itemAmount;

    @Convert(converter = MoneyAttributeConverter.class)
    private Money marketingUnitPrice;

    @Convert(converter = MoneyAttributeConverter.class)
    private Money totalCost;

    @Column(name = "total_cost_VAT")
    @Convert(converter = MoneyAttributeConverter.class)
    private Money totalCostVAT;

    private String comments;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "procurement_plan_id")
    private ProcurementPlan plan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getGswUniqueNumber() {
        return gswUniqueNumber;
    }

    public void setGswUniqueNumber(String gswUniqueNumber) {
        this.gswUniqueNumber = gswUniqueNumber;
    }

    public String getGswName() {
        return gswName;
    }

    public void setGswName(String gswName) {
        this.gswName = gswName;
    }

    public String getGswShortDescription() {
        return gswShortDescription;
    }

    public void setGswShortDescription(String gswShortDescription) {
        this.gswShortDescription = gswShortDescription;
    }

    public String getGswAdditionalDescription() {
        return gswAdditionalDescription;
    }

    public void setGswAdditionalDescription(String gswAdditionalDescription) {
        this.gswAdditionalDescription = gswAdditionalDescription;
    }

    public GSWType getGswType() {
        return gswType;
    }

    public void setGswType(GSWType gswType) {
        this.gswType = gswType;
    }

    public int getPaymentConditions() {
        return paymentConditions;
    }

    public void setPaymentConditions(int paymentConditions) {
        this.paymentConditions = paymentConditions;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public Integer getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(Integer itemAmount) {
        this.itemAmount = itemAmount;
    }

    public Money getMarketingUnitPrice() {
        return marketingUnitPrice;
    }

    public void setMarketingUnitPrice(Money marketingUnitPrice) {
        this.marketingUnitPrice = marketingUnitPrice;
    }

    public Money getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Money totalCost) {
        this.totalCost = totalCost;
    }

    public Money getTotalCostVAT() {
        return totalCostVAT;
    }

    public void setTotalCostVAT(Money totalCostVAT) {
        this.totalCostVAT = totalCostVAT;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ProcurementPlan getPlan() {
        return plan;
    }

    void setPlan(ProcurementPlan plan) {
        this.plan = plan;
    }
}

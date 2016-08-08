package kz.gbk.eprocurement.tenders.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by abai on 25.07.2016.
 */

@Entity
@Table(name = "tender_lots")
public class TenderLot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tender_lots")
    @SequenceGenerator(name = "tender_lots", sequenceName = "tender_lots_seq", allocationSize = 1)
    private long id;

    @Column(name = "lot_number")
    private int lotNumber;

    @Column(name = "lot_name")
    private String lotName;

    @Column(name = "lot_desc")
    private String lotDesc;

    @Column(name = "lot_quantity")
    private BigDecimal lotQuantity;

    @Column(name = "lot_price")
    private BigDecimal lotPrice;

    @Column(name = "lot_sum")
    private BigDecimal lotSum;

    @Column(name = "lot_place")
    private String lotPlace;

    @Column(name = "lot_timeframe")
    private String lotTimeframe;

    @Column(name = "lot_condition")
    private String lotCondition;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(int lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public String getLotDesc() {
        return lotDesc;
    }

    public void setLotDesc(String lotDesc) {
        this.lotDesc = lotDesc;
    }

    public BigDecimal getLotPrice() {
        return lotPrice;
    }

    public void setLotPrice(BigDecimal lotPrice) {
        this.lotPrice = lotPrice;
    }

    public BigDecimal getLotQuantity() {
        return lotQuantity;
    }

    public void setLotQuantity(BigDecimal lotQuantity) {
        this.lotQuantity = lotQuantity;
    }

    public BigDecimal getLotSum() {
        return lotSum;
    }

    public void setLotSum(BigDecimal lotSum) {
        this.lotSum = lotSum;
    }

    public String getLotPlace() {
        return lotPlace;
    }

    public void setLotPlace(String lotPlace) {
        this.lotPlace = lotPlace;
    }

    public String getLotTimeframe() {
        return lotTimeframe;
    }

    public void setLotTimeframe(String lotTimeframe) {
        this.lotTimeframe = lotTimeframe;
    }

    public String getLotCondition() {
        return lotCondition;
    }

    public void setLotCondition(String lotCondition) {
        this.lotCondition = lotCondition;
    }
}

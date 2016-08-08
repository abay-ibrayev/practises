package kz.gbk.eprocurement.tenders.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by abai on 24.07.2016.
 */
@Entity
@Table(name = "tenders")
public class Tender implements Comparable<Tender>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tenders")
    @SequenceGenerator(name = "tenders", sequenceName = "tenders_seq", allocationSize = 1)
    private Long id;

    private Long tenderId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "tender_name")
    private String tenderName;

    @Column(name = "tender_method")
    private String tenderMethod;

    @Column(name = "tender_start")
    private Date tenderStart;

    @Column(name = "tender_end")
    private Date tenderEnd;

    @Column(name = "tender_status")
    private String tenderStatus;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tender_id")
    private List<TenderLot> tenderLots = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenderId() {
        return tenderId;
    }

    public void setTenderId(Long tenderId) {
        this.tenderId = tenderId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTenderName() {
        return tenderName;
    }

    public void setTenderName(String tenderName) {
        this.tenderName = tenderName;
    }

    public String getTenderMethod() {
        return tenderMethod;
    }

    public void setTenderMethod(String tenderMethod) {
        this.tenderMethod = tenderMethod;
    }

    public Date getTenderStart() {
        return tenderStart;
    }

    public void setTenderStart(Date tenderStart) {
        this.tenderStart = tenderStart;
    }

    public Date getTenderEnd() {
        return tenderEnd;
    }

    public void setTenderEnd(Date tenderEnd) {
        this.tenderEnd = tenderEnd;
    }

    public String getTenderStatus() {
        return tenderStatus;
    }

    public void setTenderStatus(String tenderStatus) {
        this.tenderStatus = tenderStatus;
    }

    public List<TenderLot> getTenderLots() {
        return tenderLots;
    }

    public void setTenderLots(List<TenderLot> tenderLots) {
        this.tenderLots = tenderLots;
    }

    @Override
    public int compareTo(Tender o) {
        return Objects.compare(tenderId, o.tenderId, Comparator.naturalOrder());
    }

    public void addTenderLot(TenderLot tenderLot) {
        assert tenderLot != null;

        tenderLots.add(tenderLot);
    }
}

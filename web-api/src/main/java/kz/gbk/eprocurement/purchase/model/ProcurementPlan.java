package kz.gbk.eprocurement.purchase.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "procurement_plans")
public class ProcurementPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procurement_plans")
    @SequenceGenerator(name = "procurement_plans", sequenceName = "procurement_plans_seq", allocationSize = 1)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date finishDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plan")
    private List<ProcurementItem> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id")
    private PurchasingParty owner;

    @SuppressWarnings("unused")
    private ProcurementPlan() {}

    public ProcurementPlan(PurchasingParty owner) {
        this.owner = Objects.requireNonNull(owner);
    }

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

    public List<ProcurementItem> getItems() {
        return items;
    }

    @SuppressWarnings("unused")
    private void setItems(List<ProcurementItem> items) {
        this.items = items;
    }

    public PurchasingParty getOwner() {
        return owner;
    }

    @SuppressWarnings("unused")
    private void setOwner(PurchasingParty owner) {
        this.owner = owner;
    }

    public void addItem(ProcurementItem item) {
        item.setPlan(this);
        this.items.add(item);
    }

    public void addItems(List<ProcurementItem> itemsList) {
        itemsList.forEach((item) -> addItem(item));
    }
}

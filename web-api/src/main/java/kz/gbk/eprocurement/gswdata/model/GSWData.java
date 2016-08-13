package kz.gbk.eprocurement.gswdata.model;

import javax.persistence.*;

/**
 * Created by abai on 11.08.2016.
 */
@Entity
@Table(name="gsw_data")
public class GSWData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GSW")
    @SequenceGenerator(name = "GSW", sequenceName = "gsw_data_seq", allocationSize = 1)
    private Long Id;

    @Column(name = "unique_code")
    private String UniqueCode;

    @Column(name = "name")
    private String Name;

    @Column(name = "description", length = 4000)
    private String Description;

    @Column(name = "measurement_units")
    private String MeasurementUnits;

    @Column(name = "MKEI_code")
    private String MKEIcode;

    @Column(name = "old_code")
    private String OldCode;

    @Column(name = "translation")
    private Boolean Translation;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUniqueCode() {
        return UniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        UniqueCode = uniqueCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMeasurementUnits() {
        return MeasurementUnits;
    }

    public void setMeasurementUnits(String measurementUnits) {
        MeasurementUnits = measurementUnits;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMKEIcode() {
        return MKEIcode;
    }

    public void setMKEIcode(String MKEIcode) {
        this.MKEIcode = MKEIcode;
    }

    public Boolean getTranslation() {
        return Translation;
    }

    public void setTranslation(Boolean translation) {
        Translation = translation;
    }

    public String getOldCode() {
        return OldCode;
    }

    public void setOldCode(String oldCode) {
        OldCode = oldCode;
    }



}

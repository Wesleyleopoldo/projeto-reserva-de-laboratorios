package com.labssyntech.labsSyntech.models;

import com.labssyntech.labsSyntech.utils.DaysInTheWeekEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "days_in_the_week")
public class DaysInTheWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "days_in_the_week_id", nullable = false)
    private Long daysInWeekId;

    @Enumerated(EnumType.STRING)
    private DaysInTheWeekEnum daysInTheWeekEnum;

    @OneToOne
    @JoinColumn(name = "fk_organization_id", referencedColumnName = "organization_id", nullable = true)
    private Organization organization;

    public DaysInTheWeek() {
    }

    public DaysInTheWeek(Long daysInWeekId, DaysInTheWeekEnum daysInTheWeekEnum, Organization organization) {
        this.daysInWeekId = daysInWeekId;
        this.daysInTheWeekEnum = daysInTheWeekEnum;
        this.organization = organization;
    }

    public Long getDaysInWeekId() {
        return daysInWeekId;
    }

    public void setDaysInWeekId(Long daysInWeekId) {
        this.daysInWeekId = daysInWeekId;
    }

    public DaysInTheWeekEnum getDaysInTheWeekEnum() {
        return daysInTheWeekEnum;
    }

    public void setDaysInTheWeekEnum(DaysInTheWeekEnum daysInTheWeekEnum) {
        this.daysInTheWeekEnum = daysInTheWeekEnum;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}

package com.labssyntech.labsSyntech.models;

import jakarta.persistence.*;

/*
 * Entidade da tabela de Dias de Funcionamento no banco de dados...
 * Atributos...
 * Getters e Setters...
 */

@Entity
@Table(name = "days_in_the_week")
public class DaysInTheWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "days_in_the_week_id", nullable = false)
    private Long daysInWeekId;

    @Column(name = "days_in_the_week",nullable = false)
    private String dayInTheWeek;

    @ManyToOne
    @JoinColumn(name = "fk_organization_id", referencedColumnName = "organization_id", nullable = false)
    private Organization fkOrganizationId;

    public DaysInTheWeek() {
    }

    public DaysInTheWeek(String daysInTheWeekEnum, Organization organization){
        this.dayInTheWeek = daysInTheWeekEnum;
        this.fkOrganizationId = organization;
    }

    public DaysInTheWeek(Long daysInWeekId, String daysInTheWeekEnum, Organization organization) {
        this.daysInWeekId = daysInWeekId;
        this.dayInTheWeek = daysInTheWeekEnum;
        this.fkOrganizationId = organization;
    }

    public Long getDaysInWeekId() {
        return daysInWeekId;
    }

    public void setDaysInWeekId(Long daysInWeekId) {
        this.daysInWeekId = daysInWeekId;
    }

    public String getDayInTheWeek() {
        return dayInTheWeek;
    }

    public void setDayInTheWeek(String daysInTheWeekEnum) {
        this.dayInTheWeek = daysInTheWeekEnum;
    }

    public Organization getOrganization() {
        return fkOrganizationId;
    }

    public void setOrganization(Organization organization) {
        this.fkOrganizationId = organization;
    }
}

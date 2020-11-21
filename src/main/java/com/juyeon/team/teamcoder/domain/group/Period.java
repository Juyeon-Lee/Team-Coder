package com.juyeon.team.teamcoder.domain.group;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Embeddable
public class Period {
    java.time.LocalDate startDate;
    java.time.LocalDate endDate;

    public Period(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Period() {

    }

    public boolean isBetween(LocalDate date){
        return date.isAfter(startDate) && date.isBefore(endDate);
    }
}

package com.juyeon.team.teamcoder.domain.group;

import javax.persistence.Embeddable;

@Embeddable
public class Age {

    private int minAge;
    private int maxAge;

    public Age(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public Age() {

    }

    public boolean isBetween(int age){
        return (age>=minAge && age<=maxAge);
    }
}

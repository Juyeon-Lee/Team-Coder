package com.juyeon.team.teamcoder.domain.group;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Num {
    @Column(nullable = false)
    private int maxNum;

    private int currentNum;

    public Num(int maxNum, int currentNum) {
        this.maxNum = maxNum;
        this.currentNum = currentNum;
    }

    public Num() {

    }

    public boolean hasEmptySeat(){
        return currentNum<maxNum;
    }
}

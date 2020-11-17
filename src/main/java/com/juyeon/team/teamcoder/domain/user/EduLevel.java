package com.juyeon.team.teamcoder.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;

@Getter
@RequiredArgsConstructor
public enum EduLevel {
    HIGH("고졸", 1),
    COLLEGE("대학 재학중", 2),
    JUNIOR("전문대졸", 3),
    BACHELOR("대졸(학사)", 4),
    GRADUATE("대학원 재학중", 5),
    MASTER("석사", 6),
    DOCTOR("박사", 7);
    
    private final String title;
    private final int index;

    int getIndex()  { return index;}

    public static Comparator<EduLevel> indexComparator = new Comparator<EduLevel>() {
        @Override
        public int compare(EduLevel e1, EduLevel e2) {
            return e1.getIndex() - e2.getIndex();
        }
    };
}

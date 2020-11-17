package com.juyeon.team.teamcoder.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;

@Getter
@RequiredArgsConstructor
public enum Location {
    SEOUL("서울", 1),
    INCHEON("인천", 2),
    BUSAN("부산", 3),
    DAEGU("대구", 4),
    DAEJEON("대전", 5),
    GWANGJU("광주",6),
    ULSAN("울산", 7),
    GYEONGGI("경기도", 8),
    GANGWON("강원도", 9),
    CHUNGCHEONGN("충청북도", 10),
    CHUNGCHEONGS("충청남도", 11),
    JEOLLAN("전라북도", 12),
    JEOLLAS("전라남도", 13),
    GYEONGSANGN("경상북도", 14),
    GYEONGSANGS("경상남도", 15),
    JEJU("제주도", 16),
    SEJONG("세종시", 17);

    private final String title;
    private final int index;

    int getIndex()  { return index;}

    public static Comparator<Location> indexComparator = new Comparator<Location>() {
        @Override   //오름차순
        public int compare(Location o1, Location o2) {
            return o1.getIndex() - o2.getIndex();
        }
    };
}

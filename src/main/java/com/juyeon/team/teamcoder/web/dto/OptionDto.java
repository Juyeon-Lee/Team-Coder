package com.juyeon.team.teamcoder.web.dto;

import com.juyeon.team.teamcoder.domain.user.EduLevel;
import com.juyeon.team.teamcoder.domain.user.Location;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class  OptionDto {
    private List<String> engLoc;
    private List<String> korLoc;
    private Map<String,String> loc;
    private List<String> engEdu;
    private List<String> korEdu;
    private Map<String,String> edu;

    public OptionDto(){

        List<Location> allLocations = Arrays.asList(Location.values());
        //allLocations.sort(Location.indexComparator);

        this.engLoc = allLocations.stream().map(Location::name)
                .collect(Collectors.toList());
        this.korLoc = allLocations.stream().map(Location::getTitle)
                .collect(Collectors.toList());
        //왜 순서 정렬이 안되지?? -> 이 이후로 매핑하면서 망가지는듯 하다.
        this.loc = IntStream.range(0, engLoc.size())
                .boxed().collect(Collectors.toMap(i -> this.engLoc.get(i),
                                                i-> this.korLoc.get(i)));



        List<EduLevel> allLevels = Arrays.asList(EduLevel.values());
        //allLevels.sort(EduLevel.indexComparator);

        this.engEdu = allLevels.stream().map(EduLevel::name)
                .collect(Collectors.toList());
        this.korEdu = allLevels.stream().map(EduLevel::getTitle)
                .collect(Collectors.toList());
        this.edu = IntStream.range(0, engEdu.size())
                .boxed().collect(Collectors.toMap(i ->this.engEdu.get(i),
                                                i -> this.korEdu.get(i)));
    }

    public Map<String,String> getLocOption(){
        return this.loc;
    }

    public Map<String,String> getEduOption(){
        return this.edu;
    }
}

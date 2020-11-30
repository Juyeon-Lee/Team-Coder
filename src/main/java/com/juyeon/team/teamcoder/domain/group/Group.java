package com.juyeon.team.teamcoder.domain.group;

import com.juyeon.team.teamcoder.domain.participate.Participate;
import com.juyeon.team.teamcoder.domain.tagGroup.TagGroup;
import com.juyeon.team.teamcoder.domain.tagUser.TagUser;
import com.juyeon.team.teamcoder.domain.user.EduLevel;
import com.juyeon.team.teamcoder.domain.user.Location;
import com.juyeon.team.teamcoder.domain.user.Role;
import com.juyeon.team.teamcoder.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity(name = "groups")
public class Group {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User manager;

    @Enumerated(EnumType.STRING)
    private GroupAim aim;

    @Enumerated(EnumType.STRING)
    private Location location;

    @Enumerated(EnumType.STRING)
    private EduLevel education;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    private GroupStatus status;

    @Column(length = 100)
    private String file;

    @Embedded Num memberNum;
    @Embedded Period workPeriod;
    @Embedded Age ageLimit;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<TagGroup> tagGroups = new HashSet<TagGroup>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL,
             fetch = FetchType.LAZY)
    private Set<Participate> applyUsers = new HashSet<Participate>();

    //======================Methods============================
    @Builder
    public Group(String name, User manager,
                 GroupAim aim, Location location, EduLevel education,
                 String description,
                 Num memberNum, Period workPeriod,
                 Age ageLimit) {
        this.name = name;
        this.manager = manager;
        this.aim = aim;
        this.location = location;
        this.education = education;
        this.description = description;
        this.status = GroupStatus.RECRUIT;
        this.memberNum = memberNum;
        this.workPeriod = workPeriod;
        this.ageLimit = ageLimit;
    }

    public void setTagGroups(Set<TagGroup> tagGroups){
        this.tagGroups.addAll(tagGroups);
    }

    public Group updateFile(String fileName) {
        this.file = fileName;
        return this;
    }

    @Transient
    public String getFilePath(){
        if(file==null || id== null) return null;

        return "/group-files/" + id+"/"+ file;
    }

    public Group update(String name, String file, GroupAim aim,
                        EduLevel education, Location location,
                        String description, Num num, Age age,
                        Period period, Set<TagGroup> tagGroups){
        this.name = name;
        this.file = file;
        this.aim = aim;
        this.education = education;
        this.location = location;
        this.description = description;
        this.memberNum = num;
        this.ageLimit = age;
        this.workPeriod = period;
        this.tagGroups.clear();
        this.tagGroups.addAll(tagGroups);
        return this;
    }

    public void make() {
        this.manager.getCreatedGroups().add(this);
    }
}

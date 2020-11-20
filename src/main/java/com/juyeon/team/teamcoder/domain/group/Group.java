package com.juyeon.team.teamcoder.domain.group;

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

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User manager;

    @Enumerated(EnumType.STRING)
    private GroupAim aim;

    @Enumerated(EnumType.STRING)
    private Location location;

    @Enumerated(EnumType.STRING)
    private EduLevel education;

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
        this.tagGroups = tagGroups;
    }

    public Group updateFile(String fileName) {
        this.file = fileName;
        return this;
    }

    public Group update(String name, String file,
                               EduLevel education,
                               Location location, Set<TagGroup> tagGroups){
        this.name = name;
        this.file = file;
        this.education = education;
        this.location = location;
        this.tagGroups = tagGroups;
        return this;
    }

}

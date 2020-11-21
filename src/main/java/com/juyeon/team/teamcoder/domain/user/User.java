package com.juyeon.team.teamcoder.domain.user;

import com.juyeon.team.teamcoder.domain.group.Group;
import com.juyeon.team.teamcoder.domain.tag.Tag;
import com.juyeon.team.teamcoder.domain.tagGroup.TagGroup;
import com.juyeon.team.teamcoder.domain.tagUser.TagUser;
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
@Entity(name = "USERS")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(length = 100)
    private String picture;
    private int birth;
    @Enumerated(EnumType.STRING)
    private Location location;

    @Enumerated(EnumType.STRING)
    private EduLevel education;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<TagUser> tagUsers = new HashSet<TagUser>();

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Group> createdGroups = new ArrayList<Group>();

    @Builder
    private User(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = Role.GUEST;
    }

    public User updateName(String name){
        this.name = name;
        return this;
    }

    public User updatePic(String fileName){
        this.picture = fileName;
        return this;
    }

    public User updateRegister(String name, String picture,
                               EduLevel education, int birth,
                               Location location, Set<TagUser> tagUsers){
        this.name = name;
        this.picture = picture;
        this.role = Role.USER;
        this.education = education;
        this.birth = birth;
        this.location = location;
        this.tagUsers.clear();
        this.tagUsers.addAll(tagUsers);
        return this;
    }

    @Transient
    public String getPicturePath(){
        if(picture==null || id== null) return null;

        return "/user-photos/" + id+"/"+ picture;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public void setTagUsers(Set<TagUser> tagUsers){
        this.tagUsers.addAll(tagUsers);
    }
}

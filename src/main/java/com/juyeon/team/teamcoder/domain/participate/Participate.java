package com.juyeon.team.teamcoder.domain.participate;

import com.juyeon.team.teamcoder.domain.group.Group;
import com.juyeon.team.teamcoder.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Entity
public class Participate {

    @Id @GeneratedValue
    @Column(name = "parti_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    private PartiStatus status;

    private String applyComment;

    @Builder
    public Participate(Group group, User user, String comment) {
        this.group = group;
        this.user = user;
        this.status = PartiStatus.APPLY;
        this.applyComment = comment;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(!(obj instanceof Participate)) return false;
        Participate par = (Participate) obj;
        return Objects.equals(getGroup(), par.getGroup()) &&
                Objects.equals(getUser(), par.getUser());
    }

    public void approve(){
        this.status = PartiStatus.JOIN;
    }

    public void reject(){
        this.status = PartiStatus.REJECTED;
    }
}

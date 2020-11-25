package com.juyeon.team.teamcoder.domain.store;

import com.juyeon.team.teamcoder.domain.group.Group;
import com.juyeon.team.teamcoder.domain.participate.Participate;
import com.juyeon.team.teamcoder.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Entity
public class Store {
    @Id
    @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Store(Group group, User user) {
        this.group = group;
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(!(obj instanceof Store)) return false;
        Store store = (Store) obj;
        return Objects.equals(getGroup(), store.getGroup()) &&
                Objects.equals(getUser(), store.getUser());
    }
}

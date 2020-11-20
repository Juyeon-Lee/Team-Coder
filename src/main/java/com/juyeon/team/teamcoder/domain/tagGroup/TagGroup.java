package com.juyeon.team.teamcoder.domain.tagGroup;

import com.juyeon.team.teamcoder.domain.group.Group;
import com.juyeon.team.teamcoder.domain.tag.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tag_group")
public class TagGroup {

    @Id @GeneratedValue
    @Column(name = "tag_group_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Builder
    private TagGroup(Tag tag, Group group){
        this.tag = tag;
        this.group = group;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof TagGroup))  return false;
        TagGroup tagGroup = (TagGroup) obj;
        return Objects.equals(getGroup(), tagGroup.getGroup()) &&
                Objects.equals(getTag(), tagGroup.getTag()) ;
    }
}

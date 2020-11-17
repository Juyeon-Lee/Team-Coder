package com.juyeon.team.teamcoder.domain.tag;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Tag {
    // TagUser에 단방향 다대일 매핑됨.

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(unique = true)
    private String name;

    public Tag(String name) {
        this.name = name;
    }
}

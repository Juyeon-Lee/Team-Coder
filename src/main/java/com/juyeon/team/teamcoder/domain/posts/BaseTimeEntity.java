package com.juyeon.team.teamcoder.domain.posts;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass  // JPA에서 상속할 때 속성 자동 인식
@EntityListeners(AuditingEntityListener.class) // listener -시간 자동 관리
public class BaseTimeEntity {
    
    @CreatedDate // 생성될 때 저장
    private LocalDateTime createdDate;
    
    @LastModifiedDate // 수정될 때 저장
    private LocalDateTime modifiedDate;
}

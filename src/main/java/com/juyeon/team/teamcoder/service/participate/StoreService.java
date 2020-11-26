package com.juyeon.team.teamcoder.service.participate;

import com.juyeon.team.teamcoder.domain.group.Group;
import com.juyeon.team.teamcoder.domain.group.GroupRepository;
import com.juyeon.team.teamcoder.domain.participate.Participate;
import com.juyeon.team.teamcoder.domain.store.Store;
import com.juyeon.team.teamcoder.domain.store.StoreRepository;
import com.juyeon.team.teamcoder.domain.user.User;
import com.juyeon.team.teamcoder.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.dom4j.IllegalAddException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Store addToStorage(Long userId, Long groupId){
        if(storeRepository.findByUser_idAndGroup_id(userId,groupId).isPresent()){
            throw new IllegalAddException("이미 저장했습니다. '저장한 그룹'에서 확인해보세요.");
        }
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹이 없습니다. id="+ groupId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id="+ userId));

        return storeRepository.save(Store.builder()
                                    .group(group).user(user).build());
    }

    @Transactional
    public Long deleteFromStorage(Long storeId){
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()-> new IllegalArgumentException("해당 저장 기록이 없습니다. id="+storeId));
        storeRepository.delete(store);
        return storeId;
    }

    @Transactional(readOnly = true)
    public List<Store> findAllById(Long userId){
        return storeRepository.findAllByUser_Id(userId);
    }

}

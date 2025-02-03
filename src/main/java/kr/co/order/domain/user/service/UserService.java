package kr.co.order.domain.user.service;

import jakarta.annotation.PostConstruct;
import kr.co.order.domain.user.entity.User;
import kr.co.order.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보를 찾을 수 없습니다. id=" + id));
    }

    @PostConstruct
    public void initData() {
        // User 생성
        User user = userRepository.save(
                new User(null,
                        "testUser",
                        "password",
                        "테스터",
                        "테스터 주소",
                        "010-3213-7955",
                        "juferis13@gmail.com",
                        null)
        );
    }
}

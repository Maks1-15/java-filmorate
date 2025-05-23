package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.impl.UserRepositoryImpl;
import ru.yandex.practicum.filmorate.service.GenericService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements GenericService<User> {

    private final UserRepositoryImpl userRepository;

    @Override
    public User create(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        return userRepository.create(user);
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    public void addFriend(Long friendId, Long userId) {
        userRepository.addFriend(friendId, userId);
    }

    public void removeFriend(Long friendId, Long userId) {
        userRepository.removeFriend(friendId, userId);
    }

    public List<User> getFriends(Long userId) {
        List<Long> friendIds = userRepository.getFriends(userId).stream().toList();
        return userRepository.getAll().stream()
                .filter(user -> friendIds.contains(user.getId()))
                .collect(Collectors.toList());
    }

    public List<User> getMutualFriends(Long userId, Long otherUserId) {
        List<Long> mutualFriendIds = userRepository.getMutualFriends(userId, otherUserId);
        return userRepository.getAll().stream()
                .filter(user -> mutualFriendIds.contains(user.getId()))
                .collect(Collectors.toList());
    }
}
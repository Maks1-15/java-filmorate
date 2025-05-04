package ru.yandex.practicum.filmorate.repository.Impl;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();
    private final AtomicInteger generateId = new AtomicInteger(1);

    @Override
    public void createUser(User user) {
        users.put((long) generateId.getAndIncrement(), user);
    }

    @Override
    public void updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            return;
        }
        users.put(user.getId(), user);
    }

    @Override
    public List<User> getAllUsers() {
        return users.values().stream().collect(Collectors.toList());
    }

    @Override
    public void deleteAllUsers() {
        users.clear();
    }
}

package ru.yandex.practicum.filmorate.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    public User save(User user) {
        return user.getId() > 0 ? update(user) : insert(user);
    }

    private User insert(User user) {
        String sql = String.format("""
                INSERT INTO %s (email, login, name, birthday)
                VALUES (?, ?, ?, ?)
                """, "users");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setObject(4, user.getBirthday());
            return ps;
        }, keyHolder);

        return user.toBuilder().id((Integer) keyHolder.getKey()).build();
    }

    private User update(User user) {
        String sql = String.format("""
                UPDATE %s
                SET email = ?, login = ?, name = ?, birthday = ?
                WHERE id = ?
                """, "users");

        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );
        return user;
    }

    public Optional<User> findById(int id) {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", "users");
        return queryForSingleUser(sql, id);
    }

    public List<User> findAll() {
        String sql = String.format("SELECT * FROM %s", "users");
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public void confirmFriendship(int confirmingUserId, int friendId) {
        String sql = String.format("UPDATE %s SET is_confirmed = true WHERE user1_id = ? AND user2_id = ?",
                "friends");
        jdbcTemplate.update(sql, friendId, confirmingUserId);
    }

    public boolean existsFriendship(int userId, int friendId) {
        String sql = String.format("""
                SELECT COUNT(*)
                FROM %s
                WHERE (user1_id = ? AND user2_id = ? AND is_confirmed = true)
                   OR (user2_id = ? AND user1_id = ? AND is_confirmed = true)
                """, "friends");

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                userId, friendId,
                userId, friendId
        );
        return count != null && count > 0;
    }

    public boolean existsById(int userId) {
        String sql = String.format("SELECT COUNT(1) FROM %s WHERE id = ?", "users");
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, userId));
    }

    public Optional<User> findByEmail(String email) {
        String sql = String.format("SELECT * FROM %s WHERE email = ?", "users");
        return queryForSingleUser(sql, email);
    }

    public List<User> getFriends(int userId) {
        String sql = String.format("""
                SELECT u.*
                FROM %s u
                WHERE u.id IN (
                    SELECT f.user2_id FROM %s f WHERE f.user1_id = ?
                    UNION
                    SELECT f.user1_id FROM %s f WHERE f.user2_id = ? AND f.is_confirmed = true
                )
                """, "users", "friends", "friends");
        return jdbcTemplate.query(sql, userRowMapper, userId, userId);
    }

    public List<User> getCommonFriends(int userId1, int userId2) {
        String sql = String.format("""
                SELECT u.* FROM %s u
                JOIN %s f1 ON (f1.user1_id = ? AND u.id = f1.user2_id) OR (f1.user2_id = ? AND u.id = f1.user1_id)
                JOIN %s f2 ON (f2.user1_id = ? AND u.id = f2.user2_id) OR (f2.user2_id = ? AND u.id = f2.user1_id)
                """, "users", "friends", "friends");
        return jdbcTemplate.query(sql, userRowMapper, userId1, userId1, userId2, userId2);
    }

    public boolean isValidFriendRequest(int fromUserId, int toUserId) {
        String sql = String.format("""
                SELECT COUNT(*) FROM %s
                WHERE user1_id = ? AND user2_id = ? AND is_confirmed = false
                """, "friends");
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, fromUserId, toUserId);
        return count != null && count > 0;
    }

    public void addFriends(int userId, int friendId, boolean isConfirmed) {
        String sql = String.format("""
                INSERT INTO %s (user1_id, user2_id, is_confirmed)
                VALUES (?, ?, ?)
                """, "friends");
        jdbcTemplate.update(sql, userId, friendId, isConfirmed);
    }

    public void removeFriends(int userId, int friendId) {
        String sql = String.format("""
                DELETE FROM %s
                WHERE (user1_id = ? AND user2_id = ?)
                   OR (user2_id = ? AND user1_id = ?)
                """, "friends");
        jdbcTemplate.update(sql, userId, friendId, friendId, userId);
    }

    private Optional<User> queryForSingleUser(String sql, Object... params) {
        List<User> results = jdbcTemplate.query(sql, userRowMapper, params);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
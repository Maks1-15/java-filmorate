package ru.yandex.practicum.filmorate.repository.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MpaRepository {
    private final JdbcTemplate jdbcTemplate;
    private final MpaRowMapper mpaRowMapper;

    public List<Mpa> findAll() {
        String sql = "SELECT id, name FROM content_rating ORDER BY id";
        return jdbcTemplate.query(sql, mpaRowMapper);
    }

    public Optional<Mpa> findById(int id) {
        String sql = "SELECT id, name FROM content_rating WHERE id = ?";
        List<Mpa> result = jdbcTemplate.query(sql, mpaRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.getFirst());
    }

    // получить рейтинг по mpa_id из базы (для FilmMapper)
    public Mpa findRatingById(int mpaId) {
        String sql = "SELECT id, name FROM content_rating WHERE id = ?";
        return jdbcTemplate.query(sql, mpaRowMapper, mpaId)
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("Возрастной рейтинг с ID =" + mpaId + " не найден.")
                );
    }
}
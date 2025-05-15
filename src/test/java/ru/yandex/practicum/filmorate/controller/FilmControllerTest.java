package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateFilmWithValidData() throws Exception {
        Film film = new Film();
        film.setName("Lorem");
        film.setDescription("Lorem ipsum Morbi erat ex, lacinia nec efficitur eget, sagittis ut orci...");
        film.setReleaseDate(LocalDate.of(2010, 7, 16));
        film.setDuration(148);

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateFilmWithInvalidData() throws Exception {
        Film film = new Film();
        film.setName("Lorem");
        film.setDescription("Lorem ipsum Morbi erat ex, lacinia nec efficitur eget, sagittis ut orci...");
        film.setReleaseDate(LocalDate.of(1010, 7, 16));
        film.setDuration(148);

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateFilmWithInvalidName() throws Exception {
        Film film = new Film();
        film.setName("");
        film.setDescription("Lorem ipsum Morbi erat ex, lacinia nec efficitur eget, sagittis ut orci...");
        film.setReleaseDate(LocalDate.of(2010, 7, 16));
        film.setDuration(148);

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateFilmWithInvalidDescription() throws Exception {
        Film film = new Film();
        film.setName("Lorem");
        film.setDescription("");
        film.setReleaseDate(LocalDate.of(2010, 7, 16));
        film.setDuration(148);

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateFilmWithInvalidDurationZero() throws Exception {
        Film film = new Film();
        film.setName("Lorem");
        film.setDescription("");
        film.setReleaseDate(LocalDate.of(2010, 7, 16));
        film.setDuration(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateFilmWithInvalidDurationMinus() throws Exception {
        Film film = new Film();
        film.setName("Lorem");
        film.setDescription("");
        film.setReleaseDate(LocalDate.of(2010, 7, 16));
        film.setDuration(-12);

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest());
    }


}

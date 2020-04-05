package kz.almat.profiling.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.almat.profiling.ProfilingApplicationTests;
import kz.almat.profiling.model.Post;
import kz.almat.profiling.repo.PostRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Almat on 05.04.2020
 */
@AutoConfigureMockMvc
class PostRestTest extends ProfilingApplicationTests {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepo postRepo;

    private List<Post> posts;

    @BeforeEach
    public void setUp() {
        posts = new ArrayList<>();
        posts.add(new Post(1L, "Test1", "Test11"));
        posts.add(new Post(2L, "Test2", "Test22"));
    }

    @Test
    void add_ok() throws Exception {
        Post post = new Post(null, "Test1", "Test11");

        mockMvc.perform(post("/")
                .content(om.writeValueAsString(post))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(postRepo).save(post);
    }

    @Test
    void update_ok() throws Exception {
        Post post = new Post(1L, "Test10", "Test110");

        when(postRepo.findById(1L)).thenReturn(java.util.Optional.of(posts.get(0)));
        mockMvc.perform(put("/1")
                .content(om.writeValueAsString(post))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(postRepo).save(post);
    }

    @Test
    void delete_ok() throws Exception {
        when(postRepo.findById(1L)).thenReturn(java.util.Optional.of(posts.get(0)));
        mockMvc.perform(delete("/1"))
                .andExpect(status().isOk());
        verify(postRepo).delete(posts.get(0));

    }

    @Test
    void getAll_ok() throws Exception {
        when(postRepo.findAll()).thenReturn(posts);
        mockMvc.perform(get("/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].topic", is("Test1")))
                .andExpect(jsonPath("$[0].message", is("Test11")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].topic", is("Test2")))
                .andExpect(jsonPath("$[1].message", is("Test22")));
        verify(postRepo).findAll();
    }

    @Test
    void getById_ok() throws Exception {
        when(postRepo.findById(1L)).thenReturn(java.util.Optional.of(posts.get(0)));
        mockMvc.perform(get("/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.topic", is("Test1")))
                .andExpect(jsonPath("$.message", is("Test11")));
        verify(postRepo).findById(1L);
    }
}
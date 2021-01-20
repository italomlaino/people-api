package com.italomlaino.peopleapi.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.italomlaino.peopleapi.TestConfig;
import com.italomlaino.peopleapi.domain.entity.Person;
import com.italomlaino.peopleapi.domain.exception.PersonNotFoundException;
import com.italomlaino.peopleapi.domain.view.PageView;
import com.italomlaino.peopleapi.service.PersonService;
import com.italomlaino.peopleapi.test.PersonTestHelper;
import com.italomlaino.peopleapi.web.person.PersonController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
@Import(TestConfig.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PersonTestHelper personTestHelper;

    @MockBean
    private PersonService service;

    @Test
    public void findByName_whenNonEmpty_thenReturnJsonObject()
            throws Exception {
        var sample = personTestHelper.createSample();
        PageView<Person> result = PageView.of(new PageImpl<>(List.of(sample)));
        given(service.findByName(any(), any())).willReturn(result);

        mvc.perform(get("/api/people")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(1)))
                .andExpect(jsonPath("content[0].name", is(sample.getName())))
                .andExpect(jsonPath("content[0].spouseName", is(sample.getSpouseName())));
    }

    @Test
    public void findByName_whenEmpty_thenReturnJsonObject()
            throws Exception {
        PageView<Person> result = PageView.of(new PageImpl<>(List.of()));
        given(service.findByName(any(), any())).willReturn(result);

        mvc.perform(get("/api/people")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(0)));
    }

    @Test
    public void findById_whenFound_thenReturnJsonObject()
            throws Exception {
        var sample = personTestHelper.createSample();
        given(service.findById(1L)).willReturn(sample);

        mvc.perform(get("/api/people/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(sample.getName())))
                .andExpect(jsonPath("spouseName", is(sample.getSpouseName())));
    }

    @Test
    public void findById_whenNotFound_thenReturnNotFound()
            throws Exception {
        given(service.findById(1L)).willThrow(new PersonNotFoundException());

        mvc.perform(get("/api/people/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void create_whenValid_thenReturnJsonObject()
            throws Exception {
        var sample = personTestHelper.createSample();
        given(service.create(sample)).willReturn(sample);

        mvc.perform(post("/api/people")
                .content(new ObjectMapper().writeValueAsString(sample))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void create_whenInvalid_thenReturnNotFound()
            throws Exception {
        var sample = personTestHelper.createSample();
        given(service.create(any())).willThrow(new PersonNotFoundException());

        mvc.perform(post("/api/people")
                .content(new ObjectMapper().writeValueAsString(sample))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_whenValid_thenReturnJsonObject()
            throws Exception {
        var sample = personTestHelper.createSample();
        given(service.update(1L, sample)).willReturn(sample);

        mvc.perform(put("/api/people/1")
                .content(new ObjectMapper().writeValueAsString(sample))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void update_whenNotFound_thenReturnNotFound()
            throws Exception {
        var sample = personTestHelper.createSample();
        given(service.update(anyLong(), any())).willThrow(new PersonNotFoundException());

        mvc.perform(put("/api/people/1")
                .content(new ObjectMapper().writeValueAsString(sample))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_whenFound_thenReturnNoContent()
            throws Exception {
        mvc.perform(delete("/api/people/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void delete_whenNotFound_thenReturnNotFound()
            throws Exception {
        willThrow(new PersonNotFoundException()).given(service).delete(1L);

        mvc.perform(delete("/api/people/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}

package com.hungryBear.heroes.controller.impl;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungryBear.heroes.common.VO.request.SuperHeroRequest;
import com.hungryBear.heroes.common.errors.ErrorCode;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class SuperHeroControllerTest {

  @Autowired
  MockMvc mockMvc;

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testGetAll() throws Exception {
    this.mockMvc.perform(get("/"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.*", hasSize(3)))
        .andExpect(jsonPath("$.[0].name", is("Spiderman")))
        .andExpect(jsonPath("$.[1].name", is("Batman")))
        .andExpect(jsonPath("$.[2].name", is("Manolito")));
  }

  private SuperHeroRequest createSuperHeroRequest(String name) {
    SuperHeroRequest request = new SuperHeroRequest();
    request.setName(name);
    return request;
  }

	@Test
	public void testSave() throws Exception {
		String name = "test hero name";
    this.mockMvc.perform(post("/")
			    .contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(createSuperHeroRequest(name))))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(4)))
				.andExpect(jsonPath("$.name", is(name)));
	}

	@Test
	public void testSaveDuplicatedError() throws Exception {
		this.mockMvc.perform(post("/")
			    .contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(createSuperHeroRequest("Spiderman"))))
				.andExpect(status().is(404))
				.andExpect(jsonPath("$.message", is(ErrorCode.SUPER_HERO_DUPLICATED.getCode())));
	}

	@Test
	public void testUpdate() throws Exception {
		this.mockMvc.perform(put("/{id}", 1)
			    .contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(createSuperHeroRequest("Test Hero 2"))))
		        .andExpect(status().isOk());
	}

  @Test
  public void testDelete() throws Exception {
    this.mockMvc.perform(delete("/{id}", 4).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
  }

  @Test
  public void testDeleteNotFoundError() throws Exception {
    this.mockMvc.perform(delete("/{id}", 5).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().is(404))
        .andExpect(jsonPath("$.message", is(ErrorCode.SUPER_HERO_NOT_FOUND.getCode())));
  }

}

package com.hungryBear.heroes.controller.impl;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungryBear.heroes.common.VO.request.SuperHeroRequest;
import com.hungryBear.heroes.common.errors.ErrorCode;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@TestMethodOrder(OrderAnnotation.class)
public class SuperHeroControllerTest {

  @Autowired
  MockMvc mockMvc;

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  @Order(0)
  public void testPing() throws Exception {
    String message = "here is the message";
    this.mockMvc.perform(get("/heroes/ping/{message}", message))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(message)));
  }

  @Test
  @WithMockUser(roles = "USER")
  @Order(1)
  public void testGetAllWithUserRole() throws Exception {
    testGetAll();
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  @Order(2)
  public void testGetAllWithAdminRole() throws Exception {
    testGetAll();
  }

  private void testGetAll() throws Exception {
    this.mockMvc.perform(get("/heroes/"))
      .andExpect(status().is2xxSuccessful())
      .andExpect(jsonPath("$.*", hasSize(3)))
      .andExpect(jsonPath("$.[0].name", is("Spiderman")))
      .andExpect(jsonPath("$.[1].name", is("Batman")))
      .andExpect(jsonPath("$.[2].name", is("Manolito")));
  }

  @Test
  @WithMockUser(roles = "USER")
  @Order(3)
  public void testGetSuperHeroByIdWithUserRole() throws Exception {
    this.mockMvc.perform(get("/heroes/id/{id}", 1))
      .andExpect(status().is2xxSuccessful())
      .andExpect(jsonPath("$.*", hasSize(2)))
      .andExpect(jsonPath("$.id", is(1)))
      .andExpect(jsonPath("$.name", is("Spiderman")));
  }

  @Test
  @WithMockUser(roles = "USER")
  @Order(4)
  public void testGetSuperHeroByNameWithUserRole() throws Exception {
    this.mockMvc.perform(get("/heroes/name/Bat"))
      .andExpect(status().is2xxSuccessful())
      .andExpect(jsonPath("$.*", hasSize(1)))
      .andExpect(jsonPath("$.[0].id", is(2)))
      .andExpect(jsonPath("$.[0].name", is("Batman")));
  }

  private SuperHeroRequest createSuperHeroRequest(String name) {
    SuperHeroRequest request = new SuperHeroRequest();
    request.setName(name);
    return request;
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  @Order(5)
  public void testSave() throws Exception {
    String name = "test hero name";
    this.mockMvc.perform(post("/heroes/").contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(mapper.writeValueAsString(createSuperHeroRequest(name))))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(4)))
      .andExpect(jsonPath("$.name", is(name)));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  @Order(6)
  public void testSaveDuplicatedError() throws Exception {
    this.mockMvc.perform(post("/heroes/").contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(mapper.writeValueAsString(createSuperHeroRequest("Spiderman"))))
      .andExpect(status().is(404))
      .andExpect(jsonPath("$.message", is(ErrorCode.SUPER_HERO_DUPLICATED.getCode())));
  }

  @Test
  @WithMockUser(roles = "USER")
  @Order(7)
  public void testSaveAuthForbiddenError() throws Exception {
    String name = "test hero name";
    this.mockMvc.perform(post("/heroes/").contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(mapper.writeValueAsString(createSuperHeroRequest(name))))
      .andExpect(status().isForbidden())
      .andExpect(jsonPath("$.message", is(ErrorCode.AUTH_UNAUTHORIZED.getCode())));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  @Order(8)
  public void testUpdate() throws Exception {
    this.mockMvc.perform(put("/heroes/{id}", 1).contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(mapper.writeValueAsString(createSuperHeroRequest("Test Hero 2"))))
      .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  @Order(9)
  public void testDelete() throws Exception {
    this.mockMvc.perform(delete("/heroes/{id}", 4).contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  @Order(10)
  public void testDeleteNotFoundError() throws Exception {
    this.mockMvc.perform(delete("/heroes/{id}", 4).contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message", is(ErrorCode.SUPER_HERO_NOT_FOUND.getCode())));
  }

  @Test
  @Order(11)
  public void testDeleteAuthorizationError() throws Exception {
    this.mockMvc.perform(delete("/heroes/{id}", 4).contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isUnauthorized())
      .andExpect(jsonPath("$.message", is(ErrorCode.AUTH_UNAUTHORIZED.getCode())));
  }

}

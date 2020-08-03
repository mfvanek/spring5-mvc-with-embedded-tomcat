package io.github.mfvanek.spring5mvc.controllers;

import io.github.mfvanek.spring5mvc.config.WebConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class RequestControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void verifyTestConfiguration() {
        ServletContext servletContext = wac.getServletContext();

        assertNotNull(servletContext);
        assertThat(servletContext, instanceOf(MockServletContext.class));
        assertNotNull(wac.getBean("requestController"));
    }

    @Test
    public void log() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/demo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("text/plain;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
        assertThat(mvcResult.getResponse().getContentAsString(), startsWith("Add log entry at "));
    }

    @Test
    public void logException() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/demoException"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("sample exception"))
                .andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void displayWelcomeMessage() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/welcome"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Jaeger tracing demo"))
                .andExpect(jsonPath("description").value(startsWith("Welcome from Spring MVC and Embedded Tomcat. ")))
                .andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}

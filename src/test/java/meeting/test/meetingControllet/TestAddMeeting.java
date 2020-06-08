package meeting.test.meetingControllet;

import meeting.test.model.BusinessError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TestAddMeeting {

    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void addMeetingWithoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/addMeeting")
                .param("timeBegin", String.valueOf(System.currentTimeMillis() + 100000))
                .param("timeEnd", String.valueOf(System.currentTimeMillis() + 8000000))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void addMeetingIncorrectTime() throws Exception {
        String request = mockMvc.perform(MockMvcRequestBuilders
                .post("/addMeeting")
                .param("timeBegin", String.valueOf(System.currentTimeMillis() - 100000))
                .param("timeEnd", String.valueOf(System.currentTimeMillis() + 800000))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();
        assertEquals(request, BusinessError.IncorrectTime.description());

        request = mockMvc.perform(MockMvcRequestBuilders
                .post("/addMeeting")
                .param("timeBegin", String.valueOf(System.currentTimeMillis() + 800000))
                .param("timeEnd", String.valueOf(System.currentTimeMillis() + 100000))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();
        assertEquals(request, BusinessError.IncorrectTime.description());


        request = mockMvc.perform(MockMvcRequestBuilders
                .post("/addMeeting")
                .param("timeBegin", String.valueOf(System.currentTimeMillis() + 100000))
                .param("timeEnd", String.valueOf(System.currentTimeMillis() + 110000))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();
        assertEquals(request, BusinessError.IncorrectTime.description());

        request = mockMvc.perform(MockMvcRequestBuilders
                .post("/addMeeting")
                .param("timeBegin", String.valueOf(System.currentTimeMillis() + 100000))
                .param("timeEnd", String.valueOf(System.currentTimeMillis() + 5 * 60 * 60 * 1000))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();
        assertEquals(request, BusinessError.IncorrectTime.description());
    }
}

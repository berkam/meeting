package meeting.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TestMeetingRESTController {

    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void addMeeting() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/addMeeting")
                .param("timeBegin", String.valueOf(System.currentTimeMillis()))
                .param("timeEnd", String.valueOf(System.currentTimeMillis()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void cancelMeeting() throws Exception {
        String meetingId = mockMvc.perform(MockMvcRequestBuilders
                .post("/addMeeting")
                .param("timeBegin", String.valueOf(System.currentTimeMillis()))
                .param("timeEnd", String.valueOf(System.currentTimeMillis()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/cancelMeeting")
                .param("meetingId", meetingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void addUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/addUsers")
                .param("timeBegin", String.valueOf(System.currentTimeMillis()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/deleteUser")
                .param("timeBegin", String.valueOf(System.currentTimeMillis()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
}
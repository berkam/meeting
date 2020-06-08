package meeting.test.meetingControllet;

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
                .param("timeBegin", String.valueOf(System.currentTimeMillis() + 100000))
                .param("timeEnd", String.valueOf(System.currentTimeMillis() + 800000))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void cancelMeeting() throws Exception {
        String meetingId = mockMvc.perform(MockMvcRequestBuilders
                .post("/addMeeting")
                .param("timeBegin", String.valueOf(System.currentTimeMillis()))
                .param("timeEnd", String.valueOf(System.currentTimeMillis() + 1))
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
        String meetingId = mockMvc.perform(MockMvcRequestBuilders
                .post("/addMeeting")
                .param("timeBegin", String.valueOf(System.currentTimeMillis()))
                .param("timeEnd", String.valueOf(System.currentTimeMillis() + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/addUser")
                .param("email", "berkam15@gmail.com")
                .param("meetingId", meetingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void incorrectEmail() throws Exception {
        String meetingId = mockMvc.perform(MockMvcRequestBuilders
                .post("/addMeeting")
                .param("timeBegin", String.valueOf(System.currentTimeMillis()))
                .param("timeEnd", String.valueOf(System.currentTimeMillis() + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/addUser")
                .param("email", "berkam15gmail.com")
                .param("meetingId", meetingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/addUser")
                .param("email", "berkam@15gmail.")
                .param("meetingId", meetingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteUser() throws Exception {
        String meetingId = mockMvc.perform(MockMvcRequestBuilders
                .post("/addMeeting")
                .param("timeBegin", String.valueOf(System.currentTimeMillis()))
                .param("timeEnd", String.valueOf(System.currentTimeMillis() + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        String userId = mockMvc.perform(MockMvcRequestBuilders
                .post("/addUser")
                .param("email", "berkam15@gmail.com")
                .param("meetingId", meetingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/deleteUser")
                .param("userId", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void cancelMeetingWithUser() throws Exception {
        String meetingId = mockMvc.perform(MockMvcRequestBuilders
                .post("/addMeeting")
                .param("timeBegin", String.valueOf(System.currentTimeMillis()))
                .param("timeEnd", String.valueOf(System.currentTimeMillis() + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        String userId = mockMvc.perform(MockMvcRequestBuilders
                .post("/addUser")
                .param("email", "berkam15@gmail.com")
                .param("meetingId", meetingId)
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
}

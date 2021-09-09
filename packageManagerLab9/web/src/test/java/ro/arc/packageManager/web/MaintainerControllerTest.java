package ro.arc.packageManager.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.arc.packageManager.core.domain.Maintainer;
import ro.arc.packageManager.core.service.MaintainerService;
import ro.arc.packageManager.web.controller.MaintainerController;
import ro.arc.packageManager.web.converter.MaintainerConverter;
import ro.arc.packageManager.web.dto.MaintainerDto;

import java.util.*;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MaintainerControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MaintainerController maintainerController;

    @Mock
    private MaintainerService maintainerService;

    @Mock
    private MaintainerConverter maintainerConverter;

    private Maintainer maintainer1;
    private Maintainer maintainer2;
    private MaintainerDto maintainerDto1;
    private MaintainerDto maintainerDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(maintainerController)
                .build();
        initData();
    }

    @Test
    public void getStudents() throws Exception {
        List<Maintainer> maintainers = Arrays.asList(maintainer1, maintainer2);
        Set<MaintainerDto> maintainerDtos =
                new HashSet<>(Arrays.asList(maintainerDto1, maintainerDto2));
        when(maintainerService.getAllMaintainers()).thenReturn(maintainers);
        when(maintainerConverter.convertModelsToDtos(maintainers)).thenReturn(maintainerDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/maintainers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userName", anyOf(is("cosmin_test"), is("user_test2"))))
                .andExpect(jsonPath("$[1].email", anyOf(is("cosmintest@gmail.com"), is("usertest2@gmail.com"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println(result);

        verify(maintainerService, times(1)).getAllMaintainers();
        verify(maintainerConverter, times(1)).convertModelsToDtos(maintainers);
        verifyNoMoreInteractions(maintainerService, maintainerConverter);


    }

    @Test
    public void updateStudent() throws Exception {

        when(maintainerService.updateMaintainer(maintainerConverter.convertDtoToModel(maintainerDto1)))
                .thenReturn(maintainer1);

        when(maintainerConverter.convertModelToDto(maintainer1)).thenReturn(maintainerDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/maintainers/{maintainerID}", maintainer1.getId(), maintainerDto1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(maintainerDto1)))

                .andExpect(status().isOk());

        verify(maintainerService, times(1)).updateMaintainer(maintainerConverter.convertDtoToModel(maintainerDto1));
        verify(maintainerConverter, times(1)).convertModelToDto(maintainer1);

    }

    private String toJsonString(Map<String, MaintainerDto> maintainerDtoMap) {
        try {
            return new ObjectMapper().writeValueAsString(maintainerDtoMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJsonString(MaintainerDto maintainerDto) {
        try {
            return new ObjectMapper().writeValueAsString(maintainerDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deleteStudent() throws Exception {

        when(maintainerConverter.convertModelToDto(maintainer1)).thenReturn(maintainerDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/maintainers/{maintainerID}", maintainer1.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(maintainerDto1)))

                .andExpect(status().isOk());


    }

    private void initData() {
        maintainer1 = Maintainer.builder()
                .userName("cosmin_test")
                .fullName("cosmin test c")
                .email("cosmintest@gmail.com")
                .build();
        maintainer1.setId(1l);
        maintainer2 = Maintainer.builder()
                .userName("user_test2")
                .fullName("user test 2 c")
                .email("usertest2@gmail.com")
                .build();
        maintainer2.setId(2l);

        maintainerDto1 = createMaintainerDto(maintainer1);
        maintainerDto2 = createMaintainerDto(maintainer2);

    }

    private MaintainerDto createMaintainerDto(Maintainer maintainer) {
        MaintainerDto maintainerDto = MaintainerDto.builder()
                .userName(maintainer.getUserName())
                .fullName(maintainer.getFullName())
                .email(maintainer.getEmail())
                .build();
        maintainerDto.setId(maintainer.getId());
        return maintainerDto;
    }


}

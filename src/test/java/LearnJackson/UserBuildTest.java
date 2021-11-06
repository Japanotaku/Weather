package LearnJackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UserBuildTest {
    @Test
    public void test() throws JsonProcessingException {
    UserBuild user = new UserBuild(1,"Jorge");
    String result = new ObjectMapper().writeValueAsString(user);
    System.out.println(result);


}

}
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TaskExcludeTest {

    @Test
    @Tag("smoke")
    void taskExcludeTests(){
        System.out.println("Не работаю");
    }
}

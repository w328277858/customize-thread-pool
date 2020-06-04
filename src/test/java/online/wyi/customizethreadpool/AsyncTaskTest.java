package online.wyi.customizethreadpool;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsyncTaskTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AsyncTask asyncTask;

    @Test
    public void AsyncTaskTest() throws InterruptedException, ExecutionException {
        for (int i = 0; i < 10; i++) {
            asyncTask.doTask1(i);
        }
        Thread.sleep(10000);
        logger.info("All tasks finished");
    }

    @Test
    public void AsyncTaskNativeTest() throws InterruptedException, ExecutionException {
        for (int i = 0; i < 10; i++) {
            TaskBO taskBO = new TaskBO();
            taskBO.setCode("wyi Task"+ i);
            taskBO.setName("wyi name " + i);
            asyncTask.doTask2(taskBO);
        }
        Thread.sleep(10000);
        logger.info("All tasks finished");
    }

    @Test
    public void testSell() throws InterruptedException, ExecutionException {
        List<String> windowsList = Stream.of("售票口A","售票口B","售票口C","售票口D","售票口E","售票口F","售票口G","售票口H",
                "售票口I","售票口J","售票口K","售票口L","售票口M","售票口N","售票口O","售票口P",
                "售票口Q","售票口R","售票口S","售票口T","售票口U","售票口V","售票口W","售票口X","售票口Y","售票口Z").collect(Collectors.toList());
        int customerCount = 190;
        windowsList.forEach(t -> {
            try {
                logger.info("MyExecutor2 -" + windowsList.indexOf(t) + " is start");
                asyncTask.sellTickets(t, customerCount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(15000);
        logger.info("All tasks finished");
    }
}

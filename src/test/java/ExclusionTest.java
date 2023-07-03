import io.dev.SemaphoreSequenceGenerator;
import io.dev.SequenceGenerator;
import io.dev.SynchedSequenceGenerator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class ExclusionTest {

    @Test
    public void 상호배제없이_멀티스레팅처리하는경우_기대값이출력되지않음() throws Exception {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGenerator(), count);
        Assert.assertNotEquals(count, uniqueSequences.size());
    }

    @Test
    public void 상호배제처리후_멀티스레딩처리하는경우_기대값이출력됨() throws Exception {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SynchedSequenceGenerator(), count);
        Assert.assertEquals(count, uniqueSequences.size());
    }

    @Test
    public void 세마포어활용_멀티스레딩처리하는경우_기대값이출력됨() throws Exception {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SemaphoreSequenceGenerator(), count);
        Assert.assertEquals(count, uniqueSequences.size());
    }


    /**
     * count만큼 순번을 생성한다.
     * 순번 생성 시 멀티스레딩처리로 진행한다.
     */
    private Set<Integer> getUniqueSequences(SequenceGenerator generator, int count) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Set<Integer> uniqueSequences = new LinkedHashSet<>();
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            futures.add(executor.submit(generator::getNextSequence));
        }

        for (Future<Integer> future : futures) {
            uniqueSequences.add(future.get());
        }

        executor.awaitTermination(1, TimeUnit.SECONDS);
        executor.shutdown();

        return uniqueSequences;
    }
}

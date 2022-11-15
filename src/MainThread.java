import java.util.concurrent.CyclicBarrier;

public class MainThread {
    private long sum;
    private int threadCount;
    private int partCount;
    private int size;
    private int[] mas;
    private boolean[] partsAreCalculated;
    private CyclicBarrier bar;

    MainThread(int[] mas_, int threadCount_, int partCount_)
    {
        mas = mas_;
        size = mas.length;
        threadCount = threadCount_;
        partCount = partCount_;
        sum = 0;
        partsAreCalculated = new boolean[partCount];
        for (int i = 0; i < threadCount; i++)
        {
            partsAreCalculated[i] = true;
        }
        bar = new CyclicBarrier(threadCount, new Runnable() {
            @Override
            public void run() {
                WakeUp();
            }
        });
    }

    public long getSum()
    {
        return sum;
    }
    public long getPartCount()
    {
        return partCount;
    }

    synchronized public void Start(){
        //thread creating
        int neededThreadCount = Math.min(threadCount, partCount);
        SumCalculatorWithInterface[] threadsOther = new SumCalculatorWithInterface[neededThreadCount];
        Thread[] threads = new Thread[neededThreadCount];

        //thread starting
        for (int i = 0; i < neededThreadCount; i++) {
            threadsOther[i] = new SumCalculatorWithInterface(this, mas, i);
            threads[i] = new Thread(threadsOther[i]);
            threads[i].start();
        }

        //waiting for threads to finish
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    synchronized public void setPartSum(long partSum){
        sum = sum + partSum;
    }

    synchronized public int GetFreePart(){
        int newPart = -1;
        for (int i = partsAreCalculated.length - 1; i >= threadCount; i--)
        {
            if (!partsAreCalculated[i])
            {
                newPart = i;
                partsAreCalculated[i] = true;
                break;
            }
        }
        return newPart;
    }

    public void AwaitBarrier()
    {
        try {
            bar.await();
        } catch (InterruptedException ex) {
            return;
        } catch (java.util.concurrent.BrokenBarrierException ex) {
            return;
        }
    }
    synchronized public void WakeUp()
    {
        notify();
    }
}

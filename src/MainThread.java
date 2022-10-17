public class MainThread {
    private long sum;
    private int threadCount;
    private int currThreadCount = 0;
    private int size;
    private int[] mas;

    MainThread(int[] mas_, int threadCount_)
    {
        mas = mas_;
        size = mas.length;
        threadCount = threadCount_;
        sum = 0;
    }

    public long getSum()
    {
        return sum;
    }

    synchronized public void Start(){
        //variables preparation
        int[] firstElements = new int[threadCount];
        int[] lastElements = new int[threadCount];

        for (int i = 0; i < threadCount; i++)
            firstElements[i] = size / threadCount * i;
        for (int i = 0; i < threadCount - 1; i++)
            lastElements[i] = firstElements[i + 1] - 1;

        lastElements[threadCount - 1] = size - 1;

        SumCalculatorWithInterface[] threadsOther = new SumCalculatorWithInterface[threadCount];
        Thread[] threads = new Thread[threadCount];

        //thread starting
        for (int i = 0; i < threadCount; i++) {
            threadsOther[i] = new SumCalculatorWithInterface(this, mas, firstElements[i], lastElements[i]);
            threads[i] = new Thread(threadsOther[i]);
            threads[i].start();
        }

        //waiting for threads to finish
        while (currThreadCount < threadCount){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    synchronized public void setPartSum(long partSum){
        sum = sum + partSum;
        currThreadCount++;
        notify();
    }
}

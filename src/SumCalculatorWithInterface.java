public class SumCalculatorWithInterface implements Runnable {
    long partSum = 0;
    int[] mas;
    int begin, end;
    int partNum;
    MainThread mainThread;

    SumCalculatorWithInterface(MainThread mainThread_, int[] mas, int partNum_) {
        this.mas = mas;
        mainThread = mainThread_;
        partNum = partNum_;
    }

    @Override
    public void run() {
        int newPart = -1;
        do {
            partSum = 0;
            begin = (int) (mas.length / mainThread.getPartCount() * partNum);
            end = (int) (begin + mas.length / mainThread.getPartCount() - 1);
            for (int i = begin; i <= end; i++) {
                partSum = partSum + mas[i];
            }
            mainThread.setPartSum(partSum);
            newPart = mainThread.GetFreePart();
            partNum = newPart;
        }
        while (newPart != -1);
    }
}

public class SumCalculatorWithExtending extends Thread {
    long partSum = 0;
    int[] mas;
    int begin, end;

    SumCalculatorWithExtending(int[] mas, int partStart, int partEnd) {
        this.mas = mas;
        begin = partStart;
        end = partEnd;
    }

    public void run() {
        for (int i = begin; i <= end; i++) {
            partSum = partSum + mas[i];
        }
    }
}

package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;
    private int sum;

    public AcceleratingSawToothGenerator(int period, double factor){
        this.period = period;
        state = 0;
        this.factor = factor;
        sum = 0;
    }

    @Override
    public double next(){
        state = state + 1;
        if((state - sum) % period == 0){
            sum = sum + period;
            period = (int)Math.round(period * factor);
        }
        return normalize(state - sum);
    }

    private double normalize(int i){
        return 2 * i / (0.0 + period - 1) - 1;
    }
}

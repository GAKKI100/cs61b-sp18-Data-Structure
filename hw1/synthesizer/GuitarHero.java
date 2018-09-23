package synthesizer;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdAudio;

public class GuitarHero {
    private static int i;
    public static void main(String[] args){
        GuitarString[] gs = new GuitarString[37];
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        for (char s : keyboard.toCharArray()){
            i = keyboard.indexOf(s);
            double temp = (i - 24.0)/12.0;
            double frequency = 440 * Math.pow(2, temp);
            gs[i] = new GuitarString(frequency);
        }
        while (true){
            if (StdDraw.hasNextKeyTyped()){
                char key = StdDraw.nextKeyTyped();
                for (char s : keyboard.toCharArray()){
                    if(s == key){
                        i = keyboard.indexOf(s);
                        gs[i].pluck();
                        break;
                    }

                }
            }
            double sample = gs[i].sample();
            StdAudio.play(sample);
            gs[i].tic();
        }

    }

}

package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleFunction;
import java.util.function.IntToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GuitarHero {
    private static final String KEYS = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static final char[] KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ".toCharArray();
    private static final IntStream KEY37 = IntStream.range(0, 37);
    private static final Stream<Integer> KEY37_BOXED = IntStream.range(0, 37).boxed();
    private static final IntToDoubleFunction FREQ_FUNC = (i -> 440 * Math.pow(2, (i - 24) / (double) 12));
    private static final DoubleFunction<GuitarString> STRING_FUNC = GuitarString::new;
    private static final GuitarString[] STRINGS = (GuitarString[]) KEY37.mapToDouble(FREQ_FUNC).mapToObj(STRING_FUNC).toArray(GuitarString[]::new);
    private static final Map<Character, GuitarString> STRING_MAP =
            KEY37_BOXED.collect(Collectors.toMap(i -> KEYBOARD[i], i -> STRINGS[i]));

    public static void pluckString(char key) {
        STRING_MAP.get(key).pluck();
    }

    public static double getSample() {
        double sample = 0D;
        for (GuitarString guitarString : STRINGS) {
            sample += guitarString.sample();
        }
        return sample;
    }

    public static void ticAll() {
        Arrays.stream(STRINGS).forEachOrdered(GuitarString::tic);
    }

    public static boolean validKey(char key) {
        return KEYS.contains(Character.toString(key));
    }

    public static void main(String[] args) {
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (validKey(key)) {
                    pluckString(key);
                }
            }

            /* compute the superposition of samples */
            double sample = getSample();

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            ticAll();
        }
    }
}

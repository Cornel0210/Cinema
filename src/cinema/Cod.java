package cinema;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cod {
    public static void main(String[] args) {
        int[] array = {1,1,1,1,1,1,1,2,4,5,6,1,3,4,1}; //exemplu de test

        Map<Integer, Integer> frequencies = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            frequencies.computeIfPresent(array[i], (k,v)-> v+1);
            frequencies.computeIfAbsent(array[i], v-> 1);
        }
        frequencies.forEach((k,v)-> System.out.println(k + " " + v)); //printeaza frecventele pt fiecare valoare in parte

        int maxFreq = Integer.MIN_VALUE;
        int value = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Integer> entry : frequencies.entrySet()){ //identifica valoarea cu cea mai mare frecventa
            if (entry.getValue() > maxFreq){
                maxFreq = entry.getValue();
                value = entry.getKey();
            }
        }
        System.out.println(value);
        String b = new String("blabla");
        String a = "blabla";
        System.out.println(a==b);

        System.out.println(Collections.max(frequencies.entrySet(), Map.Entry.comparingByValue()).getKey());
        Collections.max(frequencies.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}

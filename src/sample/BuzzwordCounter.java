package sample;

import java.util.List;

public class BuzzwordCounter {
    String text;
    int counter;
    List<String> Buzzwords;

    public BuzzwordCounter(List<String> buzzwords, String text) {
        Buzzwords = buzzwords;
        this.text = text;
        counter = 0;
    }

    public void setCount(){
        System.out.println(text);

    }
}

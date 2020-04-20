package sample;

import javafx.beans.property.StringProperty;

import java.util.List;

public class BuzzwordCounter {
    String text;
    int counter;
    List<String> buzzwords;

    public BuzzwordCounter(List<String> buzzwords, String text) {
        buzzwords = buzzwords;
        this.text = text;
        counter = 0;
    }

    public int setCount(StringProperty textProperty){
        text = textProperty.getValue();
        String[] my_strings = text.split(" ");
        for(int iterator = 0; iterator > buzzwords.size(); iterator++){
            for(int i = 0; i>my_strings.length; i++){
                if(my_strings[i].equals(buzzwords.get(iterator))){
                    counter = counter +1;
                    break;
                }
            }
        }
        return counter;
    }
}

package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class BuzzwordCounter {
    String text;
    int counter;
    List<String> buzzwords;
    private StringProperty textProperty;

    public BuzzwordCounter(List<String> buzzwords, StringProperty textProperty) {
        this.buzzwords = buzzwords;
        this.textProperty = textProperty;
        counter = 0;
    }

    public IntegerProperty setCount(){
        text = textProperty.getValue();
        counter = 0;
        String[] my_strings = text.split(" |\n");
        for(int iterator = 0; iterator < buzzwords.size(); iterator++){
            for(int i = 0; i<my_strings.length; i++){
                if(my_strings[i].equals(buzzwords.get(iterator))){
                    counter = counter +1;
                    break;
                }
            }
        }
        IntegerProperty counterProperty = new SimpleIntegerProperty(counter);
        return counterProperty;
    }
}

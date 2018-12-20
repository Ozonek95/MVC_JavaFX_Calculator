package sample;

import java.util.LinkedList;
import java.util.List;

public class HistoryTextGenerator {
    private StringBuilder stringBuilder = new StringBuilder();
    private List<StringBuilder> stringBuilders = new LinkedList<>();

    private  CalculatorTextFieldGenerator textGenerator;

    public HistoryTextGenerator(CalculatorTextFieldGenerator textGenerator) {
        this.textGenerator = textGenerator;
    }

    public void addToHistoryArchive(){
        stringBuilders.add(stringBuilder);
    }

    public void updateHistory(){
        stringBuilder.append(textGenerator.getStringBuilder().toString());
    }
    public void updateHistory(String value){
        stringBuilder.append(value);
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }
    public void deleteLastLine(){
        String[] strings = stringBuilder.toString().split("\n");
        if (stringBuilder.length() > 0) {
            int last, prev = stringBuilder.length() - 1;
            while ((last = stringBuilder.lastIndexOf("\n", prev)) == prev) { prev = last - 1; }
            if (last >= 0) { stringBuilder.delete(last, stringBuilder.length());
            }
        }

        if(strings.length==1){
            setStringBuilder(new StringBuilder());
        }


    }

    public void setEarlierStringBuilder() {
        this.stringBuilder = stringBuilders.get(stringBuilders.size()-1);
        stringBuilders.remove(stringBuilders.size()-1);
    }

    public void setStringBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public void newLine() {
        stringBuilder.append("\r\n");
    }
}

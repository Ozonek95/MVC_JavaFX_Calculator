package sample;

public class HistoryTextGenerator {
    private StringBuilder stringBuilder = new StringBuilder();

    private  CalculatorTextFieldGenerator textGenerator;

    public HistoryTextGenerator(CalculatorTextFieldGenerator textGenerator) {
        this.textGenerator = textGenerator;
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

    public void setStringBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public void newLine() {
        stringBuilder.append("\r\n");
    }
}

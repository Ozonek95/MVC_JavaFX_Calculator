package sample;

public class HistoryTextGenerator {
    private StringBuilder stringBuilder = new StringBuilder();

    public void updateHistory(String text){
        stringBuilder.append(text);
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    public void setStringBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public void newLine() {
        stringBuilder.append(System.getProperty("line.separator"));
    }
}

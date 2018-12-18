package sample;

public class CalculatorTextFieldGenerator {
    StringBuilder stringBuilder = new StringBuilder();
    public  void updateTextField(String number){
        stringBuilder.append(number);
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    public void clearStringBuilder(){
        this.stringBuilder.delete(0,stringBuilder.toString().length());
    }

    public void setStringBuilder(double value) {
        clearStringBuilder();
        stringBuilder.append(value);
    }

    public void deleteLastChar(){
        if(stringBuilder.toString().length()>0){
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }
    }
}

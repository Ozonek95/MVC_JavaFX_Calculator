package sample;

import java.util.ArrayList;
import java.util.List;


public class CalculatorControllerImpl implements CalculatorMVC.Controller {

    private CalculatorMVC.View view;
    private CalculatorTextFieldGenerator textFieldGenerator = new CalculatorTextFieldGenerator();
    private HistoryTextGenerator historyTextGenerator = new HistoryTextGenerator();
    private Operation operation = Operation.EMPTY;
    private CurrentInputState inputState = CurrentInputState.INPUT_NOT_PROVIDED;
    private boolean firstOperation = true;
    private boolean afterEqualsOperation = false;
    private double currentResult = 0;
    private double tempNumber = 0;
    private List<Double> resultsQueue = new ArrayList<>();
    private double lastResult = 0;


    enum Operation {
        ADD, SUBTRACT, MULTIPLY, DEVIDE, SQRT, EMPTY
    }

    enum CurrentInputState {
        INPUT_PROVIDED, INPUT_NOT_PROVIDED
    }

    CalculatorControllerImpl(CalculatorMVC.View view) {
        this.view = view;
        resultsQueue.add(0.0);
    }

    @Override
    public void plus() {
        if (firstOperation && inputState.equals(CurrentInputState.INPUT_PROVIDED)) {
            currentResult = Double.parseDouble(textFieldGenerator.getStringBuilder().toString());
            firstOperation = false;
        } else {
            setTempNumber();
            if (operation != null) {
                if (inputState.equals(CurrentInputState.INPUT_PROVIDED)) {
                    setCurrentResult();
                    inputState = CurrentInputState.INPUT_NOT_PROVIDED;
                }
            }

        }
        if (afterEqualsOperation) {
            afterEqualsOperation = false;
        }

        operation = Operation.ADD;
        textFieldGenerator.clearStringBuilder();
        showResult();

    }

    @Override
    public void minus() {
        if (firstOperation && !textFieldGenerator.getStringBuilder().toString().equals("") && inputState.equals(CurrentInputState.INPUT_PROVIDED)) {
            currentResult = Double.parseDouble(textFieldGenerator.getStringBuilder().toString());
            firstOperation = false;
        }
        if (afterEqualsOperation) {
            afterEqualsOperation = false;
        } else {

            setTempNumber();
            if (operation != null) {
                if (inputState.equals(CurrentInputState.INPUT_PROVIDED)) {
                    setCurrentResult();
                    inputState = CurrentInputState.INPUT_NOT_PROVIDED;
                }
            }
        }
        operation = Operation.SUBTRACT;
        textFieldGenerator.clearStringBuilder();
        showResult();


    }

    @Override
    public void multiply() {
        if (firstOperation && !textFieldGenerator.getStringBuilder().toString().equals("") && inputState.equals(CurrentInputState.INPUT_PROVIDED)) {
            currentResult = Double.parseDouble(textFieldGenerator.getStringBuilder().toString());
            firstOperation = false;
        }
        if (afterEqualsOperation) {
            afterEqualsOperation = false;
        } else {
            setTempNumber();
            if (operation != null) {
                if (inputState.equals(CurrentInputState.INPUT_PROVIDED)) {
                    setCurrentResult();
                    inputState = CurrentInputState.INPUT_NOT_PROVIDED;
                }
            }
        }
        operation = Operation.MULTIPLY;
        textFieldGenerator.clearStringBuilder();
        showResult();

    }

    @Override
    public void divide() {
        if (firstOperation && !textFieldGenerator.getStringBuilder().toString().equals("") && inputState.equals(CurrentInputState.INPUT_PROVIDED)) {
            currentResult = Double.parseDouble(textFieldGenerator.getStringBuilder().toString());
            firstOperation = false;
        }
        if (afterEqualsOperation) {
            afterEqualsOperation = false;
        } else {

            setTempNumber();
            if (operation != null) {
                if (inputState.equals(CurrentInputState.INPUT_PROVIDED)) {
                    setCurrentResult();
                    inputState = CurrentInputState.INPUT_NOT_PROVIDED;
                }
            }
        }
        operation = Operation.DEVIDE;
        textFieldGenerator.clearStringBuilder();
        showResult();
    }

    @Override
    public void sqrt() {


        setTempNumber();
        setCurrentResult();
        if (inputState.equals(CurrentInputState.INPUT_PROVIDED) && resultsQueue.size() == 1) {
            operation = Operation.SQRT;
            setTempNumber();
            currentResult = tempNumber;
          //  inputState = CurrentInputState.INPUT_PROVIDED;
            lastResult = currentResult;
            resultsQueue.add(lastResult);
            currentResult = Math.sqrt(currentResult);
            textFieldGenerator.setStringBuilder(currentResult);
            showResult();
        }

        else if(inputState.equals(CurrentInputState.INPUT_PROVIDED)&&!operation.equals(Operation.SQRT)){
            operation = Operation.SQRT;
            lastResult=currentResult;
            currentResult=Math.sqrt(currentResult);
            textFieldGenerator.setStringBuilder(currentResult);
            showResult();

        }

        else {

            operation = Operation.SQRT;
            lastResult = currentResult;
            resultsQueue.add(lastResult);
            currentResult = Math.sqrt(currentResult);
          //  inputState = CurrentInputState.INPUT_PROVIDED;
            textFieldGenerator.setStringBuilder(currentResult);
            showResult();
        }

    }

    @Override
    public void typeEquals() {
        if (operation != null && inputState.equals(CurrentInputState.INPUT_PROVIDED)) {
            afterEqualsOperation = true;
            inputState = CurrentInputState.INPUT_NOT_PROVIDED;
            setTempNumber();
            setCurrentResult();
            showResult();
            setTempNumber(0);
        }


    }

    @Override
    public void typeDot() {
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField(".");
            addNumberToTextField();
        }

    }

    @Override
    public void deleteLastNumber() {
        if (inputState.equals(CurrentInputState.INPUT_PROVIDED)) {
            textFieldGenerator.deleteLastChar();
            if (textFieldGenerator.getStringBuilder().toString().length() == 0) {
                inputState = CurrentInputState.INPUT_NOT_PROVIDED;
            }
            addNumberToTextField();
        }
    }

    @Override
    public void typePrevious() {
        if (resultsQueue.size() > 0) {
            currentResult = resultsQueue.get(resultsQueue.size() - 1);
            resultsQueue.remove(resultsQueue.size() - 1);
            showResult();
        }
    }


    private void setTempNumber() {
        if (!textFieldGenerator.getStringBuilder().toString().equals("")) {
            tempNumber = Double.parseDouble(textFieldGenerator.getStringBuilder().toString());
        }
    }

    private void setTempNumber(int number) {
        tempNumber = number;
    }

    private void setCurrentResult() {
        if (operation.equals(Operation.ADD)) {
            lastResult = currentResult;
            currentResult += tempNumber;
            resultsQueue.add(lastResult);
        } else if (operation.equals(Operation.SUBTRACT)) {
            lastResult = currentResult;
            currentResult -= tempNumber;
            resultsQueue.add(lastResult);
        } else if (operation.equals(Operation.MULTIPLY)) {
            lastResult = currentResult;
            currentResult *= tempNumber;
        }
        else if(operation.equals(Operation.DEVIDE)){
            if(tempNumber==0){
                typeClear();
            }
            else {
            lastResult=currentResult;
            resultsQueue.add(lastResult);
            currentResult/=tempNumber;
            }
        }
    }


    @Override
    public void typeOne() {
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("1");
            //   historyTextGenerator.updateHistory("1");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
//        updateHistoryText();
        }
    }

    @Override
    public void typeTwo() {
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("2");
            //   historyTextGenerator.updateHistory("2");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
//        updateHistoryText();
        }
    }

    @Override
    public void typeThree() {
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("3");
            //   historyTextGenerator.updateHistory("3");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
            //  updateHistoryText();
        }
    }

    @Override
    public void typeFour() {
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("4");
            //   historyTextGenerator.updateHistory("4");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
            //  updateHistoryText();

        }
    }

    @Override
    public void typeFive() {
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("5");
            //   historyTextGenerator.updateHistory("5");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
            // updateHistoryText();
        }
    }

    @Override
    public void typeSix() {
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("6");
            //   historyTextGenerator.updateHistory("6");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
            // updateHistoryText();
        }
    }

    @Override
    public void typeSeven() {
        if (!operation.equals(operation.SQRT)) {
            textFieldGenerator.updateTextField("7");
            //  historyTextGenerator.updateHistory("7");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
            // updateHistoryText();
        }
    }

    @Override
    public void typeEight() {
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("8");
            //  historyTextGenerator.updateHistory("8");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
            //  updateHistoryText();
        }
    }

    @Override
    public void typeNine() {
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("9");
            // historyTextGenerator.updateHistory("9");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
            //    updateHistoryText();
        }
    }

    @Override
    public void typeZero() {
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("0");
            historyTextGenerator.updateHistory("0");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
            //   updateHistoryText();
        }
    }


    @Override
    public void typeClear() {

        view.setCounterText("00.0");
        view.setHistoryText("");
        operation = Operation.EMPTY;
        firstOperation = true;
        currentResult = 0;
        tempNumber = 0;
        textFieldGenerator.clearStringBuilder();
        //historyTextGenerator.setStringBuilder(new StringBuilder());
        resultsQueue = new ArrayList<>();
    }

    @Override
    public void addNumberToTextField() {

        view.setCounterText(textFieldGenerator.stringBuilder.toString());
    }

    public void showResult() {
        view.setCounterText(currentResult + "");
    }

    @Override
    public void updateHistoryText() {
        view.setHistoryText(historyTextGenerator.getStringBuilder().toString());
    }

}

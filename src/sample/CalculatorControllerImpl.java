package sample;

import java.util.ArrayList;
import java.util.List;


public class CalculatorControllerImpl implements CalculatorMVC.Controller {

    private CalculatorMVC.View view;
    private CalculatorTextFieldGenerator textFieldGenerator = new CalculatorTextFieldGenerator();
    private HistoryTextGenerator historyTextGenerator = new HistoryTextGenerator(textFieldGenerator);
    private Operation operation = Operation.EMPTY;
    private CurrentInputState inputState = CurrentInputState.INPUT_NOT_PROVIDED;
    private boolean firstOperation = true;
    private boolean afterEqualsOperation = false;
    private double currentResult = 0;
    private double tempNumber = 0;
    private List<Double> resultsQueue = new ArrayList<>();
    private double lastResult = 0;


    enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, SQRT, EMPTY
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
        if (!operation.equals(Operation.ADD) && !operation.equals(Operation.SQRT) && !operation.equals(Operation.EMPTY)) {
            if (historyTextGenerator.getStringBuilder().length() > 0) {
                historyTextGenerator.getStringBuilder().deleteCharAt(historyTextGenerator.getStringBuilder().length() - 1);
                historyTextGenerator.getStringBuilder().append("+");
            }

        }
        writeSignToHistory("+");
        writeSignToHistoryAtBeginningOfLineAfterEqualsOperation("+");
        operate();

        changeCurrentFlags(Operation.ADD);
        textFieldGenerator.clearStringBuilder();
        showResult();

    }


    @Override
    public void minus() {
        if (!operation.equals(Operation.SUBTRACT) && !operation.equals(Operation.SQRT) && !operation.equals(Operation.EMPTY)) {
            if (historyTextGenerator.getStringBuilder().length() > 0) {
                historyTextGenerator.getStringBuilder().deleteCharAt(historyTextGenerator.getStringBuilder().length() - 1);
                historyTextGenerator.getStringBuilder().append("-");
            }

        }
        if (afterEqualsOperation || firstOperation) {
            textFieldGenerator.getStringBuilder().append('-');
            addNumberToTextField();
        }



        writeSignToHistory("-");
        writeSignToHistoryAtBeginningOfLineAfterEqualsOperation("-");
        operate();
        changeCurrentFlags(Operation.SUBTRACT);
        if (!textFieldGenerator.getStringBuilder().toString().equals("-")) {
            textFieldGenerator.clearStringBuilder();
        }

        if(!textFieldGenerator.getStringBuilder().toString().equals("-")){
            showResult();
        }



    }


    @Override
    public void multiply() {
        if (!operation.equals(Operation.MULTIPLY) && !operation.equals(Operation.SQRT)) {
            if (historyTextGenerator.getStringBuilder().length() > 0) {
                historyTextGenerator.getStringBuilder().deleteCharAt(historyTextGenerator.getStringBuilder().length() - 1);
                historyTextGenerator.getStringBuilder().append("*");
            }

        }
        writeSignToHistory("*");
        writeSignToHistoryAtBeginningOfLineAfterEqualsOperation("*");
        operate();
        changeCurrentFlags(Operation.MULTIPLY);
        textFieldGenerator.clearStringBuilder();
        showResult();

    }

    @Override
    public void divide() {
        if (!operation.equals(Operation.MULTIPLY) && !operation.equals(Operation.SQRT)) {
            if (historyTextGenerator.getStringBuilder().length() > 0) {
                historyTextGenerator.getStringBuilder().deleteCharAt(historyTextGenerator.getStringBuilder().length() - 1);
                historyTextGenerator.getStringBuilder().append("/");
            }

        }
        writeSignToHistory("/");
        writeSignToHistoryAtBeginningOfLineAfterEqualsOperation("/");
        operate();
        changeCurrentFlags(Operation.DIVIDE);
        textFieldGenerator.clearStringBuilder();
        showResult();
    }

    private void changeCurrentFlags(Operation divide) {
        operation = divide;
        inputState = CurrentInputState.INPUT_NOT_PROVIDED;
    }

    @Override
    public void sqrt() {


        setTempNumber();
        setCurrentResult();
        if (inputState.equals(CurrentInputState.INPUT_PROVIDED) && resultsQueue.size() == 1) {
            operation = Operation.SQRT;
            setTempNumber();
            currentResult = tempNumber;
            lastResult = currentResult;
            resultsQueue.add(lastResult);
            currentResult = Math.sqrt(currentResult);
            textFieldGenerator.setStringBuilder(currentResult);
            showResult();
        } else if (inputState.equals(CurrentInputState.INPUT_PROVIDED) && !operation.equals(Operation.SQRT)) {
            operation = Operation.SQRT;
            lastResult = currentResult;
            currentResult = Math.sqrt(currentResult);
            textFieldGenerator.setStringBuilder(currentResult);
            showResult();

        } else {

            operation = Operation.SQRT;
            lastResult = currentResult;
            resultsQueue.add(lastResult);
            currentResult = Math.sqrt(currentResult);
            textFieldGenerator.setStringBuilder(currentResult);
            showResult();
        }

    }

    @Override
    public void typeEquals() {

        if (operation != null && inputState.equals(CurrentInputState.INPUT_PROVIDED)) {

            equalsOperations();
        }


    }

    private void equalsOperations() {
        historyTextGenerator.newLine();
        afterEqualsOperation = true;
        inputState = CurrentInputState.INPUT_NOT_PROVIDED;
        setTempNumber();
        setCurrentResult();
        historyTextGenerator.updateHistory();
        historyTextGenerator.updateHistory("=");
        historyTextGenerator.updateHistory(String.valueOf(currentResult));
        historyTextGenerator.newLine();
        updateHistoryText();
        textFieldGenerator.setStringBuilder("");
        showResult();
        setTempNumber(0);
    }

    private void operate() {
        if (firstOperation && inputState.equals(CurrentInputState.INPUT_PROVIDED) && !textFieldGenerator.getStringBuilder().toString().equals("-")) {
            currentResult = Double.parseDouble(textFieldGenerator.getStringBuilder().toString());
            firstOperation = false;
        } else {
            if (!textFieldGenerator.getStringBuilder().toString().equals("-"))
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
    }

    private void writeSignToHistoryAtBeginningOfLineAfterEqualsOperation(String s) {
        if (afterEqualsOperation) {
            if (historyTextGenerator.getStringBuilder().toString().length() > 0) {
                if (!String.valueOf(historyTextGenerator.getStringBuilder().charAt(historyTextGenerator.getStringBuilder().length() - 1)).equals(s)) {
                    historyTextGenerator.updateHistory(s);
                }

            }
        }
    }

    private void writeSignToHistory(String s) {
        if (inputState.equals(CurrentInputState.INPUT_PROVIDED)) {
            historyTextGenerator.updateHistory();
            historyTextGenerator.updateHistory(s);
            // historyTextGenerator.addToHistoryArchive();
            updateHistoryText();
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
        if (resultsQueue.size() == 0) {
            textFieldGenerator.clearStringBuilder();
        }
        historyTextGenerator.deleteLastLine();
        updateHistoryText();
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
        } else if (operation.equals(Operation.DIVIDE)) {
            if (tempNumber == 0) {
                typeClear();
            } else {
                lastResult = currentResult;
                resultsQueue.add(lastResult);
                currentResult /= tempNumber;
            }
        }
    }

    private void newOperation() {
        currentResult = 0;
        operation = Operation.EMPTY;
        resultsQueue = new ArrayList<>();
        resultsQueue.add(0.0);
        inputState = CurrentInputState.INPUT_NOT_PROVIDED;
        firstOperation = true;
    }


    @Override
    public void typeOne() {
        if (afterEqualsOperation) {
            historyTextGenerator.newLine();
            newOperation();
        }
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("1");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
        }
    }

    @Override
    public void typeTwo() {
        if (afterEqualsOperation) {
            historyTextGenerator.newLine();
            newOperation();
        }
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("2");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
        }
    }

    @Override
    public void typeThree() {
        if (afterEqualsOperation) {
            historyTextGenerator.newLine();
            newOperation();
        }
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("3");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
        }
    }

    @Override
    public void typeFour() {
        if (afterEqualsOperation) {
            historyTextGenerator.newLine();
            newOperation();
        }
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("4");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();

        }
    }

    @Override
    public void typeFive() {
        if (afterEqualsOperation) {
            historyTextGenerator.newLine();
            newOperation();
        }
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("5");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
        }
    }

    @Override
    public void typeSix() {
        if (afterEqualsOperation) {
            historyTextGenerator.newLine();
            newOperation();
        }
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("6");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
        }
    }

    @Override
    public void typeSeven() {
        if (afterEqualsOperation) {
            historyTextGenerator.newLine();
            newOperation();
        }
        if (!operation.equals(operation.SQRT)) {
            textFieldGenerator.updateTextField("7");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
        }
    }

    @Override
    public void typeEight() {
        if (afterEqualsOperation) {
            historyTextGenerator.newLine();
            newOperation();
        }
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("8");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
        }
    }

    @Override
    public void typeNine() {
        if (afterEqualsOperation) {
            historyTextGenerator.newLine();
            newOperation();
        }
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("9");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
        }
    }

    @Override
    public void typeZero() {
        if (afterEqualsOperation) {
            historyTextGenerator.newLine();
            newOperation();
        }
        if (!operation.equals(Operation.SQRT)) {
            textFieldGenerator.updateTextField("0");
            inputState = CurrentInputState.INPUT_PROVIDED;
            addNumberToTextField();
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
        historyTextGenerator.setStringBuilder(new StringBuilder());
        resultsQueue = new ArrayList<>();
        resultsQueue.add(0.0);
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

package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CalculatorView implements CalculatorMVC.View {

    CalculatorMVC.Controller controller = new CalculatorControllerImpl(this);


    @FXML
    private TextField display_textfield;

    @FXML

    private TextArea history_text_area;

    @FXML
    public void clickOne() {

        controller.typeOne();
    }

    @FXML
    public void clickTwo() {

        controller.typeTwo();
    }

    @FXML
    public void clickThree() {
        controller.typeThree();

    }

    @FXML
    public void clickFour() {

        controller.typeFour();
    }

    @FXML
    public void clickFive() {

        controller.typeFive();
    }

    @FXML
    public void clickSix() {

        controller.typeSix();
    }

    @FXML
    public void clickSeven() {

        controller.typeSeven();
    }

    @FXML
    public void clickEight() {

        controller.typeEight();
    }

    @FXML
    public void clickNine() {
        controller.typeNine();
    }

    @FXML
    public void clickZero() {
        controller.typeZero();
    }

    @FXML
    public void dot(){
        controller.typeDot();
    }

    @FXML

    public void clickAdd(){
        controller.plus();
    }

    @FXML

    public void clickSubtract(){
        controller.minus();
    }

    @FXML

    public void clickMultiply(){
        controller.multiply();
    }

    @FXML
    public void clickEquals(){
        controller.typeEquals();
    }

    @FXML
    public void clickClear(){
        controller.typeClear();
    }

    @FXML
    public void previous(){
        controller.typePrevious();
    }

    @FXML
    public void deleteLastNumber(){
        controller.deleteLastNumber();
    }

    @FXML

    public void clickSqrt(){
        controller.sqrt();
    }

    @FXML

    public void clickDivide(){
        controller.divide();
    }

    @Override
    public void setCounterText(String counter) {
        display_textfield.setText(counter + "");
    }

    @Override
    public void setHistoryText(String history) {
        history_text_area.setText(history);
    }
}

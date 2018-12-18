package sample;

public interface CalculatorMVC {
    interface View {
        void setCounterText(String counter);
        void setHistoryText(String history);
    }

    interface Controller {
        void plus();
        void minus();
        void multiply();
        void divide();
        void typeOne();
        void typeTwo();
        void typeThree();
        void typeFour();
        void typeFive();
        void typeSix();
        void typeSeven();
        void typeEight();
        void typeNine();
        void typeZero();
        void typeEquals();
        void typeClear();
        void addNumberToTextField();
        void updateHistoryText();

        void typeDot();

        void deleteLastNumber();

        void typePrevious();

        void sqrt();
    }
}

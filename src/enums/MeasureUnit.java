package enums;

public enum MeasureUnit {
    PIECES("шт."),
    PACKAGES("уп."),
    KILOGRAMMS("кг");

    private final String title;

    MeasureUnit(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return this.title;
    }
}
package pl.task.enums;

/**
 * Created by dominik on 12.03.21.
 */
public enum Field {
    a("a"), b("b"), c("c"), d("d"), e("e"), f("f"), g("g"), h("h");

    private String stringVal;

    Field(String stringVal) {
        this.stringVal = stringVal;
    }

    public String getStringVal() {
        return stringVal;
    }
}

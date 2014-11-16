package ar.exa.edu.unicen.compiler.lexical.utils;

public class Triplet {

    private final Object operator;

    private final Object operand1;

    private Object operand2;

    public Triplet(final Object operator, final Object operand1,
            final Object operand2) {

        this.operator = operator;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public Object getOperand1() {
        return this.operand1;
    }

    public Object getOperand2() {
        return this.operand2;
    }

    public Object getOperator() {
        return this.operator;
    }

    public void setOperand2(final Object operand2) {
        this.operand2 = operand2;
    }

}

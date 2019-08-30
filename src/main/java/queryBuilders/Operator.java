package queryBuilders;

public enum Operator
{
    EQUALS,
    GREATER_THAN,
    LESSER_THAN,
    NOT_EQUALS;

    public String getOperator() {
        if(this == EQUALS){
            return " = ";
        }
        else if (this == GREATER_THAN){
            return " > ";
        }
        else if(this == LESSER_THAN){
            return " < ";
        }
        else {
            return " != ";
        }
    }
}

package exception;

public class FieldMissingException extends Exception {
    /**
     * This exception must be thrown in place where mandatory field is missing.
     * @param fieldName of field which is missing
     */
    public FieldMissingException(String fieldName){
        super(fieldName+" is  missing");
    }
    public FieldMissingException(String fieldName, String help){
        super(fieldName + " is mandatory or can be wrong. Try " + help);
    }
}

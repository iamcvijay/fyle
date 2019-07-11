package example;

import java.util.ArrayList;
import java.util.List;

public class FieldFactory
{
    public static List<String> getBankFields(){
        List<String> bankFields = new ArrayList<String>();
        bankFields.add("name");
        bankFields.add("id");
        return bankFields;
    }

    public static List<String> getBranchFields(){
        List<String> branchFields = new ArrayList<String>();
        branchFields.add("ifsc");
        branchFields.add("bank_id");
        branchFields.add("branch");
        branchFields.add("address");
        branchFields.add("city");
        branchFields.add("districe");
        branchFields.add("state");
        return branchFields;
    }
}

package net.redhogs.cronparser.parser.field;

public class On extends CronFieldExpression {
    private int time;
    private int nth;
    private SpecialCharEnum specialChar;

    public On(FieldConstraints constraints, String exp){
        super(constraints);
        nth = -1;
        specialChar = SpecialCharEnum.NONE;
        time = getConstraints().validateInRange(
                getConstraints().intToInt(
                        getConstraints().stringToInt(
                                retrieveSpecialChar(getConstraints(), exp)
                        )
                )
        );
    }

    public int getTime(){
        return time;
    }

    public int getNth() {
        return nth;
    }

    public SpecialCharEnum getSpecialChar() {
        return specialChar;
    }

    private String retrieveSpecialChar(FieldConstraints constraints, String exp){
        if(exp.contains("#")){
            specialChar = SpecialCharEnum.HASH;
            String [] array = exp.split("#");
            nth = constraints.validateInRange(constraints.intToInt(constraints.stringToInt(array[1])));
            if(array[0].isEmpty()){
                throw new RuntimeException("Time should be specified!");
            }
            return array[0];
        }
        if(exp.contains("L")){
            specialChar = SpecialCharEnum.L;
            exp = exp.replace("L", "");
            if("".equals(exp)){
                return "0";//to avoid a NumberFormatException
            }else{
                return exp;
            }
        }
        if(exp.contains("W")){
            specialChar = SpecialCharEnum.W;
            return exp.replace("W", "");
        }
        return exp;
    }

    public enum  SpecialCharEnum {
        L, W, HASH, NONE
    }
}

package sportify.bean;

/**
 * The SportQuizBean class represents a bean for a sport quiz.
 */
public class SportQuizBean {

    /**
     * This method checks if a given string
     * is numeric.
     *
     * @param s the string to be checked
     *
     * @return true if the string is numeric,
     * false otherwise
     */
    public boolean checkIsNumeric(String s){
        return s.matches("\\d+");
    }

    /**
     * This method checks if a given environment
     * is either "indoor" or "outdoor".
     *
     * @param env the environment to be checked
     *
     * @return true if the environment is "indoor"
     * or "outdoor", false otherwise
     */
    public boolean checkEnv(String env){
        return env.equals("indoor") || env.equals("outdoor");
    }

    public boolean checkType(String type){
        return type.equals("alone") || type.equals("group");
    }
}
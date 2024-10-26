import DatabaseDomain.Curriculum;

public class Query {

    // WHERE curriculum = CS
    public static boolean query(Fact fact) {
        if (fact.curriculum == Curriculum.CS) {
            return true;
        }
        return false;
    }
}

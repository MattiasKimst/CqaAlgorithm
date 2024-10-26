import DatabaseDomain.Curriculum;

public class Query {
    public final static int K = 1; //number of atoms in query

    // WHERE curriculum = CS
    public static boolean query(Fact fact) {
        if (fact.curriculum == Curriculum.CS) {
            return true;
        }
        return false;
    }
}

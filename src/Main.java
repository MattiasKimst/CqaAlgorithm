import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        HashSet<Fact> database = Database.populateDatabase();
        System.out.println(Algorithm.isQueryCertainOnGivenDatabase(database) ? "Query is certain" : "Query is not certain");
    }


}
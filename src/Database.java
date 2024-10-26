import DatabaseDomain.Curriculum;
import java.util.HashSet;

public class Database {
    public static HashSet<Fact> database = new HashSet<>();

    public static HashSet<Fact> populateDatabase(){
        database.add(new Fact(1,"Mart", "Mets", Curriculum.CS));
        database.add(new Fact(2, "Eha", "Kask", Curriculum.MEDICINE));
        database.add(new Fact(3, "Kadri", "Pärn", Curriculum.LAW));

        database.add(new Fact(1,"Mart", "Põld", Curriculum.MATHS));

        return database;
    }
}

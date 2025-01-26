package main.CQA;

import main.data.models.Database;
import main.data.queries.Query;
import main.data.relations.Fact;
import java.util.HashSet;

public class Delta {
    public HashSet<HashSet<Fact>> set;

    public void initialize(Query query, Database database) {
        set = query.findSatisfyingFacts(database);
        //System.out.println("Delta initialized: " + set.toString());
    }
}

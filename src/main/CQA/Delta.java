package main.CQA;

import main.data.models.Database;
import main.data.queries.Query;
import main.data.facts.Fact;
import java.util.HashSet;

/**
 * A class containing Delta - a set initially containing query-satisfying facts and where new facts are added during
 * CQA algorithm iterations
 * Contains method for finding initally facts satisfying the query to Delta
 */
public class Delta {

    public HashSet<HashSet<Fact>> set;

    public void initialize(Query query, Database database) {
        set = query.findPluggedQuerySatisfyingFacts(database);
        //System.out.println("Delta initialized: " + set.toString());
    }

}

package main.data.queries;

import main.data.models.Database;
import main.data.facts.Fact;
import java.util.HashSet;
import java.util.List;

/**
 * An interface that defines which methods query classes should implement to keep their behaviour similar
 */
public interface Query {

    public int getK();
    public Query createWithPluggedVariables(List<String> freeVariables);
    public Boolean runBooleanQuery(List<Fact> facts);
    public List<List<String>> runSelectQuery(Database database);
    public HashSet<HashSet<Fact>> findSatisfyingFacts(Database database);
    public void makeCombinationOfFactsSatisfyQuery(List<Fact> facts);
    public String getQueryAnswers();

}

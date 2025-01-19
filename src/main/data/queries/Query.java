package main.data.queries;

import main.data.models.Database;
import main.data.relations.Fact;
import java.util.HashSet;
import java.util.List;

public interface Query {

    public int getK();
    public Query createWithPluggedVariables(List<String> freeVariables);
    public Boolean runBooleanQuery(List<Fact> facts);
    public List<List<String>> runSelectQuery(Database database);
    public HashSet<HashSet<Fact>> findSatisfyingFacts(Database database);
}

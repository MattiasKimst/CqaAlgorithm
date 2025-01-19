package main.data.queries;

import main.data.models.Database;
import main.data.relations.Fact;

import java.util.HashSet;
import java.util.List;

public interface Query {

    public int getK();
    public Boolean runBooleanQuery(List<Fact> facts);
    public List<List<String>> runSelectQuery(Database database);
    public void plug(List<String> freeVariables);
    public HashSet<HashSet<Fact>> findSatisfyingFacts(Database database);
}

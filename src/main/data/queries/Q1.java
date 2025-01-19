package main.data.queries;

import main.data.models.Database;
import main.data.relations.Fact;
import main.data.relations.R1;
import main.data.relations.R2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Q1 implements Query {

    private final String z;

    public Q1() {
        this.z = null;
    }

    private Q1(String z) { this.z = z; }

    public int getK() {
        return 2;
    }

    public Query createWithPluggedVariables(List<String> freeVariables) {
        return new Q1(freeVariables.getFirst());
    }

    //query itself
    private Boolean booleanQueryCondition(R1 r1Fact, R2 r2Fact) {
        return r1Fact.y.equals(r2Fact.y) && r1Fact.z.equals(z);
    }

    public Boolean runBooleanQuery(List<Fact> facts) {
        R1 r1Fact = (R1) facts.get(0);
        R2 r2Fact = (R2) facts.get(1);
        return booleanQueryCondition(r1Fact, r2Fact);
    }

    private List<String> selectQueryCondition(R1 r1Fact, R2 r2Fact) {
        if (r1Fact.y.equals(r2Fact.y)) {
            List<String> answer = new ArrayList<>();
            answer.add(r1Fact.z);
            return answer;
        }
        return null;
    }

    public List<List<String>> runSelectQuery(Database database) {
        List<Fact> firstList = database.getDatabase().get(0);
        List<Fact> secondList = database.getDatabase().get(1);

        return firstList.stream()
                .flatMap(fact1 -> secondList.stream()
                        .map(fact2 -> selectQueryCondition((R1) fact1, (R2) fact2)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public HashSet<HashSet<Fact>> findSatisfyingFacts(Database database) {
        List<Fact> firstList = database.getDatabase().get(0);
        List<Fact> secondList = database.getDatabase().get(1);

        return firstList.stream()
                .flatMap(fact1 -> secondList.stream()
                        .filter(fact2 -> booleanQueryCondition((R1) fact1, (R2) fact2))
                        .map(fact2 -> {
                            HashSet<Fact> set = new HashSet<>();
                            set.add(fact1);
                            set.add(fact2);
                            return set;
                        }))
                .collect(Collectors.toCollection(HashSet::new));
    }
}

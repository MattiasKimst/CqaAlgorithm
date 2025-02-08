package main.data.queries;

import main.data.models.Database;
import main.data.relations.Fact;
import main.data.relations.R1;
import main.data.relations.R2_2;
import main.data.relations.R3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Q4 implements Query {

    private final String z;
    private final String d;

    public Q4() {
        this.z = null;
        this.d = null;
    }

    private Q4(String z, String d) {
        this.z = z;
        this.d = d;
    }

    public int getK() {
        return 3;
    }

    public Query createWithPluggedVariables(List<String> freeVariables) {
        return new Q4(freeVariables.getFirst(), freeVariables.getLast());
    }

    //query itself
    private Boolean queryCondition(R1 r1Fact, R3 r3Fact, R2_2 r2_2Fact) {
        return r1Fact.y.equals(r3Fact.y) && r3Fact.v.equals(r2_2Fact.v);
    }

    private Boolean booleanQueryCondition(R1 r1Fact, R3 r3Fact, R2_2 r2_2Fact) {
        return queryCondition(r1Fact, r3Fact, r2_2Fact) && r1Fact.z.equals(z) && r2_2Fact.d.equals(d);
    }

    public Boolean runBooleanQuery(List<Fact> facts) {
        R1 r1Fact = (R1) facts.get(0);
        R3 r3Fact = (R3) facts.get(1);
        R2_2 r2_2Fact = (R2_2) facts.get(2);
        return booleanQueryCondition(r1Fact, r3Fact, r2_2Fact);
    }

    private List<String> selectAnswer(R1 r1Fact, R3 r3Fact, R2_2 r2_2Fact) {
        if (queryCondition(r1Fact, r3Fact, r2_2Fact)) {
            List<String> answer = new ArrayList<>();
            answer.add(r1Fact.z);
            answer.add(r2_2Fact.d);
            return answer;
        }
        return null;
    }

    public List<List<String>> runSelectQuery(Database database) {
        List<Fact> firstList = database.getDatabase().get(0);
        List<Fact> secondList = database.getDatabase().get(1);
        List<Fact> thirdList = database.getDatabase().get(2);

        return firstList.parallelStream()
                .flatMap(fact1 -> secondList.stream()
                        .flatMap(fact2 -> thirdList.stream()
                                .map(fact3 -> selectAnswer((R1) fact1, (R3) fact2, (R2_2) fact3))))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public HashSet<HashSet<Fact>> findSatisfyingFacts(Database database) {
        List<Fact> firstList = database.getDatabase().get(0);
        List<Fact> secondList = database.getDatabase().get(1);
        List<Fact> thirdList = database.getDatabase().get(2);

        return firstList.parallelStream()
                .flatMap(fact1 -> secondList.stream()
                        .flatMap(fact2 -> thirdList.stream()
                                .filter(fact3 -> booleanQueryCondition((R1) fact1, (R3) fact2, (R2_2) fact3))
                                .map(fact3 -> {
                                    HashSet<Fact> set = new HashSet<>();
                                    set.add(fact1);
                                    set.add(fact2);
                                    set.add(fact3);
                                    return set;
                                })))
                .collect(Collectors.toCollection(HashSet::new));
    }

    public void makeCombinationOfFactsSatisfyQuery(List<Fact> facts) {
        R1 r1Fact = (R1) facts.get(0);
        R3 r3Fact = (R3) facts.get(1);
        R2_2 r2_2Fact = (R2_2) facts.get(2);
        r1Fact.setY(r3Fact.y);
        r3Fact.setV(r2_2Fact.v);
    }

    public String getQueryAnswers() {
        return "z = " + z + ", d = " + d;
    }
}

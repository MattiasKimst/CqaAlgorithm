package main.data.queries;

import main.data.models.Database;
import main.data.relations.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Q6 implements Query {

    private final String z;

    public Q6() {
        this.z = null;
    }

    private Q6(String z) { this.z = z; }

    public int getK() {
        return 3;
    }

    public Query createWithPluggedVariables(List<String> freeVariables) {
        return new Q6(freeVariables.getFirst());
    }

    //query itself
    private Boolean queryCondition(R1 r1Fact, R2_3 r2_3Fact, R5 r5Fact) {
        return r1Fact.y.equals(r2_3Fact.y) && r2_3Fact.y.equals(r5Fact.y) && r1Fact.x.equals(r5Fact.x);
    }

    private Boolean booleanQueryCondition(R1 r1Fact, R2_3 r2_3Fact, R5 r5Fact) {
        return queryCondition(r1Fact, r2_3Fact, r5Fact) && r1Fact.z.equals(z);
    }

    public Boolean runBooleanQuery(List<Fact> facts) {
        R1 r1Fact = (R1) facts.get(0);
        R2_3 r2_3Fact = (R2_3) facts.get(1);
        R5 r5Fact = (R5) facts.get(2);
        return booleanQueryCondition(r1Fact, r2_3Fact, r5Fact);
    }

    private List<String> selectAnswer(R1 r1Fact, R2_3 r2_3Fact, R5 r5Fact) {
        if (queryCondition(r1Fact, r2_3Fact, r5Fact)) {
            List<String> answer = new ArrayList<>();
            answer.add(r1Fact.z);
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
                                .map(fact3 -> selectAnswer((R1) fact1, (R2_3) fact2, (R5) fact3))))
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
                                .filter(fact3 -> booleanQueryCondition((R1) fact1, (R2_3) fact2, (R5) fact3))
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
        R2_3 r2_3Fact = (R2_3) facts.get(1);
        R5 r5Fact = (R5) facts.get(2);
        r1Fact.setY(r2_3Fact.y);
        r5Fact.setY(r2_3Fact.y);
        r1Fact.setX(r5Fact.x);
    }

    public String getQueryAnswers() {
        return "z = " + z;
    }
}

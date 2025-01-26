package main.data.queries;

import main.data.models.Database;
import main.data.relations.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Q13 implements Query {

    private final String v;

    public Q13() {
        this.v = null;
    }

    private Q13(String v) {
        this.v = v;
    }

    public int getK() {
        return 4;
    }

    public Query createWithPluggedVariables(List<String> freeVariables) {
        return new Q13(freeVariables.getFirst());
    }

    //query itself
    private Boolean queryCondition(R3_2 r3_2Fact, R6 r6Fact, R7 r7Fact, R4_3 r4_3Fact) {
        return r6Fact.y.equals(r3_2Fact.y) && r7Fact.x.equals(r3_2Fact.x) && r7Fact.z.equals(r6Fact.z)
                && r4_3Fact.x.equals(r7Fact.x);
    }

    private Boolean booleanQueryCondition(R3_2 r3_2Fact, R6 r6Fact, R7 r7Fact, R4_3 r4_3Fact) {
        return queryCondition(r3_2Fact, r6Fact, r7Fact, r4_3Fact) && r4_3Fact.v.equals(v);
    }

    public Boolean runBooleanQuery(List<Fact> facts) {
        R3_2 r3_2Fact = (R3_2) facts.get(0);
        R6 r6Fact = (R6) facts.get(1);
        R7 r7Fact = (R7) facts.get(2);
        R4_3 r4_3Fact = (R4_3) facts.get(3);
        return booleanQueryCondition(r3_2Fact, r6Fact, r7Fact, r4_3Fact);
    }

    private List<String> selectAnswer(R3_2 r3_2Fact, R6 r6Fact, R7 r7Fact, R4_3 r4_3Fact) {
        if (queryCondition(r3_2Fact, r6Fact, r7Fact, r4_3Fact)) {
            List<String> answer = new ArrayList<>();
            answer.add(r4_3Fact.v);
            return answer;
        }
        return null;
    }

    public List<List<String>> runSelectQuery(Database database) {
        List<Fact> firstList = database.getDatabase().get(0);
        List<Fact> secondList = database.getDatabase().get(1);
        List<Fact> thirdList = database.getDatabase().get(2);
        List<Fact> fourthList = database.getDatabase().get(3);

        return firstList.stream()
                .flatMap(fact1 -> secondList.stream()
                        .flatMap(fact2 -> thirdList.stream()
                                .flatMap(fact3 -> fourthList.stream()
                                        .map(fact4 -> selectAnswer(
                                                (R3_2) fact1,
                                                (R6) fact2,
                                                (R7) fact3,
                                                (R4_3) fact4)))))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public HashSet<HashSet<Fact>> findSatisfyingFacts(Database database) {
        List<Fact> firstList = database.getDatabase().get(0);
        List<Fact> secondList = database.getDatabase().get(1);
        List<Fact> thirdList = database.getDatabase().get(2);
        List<Fact> fourthList = database.getDatabase().get(3); // Assuming the fourth list exists

        return firstList.stream()
                .flatMap(fact1 -> secondList.stream()
                        .flatMap(fact2 -> thirdList.stream()
                                .flatMap(fact3 -> fourthList.stream()
                                        .filter(fact4 -> booleanQueryCondition(
                                                (R3_2) fact1,
                                                (R6) fact2,
                                                (R7) fact3,
                                                (R4_3) fact4))
                                        .map(fact4 -> {
                                            HashSet<Fact> set = new HashSet<>();
                                            set.add(fact1);
                                            set.add(fact2);
                                            set.add(fact3);
                                            set.add(fact4);
                                            return set;
                                        }))))
                .collect(Collectors.toCollection(HashSet::new));
    }


    public void makeCombinationOfFactsSatisfyQuery(List<Fact> facts) {
        R3_2 r3_2Fact = (R3_2) facts.get(0);
        R6 r6Fact = (R6) facts.get(1);
        R7 r7Fact = (R7) facts.get(2);
        R4_3 r4_3Fact = (R4_3) facts.get(3);

        r3_2Fact.setY(r6Fact.y);
        r6Fact.setZ(r7Fact.z);
        r7Fact.setX(r3_2Fact.x);
        r4_3Fact.setX(r3_2Fact.x);
    }

    public String getQueryAnswers() {
        return "v = " + v;
    }
}

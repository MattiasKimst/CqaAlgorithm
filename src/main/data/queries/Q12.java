package main.data.queries;

import main.data.models.Database;
import main.data.relations.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Q12 implements Query {

    private final String v;
    private final String d;

    public Q12() {
        this.v = null;
        this.d = null;
    }

    private Q12(String v, String d) {
        this.v = v;
        this.d = d;
    }

    public int getK() {
        return 4;
    }

    public Query createWithPluggedVariables(List<String> freeVariables) {
        return new Q12(freeVariables.getFirst(), freeVariables.getLast());
    }

    //query itself
    private Boolean queryCondition(R3_2 r3_2Fact, R6 r6Fact, R1_2 r1_2Fact, R4_3 r4_3Fact) {
        return r6Fact.y.equals(r3_2Fact.y) && r1_2Fact.x.equals(r3_2Fact.x) && r1_2Fact.z.equals(r6Fact.z)
                && r4_3Fact.x.equals(r1_2Fact.x);
    }

    private Boolean booleanQueryCondition(R3_2 r3_2Fact, R6 r6Fact, R1_2 r1_2Fact, R4_3 r4_3Fact) {
        return queryCondition(r3_2Fact, r6Fact, r1_2Fact, r4_3Fact)
                && r1_2Fact.d.equals(d) && r4_3Fact.v.equals(v);
    }

    public Boolean runBooleanQuery(List<Fact> facts) {
        R3_2 r3_2Fact = (R3_2) facts.get(0);
        R6 r6Fact = (R6) facts.get(1);
        R1_2 r1_2Fact = (R1_2) facts.get(2);
        R4_3 r4_3Fact = (R4_3) facts.get(3);
        return booleanQueryCondition(r3_2Fact, r6Fact, r1_2Fact, r4_3Fact);
    }

    private List<String> selectAnswer(R3_2 r3_2Fact, R6 r6Fact, R1_2 r1_2Fact, R4_3 r4_3Fact) {
        if (queryCondition(r3_2Fact, r6Fact, r1_2Fact, r4_3Fact)) {
            List<String> answer = new ArrayList<>();
            answer.add(r4_3Fact.v);
            answer.add(r1_2Fact.d);
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
                                                (R1_2) fact3,
                                                (R4_3) fact4)))))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public HashSet<HashSet<Fact>> findSatisfyingFacts(Database database) {
        List<Fact> firstList = database.getDatabase().get(0);
        List<Fact> secondList = database.getDatabase().get(1);
        List<Fact> thirdList = database.getDatabase().get(2);
        List<Fact> fourthList = database.getDatabase().get(3);

        return firstList.stream()
                .flatMap(fact1 -> secondList.stream()
                        .flatMap(fact2 -> thirdList.stream()
                                .flatMap(fact3 -> fourthList.stream()
                                        .filter(fact4 -> booleanQueryCondition(
                                                (R3_2) fact1,
                                                (R6) fact2,
                                                (R1_2) fact3,
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
        R1_2 r1_2Fact = (R1_2) facts.get(2);
        R4_3 r4_3Fact = (R4_3) facts.get(3);

        r3_2Fact.setY(r6Fact.y);
        r6Fact.setZ(r1_2Fact.z);
        r1_2Fact.setX(r3_2Fact.x);
        r4_3Fact.setX(r3_2Fact.x);
    }

    public String getQueryAnswers() {
        return "v = " + v + " d = " + d;
    }
}

package main.data.queries;

import main.data.models.Database;
import main.data.facts.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A class implementing the Query interface and representing a specific query.
 * It defines methods to create Boolean query instances with plugged answer z or SELECT query with z quantified;
 * methods to run the SELECT or Boolean query;
 * a method findSatisfyingFacts for finding the facts that satisfy the query for initializing set Delta in CQA algorithm;
 * a method for constructing a query answer out of given facts.
 */
public class Q9 implements Query {

    private final String z;

    public Q9() {
        this.z = null;
    }

    private Q9(String z) { this.z = z; }

    public int getK() {
        return 3;
    }

    public Query createWithPluggedVariables(List<String> freeVariables) {
        return new Q9(freeVariables.getFirst());
    }

    //query itself
    private Boolean queryCondition(R1 r1Fact, R2_4 r2_4Fact, R4_2 r4_2Fact) {
        return r1Fact.y.equals(r2_4Fact.y) && r2_4Fact.y.equals(r4_2Fact.y) && r1Fact.x.equals(r2_4Fact.x);
    }

    private Boolean booleanQueryCondition(R1 r1Fact, R2_4 r2_4Fact, R4_2 r4_2Fact) {
        return queryCondition(r1Fact, r2_4Fact, r4_2Fact) && r1Fact.z.equals(z);
    }

    public Boolean runBooleanQuery(List<Fact> facts) {
        R1 r1Fact = (R1) facts.get(0);
        R2_4 r2_4Fact = (R2_4) facts.get(1);
        R4_2 r4_2Fact = (R4_2) facts.get(2);
        return booleanQueryCondition(r1Fact, r2_4Fact, r4_2Fact);
    }

    private List<String> selectAnswer(R1 r1Fact, R2_4 r2_4Fact, R4_2 r4_2Fact) {
        if (queryCondition(r1Fact, r2_4Fact, r4_2Fact)) {
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
                                .map(fact3 -> selectAnswer((R1) fact1, (R2_4) fact2, (R4_2) fact3))))
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
                                .filter(fact3 -> booleanQueryCondition((R1) fact1, (R2_4) fact2, (R4_2) fact3))
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
        R2_4 r2_4Fact = (R2_4) facts.get(1);
        R4_2 r4_2Fact = (R4_2) facts.get(2);

        r1Fact.setY(r2_4Fact.y);
        r4_2Fact.setY(r2_4Fact.y);
        r1Fact.setX(r2_4Fact.x);
    }

    public String getQueryAnswers() {
        return "z = " + z;
    }

}

package main.data.queries;

import main.data.models.Database;
import main.data.facts.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * A class implementing the Query interface and representing a specific query.
 * It defines methods to create Boolean query instances with plugged answer v or SELECT query with v quantified;
 * methods to run the SELECT or Boolean query;
 * a method findSatisfyingFacts for finding the facts that satisfy the query for initializing set Delta in CQA algorithm;
 * a method for constructing a query answer out of given facts.
 */
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

        return firstList.parallelStream()
                .flatMap(fact1 -> secondList.stream()
                        .flatMap(fact2 -> thirdList.stream()
                                .flatMap(fact3 -> fourthList.stream()
                                        .map(fact4 -> selectAnswer(
                                                (R3_2) fact1,
                                                (R6) fact2,
                                                (R7) fact3,
                                                (R4_3) fact4)))))
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }


    public HashSet<HashSet<Fact>> findPluggedQuerySatisfyingFacts(Database database) {
        List<Fact> firstList = database.getDatabase().get(0);
        List<Fact> secondList = database.getDatabase().get(1);
        List<Fact> thirdList = database.getDatabase().get(2);
        List<Fact> fourthList = database.getDatabase().get(3);

        Set<HashSet<Fact>> concurrentSet = firstList.parallelStream()
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
                .collect(Collectors.toCollection(ConcurrentHashMap::newKeySet));

        return new HashSet<>(concurrentSet);
    }

    public List<HashSet<Fact>> findRelevantFacts(Database database, boolean isPluggedQuery) {

        List<Fact> firstList = database.getDatabase().get(0);
        List<Fact> secondList = database.getDatabase().get(1);
        List<Fact> thirdList = database.getDatabase().get(2);
        List<Fact> fourthList = database.getDatabase().get(3);

        Set<Fact> r1RelevantFactsSet = ConcurrentHashMap.newKeySet();
        Set<Fact> r2RelevantFactsSet = ConcurrentHashMap.newKeySet();
        Set<Fact> r3RelevantFactsSet = ConcurrentHashMap.newKeySet();
        Set<Fact> r4RelevantFactsSet = ConcurrentHashMap.newKeySet();

        firstList.parallelStream().forEach(fact1 ->
                secondList.parallelStream().forEach(fact2 ->
                        thirdList.parallelStream().forEach(fact3 ->
                                fourthList.parallelStream()
                                        .filter(fact4 -> isPluggedQuery
                                                ? booleanQueryCondition((R3_2) fact1, (R6) fact2, (R7) fact3, (R4_3) fact4)
                                                : queryCondition((R3_2) fact1, (R6) fact2, (R7) fact3, (R4_3) fact4))
                                        .forEach(fact4 -> {
                                            r1RelevantFactsSet.add(fact1);
                                            r2RelevantFactsSet.add(fact2);
                                            r3RelevantFactsSet.add(fact3);
                                            r4RelevantFactsSet.add(fact4);
                                        })
                        )
                )
        );
        return List.of(
                new HashSet<>(r1RelevantFactsSet),
                new HashSet<>(r2RelevantFactsSet),
                new HashSet<>(r3RelevantFactsSet),
                new HashSet<>(r4RelevantFactsSet)
        );
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

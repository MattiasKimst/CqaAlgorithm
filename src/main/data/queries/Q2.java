package main.data.queries;

import main.data.models.Database;
import main.data.facts.Fact;
import main.data.facts.R1;
import main.data.facts.R2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * A class implementing the Query interface and representing a specific query.
 * It defines methods to create Boolean query instances with plugged answer z, w or SELECT query with z, w quantified;
 * methods to run the SELECT or Boolean query;
 * a method findSatisfyingFacts for finding the facts that satisfy the query for initializing set Delta in CQA algorithm;
 * a method for constructing a query answer out of given facts.
 */
public class Q2 implements Query {

    private final String z;
    private final String w;

    public Q2() {
        this.z = null;
        this.w = null;
    }

    private Q2(String z, String w) {
        this.z = z;
        this.w = w;
    }

    public int getK() {
        return 2;
    }

    public Query createWithPluggedVariables(List<String> freeVariables) {
        return new Q2(freeVariables.getFirst(), freeVariables.getLast());
    }

    //query itself
    private Boolean queryCondition(R1 r1Fact, R2 r2Fact) {
        return r1Fact.y.equals(r2Fact.y);
    }

    private Boolean booleanQueryCondition(R1 r1Fact, R2 r2Fact) {
        return queryCondition(r1Fact, r2Fact) && r1Fact.z.equals(z) && r2Fact.w.equals(w);
    }

    public Boolean runBooleanQuery(List<Fact> facts) {
        R1 r1Fact = (R1) facts.get(0);
        R2 r2Fact = (R2) facts.get(1);
        return booleanQueryCondition(r1Fact, r2Fact);
    }

    private List<String> selectAnswer(R1 r1Fact, R2 r2Fact) {
        if (queryCondition(r1Fact, r2Fact)) {
            List<String> answer = new ArrayList<>();
            answer.add(r1Fact.z);
            answer.add(r2Fact.w);
            return answer;
        }
        return null;
    }

    public List<List<String>> runSelectQuery(Database database) {
        List<Fact> firstList = database.getDatabase().get(0);
        List<Fact> secondList = database.getDatabase().get(1);

        return firstList.parallelStream()
                .flatMap(fact1 -> secondList.stream()
                        .map(fact2 -> selectAnswer((R1) fact1, (R2) fact2)))
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    public HashSet<HashSet<Fact>> findPluggedQuerySatisfyingFacts(Database database) {
        List<Fact> firstList = database.getDatabase().get(0);
        List<Fact> secondList = database.getDatabase().get(1);

        Set<HashSet<Fact>> concurrentSet = firstList.parallelStream()
                .flatMap(fact1 -> secondList.stream()
                        .filter(fact2 -> booleanQueryCondition((R1) fact1, (R2) fact2))
                        .map(fact2 -> {
                            HashSet<Fact> set = new HashSet<>();
                            set.add(fact1);
                            set.add(fact2);
                            return set;
                        }))
                .collect(Collectors.toCollection(ConcurrentHashMap::newKeySet));

        return new HashSet<>(concurrentSet);
    }

    public void makeCombinationOfFactsSatisfyQuery(List<Fact> facts) {
        R1 r1Fact = (R1) facts.get(0);
        R2 r2Fact = (R2) facts.get(1);
        r1Fact.setY(r2Fact.y);
    }

    public List<HashSet<Fact>> findRelevantFacts(Database database, boolean isPluggedQuery) {

        List<Fact> firstList = database.getDatabase().get(0);
        List<Fact> secondList = database.getDatabase().get(1);

        Set<Fact> r1RelevantFactsSet = ConcurrentHashMap.newKeySet();
        Set<Fact> r2RelevantFactsSet = ConcurrentHashMap.newKeySet();

        firstList.parallelStream()
                .forEach(fact1 -> {
                    secondList.stream()
                            .filter(fact2 -> isPluggedQuery
                                    ? booleanQueryCondition((R1) fact1, (R2) fact2)
                                    : queryCondition((R1) fact1, (R2) fact2))
                            .forEach(fact2 -> {
                                r1RelevantFactsSet.add(fact1);
                                r2RelevantFactsSet.add(fact2);
                            });
                });

        return List.of(new HashSet<>(r1RelevantFactsSet), new HashSet<>(r2RelevantFactsSet));
    }

    public String getQueryAnswers() {
        return "z = " + z + ", w = " + w;
    }

}

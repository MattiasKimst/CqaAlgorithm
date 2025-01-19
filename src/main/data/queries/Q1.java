package main.data.queries;

import main.data.models.Database;
import main.data.relations.Fact;
import main.data.relations.R1;
import main.data.relations.R2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Q1 implements Query {

    public String z;

    public Q1() {
        this.z = null;
    }

    public int getK() {
        return 2;
    }

    public void plug(List<String> freeVariables) {
        this.z = freeVariables.get(0);
    }

    //query itself
    public Boolean booleanQueryCondition(R1 r1Fact, R2 r2Fact) {
        return r1Fact.y.equals(r2Fact.y) && r1Fact.z.equals(z);
    }

    public Boolean runBooleanQuery(List<Fact> facts) {
        R1 r1Fact = (R1) facts.get(0);
        R2 r2Fact = (R2) facts.get(1);
        return booleanQueryCondition(r1Fact, r2Fact);
    }

    public List<String> selectQueryCondition(R1 r1Fact, R2 r2Fact) {
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

        List<List<String>> answer = new ArrayList<>();

        for (Fact fact1 : firstList) {
            for (Fact fact2 : secondList) {
                List<String> result = selectQueryCondition((R1) fact1, (R2) fact2);
                if (result != null) {
                    answer.add(result);
                }
            }
        }
        return answer;
    }

    public HashSet<HashSet<Fact>> findSatisfyingFacts(Database database) {
        List<Fact> firstList = database.getDatabase().get(0);
        List<Fact> secondList = database.getDatabase().get(1);

        HashSet<HashSet<Fact>> answer = new HashSet<>();

        for (Fact fact1 : firstList) {
            for (Fact fact2 : secondList) {
                if (booleanQueryCondition((R1) fact1, (R2) fact2)) {
                    HashSet<Fact> set = new HashSet<>();
                    set.add(fact1);
                    set.add(fact2);
                    answer.add(set);
                }
            }
        }
        return answer;
    }
}

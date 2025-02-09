package main.tests;

import main.data.models.Database;
import main.data.facts.Fact;
import main.data.facts.R1;
import main.data.facts.R2;
import java.util.ArrayList;
import java.util.List;

/**
 * A testcase that checks on a simple database example if the algorithm correctly detects if a query is not certain
 */
public class TestUncertainQuery extends TestBase {

    public static boolean testUncertainQuery() {
        List<List<Fact>> listOfRelationFactsLists = new ArrayList<>();

        R1 r1_1 = new R1();
        r1_1.z= "ABCD";
        r1_1.y="PKEY";
        r1_1.x="EFGH";

        R1 r1_2 = new R1();
        r1_2.z="ABC";
        r1_2.y="PKEY";
        r1_2.x="EFGH";

        R2 r2_1 = new R2();
        r2_1.v="ABCD";
        r2_1.y="PKEY";
        r2_1.w="EFGH";

        R2 r2_2 = new R2();
        r2_2.v="ABCD";
        r2_2.y="PKEY";
        r2_2.w="EFGH";

        listOfRelationFactsLists.add(List.of(r1_1, r1_2));
        listOfRelationFactsLists.add(List.of(r2_1, r2_2));
        Database database = new Database(listOfRelationFactsLists);

        List<List<String>> selectQueryResults = q1.runSelectQuery(database);
        List<List<String>> consitentAnswers = findConsistentAnswersAlgorithm.findConsistentAnswers(selectQueryResults, database, q1);

        return consitentAnswers.isEmpty(); //the query is uncertain therefore there should be no consistent answers
    }

}

package main.tests;

import main.data.models.Database;
import main.data.facts.Fact;
import main.data.facts.R1;
import main.data.facts.R2;
import java.util.ArrayList;
import java.util.List;

/**
 * A testcase that checks on a simple database example if the algorithm detects correctly certain query
 */
public class TestCertainQuery extends TestBase {

    public static boolean testCertainQuery() {
        List<List<Fact>> listOfRelationFactsLists = new ArrayList<>();

        R1 r1_1 = new R1();
        r1_1.z= "ABCD";
        r1_1.y="SAME";
        r1_1.x="EFGH";
        R2 r2_1 = new R2();
        r2_1.v="UVWX";
        r2_1.y="SAME";
        r2_1.w="YZAB";

        R1 r1_2 = new R1();
        r1_2.z="IJKL";
        r1_2.y="MNOP";
        r1_2.x="QRST";
        R2 r2_2 = new R2();
        r2_2.v="CDEF";
        r2_2.y="GHIJ";
        r2_2.w="KLMN";

        listOfRelationFactsLists.add(List.of(r1_1, r1_2));
        listOfRelationFactsLists.add(List.of(r2_1, r2_2));
        Database database = new Database(listOfRelationFactsLists);

        List<List<String>> selectQueryResults = q1.runSelectQuery(database);
        List<List<String>> consitentAnswers = findConsistentAnswersAlgorithm.findConsistentAnswers(selectQueryResults, database, q1, true);

        return consitentAnswers.size() == 1 && consitentAnswers.get(0).get(0).equals("ABCD");
    }

}

package main.tests;

import main.data.models.Database;
import main.data.facts.Fact;
import main.data.facts.R1;
import main.data.facts.R2;

import java.util.ArrayList;
import java.util.List;

/**
 * A testcase that checks if algorithm correctly detects certain query on an example of duplicate facts
 */
public class TestCertainQueryDuplicateFacts extends TestBase {

    public static boolean testCertainQueryDuplicateFacts() {
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
        r1_2.z= "ABCD";
        r1_2.y="SAME";
        r1_2.x="EFGH";
        R2 r2_2 = new R2();
        r2_2.v="UVWX";
        r2_2.y="SAME";
        r2_2.w="YZAB";

        listOfRelationFactsLists.add(List.of(r1_1, r1_2));
        listOfRelationFactsLists.add(List.of(r2_1, r2_2));
        Database database = new Database(listOfRelationFactsLists);

        List<List<String>> selectQueryResults = q1.runSelectQuery(database);
        List<List<String>> consitentAnswers = findConsistentAnswersAlgorithm.findConsistentAnswers(selectQueryResults, database, q1, true);

        return consitentAnswers.size() == 4 && consitentAnswers.getFirst().getFirst().equals("ABCD");
    }

}

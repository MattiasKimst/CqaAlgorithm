package main.tests;

import main.FindConsistentAnswersAlgorithm;
import main.data.models.Database;
import main.data.queries.Q1;
import main.data.relations.Fact;
import main.data.relations.R1;
import main.data.relations.R2;

import java.util.ArrayList;
import java.util.List;

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
        List<List<String>> consitentAnswers = findConsistentAnswersAlgorithm.findConsistentAnswers(selectQueryResults, database, q1);

        return consitentAnswers.size() == 1 && consitentAnswers.get(0).get(0).equals("ABCD");
    }
}

package main;

import main.data.DataGenerator;
import main.data.queries.*;
import main.data.models.Schema;
import main.data.relations.*;
import main.data.models.Database;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static main.Logger.logQueryResults;
import static main.tests.TestCertainQuery.testCertainQuery;
import static main.tests.TestCertainQueryDuplicateFacts.testCertainQueryDuplicateFacts;
import static main.tests.TestUncertainQuery.testUncertainQuery;

public class Main {

    private static final int REPEATS = 1;
    private static final int CLEAN_FACTS_TO_BE_GENERATED_IN_EACH_RELATION = 950;
    private static final int FACTS_TO_BE_DUPLICATED_IN_EACH_RELATION = 50;
    private static final int SIZE_OF_KEY_EQUAL_GROUPS = 2;
    private static final int QUERY_ANSWERS_TO_BE_INSERTED = 150;

    private static final Schema schema1 = new Schema(Arrays.asList(R1.class, R2.class));
    private static final Schema schema2 = new Schema(Arrays.asList(R1.class, R3.class, R2_2.class));
    private static final Schema schema3 = new Schema(Arrays.asList(R1.class, R4.class));
    private static final Schema schema4 = new Schema(Arrays.asList(R1.class, R2_3.class, R5.class));
    private static final Schema schema5 = new Schema(Arrays.asList(R1.class, R2_4.class, R5.class));
    private static final Schema schema6 = new Schema(Arrays.asList(R1.class, R2_4.class));
    private static final Schema schema7 = new Schema(Arrays.asList(R1.class, R2_4.class, R4_2.class));
    private static final Schema schema8 = new Schema(Arrays.asList(R3_2.class, R6.class, R1_2.class, R4_3.class));
    private static final Schema schema9 = new Schema(Arrays.asList(R3_2.class, R6.class, R7.class, R4_3.class));
    private static final Schema schema10 = new Schema(Arrays.asList(R3_2.class, R6.class, R1_2.class, R7_2.class));

    private static final Map<Query, Schema> schemaQueryPairs = createSchemaQueryPairs();

    public static void main(String[] args) throws Exception {
        // Running tests
        runTests();

        // Processing schema-query pairs
        for (int i = 0; i < REPEATS; i++) {
            for (Map.Entry<Query, Schema> entry : schemaQueryPairs.entrySet()) {
                run(entry.getKey(), entry.getValue());
            }
        }
    }

    private static void run(Query query, Schema schema) throws Exception {
        DataGenerator dataGenerator = new DataGenerator();
        FindConsistentAnswersAlgorithm findConsistentAnswersAlgorithm = new FindConsistentAnswersAlgorithm();

        Database database = dataGenerator.generateInconsistentDatabase(
                schema, CLEAN_FACTS_TO_BE_GENERATED_IN_EACH_RELATION, FACTS_TO_BE_DUPLICATED_IN_EACH_RELATION,
                SIZE_OF_KEY_EQUAL_GROUPS, query, QUERY_ANSWERS_TO_BE_INSERTED);

        System.out.println("database generated");
        List<List<String>> selectQueryResults = query.runSelectQuery(database);
        System.out.println("Select query run ");

        long startTime = System.nanoTime();
        List<List<String>> consistentAnswers =
                findConsistentAnswersAlgorithm.findConsistentAnswers(selectQueryResults, database, query);
        long endTime = System.nanoTime();
        long durationInMilliseconds = (endTime - startTime) / 1_000_000;

        int checkedSUnionATotal = findConsistentAnswersAlgorithm.cqaAlgorithm.checkedSUnionATotal;
        int checkedPotentialKsetTotal = findConsistentAnswersAlgorithm.cqaAlgorithm.checkedPotentialKsetTotal;
        int checkedBlockTotal = findConsistentAnswersAlgorithm.cqaAlgorithm.checkedBlockTotal;
        int addedNewKSetToDeltaTotal = findConsistentAnswersAlgorithm.cqaAlgorithm.addedNewKSetToDeltaTotal;

        logQueryResults(CLEAN_FACTS_TO_BE_GENERATED_IN_EACH_RELATION, FACTS_TO_BE_DUPLICATED_IN_EACH_RELATION,
                QUERY_ANSWERS_TO_BE_INSERTED, query, selectQueryResults, consistentAnswers, durationInMilliseconds,
                checkedSUnionATotal, checkedPotentialKsetTotal, checkedBlockTotal, addedNewKSetToDeltaTotal,
                SIZE_OF_KEY_EQUAL_GROUPS);
    }

    private static Map<Query, Schema> createSchemaQueryPairs() {
        Map<Query, Schema> map = new LinkedHashMap<>();
        map.put(new Q1(), schema1);
        map.put(new Q2(), schema1);
        map.put(new Q3(), schema2);
        map.put(new Q4(), schema2);
        map.put(new Q5(), schema3);
        map.put(new Q6(), schema4);
        map.put(new Q7(), schema5);
        map.put(new Q8(), schema6);
        map.put(new Q9(), schema7);
        map.put(new Q10(), schema7);
        map.put(new Q11(), schema6);
        map.put(new Q12(), schema8);
        map.put(new Q13(), schema9);
        map.put(new Q14(), schema10);
        return map;
    }

    private static void runTests() {
        System.out.println("Uncertain query test: " + (testUncertainQuery() ? "PASSED" : "FAILED") + "\n");
        System.out.println("Certain query test: " + (testCertainQuery() ? "PASSED" : "FAILED")+ "\n");
        System.out.println("Certain query with duplicate facts test: "
                + (testCertainQueryDuplicateFacts() ? "PASSED" : "FAILED")+ "\n");
    }
}
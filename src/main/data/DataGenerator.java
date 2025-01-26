package main.data;

import main.data.models.Database;
import main.data.models.Schema;
import main.data.queries.Query;


public class DataGenerator {

    public int numberOfCleanFactsToBeGeneratedInEachRelation;
    public int numberOfFactsToBeDuplicatedInEachRelation;
    public int numberOfAnswersToBeGenerated;

    private final CleanDataGenerator generator = new CleanDataGenerator();
    private final InconsistenciesInserter inconsistenciesInserter = new InconsistenciesInserter();
    private final QueryAnswersInserter queryAnswersInserter = new QueryAnswersInserter();

    public Database generateInconsistentDatabase(Schema schema, double rateOfInconsistencies, int relationTotalSize,
                                                int sizeOfKeyEqualGroups, Query query, double rateOfQueryAnswers) throws Exception {
        numberOfCleanFactsToBeGeneratedInEachRelation = findNumberOfCleanFactsToBeGenerated(rateOfInconsistencies,
                relationTotalSize, sizeOfKeyEqualGroups);
        numberOfFactsToBeDuplicatedInEachRelation = findNumberOfFactsToBeDuplicated(relationTotalSize,
                rateOfInconsistencies, sizeOfKeyEqualGroups);
        numberOfAnswersToBeGenerated = findNumberOfAnswersToBeGenerated(relationTotalSize, rateOfQueryAnswers);

        Database database = generator.generateFacts(schema, numberOfCleanFactsToBeGeneratedInEachRelation);
        queryAnswersInserter.addQueryAnswers(database, numberOfAnswersToBeGenerated, query);
        inconsistenciesInserter.insertInconsistencies(database, numberOfFactsToBeDuplicatedInEachRelation, sizeOfKeyEqualGroups);
        return database;
    }

    private int findNumberOfCleanFactsToBeGenerated(double rateOfInconsistencies, int totalDatabaseSize,
                                                           int sizeOfKeyEqualGroups) {
        return (int) (totalDatabaseSize * (1 - rateOfInconsistencies/sizeOfKeyEqualGroups));
    }

    private int findNumberOfFactsToBeDuplicated(double totalDatabaseSize, double rateOfInconsistencies,
                                                       int sizeOfKeyEqualGroups) {
        return (int) Math.ceil(totalDatabaseSize * ( rateOfInconsistencies / sizeOfKeyEqualGroups));
    }

    private int findNumberOfAnswersToBeGenerated(int totalDatabaseSize, double rateOfQueryAnswers) {
        return (int) (totalDatabaseSize * rateOfQueryAnswers);
    }

}

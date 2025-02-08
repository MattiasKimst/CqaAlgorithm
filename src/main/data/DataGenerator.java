package main.data;

import main.data.models.Database;
import main.data.models.Schema;
import main.data.queries.Query;


public class DataGenerator {

    private final CleanDataGenerator generator = new CleanDataGenerator();
    private final InconsistenciesInserter inconsistenciesInserter = new InconsistenciesInserter();
    private final QueryAnswersInserter queryAnswersInserter = new QueryAnswersInserter();

    public Database generateInconsistentDatabase(Schema schema, int numberOfCleanFactsToBeGenerated,
                                                 int numberOfFactsToBeDuplicated, int sizeOfKeyEqualGroups, Query query,
                                                 int numberOfAnswersToBeInserted) throws Exception {

        Database database = generator.generateFacts(schema, numberOfCleanFactsToBeGenerated);
        queryAnswersInserter.insertQueryAnswers(database, numberOfAnswersToBeInserted, query);
        inconsistenciesInserter.insertInconsistencies(database, numberOfFactsToBeDuplicated, sizeOfKeyEqualGroups);
        return database;
    }
}

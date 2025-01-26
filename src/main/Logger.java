package main;

import main.data.queries.Query;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

public class Logger {
    public static void logQueryResults(int numberOfCleanFactsToBeGeneratedInEachRelation,
                                       int numberOfFactsToBeDuplicatedInEachRelation, int numberOfAnswersGenerated,
                                       Query query, List<List<String>> selectQueryResults,
                                       List<List<String>> certainAnswers, long durationInMilliseconds,
                                       int checkedSUnionATotal, int checkedPotentialKsetTotal, int checkedBlockTotal,
                                       int addedNewKSetToDeltaTotal, double rateOfInconsistencies, int sizeOfKeyEqualGroups) {

        int finalRelationSize = numberOfCleanFactsToBeGeneratedInEachRelation
                + numberOfFactsToBeDuplicatedInEachRelation * (sizeOfKeyEqualGroups - 1);
        int finalDatabaseSize = finalRelationSize * query.getK();

        System.out.println("Generated database with " + numberOfCleanFactsToBeGeneratedInEachRelation
                + " consitent facts in each relation");
        System.out.println("Duplicating " + numberOfFactsToBeDuplicatedInEachRelation
                + " facts in each relation so that they share the primary key");
        System.out.println("Size of key-equal-groups is " + sizeOfKeyEqualGroups);
        System.out.println("Modifying " + numberOfAnswersGenerated + " of those facts to be query answers");
        System.out.println("Each relation size in total is " + finalRelationSize);
        System.out.println("In total there are " + finalDatabaseSize + " facts in database.");
        System.out.println("Testing " + query.getClass().getSimpleName());
        System.out.println("SELECT query returned " + selectQueryResults.size() + " answers.");
        System.out.println("Certain were "  + certainAnswers.size() + " answers.");
        System.out.println("Time taken to find certain answers: " + durationInMilliseconds + " ms");
        System.out.println("In total checked " + checkedSUnionATotal
                + " times if there exists suitable S prim, "
                + checkedPotentialKsetTotal + " potential KSets, "
                + checkedBlockTotal + " blocks, added "
                + addedNewKSetToDeltaTotal + " times a new Kset to delta\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("query_results.csv", true))) {
            String csvEntry = String.join(";",
                    query.getClass().getSimpleName(),
                    String.valueOf(query.getK()),
                    String.valueOf(finalRelationSize),
                    String.valueOf(rateOfInconsistencies),
                    String.valueOf(sizeOfKeyEqualGroups),
                    String.valueOf(numberOfCleanFactsToBeGeneratedInEachRelation),
                    String.valueOf(numberOfFactsToBeDuplicatedInEachRelation),
                    String.valueOf(numberOfAnswersGenerated),
                    String.valueOf(finalDatabaseSize),
                    String.valueOf(selectQueryResults.size()),
                    String.valueOf(certainAnswers.size()),
                    String.valueOf(checkedSUnionATotal),
                    String.valueOf(checkedPotentialKsetTotal),
                    String.valueOf(checkedBlockTotal),
                    String.valueOf(addedNewKSetToDeltaTotal),
                    String.valueOf(durationInMilliseconds)
                    );
            writer.write(csvEntry);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

}

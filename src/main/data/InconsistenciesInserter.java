package main.data;

import main.data.annotation.PrimaryKey;
import main.data.models.Database;
import main.data.facts.Fact;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class for inserting inconsistent facts into database
 * insertInconsistencies method takes a database with consistent facts and duplicates specified number of randomly
 * selected facts by keeping primary key, but overwriting with new random values other attributes
 * defined number of times so that primary-key-equal groups of defined size are formed
 */
public class InconsistenciesInserter {

    public void insertInconsistencies(Database database, int numberOfFactsToBeDuplicated,
                                                           int sizeOfKeyEqualGroups) throws Exception {
        for (List<Fact> relation : database.getDatabase()) {
            List<Fact> factsToBeDuplicated = getRandomFacts(relation, numberOfFactsToBeDuplicated);
            List<Fact> duplicatedFacts = duplicateFacts(factsToBeDuplicated, sizeOfKeyEqualGroups-1);
            relation.addAll(duplicatedFacts);
        }
    }

    private List<Fact> getRandomFacts(List<Fact> factsInRelation, int numberOfFactsToBeSelected) {
        List<Fact> copy = new ArrayList<>(factsInRelation);
        Collections.shuffle(copy);
        return copy.subList(0, numberOfFactsToBeSelected);
    }

    public List<Fact> duplicateFacts(List<Fact> factsToBeDuplicated, int numberOfTimesToBeDuplicated) throws Exception {

        List<Fact> duplicatedList = new ArrayList<>();

        for (Fact originalFact : factsToBeDuplicated) {
            Class<?> relation = originalFact.getClass();

            for (int i = 0; i < numberOfTimesToBeDuplicated; i++) {
                Fact duplicateFact = (Fact) relation.getDeclaredConstructor().newInstance();

                for (Field field : relation.getDeclaredFields()) {
                    //if the field is primary key we keep original value, otherwise generate new value
                    Object valueToBeAssigned = field.get(originalFact);
                    if (!field.isAnnotationPresent(PrimaryKey.class)) {
                        valueToBeAssigned = RandomAttributeValueGenerator.generateRandomAlphanumericString();
                    }
                    field.set(duplicateFact, valueToBeAssigned);
                }
                duplicatedList.add(duplicateFact);
            }
        }
        return duplicatedList;
    }

}

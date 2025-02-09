package main.data;

import main.data.models.Database;
import main.data.models.Schema;
import main.data.facts.Fact;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for generating defined number of clean facts into database of defined schema
 * Clean facts are facts not participating in primary key violations
 */
public class CleanDataGenerator {

    public Database generateFacts(Schema schema, int numberOfFactsToBeGenerated) throws Exception {
        List<List<Fact>> allRelationFacts = new ArrayList<>();

        for (Class<?> relationClass : schema.getRelations()) {
            List<Fact> factsForRelation = generateFactsForRelation(relationClass, numberOfFactsToBeGenerated);
            allRelationFacts.add(factsForRelation);
        }

        return new Database(allRelationFacts);
    }

    private List<Fact> generateFactsForRelation(Class<?> relationClass, int numberOfFactsToBeGenerated) throws Exception {
        List<Fact> facts = new ArrayList<>();

        Constructor<?> constructor = relationClass.getDeclaredConstructor();
        constructor.setAccessible(true);

        for (int i = 0; i < numberOfFactsToBeGenerated; i++) {
            Fact fact = createFactWithRandomAttributes(constructor, relationClass);
            facts.add(fact);
        }

        return facts;
    }

    private Fact createFactWithRandomAttributes(Constructor<?> constructor, Class<?> relationClass) throws Exception {
        Fact fact = (Fact) constructor.newInstance();

        for (Field attribute : relationClass.getDeclaredFields()) {
            attribute.setAccessible(true);
            String randomValue = RandomAttributeValueGenerator.generateRandomAlphanumericString();
            attribute.set(fact, randomValue);
        }

        return fact;
    }

}

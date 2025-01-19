package main.data;

import main.data.models.Database;
import main.data.models.Schema;
import main.data.relations.Fact;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DataGenerator {
    public Database generateFacts(Schema schema, int numberOfFactsToBeGenerated) throws Exception {
        List<List<Fact>> listOfRelationFactsLists = new ArrayList<>();

        for (Class<?> relation : schema.getRelations()) {
            List<Fact> factsOfRelation = new ArrayList<>();

            Fact fact = null;
            Constructor<?> constructor = relation.getDeclaredConstructor();
            for (int i = 0; i < numberOfFactsToBeGenerated; i++) {
                fact = (Fact) constructor.newInstance();

                for (Field attribute : relation.getDeclaredFields()) {
                    String randomValue = RandomAttributeValueGenerator.generateRandomStringOfLengthBetween4And10();
                    attribute.set(fact, randomValue);
                }
                factsOfRelation.add(fact);
            }
            listOfRelationFactsLists.add(factsOfRelation);
        }
        return new Database(listOfRelationFactsLists);
    }

}

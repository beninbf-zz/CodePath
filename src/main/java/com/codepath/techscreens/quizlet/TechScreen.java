package main.java.com.codepath.techscreens.quizlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TechScreen {
    /*
        Below is a tech screen I received from Quizlet. It was interesting as there was a DB portion
        and a coding portion. I only got through 2.5 questions of the SQL :(. I was rusty because
        I don't typically do a lot of SQL any more.
        Your previous MySQL content is preserved below:

        # Hello from Quizlet!!!

        # Given the following schema:
        #
        # desc teachers;
        # +-----------+--------------+------+-----+---------+-------+
        # | Field     | Type         | Null | Key | Default | Extra |
        # +-----------+--------------+------+-----+---------+-------+
        # | id        | int(11)      | YES  |     | NULL    |       |
        # | name      | varchar(128) | YES  |     | NULL    |       |
        # | school_id | int(11)      | YES  |     | NULL    |       |
        # | age       | int(11)      | YES  |     | NULL    |       |
        # +-----------+--------------+------+-----+---------+-------+
        #
        # desc schools;
        # +-------+--------------+------+-----+---------+-------+
        # | Field | Type         | Null | Key | Default | Extra |
        # +-------+--------------+------+-----+---------+-------+
        # | id    | int(11)      | NO   | PRI | NULL    |       |
        # | name  | varchar(128) | YES  |     | NULL    |       |
        # +-------+--------------+------+-----+---------+-------+

        # Write some SQL to answer the following:

        #
        # 1. Output a list of all teacher names and the name of the school they teach.
        # Select t.name, s.name from teachers t
        # JOIN schools s
        #   ON t.school_id = s.id;
        #
        # 2. Output the school name and average age of teachers at each school.
         # Select avg(t.age), s.name
         # from teachers t
         # JOIN schools s
         #   ON t.school_id = s.id
         # group by (s.name);
        #
        # 3. Output the name and age of the oldest teacher(s) at each school.
         Select max(t.age), school_id
         from teachers t
         GROUP BY (school_id);


        #
        # 4. Output the difference in age between each teacher and the next oldest teacher at each school.
    */

    /**
     * The Coding question from Quizlet. This was fairly easy, just need to make use
     * of Maps.
     * Write a function that takes in a list of key-value mappings
     * and returns true iff they all have the "same schema":
     *
     *   - Each mapping has the same set of keys
     *   - For a given key, each mapping has the same data type for the corresponding value
     */
    public boolean sameSchema(List<Map<String, Object>> inputs) {

        Map<String, Object> map = inputs.get(0);
        Set<String> setOfKeys = map.keySet();
        Map<String, String> typeMap = new HashMap<>();

        for (String key: setOfKeys) {
            Object value = map.get(key);
            typeMap.put(key, value.getClass().getTypeName());
        }

        for (int i = 1; i < inputs.size(); i++) {

            Map<String, Object> candidateMap = inputs.get(i);
            Set<String> keySet = candidateMap.keySet();

            if (keySet.size() != setOfKeys.size()) {
                return false;
            }

            for (String key: keySet) {
                if (!setOfKeys.contains(key)) {
                    return false;
                }

                Object candidateValue = candidateMap.get(key);
                String type = typeMap.get(key);

                if (!candidateValue.getClass().getTypeName().equals(type)) {
                    return false;
                }
            }
        }
        return true;
    }
}

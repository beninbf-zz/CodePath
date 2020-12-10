package main.java.com.codepath.techscreens.roblox;

import java.util.*;

public class TechScreen {
    /**
     * Karat via Roblox:
     *
     * This problem presented me with a lot of issues even though its not really difficult.
     * I had troubles breaking down the problem and seeing how simple the solution was.
     * From the outset of the problem it appeared as if it required a recursive
     * dfs solution, but it was much more simple than that. I'm not sure why it was
     * so hard to see the simplicity for what it was.
     *
     *
     * You are in charge of a display advertising program. Your ads are displayed on websites all
     * over the internet. You have some CSV input data that counts how many times that users
     * have clicked on an ad on each individual domain. Every line consists of a click count
     * and a domain name, like this:
     *
     * counts = [ "900,google.com",
     *      "60,mail.yahoo.com",
     *      "10,mobile.sports.yahoo.com",
     *      "40,sports.yahoo.com",
     *      "300,yahoo.com",
     *      "10,stackoverflow.com",
     *      "2,en.wikipedia.org",
     *      "1,es.wikipedia.org",
     *      "1,mobile.sports" ]
     *
     * Write a function that takes this input as a parameter and returns a data structure containing
     * the number of clicks that were recorded on each domain AND each sub domain under it.
     * For example, a click on "mail.yahoo.com" counts toward the totals for "mail.yahoo.com",
     * "yahoo.com", and "com". (Sub domains are added to the left of their parent domain.
     * So "mail" and "mail.yahoo" are not valid domains. Note that "mobile.sports" appears
     * as a separate domain as the last item of the input.)
     *
     * Sample output (in any order/format):
     *
     * calculateClicksByDomain(counts)
     * 1320    com
     *  900    google.com
     *  410    yahoo.com
     *   60    mail.yahoo.com
     *   10    mobile.sports.yahoo.com
     *   50    sports.yahoo.com
     *   10    stackoverflow.com
     *    3    org
     *    3    wikipedia.org
     *    2    en.wikipedia.org
     *    1    es.wikipedia.org
     *    1    mobile.sports
     *    1    sports
     *
     *
     * @param counts String array
     * @return Map of domains and their counts
     */
    public Map<String,Integer> aggregateCounts(String[] counts) {
        Map<String,Integer> results = new HashMap<>();
        for (String s: counts) {
            String[] items = s.split(",");

            int count = Integer.valueOf(items[0]);
            List<String> domains = getDomains(items[1]);
            for (String domain: domains) {
                if (!results.containsKey(domain)) {
                    results.put(domain, count);
                } else {
                    Integer currentCount = results.get(domain);
                    results.put(domain, Integer.sum(currentCount, count));
                }
            }
        }

        return results;
    }

    public List<String> getDomains(String domain) {
        int length = domain.length();
        List<String> subDomains = new ArrayList<>();
        for (int i = length - 1; i >= 0; i--) {
            if(domain.charAt(i) ==  '.') {
                subDomains.add(domain.substring(i + 1, length));
            }
        }
        subDomains.add(domain);
        return subDomains;
    }

    /**
     *
     * My second tech screen with Karat via Roblox. Again...I fucked it up.
     *
     * I definitely need practice on problems that require a lot of hash maps.
     * I didn't exactly understand how taking the approach I had chosen was going to work
     * but I thought it was mostly there and it really bit me in the ass. The approach
     * I initially took was mapping classes to students, this seemed promising,
     * but I couldn't make it work.
     *
     * BEFORE CODING ANYTHING...make sure I have the right approach first.
     *
     * After struggling after the tech screen I switched up it up. In this case all I had
     * to do was build a hash map with students mapped to their courses. Then to find the
     * over lap, I just had to iterate over the keys (the students)
     * for each student, and compare student to student with a for loop. My problem was, I didn't
     * know how to generate these pairs, but it should of been obvious. Its just a double for loop.
     * Pairing each student with the other.
     *
     * We would simply skip keys that matched, and make sure we didn't generate the same pair
     * twice by storing students we had already paired in a HashMap with students mapping
     * to a set of students that it had been paired with.
     *
     * Once I realized that was the correct approach to take, writing the code wasn't complicated.
     *
     * FUCKING GODDDAMNIT!!
     *
     * You are a developer for a university. Your current project is to develop a system for students
     * to find courses they share with friends. The university has a system for querying courses students
     * are enrolled in, returned as a list of (ID, course) pairs. Write a function that takes in a list
     * of (student ID number, course name) pairs and returns, for every pair of students, a list of all courses
     * they share.
     * Sample Input:
     * student_course_pairs = [["58", "Software Design"],
     * ["58", "Linear Algebra"],
     *   ["94", "Art History"],
     *   ["94", "Operating Systems"],
     *   ["17", "Software Design"],
     *   ["58", "Mechanics"],
     *   ["58", "Economics"],
     *   ["17", "Linear Algebra"],
     *   ["17", "Political Science"],
     *   ["94", "Economics"]
     *   ]
     *   Sample Output (pseudocode, in any order)
     *
     *   find_pairs(student_course_pairs) => {
     * [58, 17]: ["Software Design", "Linear Algebra"]
     * [58, 94]: ["Economics"]
     * [17, 94]: []
     * }
     */
    public List<StudentCoursePair> find_pairs(String[][] input) {
        List<StudentCoursePair> results = new ArrayList<>();
        Map<String, Set<String>> studentsToClasses = new HashMap<>();
        for (int i = 0; i < input.length; i++) {
            if (!studentsToClasses.containsKey(input[i][0])) {
                Set<String> classes = new HashSet<>();
                classes.add(input[i][1]);
                studentsToClasses.put(input[i][0], classes);
            } else {
                Set<String> classes = studentsToClasses.get(input[i][0]);
                classes.add(input[i][1]);
            }
        }

        Map<String, Set<String>> alreadyCoupled = new HashMap<>();
        for (String key : studentsToClasses.keySet()) {
            Set<String> classes = studentsToClasses.get(key);
            for (String otherKey: studentsToClasses.keySet()) {
                if (key.equals(otherKey)) {
                    continue;
                }


                if (alreadyCoupled.containsKey(otherKey)) {
                    Set<String> setOfCoupledStudents = alreadyCoupled.get(otherKey);
                    if (setOfCoupledStudents.contains(key)) {
                        continue;
                    }
                }
                Set<String> otherClasses = studentsToClasses.get(otherKey);
                List<String> classesForPair = new ArrayList<>();
                for (String schoolClass: classes) {
                    if (otherClasses.contains(schoolClass)) {
                        classesForPair.add(schoolClass);
                    }
                }

                if (!alreadyCoupled.containsKey(key)) {
                    Set<String> coupledStudents = new HashSet<>();
                    coupledStudents.add(otherKey);
                    alreadyCoupled.put(key, coupledStudents);
                } else {
                    Set<String> coupledStudents = alreadyCoupled.get(key);
                    coupledStudents.add(otherKey);
                }

                StudentCoursePair pair = new StudentCoursePair();
                pair.students.add(key);
                pair.students.add(otherKey);
                pair.classes.addAll(classesForPair);
                results.add(pair);
            }
        }
        return results;
    }
}

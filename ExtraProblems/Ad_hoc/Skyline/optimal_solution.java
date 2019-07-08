import java.util.*;

public class optimal_solution {

    public static class BuildingIndex {
        int index;
        char startEnd;
        Integer height;

        public BuildingIndex(int index, char startEnd, int height) {
            this.index = index;
            this.startEnd = startEnd;
            this.height = height;
        }

        @Override
        public String toString() {
            return index + " " + startEnd + " ";
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numOfTestCases = in.nextInt();
        for (int n = 0; n < numOfTestCases; n++) {
            int numOfBuildings = in.nextInt();
            List<List<Integer>> buildings = new ArrayList<>();
            for (int i = 0; i < numOfBuildings; i++) {
                List<Integer> building = new ArrayList<>();
                building.add(in.nextInt());
                building.add(in.nextInt());
                building.add(in.nextInt());
                buildings.add(building);
            }
            List<List<Integer>> skyline = findSkyline(buildings);

            for(List<Integer> point : skyline){
                System.out.println(point.get(0) + " " + point.get(1));
            }
            System.out.println("");
        }
    }

    private static List<List<Integer>> findSkyline(List<List<Integer>> buildings) {

        List<List<Integer>> ans = new ArrayList<>();
        int numOfBuildings = buildings.size();
        TreeSet<BuildingIndex> priorityQ = new TreeSet<>(new Comparator<BuildingIndex>() {
            @Override
            public int compare(BuildingIndex o1, BuildingIndex o2) {
                if (o1.index != o2.index) {
                    return o1.index - o2.index;
                }
                if (o2.startEnd != o1.startEnd) {
                    // start index is higher priority than end index
                    if (o2.startEnd == 'e') {
                        return -1;
                    } else {
                        return 1;
                    }
                }
                // If two buildings start at the same point, then building with higher height should be higher priority.
                if (o1.startEnd == 's') {
                    return o2.height - o1.height;
                }
                // If two buildings end at the same point, then building with smaller height should be looked at first.
                else {
                    return o1.height - o2.height;
                }
            }
        });

        // Creating the priority queue of starting and end indices of all buildings
        for (List<Integer> building : buildings) {
            BuildingIndex buildingIndex1 = new BuildingIndex(building.get(0), 's', building.get(2));
            priorityQ.add(buildingIndex1);
            BuildingIndex buildingIndex2 = new BuildingIndex(building.get(1), 'e', building.get(2));
            priorityQ.add(buildingIndex2);
        }

        // A map (heightCountQ) which keeps track of all buildings so far in decreasing order of their heights.
        TreeMap<Integer, Integer> heightCountQ = new TreeMap<>();
        heightCountQ.put(0, 1);
        Integer maxHeight = 0;

        while (!priorityQ.isEmpty()) {
            BuildingIndex buildingIndex = priorityQ.pollFirst();
            // starting index signifies start of a buiding, so check if highest.
            if (buildingIndex.startEnd == 's') {
                heightCountQ.putIfAbsent(buildingIndex.height, 0);
                heightCountQ.put(buildingIndex.height, heightCountQ.get(buildingIndex.height) + 1);
                // if tallest buiding detected then update the skyline.
                if (buildingIndex.height > maxHeight) {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(buildingIndex.index);
                    list.add(buildingIndex.height);
                    maxHeight = buildingIndex.height;
                    ans.add(list);
                }
            }
            // end index signifies end of a buiding, so remove it from the heightCountQ, and update skyline if highest.
            else {
                if (heightCountQ.get(buildingIndex.height) == 1) {
                    heightCountQ.remove(buildingIndex.height);
                } else {
                    heightCountQ.put(buildingIndex.height, heightCountQ.get(buildingIndex.height) - 1);
                }
                // update skyline, if tallest buiding ends.
                if (maxHeight.equals(buildingIndex.height)) {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(buildingIndex.index);
                    Map.Entry<Integer, Integer> val = heightCountQ.pollLastEntry();
                    if (!maxHeight.equals(val.getKey())) {
                        list.add(val.getKey());
                        ans.add(list);
                    }

                    heightCountQ.put(val.getKey(), val.getValue());
                    maxHeight = val.getKey();
                }
            }
        }
        return ans;
    }
}

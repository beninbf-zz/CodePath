package test.java.com.codepath.objects;

import main.java.com.codepath.objects.MinHeap;
import main.java.com.codepath.objects.Vertex;
import main.java.com.codepath.techscreens.objects.MyMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MinHeapTest {

    @Test
    public void testAdd() {
        MinHeap<Vertex<String>> minHeap = new MinHeap<>();

        Vertex<String> aVertex = new Vertex<>("A");
        aVertex.weight = 15;

        Vertex<String> dVertex = new Vertex<>("D");
        dVertex.weight = 13;

        Vertex<String> cVertex = new Vertex<>("C");
        cVertex.weight = 11;

        Vertex<String> bVertex = new Vertex<>("B");
        bVertex.weight = 7;

        Vertex<String> eVertex = new Vertex<>("E");
        eVertex.weight = 2;

        minHeap.add(aVertex, aVertex.weight);
        minHeap.add(dVertex, dVertex.weight);
        minHeap.add(cVertex, cVertex.weight);
        minHeap.add(bVertex, bVertex.weight);
        minHeap.add(eVertex, eVertex.weight);
    }

    @Test
    public void textExtractMin() {
        MinHeap<Vertex<String>> minHeap = new MinHeap<>();

        Vertex<String> aVertex = new Vertex<>("A", 15);
        Vertex<String> dVertex = new Vertex<>("D", 13);
        Vertex<String> cVertex = new Vertex<>("C", 11);
        Vertex<String> bVertex = new Vertex<>("B", 7);
        Vertex<String> eVertex = new Vertex<>("E", 4);
        Vertex<String> fVertex = new Vertex<>("F", 23);
        Vertex<String> gVertex = new Vertex<>("G", 34);
        Vertex<String> hVertex = new Vertex<>("H", 12);
        Vertex<String> iVertex = new Vertex<>("I", 5);
        Vertex<String> jVertex = new Vertex<>("J", 2);
        Vertex<String> kVertex = new Vertex<>("k", 34);

        minHeap.add(aVertex, aVertex.weight);
        minHeap.add(dVertex, dVertex.weight);
        minHeap.add(cVertex, cVertex.weight);
        minHeap.add(bVertex, bVertex.weight);
        minHeap.add(eVertex, eVertex.weight);
        minHeap.add(fVertex, fVertex.weight);
        minHeap.add(gVertex, gVertex.weight);
        minHeap.add(hVertex, hVertex.weight);
        minHeap.add(iVertex, iVertex.weight);
        minHeap.add(jVertex, jVertex.weight);
        minHeap.add(kVertex, kVertex.weight);

        Vertex<String> current = minHeap.extractMin();
        while(!minHeap.isEmpty()) {
            Vertex<String> next = minHeap.extractMin();
            assertTrue("values should come out in ascending order", next.weight >= current.weight);
            current = next;
            System.out.println(next);
        }
    }

    @Test
    public void textDecrease() {
        MinHeap<Vertex<String>> minHeap = new MinHeap<>();

        Vertex<String> aVertex = new Vertex<>("A", 15);
        Vertex<String> dVertex = new Vertex<>("D", 13);
        Vertex<String> cVertex = new Vertex<>("C", 11);
        Vertex<String> bVertex = new Vertex<>("B", 7);
        Vertex<String> eVertex = new Vertex<>("E", 4);
        Vertex<String> fVertex = new Vertex<>("F", 23);
        Vertex<String> gVertex = new Vertex<>("G", 34);
        Vertex<String> hVertex = new Vertex<>("H", 12);
        Vertex<String> iVertex = new Vertex<>("I", 5);
        Vertex<String> jVertex = new Vertex<>("J", 2);
        Vertex<String> kVertex = new Vertex<>("K", 34);

        minHeap.add(aVertex, aVertex.weight);
        minHeap.add(dVertex, dVertex.weight);
        minHeap.add(cVertex, cVertex.weight);
        minHeap.add(bVertex, bVertex.weight);
        minHeap.add(eVertex, eVertex.weight);
        minHeap.add(fVertex, fVertex.weight);
        minHeap.add(gVertex, gVertex.weight);
        minHeap.add(hVertex, hVertex.weight);
        minHeap.add(iVertex, iVertex.weight);
        minHeap.add(jVertex, jVertex.weight);
        minHeap.add(kVertex, kVertex.weight);

        assertEquals("min node should be J", "J", minHeap.seeMin().label);

        kVertex.weight = 1;
        minHeap.decrease(kVertex, kVertex.weight);

        assertEquals("min node should be K", "K", minHeap.seeMin().label);
        Vertex<String> current = minHeap.extractMin();
        System.out.println(current);
        while(!minHeap.isEmpty()) {
            Vertex<String> next = minHeap.extractMin();
            assertTrue("values should come out in ascending order", next.weight >= current.weight);
            current = next;
            System.out.println(next);
        }
    }

    @Test
    public void testMyMap() {
        MyMap map = new MyMap();
        map.set("foo", "bar");
        map.set("A", "B");
        map.set("C", "D");
        map.set("E", "F");

        System.out.println(map);
        System.out.println(map.keyListStr());
        System.out.println(map.locationMapStr());

        map.delete("A");
        System.out.println("AFTER DELETE");
        System.out.println(map);
        System.out.println(map.keyListStr());
        System.out.println(map.locationMapStr());

        map.set("E", "test");
        System.out.println("AFTER E SET");
        System.out.println(map);
        System.out.println(map.keyListStr());
        System.out.println(map.locationMapStr());

        System.out.println("Random");
        System.out.println(map.getRandom());
        System.out.println("Random");
        System.out.println(map.getRandom());
    }
}
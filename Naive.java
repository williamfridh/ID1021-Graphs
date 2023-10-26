public class Naive {
    public static void main(String[] args) {
        Map map = new Map("trains.csv");
        //map.print();
        //String from = args[0];
        //String to = args[1];
        //Integer max = Integer.valueOf(args[2]);
        String[][] data = {
            {"Malmö", "Göteborg"},
        };
/* 
        System.out.println("\nMax SIZE = 100");
        for (String[] a : data)
            benchmark(map.lookup(a[0]), map.lookup(a[1]), 100);

        System.out.println("\nMax SIZE = 200");
        for (String[] a : data)
            benchmark(map.lookup(a[0]), map.lookup(a[1]), 200);
*/
        System.out.println("\nMax SIZE = 300");
        for (String[] a : data)
            benchmark(map.lookup(a[0]), map.lookup(a[1]), 300);
/* 
        System.out.println("\nMax SIZE = 400");
        for (String[] a : data)
            benchmark(map.lookup(a[0]), map.lookup(a[1]), 400);

        System.out.println("\nMax SIZE = 500");
        for (String[] a : data)
            benchmark(map.lookup(a[0]), map.lookup(a[1]), 500);

        System.out.println("\nMax SIZE = 600");
        for (String[] a : data)
            benchmark(map.lookup(a[0]), map.lookup(a[1]), 600);*/
    }



    private static void benchmark(Map.City from, Map.City to, Integer max) {
        System.out.println("Benchmarking " + from.name + " to " + to.name);
        long t0 = System.nanoTime();
        Integer dist = shortest(from, to, max);
        long time = (System.nanoTime() - t0)/1_000_000;

        System.out.println("shortest: " + dist + " min (" + time + " ms)\n");
    }



    private static Integer shortest(Map.City from, Map.City to, Integer max) {
        //System.out.println("FROM: " + from.name + " TO: " + to.name + " MAX: " + max);
        if (max < 0)
            return null;
            
        if (from == to)
            return 0;

        Integer shrt = null;
        for (int i = 0; i < from.connections.length; i++) {

            Map.City.Connection conn = from.connections[i];
            Integer t = shortest(conn.destination, to, max - conn.distance);

            if (t == null) // Bad route.
                continue;
            
            Integer new_shrt = conn.distance;
            if (t != 0)
                new_shrt += t;

            if (shrt == null || shrt.compareTo(new_shrt) > 0)
                shrt = new_shrt;

        }
        return shrt;
    }
}


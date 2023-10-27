class Paths {

    public static void main(String[] args) {
        Paths p = new Paths();
        Map map = new Map("trains.csv");

        String[][] data = {
            {"Malmö", "Göteborg"},
            {"Göteborg", "Stockholm"},
            {"Malmö", "Stockholm"},
            {"Stockholm", "Sundsvall"},
            {"Stockholm", "Umeå"},
            {"Göteborg", "Sundsvall"},
            {"Sundsvall", "Umeå"},
            {"Umeå", "Göteborg"},
            {"Göteborg", "Umeå"},
        };

        for (String[] a : data)
            p.benchmark(map.lookup(a[0]), map.lookup(a[1]));
    }



    private void benchmark(Map.City from, Map.City to) {
        System.out.println("Benchmarking " + from.name + " to " + to.name);
        long t0 = System.nanoTime();
        Integer dist = shortest(from, to);
        long time = (System.nanoTime() - t0)/1_000_000;

        System.out.println("shortest: " + dist + " min (" + time + " ms)");
        System.out.println();
    }



    Map.City[] path;
    int sp;



    public Paths() {
        path = new Map.City[54];
        sp = 0;
    }



    void print() {
        for (Map.City c : path)
            if (c != null)
                System.out.print(c.name + " --> ");
        System.out.print("\n");
    }



    private Integer shortest(Map.City from, Map.City to) {

        if (from == to)
            return 0;

        path[sp++] = from;
        
        Integer shrt = null;
        outerloop:
        for (Map.City.Connection conn : from.connections) {

            for (Map.City c : path)
                if (c == conn.destination)
                    continue outerloop;

            Integer dist = shortest(conn.destination, to);
            
            if (dist != null && (shrt == null || shrt > dist + conn.distance))
                shrt = dist + conn.distance;
        }

        path[sp--] = null;
        return shrt;
    }
}


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

        System.out.println("shortest: " + dist + " min (" + time + " ms)\n");
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
        //System.out.println("FROM: " + from.name + " TO: " + to.name + " MAX: " + max);

        for (Map.City c : path)
            if (c == from)
                return null;

        path[sp++] = from;
        //System.out.println("ADDED: " + path[sp - 1].name);
        print();
            
        if (from == to) // Searched for found.
            return 0;

        Integer shrt = null;
        for (int i = 0; i < from.connections.length; i++) {

            Map.City.Connection conn = from.connections[i];

            /*for (Map.City c : path)
                if (conn.destination.name.equals(c.name))
                    continue;*/

            Integer t = shortest(conn.destination, to);

            if (t == null) { // Bad route.
                System.out.println("SP: " + sp);
                continue;
            }
            
            Integer new_shrt = conn.distance;
            if (t != 0)
                new_shrt += t;

            if (shrt == null || shrt.compareTo(new_shrt) > 0)
                shrt = new_shrt;

        }
        sp--;
        return shrt;
    }
}


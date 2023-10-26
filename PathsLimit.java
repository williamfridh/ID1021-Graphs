class PathsLimit {

    public static void main(String[] args) {
        PathsLimit p = new PathsLimit();
        Map map = new Map("trains.csv");

        /*String[][] data = {
            {"Malmö", "Göteborg"},
            {"Göteborg", "Stockholm"},
            {"Malmö", "Stockholm"},
            {"Stockholm", "Sundsvall"},
            {"Stockholm", "Umeå"},
            {"Göteborg", "Sundsvall"},
            {"Sundsvall", "Umeå"},
            {"Umeå", "Göteborg"},
            {"Göteborg", "Umeå"},
        };*/

        String[][] data = {
            {"Malmö", "Mora"},
            {"Malmö", "Sveg"},
            {"Malmö", "Falköping"},
            {"Malmö", "Boden"},
            {"Malmö", "Gällivare"},
            {"Malmö", "Sundsvall"},
            {"Malmö", "Uppsala"},
            {"Malmö", "Sala"},
            {"Malmö", "Storvik"},
            {"Malmö", "Östersund"},
            {"Malmö", "Emmaboda"},
            {"Malmö", "Uddevalla"},
            {"Malmö", "Helsingborg"},
            {"Malmö", "Hallsberg"},
            {"Malmö", "Skövde"},
            {"Malmö", "Norrköping"},
            {"Malmö", "Alvesta"},
            {"Malmö", "Lund"},
            {"Malmö", "Hässleholm"},
            {"Malmö", "Fagersta"},
            {"Malmö", "Katrineholm"},
            {"Malmö", "Södertälje"},
            {"Malmö", "Värnamo"},
            {"Malmö", "Strömstad"},
            {"Malmö", "Herrljunga"},
            {"Malmö", "Umeå"},
            {"Malmö", "Arboga"},
            {"Malmö", "Jönköping"},
            {"Malmö", "Ånge"},
            {"Malmö", "Eskilstuna"},
            {"Malmö", "Gävle"},
            {"Malmö", "Luleå"},
            {"Malmö", "Örebro"},
            {"Malmö", "Västerås"},
            {"Malmö", "Åstorp"},
            {"Malmö", "Göteborg"},
            {"Malmö", "Mjölby"},
            {"Malmö", "Nässjö"},
            {"Malmö", "Borlänge"},
            {"Malmö", "Kalmar"},
            {"Malmö", "Stockholm"},
            {"Malmö", "Kristianstad"},
            {"Malmö", "Varberg"},
            {"Malmö", "Kiruna"},
            {"Malmö", "Linköping"},
            {"Malmö", "Trollhättan"},
            {"Malmö", "Avesta"},
            {"Malmö", "Frövi"},
            {"Malmö", "Karlskrona"},
            {"Malmö", "Ludvika"},
            {"Malmö", "Halmstad"}
        };

        for (String[] a : data)
            p.benchmark(map.lookup(a[0]), map.lookup(a[1]));
    }



    private void benchmark(Map.City from, Map.City to) {

        
        long t0 = System.nanoTime();
        Integer dist = shortest(from, to);
        long time = (System.nanoTime() - t0)/1_000_000;

        System.out.println(to.name + ", " + time + ", " + dist);
    }



    Map.City[] path;
    int sp;



    public PathsLimit() {
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
        Integer max = Integer.MAX_VALUE;
        outerloop:
        for (Map.City.Connection conn : from.connections) {

            for (Map.City c : path)
                if (c == conn.destination)
                    continue outerloop;

            if (conn == null || conn.distance > max)
                continue;

            Integer dist = shortest(conn.destination, to);
            
            if (dist != null && (shrt == null || shrt > dist + conn.distance)) {
                shrt = dist + conn.distance;
                if (shrt > 0 && max > shrt)
                    max = shrt;
            }
        }

        path[sp--] = null;
        return shrt;
    }
}


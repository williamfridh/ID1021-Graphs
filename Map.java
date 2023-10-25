import java.io.BufferedReader;
import java.io.FileReader;



public class Map {

    public static void main(String[] args) {
        Map m = new Map("trains.csv");
        //m.print();
    }



    private City[] cities;
    private final int mod = 541;

    public Map(String file) {
        cities = new City[mod];
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                addConnection(d[0], d[1], Integer.parseInt(d[2]));
            }
        } catch (Exception e) {
            System.out.println(" file " + file + " not found or corrupt");
        }
    }



    private class City {

        String name;
        Connection[] connections;

        City (String n) {
            name = n;
            connections = new Connection[0];
        }

        void addConnection (City dest, int di) {
            Connection c = new Connection(dest, di);
            Connection[] newConnections = new Connection[connections.length + 1];
            for (int i = 0; i < connections.length; i++)
                newConnections[i] = connections[i];
            newConnections[newConnections.length - 1] = c;
            connections = newConnections;
        }

        private class Connection {

            City destination;   // Made for navigating the graph.
            int distance;       // In minutes.

            Connection (City dest, int di) {
                destination = dest;
                distance = di;
            }

        }

    }



    private Integer hash(String name) {
        Integer hash = 0;
        for (int i = 0; i < name.length(); i++)
            hash = (hash*31 % mod) + name.charAt(i);
        return hash % mod;
    }



    private City lookup (String name) {
        Integer hashed = hash(name);
        if (cities[hashed] == null)
            return null;
        while (!cities[hashed].name.equals(name))
            hashed++;
        return cities[hashed];
    }



    private City addCity(String name) {
        City c = new City(name);
        Integer hashed = hash(name);
        System.out.println("Adding to hash: " + hashed);
        //if (cities[hashed] != null)
        //    System.out.print(" but collision detected");
        //System.out.println(c + " --- " + c.name + " hash: " + hashed + " currently holds " + cities[hashed]);
        //System.out.print(" current hashed: " + hashed);
        //while (cities[hashed] != null)
        //    hashed++;
        //    System.out.println("New hash val: " + cities[hashed++]);
        //System.out.println(hashed + " : " + cities[hashed]);
        //System.out.print(" new hashed: " + hashed + "\n");
        cities[hashed] = c;
        System.out.println(c.name + " added");
        return c;
    }



    private void addConnection (String from, String to, int di) {
        System.out.println("#Adding: " + from + " to " + to + " (" + di + ")");
        City fromCity = lookup(from);
        //City toCity = lookup(to);

        if (fromCity == null)
            fromCity = addCity(from);

/* 
        if (toCity == null)
            toCity = addCity(toCity);

        fromCity.addConnection(toCity, di);*/
    }



    void print() {
        for (City c : cities) {
            if (c == null)
                continue;
            System.out.println(c.name + " (" + c.connections.length + ")");
            /*for (City.Connection con : c.connections)
                System.out.println("└──> " + con.destination.name + " (" + con.distance + " min)");
            System.out.println();*/
        }
    }
}
package rockets.mining;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rockets.dataaccess.DAO;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class RocketMiner {
    private static Logger logger = LoggerFactory.getLogger(RocketMiner.class);

    private DAO dao;

    public RocketMiner(DAO dao) {
        this.dao = dao;
    }

    /**
     * TODO: to be implemented & tested!
     * Returns the top-k most active rockets, as measured by number of completed launches.
     * @param k the number of rockets to be returned.
     * @return the list of k most active rockets.
     */
    public List<Rocket> mostLaunchedRockets(int k) {
        logger.info("find the top" + k + "most launched rockets ");
        if(k <= 0){
            throw new IllegalArgumentException("the number k cannot less than 1");
        }
        Collection<Launch> launchSet1 = dao.loadAll(Launch.class);
        if (launchSet1.isEmpty()){
            return Collections.emptyList();
        }
        List<Rocket> rockets = new ArrayList<>();
        launchSet1.stream().map(a -> a.getLaunchServiceProvider().getRockets()).forEach(a -> rockets.addAll(a));
        Map<String, List<Rocket>> collect = rockets.stream().collect(Collectors.groupingBy(a -> a.getName()));
        return collect.values().stream()
                .sorted((a,b) -> b.size()-a.size())
                .map(a -> a.get(0))
                .limit(k).collect(Collectors.toList());
    }

    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns the top-k most reliable launch service providers as measured
     * @param k the number of launch service providers to be returned.
     * @return the list of k most reliable ones.
     */
    public List<LaunchServiceProvider> mostReliableLaunchServiceProviders(int k) {
        logger.info("return top " + k +" Reliable Launch Service Providers");
        if(k <= 0){
            throw new IllegalArgumentException("the number k should larger than 0");
        }
        Collection<Launch> launchSet2 = dao.loadAll(Launch.class);
        List<LaunchServiceProvider> allLaunchServiceProvider = new ArrayList<>();
        launchSet2.stream().map(Launch::getLaunchServiceProvider).forEach((a -> allLaunchServiceProvider.add(a)));
        Map<String, List<LaunchServiceProvider>> collect = allLaunchServiceProvider.stream().collect(Collectors.groupingBy(LaunchServiceProvider::getName));

        List<LaunchServiceProvider> result1 = collect.values().stream()
                .sorted((a, b) -> {
                    int aCount = 0;
                    for (LaunchServiceProvider launchServiceProvider : a) {
                        aCount += launchServiceProvider.getRockets().size();
                    }
                    int bCount = 0;
                    for (LaunchServiceProvider launchServiceProvider : b) {
                        bCount += launchServiceProvider.getRockets().size();
                    }
                    return bCount - aCount;
                })
                .map((a) -> a.get(0))
                .limit(k).collect(Collectors.toList());
        return result1;
    }

    /**
     * <p>
     * Returns the top-k most recent launches.
     * @param k the number of launches to be returned.
     * @return the list of k most recent launches.
     */
    public List<Launch> mostRecentLaunches(int k) {
        logger.info("find most recent " + k + " launches");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        Comparator<Launch> launchDateComparator = (a, b) -> -a.getLaunchDate().compareTo(b.getLaunchDate());
        return launches.stream().sorted(launchDateComparator).limit(k).collect(Collectors.toList());
    }

    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns the dominant country who has the most launched rockets in an orbit.
     *
     * @param orbit the orbit
     * @return the country who sends the most payload to the orbit
     */
    public String dominantCountry(String orbit) {
        logger.info("find the dominant country in" + orbit + "orbit");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        String country = launches.stream()
                .filter((a) -> a.getOrbit().equals(orbit))
                .map(Launch::getLaunchServiceProvider)
                .collect(Collectors.groupingBy(LaunchServiceProvider::getCountry))
                .values().stream().sorted((a, b) -> {
                    int aCount = 0;
                    for (LaunchServiceProvider launchServiceProvider : a) {
                        aCount += launchServiceProvider.getRockets().size();
                    }
                    int bCount = 0;
                    for (LaunchServiceProvider launchServiceProvider : b) {
                        bCount += launchServiceProvider.getRockets().size();
                    }
                    return bCount - aCount;
                })
                .limit(1).findFirst().orElseGet(() -> {
                    ArrayList<LaunchServiceProvider> objects = new ArrayList<>();
                    objects.add(new LaunchServiceProvider());
                    return objects;
                }).get(0).getCountry();
        return country;
    }

    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns the top-k most expensive launches.
     *
     * @param k the number of launches to be returned.
     * @return the list of k most expensive launches.
     */
    public List<Launch> mostExpensiveLaunches(int k) {
        logger.info("return top " + k +" most expensive launches");
        if(k <= 0){
            throw new IllegalArgumentException("the number k cannot be negative or zero");
        }
        Collection<Launch> launchSet5 = dao.loadAll(Launch.class);
        if (launchSet5.isEmpty()){
            return Collections.emptyList();
        }
        List<Launch> launchList = new ArrayList<>(launchSet5);
        Collections.sort(launchList, new Comparator<Launch>() {
            @Override
            public int compare(Launch o1, Launch o2) {
                return o2.getPrice().compareTo(o1.getPrice());
            }
        });
        if (launchList.size()<k){
            return launchList;
        }
        return launchList.subList(0,k);
    }


    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns a list of launch service provider that has the top-k highest
     * sales revenue in a year.
     *
     * @param k the number of launch service provider.
     * @param year the year in request
     * @return the list of k launch service providers who has the highest sales revenue.
     * the launch price * number of launch
     */
    public List<LaunchServiceProvider> highestRevenueLaunchServiceProviders(int k, int year) {
        logger.info("find top " + k + " launch service provider who has the highest revenue");
        if (k <= 0) {
            throw new IllegalArgumentException("the number of k should no less than i");
        }
        Collection<Launch> launchSet = dao.loadAll(Launch.class);
        if (launchSet.isEmpty()) {
            return Collections.emptyList();
        }
        List<LaunchServiceProvider> result = launchSet.stream()
                .filter(a -> a.getLaunchDate().getYear() == year)
                .collect(Collectors.groupingBy(a -> a.getLaunchServiceProvider().getName()))
                .values().stream().sorted((a, b) -> {
                    BigDecimal aPrice = BigDecimal.ZERO;
                    for (Launch launch : a) {
                        aPrice = aPrice.add(launch.getPrice());
                    }
                    BigDecimal bPrice = BigDecimal.ZERO;
                    for (Launch launch : b) {
                        bPrice = bPrice.add(launch.getPrice());
                    }
                    return bPrice.compareTo(aPrice);
                })
                .map(a -> a.get(0))
                .map(Launch::getLaunchServiceProvider)
                .limit(k).collect(Collectors.toList());
        return result;
    }
}

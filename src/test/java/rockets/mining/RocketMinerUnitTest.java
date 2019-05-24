package rockets.mining;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rockets.dataaccess.DAO;
import rockets.dataaccess.neo4j.Neo4jDAO;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

public class RocketMinerUnitTest {
    Logger logger = LoggerFactory.getLogger(RocketMinerUnitTest.class);

    private DAO dao;
    private RocketMiner miner;
    private List<Rocket> rockets;
    private List<LaunchServiceProvider> lsps;
    private List<Launch> launches;
    private LaunchServiceProvider lsp1,lsp2,lsp3;


    @BeforeEach
    public void setUp() {
        dao = mock(Neo4jDAO.class);
        miner = new RocketMiner(dao);
        rockets = Lists.newArrayList();

        lsps = Arrays.asList(
                new LaunchServiceProvider("ULA", 1990, "USA"),
                new LaunchServiceProvider("SpaceX", 2002, "USA"),
                new LaunchServiceProvider("ESA", 1975, "Europe ")
        );

        lsp1 = Mockito.mock(LaunchServiceProvider.class);
        lsp2 = Mockito.mock(LaunchServiceProvider.class);
        lsp3 = Mockito.mock(LaunchServiceProvider.class);

        // index of lsp of each rocket
        int[] lspIndex = new int[]{0, 0, 0, 1, 1};
        // 5 rockets
        for (int i = 0; i < 5; i++) {
            rockets.add(new Rocket("rocket_" + i, "USA", lsps.get(lspIndex[i])));
        }
        // month of each launch
        int[] months = new int[]{1, 6, 4, 3, 4, 11, 6, 5, 12, 5};

        // index of rocket of each launch
        int[] rocketIndex = new int[]{0, 0, 0, 0, 1, 1, 1, 2, 2, 3};

        // price of each launch
        int [] launchPrice = new int[]{500,1000,2000,800,5000,4000,3000,6000,7000,10000};

        // 10 launches
        launches = IntStream.range(0, 10).mapToObj(i -> {
            logger.info("create " + i + " launch in month: " + months[i]);
            Launch l = new Launch();
            l.setLaunchDate(LocalDate.of(2017, months[i], 1));
            l.setLaunchVehicle(rockets.get(rocketIndex[i]));
            l.setLaunchSite("VAFB");
            l.setOrbit("LEO");
            l.setPrice(BigDecimal.valueOf(launchPrice[i]));
            l.setLaunchServiceProvider(rockets.get(rocketIndex[i]).getManufacturer());
            spy(l);
            return l;
        }).collect(Collectors.toList());

        Set<Rocket> rockets1 = Sets.newLinkedHashSet();
        rockets1.add(rockets.get(0));
        rockets1.add(rockets.get(1));
        rockets1.add(rockets.get(2));
        Set<Rocket> rockets2 = Sets.newLinkedHashSet();
        rockets2.add(rockets.get(3));
        rockets2.add(rockets.get(4));
        Set<Rocket> rockets3 = Sets.newLinkedHashSet();
        lsp1 = lsps.get(0);
        lsp2 = lsps.get(1);
        lsp3 = lsps.get(2);
        lsp1.setRockets(rockets1);
        lsp2.setRockets(rockets2);
        lsp3.setRockets(rockets3);
    }
    //return the top k most recent launches
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopMostRecentLaunches(int k) {
        Mockito.when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> sortedLaunches = new ArrayList<>(launches);
        sortedLaunches.sort((a, b) -> -a.getLaunchDate().compareTo(b.getLaunchDate()));
        List<Launch> loadedLaunches = miner.mostRecentLaunches(k);
        assertEquals(k, loadedLaunches.size());
        assertEquals(sortedLaunches.subList(0, k), loadedLaunches);
    }
    //if no data in the list, return an empty list
    @Test
    public void noDataInDataBase() {
        Set<Launch> emptySet = Collections.emptySet();
        List<Launch> emptyList = Collections.emptyList();
        Mockito.when(dao.loadAll(Launch.class)).thenReturn(emptySet);
        assertEquals(emptyList,miner.mostRecentLaunches(1));
        assertEquals(emptyList,miner.mostLaunchedRockets(2));
    }

    /**
     * The top-k most expensive launch.
     * if k = 10, the launch price list should be
     * {500,1000,2000,800,5000,4000,3000,6000,7000,10000}
     * {10000,7000,6000,5000,4000,3000,2000,1000,800,500}
     * the launch 10 is most expensive launch
     * the launch 9 is in second place
     * ...
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5})
    @DisplayName("should Return Top k Most Expensive Launches")
    public void shouldReturnTopMostExpensiveLaunches(int k) {
        Mockito.when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> sortedLaunches = new ArrayList<>(launches);
        sortedLaunches.sort((a, b) -> -a.getPrice().compareTo(b.getPrice()));
        List<Launch> loadedLaunches = miner.mostExpensiveLaunches(k);
        assertEquals(k, loadedLaunches.size());
        assertEquals(sortedLaunches.subList(0, k), loadedLaunches);
    }

    /**
     * Return a list of rockets most Launched Rockets
     * 10 launches with the rocket index{0, 0, 0, 0, 1, 1, 1, 2, 2, 3}
     * rocket0 4 launches
     * rocket1 3 launches
     * rocket2 2 launches
     * rocket3 1 launches
     * the top4 most launched rockets is rocket0,rocket1,rocket2,and rocket3
     * the sequence is the same as that of rockets list, so I use rocket list as assert conditions
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    @DisplayName("should Return Top k most Launched Rockets")
    public void shouldReturnTopMostLaunchedRockets(int k) {
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Rocket> loadedRockets = miner.mostLaunchedRockets(k);
        assertEquals(k, loadedRockets.size());
        assertEquals(rockets.subList(0, k), loadedRockets);
    }

    /**
     * Return a list of Highest Revenue LaunchService Providers
     * since we set all launch date in 2017, wo choose year 2017 to test
     * service provider 1 has 3 rockets, 9 launches, the total sale revenue is 29300
     * service provider 2 has 2 rockets, 1 launch, the total sale revenue is 10000
     * service provider 3 has no rocket, no launch, the sale revenue is 0
     * so the top 1 service provider is service provider 1
     * */
    @ParameterizedTest
    @CsvSource({"1,2017"})
    @DisplayName("should Return Highest Revenue LaunchServiceProviders")
    public void shouldReturnHighestRevenueLaunchServiceProviders(int k, int year) {
        Mockito.when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<LaunchServiceProvider> testResult = new ArrayList<>();
        testResult.add(lsp1);
        List<LaunchServiceProvider> loadedProviders = miner.highestRevenueLaunchServiceProviders(k, year);
        assertEquals(k, loadedProviders.size());
        assertEquals(testResult, loadedProviders);
    }

    /**
     * Return dominant country who has the most launched rockets in an orbit
     * lsp1-ULA-USA 9 launched rockets
     * lsp2-SpaceX-USA 1 launched rockets
     * lsp3-ESA-Europe no launched rocket
     * so the dominant country who has the most launched rockets in an orbit is USA
     * */
    @ParameterizedTest
    @ValueSource(strings = {"LEO"})
    @DisplayName("should Return dominant country who has the most launched rockets in an orbit")
    public void shouldDominantCountryHavingtheMostLaunchedRocketsinAnOrbit(String orbit) {
        Mockito.when(dao.loadAll(Launch.class)).thenReturn(launches);
        String dominantCountry = miner.dominantCountry(orbit);
        assertEquals("USA", dominantCountry);
    }

    /**
     * Return a list of providers with the most Reliable Launch Service
     * Reliable is measured by percentage of successful launches.
     * lsp1-ULA-USA 9 launched rockets
     * lsp2-SpaceX-USA 1 launched rockets
     * lsp3-ESA-Europe no launched rocket
     * so the most reliable launch service provider is launch service provider 1
     */
    @ParameterizedTest
    @ValueSource(ints = {1})
    @DisplayName("should Return most Reliable Launch Service Providers")
    public void mostReliableLaunchServiceProviders(int k) {
        Mockito.when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<LaunchServiceProvider> testResult = miner.mostReliableLaunchServiceProviders(k);
        assertEquals(k, testResult.size());
        List<LaunchServiceProvider> expectedResult = new ArrayList<>();
        expectedResult.add(lsp1);
        assertEquals(expectedResult, testResult);
    }
}
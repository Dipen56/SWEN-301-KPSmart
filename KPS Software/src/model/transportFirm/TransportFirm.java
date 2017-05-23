package model.transportFirm;

import model.route.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a transport firm that carries mails.
 * <p>
 * NOTICE: This class is not used for now. I don't see the need of using it.
 *
 * @author Hector
 * @version 2017/5/20
 */
public class TransportFirm {
    private List<Route> offeredRoutes = new ArrayList<>();
    private String name;

    /**
     * Constructor
     *
     * @param name   the name of this company
     * @param routes a collection of {@link model.route.Route} that this company offers
     */
    public TransportFirm(String name, Route... routes) {
        this.name = name;
        this.offeredRoutes.addAll(Arrays.asList(routes));
    }

    /**
     * Can this company offer the given <i>route</i>
     *
     * @param route
     * @return
     */
    public boolean canOffer(Route route) {
        return this.offeredRoutes.contains(route);
    }

    /**
     * @return the name of this company
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the company name to a new name
     *
     * @param newName
     */
    public void setName(String newName) {
        this.name = newName;
    }

    @Override
    public String toString() {
        return "TransportFirm{" +
                ", name='" + name + '\'' +
                "offeredRoutes=" + offeredRoutes +
                '}';
    }
}

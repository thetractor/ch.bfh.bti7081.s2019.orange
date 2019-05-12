package ch.bfh.bti7081.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An in memory dummy "database" for the example purposes. In a typical Java app
 * this class would be replaced by e.g. EJB or a Spring based service class.
 * <p>
 * In demos/tutorials/examples, get a reference to this service class with
 * {@link DemoDossierService#getInstance()}.
 */
public class DemoDossierService {

    private static DemoDossierService instance;
    private static final Logger LOGGER = Logger.getLogger(DemoDossierService.class.getName());

    private final HashMap<Long, DemoDossier> contacts = new HashMap<>();
    private long nextId = 0;

    private DemoDossierService() {
    }

    /**
     * @return a reference to an example facade for DemoDossier objects.
     */
    public static DemoDossierService getInstance() {
        if (instance == null) {
            instance = new DemoDossierService();
            instance.ensureTestData();
        }
        return instance;
    }

    /**
     * @return all available DemoDossier objects.
     */
    public synchronized List<DemoDossier> findAll() {
        return findAll(null);
    }

    /**
     * Finds all DemoDossier's that match given filter.
     *
     * @param stringFilter
     *            filter that returned objects should match or null/empty string
     *            if all objects should be returned.
     * @return list a DemoDossier objects
     */
    public synchronized List<DemoDossier> findAll(String stringFilter) {
        ArrayList<DemoDossier> arrayList = new ArrayList<>();
        for (DemoDossier contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(DemoDossierService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<DemoDossier>() {

            @Override
            public int compare(DemoDossier o1, DemoDossier o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    /**
     * Finds all DemoDossier's that match given filter and limits the resultset.
     *
     * @param stringFilter
     *            filter that returned objects should match or null/empty string
     *            if all objects should be returned.
     * @param start
     *            the index of first result
     * @param maxresults
     *            maximum result count
     * @return list a DemoDossier objects
     */
    public synchronized List<DemoDossier> findAll(String stringFilter, int start, int maxresults) {
        ArrayList<DemoDossier> arrayList = new ArrayList<>();
        for (DemoDossier contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(DemoDossierService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<DemoDossier>() {

            @Override
            public int compare(DemoDossier o1, DemoDossier o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        int end = start + maxresults;
        if (end > arrayList.size()) {
            end = arrayList.size();
        }
        return arrayList.subList(start, end);
    }

    /**
     * @return the amount of all DemoDossiers in the system
     */
    public synchronized long count() {
        return contacts.size();
    }

    /**
     * Deletes a DemoDossier from a system
     *
     * @param value
     *            the DemoDossier to be deleted
     */
    public synchronized void delete(DemoDossier value) {
        contacts.remove(value.getId());
    }

    /**
     * Persists or updates DemoDossier in the system. Also assigns an identifier
     * for new DemoDossier instances.
     *
     * @param entry
     */
    public synchronized void save(DemoDossier entry) {
        if (entry == null) {
            LOGGER.log(Level.SEVERE,
                    "DemoDossier is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
            return;
        }
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = (DemoDossier) entry.clone();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        contacts.put(entry.getId(), entry);
    }

    /**
     * Sample data generation
     */
    public void ensureTestData() {
        if (findAll().isEmpty()) {
            final String[] names = new String[] { "Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
                    "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
                    "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
                    "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
                    "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
                    "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
                    "Jaydan Jackson", "Bernard Nilsen" };
            Random r = new Random(0);
            for (String name : names) {
                String[] split = name.split(" ");
                DemoDossier c = new DemoDossier();
                c.setFirstName(split[0]);
                c.setLastName(split[1]);
                c.setBirthDate(LocalDate.now().minusDays(r.nextInt(365*100)));
                save(c);
            }
        }
    }
}

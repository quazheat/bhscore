package fr.openai.filter;

import java.util.List;

public interface OffenderDatabase {
    void addOffender(String name);
    Offender getOffender(String name);
    List<Offender> getAllOffenders();
}

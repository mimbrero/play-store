package fp.googleplay.analyzer;

import fp.googleplay.ApplicationData;

import java.util.Collection;

// "analyzer" and not service as it stores the data to work with.
public interface ApplicationDataAnalyzer {

    Integer getDataSize();

    void add(ApplicationData data);

    void add(Collection<ApplicationData> data);

    void remove(ApplicationData data);

}

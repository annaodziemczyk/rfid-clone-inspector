package com.cit.core.csv;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Created by odziea on 12/18/2018.
 */
public interface ICSVReader {

    <T> List<T> loadObjectList(Class<T> type, InputStream file);
}

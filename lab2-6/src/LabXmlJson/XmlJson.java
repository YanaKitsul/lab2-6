package LabXmlJson;

import java.io.File;

public interface XmlJson {

    void serialize(Object object, File file);
    Object deserialize(Class c, File file);
}

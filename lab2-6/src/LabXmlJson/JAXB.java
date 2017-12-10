package LabXmlJson;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JAXB implements XmlJson {

    @Override
    public void serialize(Object object, File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            System.out.println("abc");
            Marshaller marshaller = context.createMarshaller();
            System.out.println("abc1");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            System.out.println("abc2");
            marshaller.marshal(object, file);
            System.out.println("abc3");
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Object deserialize(Class c, File file) {
        Object object = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            object = unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        } finally {
            return object;
        }
    }
}


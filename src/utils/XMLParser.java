package utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class XMLParser<T> {
    private final File file;

    public XMLParser(File file) throws FileNotFoundException, IOException, Exception {
        this.file = file;
    }

    public T toObject(Class[] classList) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(classList);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        T dc = (T) jaxbUnmarshaller.unmarshal(this.file);
        return dc;
    }

    public void saveFile(T object, Class[] classList) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(classList);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(object, System.out);
        jaxbMarshaller.marshal(object, this.file);
    }
}

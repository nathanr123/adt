/**
 * 
 */
package com.cti.vpx.util;

import java.io.File;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cti.vpx.model.VPXSystem;

/**
 * @author Abi_Achu
 *
 */
public class XMLParser {

	private static final ResourceBundle rBundle = VPXUtilities.getResourceBundle();

	public XMLParser() {

	}

	public static void writeToFile(VPXSystem system) {
		try {

			File folder = new File(rBundle.getString("Scan.processor.data.path"));

			if (!folder.exists()) {

				folder.mkdir();
			}

			File file = new File(rBundle.getString("Scan.processor.data.path") + "\\"
					+ rBundle.getString("Scan.processor.data.xml"));

			JAXBContext jaxbContext = JAXBContext.newInstance(VPXSystem.class);

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(system, file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	public static VPXSystem readFromFile() {

		VPXSystem cag = null;

		try {

			File file = new File(rBundle.getString("Scan.processor.data.path") + "\\"
					+ rBundle.getString("Scan.processor.data.xml"));

			if (file.exists()) {
				JAXBContext jaxbContext = JAXBContext.newInstance(VPXSystem.class);

				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				cag = (VPXSystem) jaxbUnmarshaller.unmarshal(file);
			}

		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return cag;
	}

}

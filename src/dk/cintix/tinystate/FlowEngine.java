package dk.cintix.tinystate;

import dk.cintix.tinystate.engine.Flow;
import dk.cintix.tinystate.states.ExportDataToEPG;
import dk.cintix.tinystate.states.FindNewPrograms;
import dk.cintix.tinystate.states.FindTimeStampFromMarina;
import dk.cintix.tinystate.states.UploadImagesToIMBO;
import dk.cintix.tinystate.states.UploadImagesToIMBOMotherShip;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migo
 */
public class FlowEngine {

    private static String FLOW_DATA = null;

    public FlowEngine() {

    }

    public static void main(String[] args) {
        Flow exportDataToEPGFromWhatsOn = null;
        try {

            if (FLOW_DATA == null) {
                exportDataToEPGFromWhatsOn = new Flow("WhatsOnEPG", 1);
                exportDataToEPGFromWhatsOn.registerEntities(String.class, Integer.class, FlowEngine.class, ExportDataToEPG.class, FindNewPrograms.class, FindTimeStampFromMarina.class, UploadImagesToIMBO.class, UploadImagesToIMBOMotherShip.class);
                FLOW_DATA = toString(exportDataToEPGFromWhatsOn);
            }

            exportDataToEPGFromWhatsOn = (Flow) fromString(FLOW_DATA);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FlowEngine.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (true) {
            try {
                System.out.println("");
                System.out.println("-----------------------------------------------------");
                exportDataToEPGFromWhatsOn.start();
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Read the object from Base64 string.
     */
    private static Object fromString(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    /**
     * Write the object to a Base64 string.
     */
    private static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

}

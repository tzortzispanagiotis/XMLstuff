import java.io.File;
import net.sf.saxon.s9api.*;

public class reader {

    private static XdmSequenceIterator getIterator(XdmNode parent) {
        XdmSequenceIterator iter = parent.axisIterator(Axis.CHILD);
        return iter;
    }

    public static void main(String[] args) {
        //input validation
        if (args.length < 2) {
            System.out.println("Wrong input. Provide both XML file and XPath.");
            System.exit(0);
        }

        File f = new File(args[0]);
        String xPathInput = args[1];
        // Initialisation of objects used by saxon
        Processor p = new Processor(false);
        XPathCompiler xpath = p.newXPathCompiler();
        DocumentBuilder builder = p.newDocumentBuilder();
        builder.setLineNumbering(true);
        builder.setWhitespaceStrippingPolicy(WhitespaceStrippingPolicy.ALL);

        //Read input and check if file is xml or not
        XdmNode input = null;
        try {
            input = builder.build(f);
        } catch (SaxonApiException e) {
            System.out.println("File is not xml. Parser error.");
            e.printStackTrace();
        }

        try {
            //compile xpath and apply expression to file
            XPathSelector select = xpath.compile(xPathInput).load();
            select.setContextItem(input);
            XdmSequenceIterator test;

            for (XdmItem item : select) {
                test = getIterator((XdmNode) item);
                XdmNode next;
                while (test.hasNext()) {
                    next = (XdmNode)test.next();
                    System.out.println(next.toString());
                }
            }
        }
        catch (SaxonApiException e) {
            e.printStackTrace();
        }
    }
}

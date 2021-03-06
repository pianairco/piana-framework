package ir.piana.dev.core.document;

import ir.piana.dev.core.annotation.PianaServer;
import ir.piana.dev.core.asset.PianaAsset;
import ir.piana.dev.core.asset.PianaAssetResolver;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammad Rahmati, 5/7/2017 5:20 PM
 */
public abstract class DocumentResolver {
    private static Logger logger = Logger.getLogger(
            DocumentResolver.class);
    private static PianaAsset documentHtml = null;
    private static String rootPath;
    private static PianaAssetResolver assetResolver;

    public static PianaAsset getPianaDocumentHtml(
            PianaServer pianaServer)
            throws Exception {
        if(documentHtml == null) {
            InputStream is = DocumentResolver.class.getResourceAsStream("/document.html");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String res;
            while (reader.ready()){
                res = reader.readLine();
                int i = StringUtils.countMatches(res, "@");
                if (i >= 2) {
                    int first = StringUtils.indexOf(res, "@");
                    int second = StringUtils.indexOf(res, "@", first + 1);
                    String substring = StringUtils.substring(res, first, second + 1);
                    String removed = StringUtils.remove(substring, "@");
                    res = StringUtils.replace(res, substring, "http://"
                            .concat(pianaServer.httpDocIp())
                            .concat(":").concat(String.valueOf(pianaServer.httpDocPort()))
                            .concat("/").concat(pianaServer.docStartUrl())
                            .concat("/json-model"));
                }
                sb.append(res.concat("\n"));
            }
            documentHtml = new PianaAsset(sb.toString().getBytes(),
                    null, null, MediaType.TEXT_HTML);
        }

        return documentHtml;
    }

    public static PianaAsset getPianaDocumentJsonModel(
            PianaServer pianaServer)
            throws Exception {
        List<ServiceModel> serviceModels = new ArrayList<>();
                return new PianaAsset(null, null, null,
                MediaType.APPLICATION_JSON);
    }
}

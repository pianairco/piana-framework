package ir.piana.dev.grizzly.http;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpStatus;

/**
 * @author Mohammad Rahmati, 9/30/2018
 */
final class HttpHandlerRedirector extends HttpHandler {
    private String host;
    private int port;
    private boolean secure;
    private String url;

    public HttpHandlerRedirector(String host, int port, boolean secure) {
        this.host = host;
        this.port = port;
        this.secure = secure;
        url = secure ? "https://" : "http://";
        url = url.concat(host).concat(":").concat(String.valueOf(port));
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        response.setHeader(Header.Location, url
                .concat(request.getRequest().getRequestURI() == null ?
                        "" : request.getRequest().getRequestURI())
                .concat(request.getRequest().getQueryString() == null ?
                        "" : "?".concat(request.getRequest().getQueryString())));
        response.setStatus(HttpStatus.MOVED_PERMANENTLY_301);
    }
}

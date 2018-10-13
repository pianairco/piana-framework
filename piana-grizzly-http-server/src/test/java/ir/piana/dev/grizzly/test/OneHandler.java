package ir.piana.dev.grizzly.test;

import ir.piana.dev.core.annotation.Handler;
import ir.piana.dev.core.annotation.HandlerType;
import ir.piana.dev.core.annotation.MethodHandler;
import ir.piana.dev.core.response.PianaResponse;
import ir.piana.dev.core.role.RoleType;

import javax.ws.rs.core.Response;

/**
 * @author Mohammad Rahmati, 10/13/2018
 */
@Handler(baseUrl = "say-hello", handlerType = HandlerType.METHOD_HANDLER)
public class OneHandler {
    @MethodHandler(requiredRole = RoleType.GUEST)
    public static PianaResponse sayHello() {
        return new PianaResponse(Response.Status.OK, "hello friend!");
    }
}

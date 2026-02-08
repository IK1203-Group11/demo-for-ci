//package src.main.java;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Collectors;

/**
 * Skeleton of a ContinuousIntegrationServer which acts as webhook
 * See the Jetty documentation for API documentation of those classes.
 */
public class ContinuousIntegrationServer extends AbstractHandler {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void handle(String target,
            Request baseRequest,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        // response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        // GitHub webhooks will be POST requests with a JSON body
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            response.getWriter().println("Only POST requests are allowed");
            return;
        }

        // Read JSON payload from request body.
        String body = request.getReader().lines().collect(Collectors.joining("\n"));

        // Check what type of event (push, ping, etc.)
        String event = request.getHeader("X-GitHub-Event");
        System.out.println("Received event: " + event);
        System.out.println("Target: " + target);

        try {
            // Handle GitHub ping event (when first adding webhook).
            if ("ping".equalsIgnoreCase(event)) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("Pong (ping received)");
                return;
            }

            // Parse JSON and extract fields from push event.
            BuildTrigger trigger = parsePushPayload(body);

            System.out.println("Branch: " + trigger.branch);
            System.out.println("Commit: " + trigger.commitSha);
            System.out.println("Clone URL: " + trigger.cloneUrl);

            // DO ALL CI TASKS HERE
            // Clone repo,
            // checkout branch,
            // compile code, run tests...

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Failed to parse webhook payload: " + e.getMessage());
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("CI job done");
    }

    /**
     * Helper class to hold extracted fields from GitHub push event payload.
     * 
     * branch = branch that was pushed to
     * commitSha = commit SHA after the push
     * cloneUrl = URL to clone the repo
     */
    private static class BuildTrigger {
        final String branch;
        final String commitSha;
        final String cloneUrl;

        BuildTrigger(String branch, String commitSha, String cloneUrl) {
            this.branch = branch;
            this.commitSha = commitSha;
            this.cloneUrl = cloneUrl;
        }
    }

    /**
     * Parse the JSON payload from GitHub push event and extract branch, commit SHA and clone URL.
     * 
     * @param body
     * @return
     * @throws IOException
     */
    private static BuildTrigger parsePushPayload(String body) throws IOException {
        JsonNode root = MAPPER.readTree(body);

        // Example : "refs/heads/assessment"
        String ref = root.path("ref").asText("");
        String branch = toBranchName(ref);

        // Example : commit SHA after push.
        String sha = root.path("after").asText("");

        // Example : repo clone URL.
        String cloneUrl = root.path("repository").path("clone_url").asText("");

        if (branch.isBlank() || sha.isBlank() || cloneUrl.isBlank()) {
            throw new IllegalArgumentException("Missing required fields in payload. ref = " + ref + ", after = " + sha
                    + ", clone_url = " + cloneUrl);
        }

        return new BuildTrigger(branch, sha, cloneUrl);
    }

    private static String toBranchName(String ref) {
        String prefix = "refs/heads/";
        if (ref != null && ref.startsWith(prefix)) {
            return ref.substring(prefix.length());
        }
        return ref == null ? "" : ref; // return empty string if ref is null.
    }

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}
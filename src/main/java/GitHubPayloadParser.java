import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GitHubPayloadParser {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // Use a normal nested class instead of a record (works on older Java)
    public static class BuildTrigger {
        public final String branch;
        public final String commitSha;
        public final String cloneUrl;

        public BuildTrigger(String branch, String commitSha, String cloneUrl) {
            this.branch = branch;
            this.commitSha = commitSha;
            this.cloneUrl = cloneUrl;
        }
    }

    public BuildTrigger parsePushPayload(String body) throws IOException {
        JsonNode root = MAPPER.readTree(body);

        String ref = root.path("ref").asText("");
        String branch = toBranchName(ref);

        String sha = root.path("after").asText("");
        String cloneUrl = root.path("repository").path("clone_url").asText("");

        if (branch.isBlank() || sha.isBlank() || cloneUrl.isBlank()) {
            throw new IllegalArgumentException("Missing required fields in payload. ref = " + ref + ", after = " + sha
                    + ", clone_url = " + cloneUrl);
        }

        return new BuildTrigger(branch, sha, cloneUrl);
    }

    static String toBranchName(String ref) {
        String prefix = "refs/heads/";
        if (ref != null && ref.startsWith(prefix)) {
            return ref.substring(prefix.length());
        }
        return ref == null ? "" : ref; // return empty string if ref is null.
    }
}

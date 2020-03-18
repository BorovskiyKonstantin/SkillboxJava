import java.util.Set;

public class Node {
    private String rootDomain;
    private String nodePath;
    private Set<String> nodeLinks;

    public Node(String rootDomain, String nodePath, Set<String> nodeLinks) {
        this.rootDomain = rootDomain;
        this.nodePath = nodePath;
        this.nodeLinks = nodeLinks;
    }

    // Геттеры
    public String getRootDomain() {
        return rootDomain;
    }

    public String getNodePath() {
        return nodePath;
    }

    public Set<String> getNodeLinks() {
        return nodeLinks;
    }
}

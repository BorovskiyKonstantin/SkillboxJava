import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.RecursiveAction;

public class LinkParsingTask extends RecursiveAction {
    private Node node;
    private Set<String> siteMap;

    public LinkParsingTask(Node node, Set<String> siteMap) {
        this.node = node;
        this.siteMap = siteMap;
    }

    @Override
    protected void compute() {
        siteMap.add(node.getNodePath());
        List<LinkParsingTask> taskList = new ArrayList<>();
        for (String link : node.getNodeLinks()) {
            if (siteMap.contains(link)) {
                continue;
            } else siteMap.add(link);
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                    Node childNode = Parser.parseNode(node.getRootDomain(), link);
                    LinkParsingTask childTask = new LinkParsingTask(childNode, siteMap);
                    taskList.add(childTask);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(">>>>>>> Ошибка при обработке узла." +
                            "\nНе удалось распарсить страницу по ссылке: " + link +
                            "\nПопытка " + i + "/10");
                }
            }
        }
        Set<String> parsedLinks = new TreeSet<>();
        parsedLinks.add(node.getNodePath());

        invokeAll(taskList);
    }

    public Set<String> getSiteMap() {
        return siteMap;
    }
}

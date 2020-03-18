import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.TreeSet;

public class Parser {
    public static Node parseNode(String rootDomain, String nodePath) throws Exception {
        TreeSet<String> nodeLinks = parseNodeLinks(nodePath, rootDomain);
        Node node = new Node(rootDomain, nodePath, nodeLinks);
//        System.out.println(Thread.currentThread().getName() + "NODE created---- " + nodePath);//
        return node;
    }

    //Получение списка дочерних страниц, их фильрация и добавление в TreeSet
    private static TreeSet<String> parseNodeLinks(String nodePath, String rootDomain) throws Exception {
        Document document = Jsoup.connect(nodePath).maxBodySize(Integer.MAX_VALUE).get();
        TreeSet<String> nodeLinksSet = new TreeSet<>();
        Elements elements = document.select("a");
        if (elements.size() > 0) {
            for (Element element : elements) {
                String href = element.attr("href");
                //добавление абсолютных ссылок для корневого домена
                if (href.matches(rootDomain + ".+/$")) {
                    nodeLinksSet.add(href);
                    continue;
                }

                //добавление относительных ссылок "/aaa/bbb/ccc/"
                if (href.matches("^/.*/$")) {
                    //добавление относительных ссылок для дочерних узлов root/aaa/ + /bbb..
                    if (nodePath.matches("/$")) {
                        if (href.matches("^/")) {
                            String url = nodePath + href.substring(1);
                            nodeLinksSet.add(url);
                        }
                    } else {
                        nodeLinksSet.add(rootDomain + href);
                    }
                }
            }
        }
        return nodeLinksSet;
    }
}

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Test {
    public static void main(String[] args) {
        ObjectMapper jsonObjBuilder = new ObjectMapper();
        ObjectNode objectNode = jsonObjBuilder.createObjectNode();
        objectNode.put("message", "Problem matching service key, username and password");
        System.out.println(objectNode.toString());
    }
}
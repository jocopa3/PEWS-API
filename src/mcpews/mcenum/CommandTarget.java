package mcpews.mcenum;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/**
 *
 * @author Jocopa3
 */
public class CommandTarget {

    public enum TargetSelector {
        ALL_ENTITIES("allEntities", 'e'),
        ALL_PLAYERS("allPlayers", 'a'),
        NEAREST_PLAYER("nearestPlayer", 'p'),
        RANDOM_PLAYER("randomPlayer", 'r');

        private static HashMap<Character, String> fromCode;

        static {
            fromCode = new HashMap<>();
            for (TargetSelector selector : values()) {
                fromCode.put(selector.selectorCode, selector.type);
            }
        }

        private String type;
        private char selectorCode;

        TargetSelector(String type, char code) {
            this.type = type;
            selectorCode = code;
        }

        public String getTypeString() {
            return type;
        }

        public char getTypeCode() {
            return selectorCode;
        }

        public static String fromCode(char code) {
            return fromCode.get(code);
        }

        public static String fromCode(String s) {
            if (s.startsWith("@")) {
                return fromCode(s.charAt(1));
            }

            return null;
        }
    }

    public enum RuleType {
        name, // Entity/Player name
        type, // Entity type
        r,    // Max Radius
        rm,   // Min Radius
        m,    // Gamemode
        x,    // X-pos
        y,    // Y-pos
        z;    // Z-pos
    }

    public class TargetRule {

        private boolean inverted;
        private String name;
        private String value;

        public TargetRule(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public TargetRule(String name, String value, boolean inverted) {
            this.name = name;
            this.value = value;
            this.inverted = inverted;
        }
    }

    private String selector;
    private ArrayList<TargetRule> rules;

    JsonParser parser = new JsonParser();
    private Pattern outerQuotes = Pattern.compile("(((?<=[\\[,])\\s*)|(?<=[a-zA-Z0-9_])\\s*(?=[,\\]]))");
    private Pattern innerQuotes = Pattern.compile("\\s*=\\s*");

    public CommandTarget(String targetString) {

        selector = TargetSelector.fromCode(targetString);

        if (selector == null) {
            selector = TargetSelector.ALL_PLAYERS.getTypeString();
            addRule(RuleType.name, targetString);
        } else {
            String targetSpecifiers = targetString.replaceFirst("@[aeprAEPR]", "");

            if (!targetSpecifiers.isEmpty()) {
                targetSpecifiers = innerQuotes.matcher(outerQuotes.matcher(targetSpecifiers).replaceAll("\""))
                        .replaceAll("\":\"")
                        .replace("[", "{")
                        .replace("]", "}");

                try {
                    JsonObject ele = parser.parse(targetSpecifiers).getAsJsonObject();

                    for (Entry<String, JsonElement> entry : ele.entrySet()) {
                        String value = entry.getValue().getAsString();
                        if(value.startsWith("!")) {
                            addRule(entry.getKey(), value.substring(value.indexOf('!')+1), true);
                        } else {
                            addRule(entry.getKey(), value);
                        }
                    }
                } catch (Exception e) {
                    rules = null;
                }
            } else {
                rules = null;
            }
        }
    }
    
    public CommandTarget(TargetSelector selector, TargetRule... rules) {
        this.selector = selector.getTypeString();
        
        for(TargetRule rule : rules) {
            addRule(rule);
        }
    }

    public void addRule(TargetRule rule) {
        if (rules == null) {
            rules = new ArrayList<>();
        }
        System.out.println(rule.name + (rule.inverted ? " != " : " = ") + rule.value);
        rules.add(rule);
    }

    public void addRule(String name, String value) {
        addRule(new TargetRule(name, value));
    }

    public void addRule(String name, String value, boolean inverted) {
        addRule(new TargetRule(name, value, inverted));
    }
    
    public void addRule(RuleType type, String value) {
        addRule(new TargetRule(type.name(), value));
    }

    public void addRule(RuleType type, String value, boolean inverted) {
        addRule(new TargetRule(type.name(), value, inverted));
    }

    public String getSelector() {
        return selector;
    }

    public TargetRule[] getRules() {
        return rules.toArray(new TargetRule[0]);
    }

    public static void main(String[] args) {
        CommandTarget target = new CommandTarget("@e[type=sheep]");
    }
}

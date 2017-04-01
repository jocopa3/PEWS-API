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

        name("Name"), // Entity/Player name
        type("Type"), // Entity type
        r("Max Radius"), // Max Radius
        rm("Min Radius"), // Min Radius
        m("Game Mode"), // Gamemode
        x("X-Pos"), // X-pos
        y("Y-Pos"), // Y-pos
        z("Z-Pos");    // Z-pos

        private transient String description;

        private static HashMap<String, RuleType> fromString;

        static {
            fromString = new HashMap<>();
            for (RuleType rule : values()) {
                fromString.put(rule.name(), rule);
            }
        }

        RuleType(String desc) {
            description = desc;
        }

        public static RuleType fromString(String type) {
            return fromString.get(type);
        }
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

        public RuleType getRule() {
            return RuleType.fromString(name);
        }

        @Override
        public String toString() {
            RuleType rule = getRule();
            String val = rule.description + (inverted ? " != " : " = ");

            switch (rule) {
                case m:
                    switch (value) {
                        case "0":
                        case "s":
                            val += "Survival";
                            break;
                        case "1":
                        case "c":
                            val += "Creative";
                            break;
                        default:
                            val += "Invalid";
                            break;
                    }
                    break;
                default:
                    val += value;
            }

            return val;
        }
    }

    private String selector;
    private ArrayList<TargetRule> rules;

    private static final JsonParser parser = new JsonParser();
    private static final Pattern outerQuotes = Pattern.compile("(((?<=[\\[,])\\s*)|(?<=[a-zA-Z0-9_])\\s*(?=[,\\]]))");
    private static final Pattern innerQuotes = Pattern.compile("\\s*=\\s*");

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
                        if (value.startsWith("!")) {
                            addRule(entry.getKey(), value.substring(value.indexOf('!') + 1), true);
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

        for (TargetRule rule : rules) {
            addRule(rule);
        }
    }

    public void addRule(TargetRule rule) {
        if (rules == null) {
            rules = new ArrayList<>();
        }

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

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("Selector: ").append(selector).append("; Rules: ");
        int i;
        for (i = 0; i < rules.size() - 1; i++) {
            b.append(rules.get(0).toString()).append(", ");
        }

        b.append(rules.get(i).toString());

        return b.toString();
    }

    public static void main(String[] args) {
        String target1 = "@e[type=!sheep,r=10]";
        String target2 = "@p[m=s]";

        CommandTarget tar1 = new CommandTarget(target1);
        CommandTarget tar2 = new CommandTarget(target2);

        System.out.println("Target 1:\n" + tar1);
        System.out.println("Target 2:\n" + tar2);
    }
}

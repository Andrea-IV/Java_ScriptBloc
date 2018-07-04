package GUI;

public class Arguments implements Cloneable{
    private int id;
    private String name;
    private String value;
    private String key;
    private String description;

    public Arguments(int id, String name, String key, String description) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.description = description;
        this.value = "";
    }

    public Arguments(int id, String name, String key, String description, String Value) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.description = description;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object clone() {
        Arguments args = null;
        try{
            args = (Arguments) super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }

        return args;
    }
}

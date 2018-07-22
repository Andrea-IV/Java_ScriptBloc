package GUI;

import java.util.ArrayList;

public class Arguments implements Cloneable{
    private int id;
    private String name;
    private String value;
    private String key;
    private String description;
    public ArrayList<Option> option;

    @MethodInfo(name = "Arguments(int id, String name, String key, String description)", date = "05/07/18", arguments = "1: int id, id of the block, 2: String name, name of the block, 3: String key, key of the block , 4: String description, information about the purpose of the block", comments = "The basic constructor without value initialisation", returnValue="" ,revision = 1)
    public Arguments(int id, String name, String key, String description) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.description = description;
        this.value = "";
        this.option = new ArrayList<Option>();
    }

    @MethodInfo(name = "Arguments(int id, String name, String key, String description, String Value)", date = "05/07/18", arguments = "1: int id, id of the block, 2: String name, name of the block, 3: String key, key of the block , 4: String description, information about the purpose of the block, 5: String Value, value put in the argument", comments = "The complete constructor", returnValue="" ,revision = 1)
    public Arguments(int id, String name, String key, String description, String Value, ArrayList<Option> option) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.description = description;
        this.value = value;
        this.option = option;
    }

    public Arguments(int id, String name, String key, String description, ArrayList<Option> option) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.description = description;
        this.option = option;
    }

    @MethodInfo(name = "getId()", date = "05/07/18", arguments = "None", comments = "Getter for the ID parameter", returnValue="int id, id of the object" ,revision = 1)
    public int getId() {
        return id;
    }

    @MethodInfo(name = "setId(int id)", date = "05/07/18", arguments = "1: int id, the new id for the object", comments = "Setter for the ID parameter", returnValue="" ,revision = 1)
    public void setId(int id) {
        this.id = id;
    }

    @MethodInfo(name = "getName()", date = "05/07/18", arguments = "None", comments = "Getter for the name parameter", returnValue="String name, name of the object" ,revision = 1)
    public String getName() {
        return name;
    }

    @MethodInfo(name = "setName(String name)", date = "05/07/18", arguments = "1: String name, the new name for the object", comments = "Setter for the name parameter", returnValue="" ,revision = 1)
    public void setName(String name) {
        this.name = name;
    }

    @MethodInfo(name = "getValue()", date = "05/07/18", arguments = "None", comments = "Getter for the value parameter", returnValue="String value, value of the object" ,revision = 1)
    public String getValue() {
        return value;
    }

    @MethodInfo(name = "setValue(String value)", date = "05/07/18", arguments = "1: String value, the new value for the object", comments = "Setter for the value parameter", returnValue="" ,revision = 1)
    public void setValue(String value) {
        this.value = value;
    }

    @MethodInfo(name = "getDescription()", date = "05/07/18", arguments = "None", comments = "Getter for the description parameter", returnValue="String description, description of the object" ,revision = 1)
    public String getDescription() {
        return description;
    }

    @MethodInfo(name = "getKey()", date = "05/07/18", arguments = "None", comments = "Getter for the key parameter", returnValue="String key, key of the object" ,revision = 1)
    public String getKey() {
        return key;
    }

    @MethodInfo(name = "setKey(String key)", date = "05/07/18", arguments = "1: String key, the new key for the object", comments = "Setter for the key parameter", returnValue="" ,revision = 1)
    public void setKey(String key) {
        this.key = key;
    }

    @MethodInfo(name = "setDescription(String Description)", date = "05/07/18", arguments = "1: String Description, the new Description for the object", comments = "Setter for the Description parameter", returnValue="" ,revision = 1)
    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Option> getOption() {
        return option;
    }

    public void setOption(ArrayList<Option> option) {
        this.option = option;
    }

    @MethodInfo(name = "Object clone()", date = "05/07/18", arguments = "None", comments = "Permit the clone of the args when a block is copied", returnValue="Object, the Clone" ,revision = 1)
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

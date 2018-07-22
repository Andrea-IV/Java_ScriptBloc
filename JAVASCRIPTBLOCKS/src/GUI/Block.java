package GUI;

import java.util.ArrayList;

public class Block {
    private int id;
    private String name;
    private String description;
    private String type;
    @MethodInfo(name = "ArrayList<Arguments> args", date = "05/07/18", arguments = "", comments = "The args of the block, array list of arguments can be empty", returnValue="" ,revision = 1)
    public ArrayList<Arguments> args;

    @MethodInfo(name = "Block(int id, String name, String description, String type)", date = "05/07/18", arguments = "1: int id, id of the block, 2: String name, name of the block, 3: String description, information about the purpose of the block, 4: String type, type of the block", comments = "The basic constructor without arguments initialisation", returnValue="" ,revision = 1)
    public Block(int id, String name, String description, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        args = new ArrayList<Arguments>();
    }

    @MethodInfo(name = "Block(int id, String name, String description, String type, ArrayList<Arguments> args)", date = "05/07/18", arguments = "1: int id, id of the block, 2: String name, name of the block, 3: String description, information about the purpose of the block, 4: String type, type of the block, 5: ArrayList<Arguments> args, The array list of arguments that are used by the block", comments = "The complete constructor", returnValue="" ,revision = 1)
    public Block(int id, String name, String description, String type, ArrayList<Arguments> args, ArrayList<Option> option) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.args = args;
        this.type = type;
    }

    @MethodInfo(name = "Block(Block another)", date = "05/07/18", arguments = "1: Block another, the block from which you want to take the values", comments = "Copy Constructor", returnValue="" ,revision = 1)
    public Block(Block another){
        this.id = another.getId();
        this.name = another.getName();
        this.description = another.getDescription();
        this.args = new ArrayList<Arguments>();
        Arguments newOne;
        ArrayList<Option> newOpt;
        for(Arguments argObj: another.getArgs()){
            newOpt = new ArrayList<Option>();
            for(Option argOpt : argObj.option){
                newOpt.add(new Option(argOpt.getId(),argOpt.getId_argument(), argOpt.getId_block(), argOpt.getUnix(), argOpt.getWindows(), argOpt.getName(), argOpt.getInput()));
            }
             newOne = new Arguments(argObj.getId(), argObj.getName(), argObj.getKey(), argObj.getDescription(), newOpt);
             this.args.add(newOne);
        }
        this.type = another.getType();
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

    @MethodInfo(name = "getDescription()", date = "05/07/18", arguments = "None", comments = "Getter for the description parameter", returnValue="String description, description of the object" ,revision = 1)
    public String getDescription() {
        return description;
    }

    @MethodInfo(name = "setDescription(String Description)", date = "05/07/18", arguments = "1: String Description, the new Description for the object", comments = "Setter for the Description parameter", returnValue="" ,revision = 1)
    public void setDescription(String description) {
        this.description = description;
    }

    @MethodInfo(name = "getArgs()", date = "05/07/18", arguments = "None", comments = "Getter for the args parameter", returnValue="ArrayList<Arguments> args, args of the object" ,revision = 1)
    public ArrayList<Arguments> getArgs() {
        return args;
    }

    @MethodInfo(name = "setArgs(ArrayList<Arguments> args)", date = "05/07/18", arguments = "1: ArrayList<Arguments> args, the new args for the object", comments = "Setter for the args parameter", returnValue="" ,revision = 1)
    public void setArgs(ArrayList<Arguments> args) {
        this.args = args;
    }

    @MethodInfo(name = "getType()", date = "05/07/18", arguments = "None", comments = "Getter for the type parameter", returnValue="String type, type of the object" ,revision = 1)
    public String getType() {
        return type;
    }

    @MethodInfo(name = "setType(String type)", date = "05/07/18", arguments = "1: String type, the new type for the object", comments = "Setter for the type parameter", returnValue="" ,revision = 1)
    public void setType(String type) {
        this.type = type;
    }
}

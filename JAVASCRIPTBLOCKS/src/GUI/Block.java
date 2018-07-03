package GUI;

import java.util.ArrayList;

public class Block {
    private int id;
    private String name;
    private String description;
    private String type;
    public ArrayList<Arguments> args;

    public Block(int id, String name, String description, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        args = new ArrayList<Arguments>();
    }

    public Block(int id, String name, String description, String type, ArrayList<Arguments> args) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.args = args;
        this.type = type;
    }

    public Block(Block another){
        this.id = another.getId();
        this.name = another.getName();
        this.description = another.getDescription();
        this.args = (ArrayList<Arguments>)another.getArgs().clone();
        this.type = another.getType();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Arguments> getArgs() {
        return args;
    }

    public void setArgs(ArrayList<Arguments> args) {
        this.args = args;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package GUI;

import java.util.ArrayList;

public class Block {
    private int id;
    private String name;
    private String description;
    private ArrayList<Arguments> args;

    public Block(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Block(int id, String name, String description, ArrayList<Arguments> args) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.args = args;
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
}

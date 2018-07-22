package GUI;

public class Option {
    private int id;
    private int id_argument;
    private int id_block;
    private String unix;
    private String windows;
    private String name;
    private Boolean input;
    private String value;
    private Boolean activated;

    public Option(int id, int id_argument, int id_block, String unix, String windows, String name, Boolean input) {
        this.id = id;
        this.id_argument = id_argument;
        this.id_block = id_block;
        this.unix = unix;
        this.windows = windows;
        this.name = name;
        this.input = input;
        this.activated = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_argument() {
        return id_argument;
    }

    public void setId_argument(int id_argument) {
        this.id_argument = id_argument;
    }

    public int getId_block() {
        return id_block;
    }

    public void setId_block(int id_block) {
        this.id_block = id_block;
    }

    public String getUnix() {
        return unix;
    }

    public void setUnix(String unix) {
        this.unix = unix;
    }

    public String getWindows() {
        return windows;
    }

    public void setWindows(String windows) {
        this.windows = windows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getInput() {
        return input;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public void setInput(Boolean input) {
        this.input = input;
    }

    public Object clone() {
        Option option = null;
        try{
            option = (Option) super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }

        return option;
    }
}

package GUI;

import javafx.scene.control.Label;

public class BlockDisplay {
    @MethodInfo(name = "Label blockLabel", date = "05/07/18", arguments = "", comments = "The Label used for the archicteture", returnValue="" ,revision = 1)
    public Label blockLabel;

    private int type;

    @MethodInfo(name = "Block block", date = "05/07/18", arguments = "", comments = "The block contained by the display block",returnValue="" ,revision = 1)
    public Block block;

    @MethodInfo(name = "BlockDisplay(Label label, int type)", date = "05/07/18", arguments = "1: Label label, the label that will be put in the current BlockDisplay, 2: int type, the type that will be put in the current BlockDisplay", comments = "Basic Constructor",returnValue="None" ,revision = 1)
    public BlockDisplay(Label label, int type){
        this.blockLabel = label;
        this.type = type;
    }

    @MethodInfo(name = "BlockDisplay(BlockDisplay another)", date = "05/07/18", arguments = "1: BlockDisplay another, the BlockDisplay to copy in the current BlockDisplay", comments = "Constructor to make a Copy",returnValue="None" ,revision = 1)
    public BlockDisplay(BlockDisplay another){
        this.blockLabel = new Label(another.blockLabel.getText());
        this.type = another.getType();
        this.block = new Block(another.block);
    }

    @MethodInfo(name = "getBlockLabel()", date = "05/07/18", arguments = "None", comments = "Get the BlockLabel",returnValue="Label, the blocklabel of the class" ,revision = 1)
    public Label getBlockLabel() {
        return blockLabel;
    }

    @MethodInfo(name = "setBlockLabel()", date = "05/07/18", arguments = "1: Label Blocklabel, The new label to put in the BlockDisplay", comments = "Label Setter", returnValue="void" , revision = 1)
    public void setBlockLabel(Label blockLabel) {
        this.blockLabel = blockLabel;
    }

    @MethodInfo(name = "getType()", date = "05/07/18", arguments = "None", comments = "Get the Type", returnValue="int, the type of the class" , revision = 1)
    public int getType() {
        return type;
    }

    @MethodInfo(name = "setType()", date = "05/07/18", arguments = "1: int Type, The new type to put in the BlockDisplay", returnValue="void", comments = "Type Setter", revision = 1)
    public void setType(int type) {
        this.type = type;
    }
}

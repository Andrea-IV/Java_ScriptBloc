package GUI;

import javafx.scene.control.Label;

public class BlockDisplay {
    public Label blockLabel;
    private int type;
    private int position;
    public Block block;

    public BlockDisplay(Label label, int type){
        this.blockLabel = label;
        this.type = type;
    }

    public BlockDisplay(BlockDisplay another){
        this.blockLabel = new Label(another.blockLabel.getText());
        this.type = another.getType();
        this.block = new Block(another.block);
    }

    public Label getBlockLabel() {
        return blockLabel;
    }

    public void setBlockLabel(Label blockLabel) {
        this.blockLabel = blockLabel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

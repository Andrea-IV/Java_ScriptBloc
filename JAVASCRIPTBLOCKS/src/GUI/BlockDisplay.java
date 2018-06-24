package GUI;

import javafx.scene.control.Label;

public class BlockDisplay {
    private Label blockLabel;
    private int type;
    private int position;

    public BlockDisplay(Label label, int type){
        this.blockLabel = label;
        this.type = type;
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

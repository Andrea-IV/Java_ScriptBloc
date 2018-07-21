package GUI;

public class Configuration {
    private String scriptPath;
    private String pluginPath;
    private String backgroundColor;
    private String blockColor;
    private String listColor;

    @MethodInfo(name = "Configuration()", date = "20/07/18", arguments = "None", comments = "Default config", returnValue="None" ,revision = 1)
    public Configuration(){
        scriptPath = System.getProperty("user.dir");
        pluginPath = System.getProperty("user.dir")+"\\plugins\\";
        backgroundColor = "#1A1A1A";
        blockColor = "#1A1A1A";
        listColor = "#5e5e5e";
    }

    public Configuration(String scriptPath, String pluginPath, String backgroundColor, String blockColor, String listColor){
        this.scriptPath = scriptPath;
        this.pluginPath = pluginPath;
        this.backgroundColor = backgroundColor;
        this.blockColor = blockColor;
        this.listColor = listColor;
    }

    public String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    public String getPluginPath() {
        return pluginPath;
    }

    public void setPluginPath(String pluginPath) {
        this.pluginPath = pluginPath;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBlockColor() {
        return blockColor;
    }

    public void setBlockColor(String blockColor) {
        this.blockColor = blockColor;
    }

    public String getListColor() {
        return listColor;
    }

    public void setListColor(String listColor) {
        this.listColor = listColor;
    }
}

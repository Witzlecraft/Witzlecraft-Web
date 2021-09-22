package de.morrisbr.witzlecraft.network.objects;

public class EventPlayer {

	public String name;
    public String uuid;
    
    public EventPlayer() {}
    
    public EventPlayer(String name, String uuid) {
    	this.name = name;
    	this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}

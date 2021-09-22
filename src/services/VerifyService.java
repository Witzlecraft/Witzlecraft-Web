package services;

import com.mongodb.client.model.Filters;
import de.morrisbr.verify.mongodb.MongoManager;
import de.morrisbr.witzlecraft.main.Main;
import de.morrisbr.witzlecraft.network.events.VerifyEvent;
import de.morrisbr.witzlecraft.network.objects.EventPlayer;
import org.bson.Document;

import com.esotericsoftware.kryonet.Client;

import de.morrisbr.verify.network.objects.Account;
import org.bson.conversions.Bson;

public class VerifyService {

    private MongoManager mongodbManager;
    private Client client;

    public VerifyService(MongoManager mongodbManager, Client client) {
        this.mongodbManager = mongodbManager;
        this.client = client;
    }
    
    public VerifyService(MongoManager mongodbManager) {
        this.mongodbManager = mongodbManager;
    }
    

    public void postVerify(EventPlayer player) {
        VerifyEvent verifyEvent = new VerifyEvent();
        verifyEvent.setPlayer(player);

        verifyEvent.generateRandomCode();

        if(verifyEvent.getCode() == null || verifyEvent.getCode().equals("")) {
            System.out.println("Es wurde kein Code generiert!");
            return;
        }

        client.sendTCP(verifyEvent);
        System.out.println(player.getName() + "`s Verify event wurde gesendet!");

    }
    
    public void registerAccount(EventPlayer eventPlayer, String password) {
    	
    	Document account = new Document("_id", eventPlayer.getName())
				.append("mcname", eventPlayer.getName())
				.append("mcuuid", eventPlayer.getUuid())
				.append("password", password);

		Main.mongoDatabase.getCollection("VerifyAccounts").insertOne(account);
    }
    
    public void registerAccount(Account account) {
    	
    	Document acc = new Document("_id", account.getUsername())
				.append("mcname", account.getUsername())
				.append("mcuuid", account.getUuid())
				.append("password", account.getPassword());
		Main.mongoDatabase.getCollection("VerifyAccounts").insertOne(acc);
    }
    
    
    public Document getAccountDocument(String username) {
    	if(isAccountExist(username)) {
			Bson filter = Filters.eq("_id", username);
			Document document = (Document) Main.mongoDatabase.getCollection("VerifyAccounts").find(filter).first();
			return document == null ? null : document;
    	}
		return null;
    }
    
    public Account getAccount(String username) {
    	if(isAccountExist(username)) {
    		Document accDoc = getAccountDocument(username);

    		Account account = new Account();
    		account.setUsername(accDoc.getString("mcname"));
    		account.setUuid(accDoc.getString("mcuuid"));
    		account.setPassword(accDoc.getString("password"));
    		
    		return account;
    	}
		return null;
    }
    
    public boolean isAccountExist(String username) {
    	//return mongodbManager.getDatabaseUtil().documentExists("_id", username);

		Bson filter = Filters.eq("_id", username);
		Document document = (Document)Main.mongoDatabase.getCollection("VerifyAccounts").find(filter).first();
		return document != null;
    }
    
    public boolean isLoginValid(String username, String password) {


    	if(isAccountExist(username)) {
    		String pass = getAccountDocument(username).getString("password");
    		
    		if(password.equals(pass)) {
    			return true;
    		} else {
    			return false;
    		}
    	} else {
    		return false;
    	}
    }
    

}
